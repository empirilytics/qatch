package com.empirilytics.qatch.calibration.io;

import com.empirilytics.qatch.core.model.Finding;
import com.empirilytics.qatch.core.model.Metric;
import com.empirilytics.qatch.core.model.Property;
import com.empirilytics.qatch.core.model.PropertySet;
import lombok.NonNull;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
public class PropertiesExporter extends AbstractExporter {

  /** Constructs a new PropertiesExporter */
  public PropertiesExporter() {
    super("properties");
  }

  /**
   * This method returns the DOM Tree representation of a set of properties
   * (i.e. PropertySet object) that receives as an argument.
   *
   * This method is used for the contraction of the Quality Model XML file,
   * together with the DOM Tree representation of the Characteristics of
   * the model.
   *
   * @param obj : The properties that should be turned into DOM Tree
   * 				       representation
   * @return           : An Element object that correspond to the DOM Tree
   * 					   representation of this PropertySet object.
   *
   */
  /** {@inheritDoc} */
  @Override
  protected Element createJDOMRepresentation(@NonNull Object obj){
    if (!(obj instanceof PropertySet properties))
      return null;

    //Create an empty "root" element
    Element rootProp = new Element("properties");
    rootProp.setName("properties");



    //Return the "root" element of the properties
    return rootProp;
  }

  /** {@inheritDoc} */
  @Override
  protected void processContent(@NotNull Object obj, @NotNull Element rootProp) {
    if (!(obj instanceof PropertySet properties))
      return;

    //Iterate through the properties of this PropertySet
    Iterator<Property> iterator = properties.iterator();
    while(iterator.hasNext()){

      //Get the current Property
      Property property = iterator.next();

      Element prop = createAttributeData(property);

      //TODO: Check how to get rid of this if statements - IDEA: Initialize these fields at value ""
      if (!property.isFinding()) {
        Metric m = (Metric) property.getMeasure();
        prop.setAttribute("metricName", m.getMetricName());
      }
      else {
        Finding f = (Finding) property.getMeasure();
        prop.setAttribute("ruleset", f.getRulesetPath());
      }
      prop.setAttribute("tool", property.getMeasure().getTool());
      // Save the thresholds as well (ascending order)
      // Create a node (element) containing the thresholds of the property
      Element thresholds = new Element("thresholds");
      for(int i = 0; i < property.thresholdsSize(); i++) {
        //Create a threshold Element
        Element t = new Element("threshold");

        //Set the appropriate value of the threshold
        t.setText(String.valueOf(property.getThreshold(i)));

        //Attach the current threshold element to the element named "thresholds"
        thresholds.addContent(t);
      }

      //Attach the "thresholds" sub element to the "property" element
      prop.addContent(thresholds);

      //Attach the "property" element to the "properties" parent element
      rootProp.addContent(prop);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected Element createAttributeData(@NotNull Object obj) {
    if (!(obj instanceof Property property))
      return null;

    //Create a new Element representing this property
    Element prop = new Element("property");

    //Add the appropriate attributes
    prop.setAttribute("name", property.getName());
    prop.setAttribute("type", property.getMeasure().getClass().getSimpleName().toLowerCase());
    prop.setAttribute("description", property.getDescription());
    prop.setAttribute("positive_impact", String.valueOf(property.isPositive()));

    return prop;
  }
}