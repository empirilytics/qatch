package com.empirilytics.qatch.calibration.io;

import com.empirilytics.qatch.core.model.WeightedObject;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

/**
 * Class to export Tqi Data
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
public class TqiExporter extends AbstractExporter {

  /** Constructs a new TqiExporter */
  public TqiExporter() {
    super("tqi");
  }

  /** {@inheritDoc} */
  @Override
  protected void processContent(@NotNull Object obj, @NotNull Element root) {
    Element weights = createWeightData(obj);
    root.addContent(weights);
  }

  /** {@inheritDoc} */
  @Override
  protected Element createAttributeData(@NotNull Object obj) {
    return null;
  }
}
