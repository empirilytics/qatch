package com.empirilytics.qatch.cli;

import lombok.extern.log4j.Log4j2;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Ansi.Style;
import picocli.CommandLine.Help.ColorScheme;
import picocli.CommandLine.Option;

/**
 * Main entry point class for the Qatch Evaluation System
 *
 * @author Isaac Griffith
 * @version 2.0.0
 */
@Log4j2
public class Qatch {

  private static final String ANSI_CYAN = "\u001B[36m";
  private static final String ANSI_BLUE = "\u001B[34m";
  private static final String ANSI_YELLOW = "\u001B[33m";
  private static final String ANSI_RESET = "\u001B[0m";

  /**
   * The entry point method from the command line
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    QatchContext context = new QatchContext();
    CommandLine cmdLine = new CommandLine(context);
    context.setCommandLine(cmdLine);
    cmdLine.execute(args);

    if (context.isHelpRequested() || context.isVersionRequested()) {
      if (context.isHelpRequested())
        context.displayHelp("");
      if (context.isVersionRequested())
        cmdLine.printVersionHelp(System.out);
      System.exit(0);
    }

    Qatch qatch = new Qatch();
    qatch.run(context);
  }

  public void run(QatchContext context) {
    System.out.println(ANSI_BLUE + "                       ____        __       __" + ANSI_RESET + "\n" +
            ANSI_BLUE + "                      / __ \\____ _/ /______/ /_" + ANSI_RESET + "\n" +
            ANSI_BLUE + "                     / / / / __ `/ __/ ___/ __ \\" + ANSI_RESET + "\n" +
            ANSI_BLUE + "                    / /_/ / /_/ / /_/ /__/ / / /" + ANSI_RESET + "\n" +
            ANSI_BLUE + "                    \\___\\_\\__,_/\\__/\\___/_/ /_/" + ANSI_RESET + "\n");
    System.out.println(ANSI_YELLOW + "                   http://empirilytics.com/qatch" + ANSI_RESET + "\n\n");

    try {
      Runner runner;
      if (context.isCalibration() || context.isWeightsElicitation()) {
        runner = new BenchmarkRunner(context);
      } else {
        if (context.isMultiProject()) {
          runner = new MultiProjectRunner(context);
        } else {
          runner = new SingleProjectRunner(context);
        }
      }
      runner.run();
    } catch (Exception e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
  }
}
