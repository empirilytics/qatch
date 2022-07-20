package com.empirilytics.qatch.calibration;

/**
 * A class which prints a progress bar, this is a bad implementation, and should be removed
 *
 * @author Miltos, Isaac Griffith
 * @version 2.0.0
 */
public class ProgressDemo {

  /**
   * Updates the progress bar to the provided percentage
   *
   * @param progressPercentage New progress percentage
   */
  static void updateProgress(double progressPercentage) {
    final int width = 50; // progress bar width in chars

    System.out.print("\r*[");
    int i = 0;
    for (; i <= (int) (progressPercentage * width); i++) {
      System.out.print(".");
    }
    for (; i < width; i++) {
      System.out.print(" ");
    }
    System.out.print("]");
    System.out.print(" ");
    System.out.print((int) (progressPercentage * 100));
    System.out.print(" %");
  }
}
