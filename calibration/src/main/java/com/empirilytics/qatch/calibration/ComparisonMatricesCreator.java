package com.empirilytics.qatch.calibration;

import com.empirilytics.qatch.core.model.Characteristic;
import com.empirilytics.qatch.core.model.CharacteristicSet;
import com.empirilytics.qatch.core.model.Property;
import com.empirilytics.qatch.core.model.PropertySet;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * This class is responsible for the creation of the comparison matrices that R needs for the weight
 * elicitation.
 *
 * <p>TODO: Create an alternative class that completes the whole comparison matrix with dashes if
 * the user selects the fuzzy approach>
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class ComparisonMatricesCreator {

  // The predefined paths where the comparison matrices will be created
  public static String BASE_DIR =
      new File(System.getProperty("user.dir")).getAbsolutePath(); // TODO remove this
  public static String COMP_MATRICES =
      new File(BASE_DIR + "/Comparison_Matrices").getAbsolutePath(); // TODO remove this

  // The default value of the unused cells of the comparison matrix.
  public static String value;

  /**
   * The method that creates the appropriate comparison matrices for the quality model based on the
   * characteristics and the properties of the desired Quality Model
   *
   * @param characteristics The characteristics used, cannot be null
   * @param properties The properties to weight, cannot be null
   * @param fuzzy indicates that this will use Fuzzy AHP
   */
  public static void generateCompMatrices(
      @NonNull CharacteristicSet characteristics, @NonNull PropertySet properties, boolean fuzzy) {

    // Check if the folder exists. If not, create it.
    File dir = new File(COMP_MATRICES);
    if (!dir.exists() || !dir.isDirectory()) {
      dir.mkdir();
    }

    // Choose the default value of the matrix cells
    if (fuzzy) {
      value = "-";
    } else {
      value = "0";
    }

    // Generate Comparison Matrix for the TQI
    generateTQICompMatrix(characteristics);

    // Generate Comparison Matrices for the Characteristics of the quality model
    generateCharacteristicsCompMatricies(characteristics, properties);
  }

  /**
   * This method is responsible for the generation of the comparison matrix that is needed for the
   * elicitation of the TQI's weights.
   *
   * @param characteristics The Characteristics, cannot be null
   */
  public static void generateTQICompMatrix(@NonNull CharacteristicSet characteristics) {

    // Create a new workbook for the matrix
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("TQI Comparison Matrix");
    HSSFRow rowhead = sheet.createRow((short) 0);

    // Set the "characteristic" that this Comparison Matrix refers to
    rowhead.createCell(0).setCellValue("TQI");

    // Create the header of the xls file
    for (int i = 0; i < characteristics.size(); i++) {
      rowhead.createCell(i + 1).setCellValue(characteristics.get(i).getName());
    }

    Characteristic characteristic = new Characteristic();

    // Fill the columns appropriately
    for (int i = 0; i < characteristics.size(); i++) {

      // Get the current Characteristic
      characteristic = characteristics.get(i);

      // Create a new row for this characteristic and set its name
      HSSFRow row = sheet.createRow((short) i + 1);
      row.createCell(0).setCellValue(characteristic.getName());

      // Fulfill the unused cells of the matrix with the predefined value
      for (int j = 0; j <= i; j++) {
        row.createCell(j + 1).setCellValue(value);
      }
    }

    // Set the name of the comparison matrix
    String filename = new File(COMP_MATRICES + "/TQI.xls").getAbsolutePath();

    // Export the XLS file to the appropriate path (R's working directory)
    FileOutputStream fileOut = null;
    try {
      fileOut = new FileOutputStream(filename);
      workbook.write(fileOut);
      fileOut.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      log.error(e.getMessage());
    }
  }

  /**
   * This method is responsible for the generation of the comparison matrices that are needed for
   * the elicitation of the weights of the quality model's characteristics.
   *
   * <p>Typically, we have one comparison matrix for each Quality Model's characteristic.
   *
   * @param characteristics Characteristics to generate for, cannot be null
   * @param properties Properties to generate for, cannot be null
   */
  public static void generateCharacteristicsCompMatricies(
      @NonNull CharacteristicSet characteristics, @NonNull PropertySet properties) {

    // For each characteristic do...
    Characteristic characteristic = new Characteristic();
    Iterator<Characteristic> iterator = characteristics.iterator();
    while (iterator.hasNext()) {

      // Get the current characteristic
      characteristic = iterator.next();

      // Create a new workbook for the matrix
      HSSFWorkbook workbook = new HSSFWorkbook();
      HSSFSheet sheet = workbook.createSheet(characteristic.getName() + " Comparison Matrix");
      HSSFRow rowhead = sheet.createRow((short) 0);

      // Set the "characteristic" that this Comparison Matrix refers to
      rowhead.createCell(0).setCellValue(characteristic.getName());

      // Create the header of the xls file
      for (int i = 0; i < properties.size(); i++) {
        rowhead.createCell(i + 1).setCellValue(properties.get(i).getName());
      }

      Property property = new Property();
      for (int i = 0; i < properties.size(); i++) {

        // Get the current Property
        property = properties.get(i);

        // Create a new row for this property and set its name
        HSSFRow row = sheet.createRow((short) i + 1);
        row.createCell(0).setCellValue(property.getName());

        // Fulfill the unused cells of the matrix with the predefined value
        for (int j = 0; j <= i; j++) {

          row.createCell(j + 1).setCellValue(value);
        }
      }

      // Set the name of the comparison matrix
      String filename =
          new File(COMP_MATRICES + "/" + characteristic.getName() + ".xls").getAbsolutePath();

      // Export the XLS file to the desired path
      FileOutputStream fileOut;
      try {
        fileOut = new FileOutputStream(filename);
        workbook.write(fileOut);
        fileOut.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        log.error(e.getMessage());
      }
    }
  }
}
