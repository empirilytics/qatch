package com.empirilytics.qatch.calibration.io;

import com.empirilytics.qatch.core.model.Characteristic;
import com.empirilytics.qatch.core.model.CharacteristicSet;
import com.empirilytics.qatch.core.model.WeightedObject;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Iterator;

/**
 * A class to export characteristics data
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
public class CharacteristicsExporter extends AbstractExporter {

  /** Constructs a new CharacteristicsExporter */
  public CharacteristicsExporter() {
    super("characteristics");
  }

  /** {@inheritDoc} */
  @Override
  protected void processContent(@NotNull Object obj, @NotNull Element root) {
    if (!(obj instanceof CharacteristicSet characteristics))
      return;

    //Iterate through the characteristics of this CharacteristicSet
    Iterator<Characteristic> iterator = characteristics.iterator();
    while(iterator.hasNext()){

      //Get the current Characteristic object
      Characteristic characteristic = iterator.next();

      //Create a node (element) containing the weights of the characteristic
      Element weights = createWeightData(characteristic);

      //Attach the "weights" sub element to the "characteristic" element
      Element charNode = createAttributeData(characteristic);
      if (charNode != null) {
        charNode.addContent(weights);

        //Attach the "characteristic" element to the "characteristics" parent element
        root.addContent(charNode);
      }
    }
  }

  /** {@inheritDoc} */
  protected Element createAttributeData(@NotNull Object obj) {
    if (!(obj instanceof Characteristic characteristic)) {
        return null;
    }
    //Create a new Element representing this characteristic
    Element charNode = new Element("characteristic");

    //Add the appropriate attributes
    charNode.setAttribute("name", characteristic.getName());
    charNode.setAttribute("standard", characteristic.getStandard());
    charNode.setAttribute("description", characteristic.getDescription());

    return charNode;
  }
}
