package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import controller.handler.ImageProcessCommandHandler;
import controller.handler.LoadCommandHandler;
import controller.handler.SaveCommandHandler;
import controller.transfer.CommonImageTransferer;
import controller.transfer.ImageTransferType;
import controller.transfer.ImageTransferer;
import controller.transfer.PpmImageTransferer;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import view.ImageProcessorCLI;

/**
 * Contains all the test that pertain to the {@link view.ImageProcessorCLI}.
 */
public class ImageProcessorCLITest {

  private static final List<ImageProcessCommandHandler> SAMPLE_HANDLERS = List.of(
      new SaveCommandHandler(), new LoadCommandHandler()
  );
  private static final Map<ImageTransferType, ImageTransferer> SAMPLE_TRANSFERERS = Map.of(
      ImageTransferType.PPM, new PpmImageTransferer(),
      ImageTransferType.COMMON, new CommonImageTransferer()
  );

  /**
   * Gets a new mock CLI for testing purposes.
   *
   * @param input  the input stream
   * @param output the output stream
   * @return the mock cli
   */
  private static ImageProcessorCLI getNewSampleCli(Readable input, Appendable output) {
    return new ImageProcessorCLI(SAMPLE_HANDLERS, SAMPLE_TRANSFERERS, input, output);
  }

  @Test
  public void testConstructor() {
    ImageProcessorCLI cliWithCustomInputOuput = getNewSampleCli(
        new StringReader(""), new StringBuilder()
    );
    assertNotNull(cliWithCustomInputOuput);

    ImageProcessorCLI cliWithDefaultInputOutput = new ImageProcessorCLI(List.of(), Map.of());
    assertNotNull(cliWithDefaultInputOutput);

    ImageProcessorCLI cliWithNullInput = getNewSampleCli(null, new StringBuilder());
    assertNotNull(cliWithNullInput);

    ImageProcessorCLI cliWithNullOutput = getNewSampleCli(new StringReader(""), null);
    assertNotNull(cliWithNullOutput);
  }

  @Test
  public void itShouldPrintHelpMessage() {
    StringBuilder output = new StringBuilder();
    ImageProcessorCLI cli = getNewSampleCli(new StringReader("q\n"), output);

    String expectedWelcomeAndHelp = "Welcome to our image processor tool!\n"
        + "\n"
        + "Supported Commands:\n"
        + "- load: load [image-path] [image-name]\n"
        + "- save: save [image-path] [image-name]\n"
        + "\n"
        + "Supported File Types:\n"
        + "- BMP\n"
        + "- GIF\n"
        + "- JPEG\n"
        + "- JPG\n"
        + "- PNG\n"
        + "- TIF\n"
        + "- TIFF\n"
        + "- WBMP\n"
        + "- PPM";

    cli.run();
    assertTrue(output.toString().startsWith(expectedWelcomeAndHelp));
  }

  @Test
  public void itShouldPromptCommandAndPrintErrorWhenNotSupported() {
    StringBuilder output = new StringBuilder();
    ImageProcessorCLI cli = getNewSampleCli(new StringReader("blah\nq\n"), output);

    String expectedError = "Failed to handle [blah]: ";
    String expectedCommandPrompt = "Enter a command followed by 'enter'...";

    cli.run();
    assertTrue(output.toString().contains(expectedCommandPrompt + "\n" + expectedError));
  }

  @Test
  public void itShouldDisplayHelpMessageAtHelpString() {
    StringBuilder output = new StringBuilder();
    ImageProcessorCLI cli = getNewSampleCli(new StringReader("help\nh\nquit"), output);

    cli.run();
    assertEquals("Welcome to our image processor tool!\n"
        + "\n"
        + "Supported Commands:\n"
        + "- load: load [image-path] [image-name]\n"
        + "- save: save [image-path] [image-name]\n"
        + "\n"
        + "Supported File Types:\n"
        + "- BMP\n"
        + "- GIF\n"
        + "- JPEG\n"
        + "- JPG\n"
        + "- PNG\n"
        + "- TIF\n"
        + "- TIFF\n"
        + "- WBMP\n"
        + "- PPM\n"
        + "\n"
        + "Enter a command followed by 'enter'...\n"
        + "Supported Commands:\n"
        + "- load: load [image-path] [image-name]\n"
        + "- save: save [image-path] [image-name]\n"
        + "\n"
        + "Supported File Types:\n"
        + "- BMP\n"
        + "- GIF\n"
        + "- JPEG\n"
        + "- JPG\n"
        + "- PNG\n"
        + "- TIF\n"
        + "- TIFF\n"
        + "- WBMP\n"
        + "- PPM\n"
        + "\n"
        + "Enter a command followed by 'enter'...\n"
        + "Supported Commands:\n"
        + "- load: load [image-path] [image-name]\n"
        + "- save: save [image-path] [image-name]\n"
        + "\n"
        + "Supported File Types:\n"
        + "- BMP\n"
        + "- GIF\n"
        + "- JPEG\n"
        + "- JPG\n"
        + "- PNG\n"
        + "- TIF\n"
        + "- TIFF\n"
        + "- WBMP\n"
        + "- PPM\n"
        + "\n"
        + "Enter a command followed by 'enter'...\n", output.toString());
  }

  @Test
  public void itShouldDelegateCommandsToControllerAndPrintHelpWhenCommandUnsupported() {
    Appendable controllerOutput = new StringBuilder();

    ImageProcessorController testController = command -> {
      if (command.get(0).equalsIgnoreCase("test-throw")) {
        throw new IllegalArgumentException("testing throw");
      }

      try {
        controllerOutput.append("Handling command: ").append(command.toString()).append("\n");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    };

    Appendable cliOutput = new StringBuilder();
    String testScript = "load this-image that-image\ntest-throw\nsave that-image this-image-2\nq\n";

    ImageProcessorCLI testCli = new ImageProcessorCLI(
        SAMPLE_HANDLERS,
        SAMPLE_TRANSFERERS,
        new StringReader(testScript),
        cliOutput,
        testController
    );

    testCli.run();

    String expectedControllerOutput =
        "Handling command: [load, this-image, that-image]\n"
            + "Handling command: [save, that-image, this-image-2]\n";
    assertEquals(expectedControllerOutput, controllerOutput.toString());

    String expectedCliOutput = "Welcome to our image processor tool!\n"
        + "\n"
        + "Supported Commands:\n"
        + "- load: load [image-path] [image-name]\n"
        + "- save: save [image-path] [image-name]\n"
        + "\n"
        + "Supported File Types:\n"
        + "- BMP\n"
        + "- GIF\n"
        + "- JPEG\n"
        + "- JPG\n"
        + "- PNG\n"
        + "- TIF\n"
        + "- TIFF\n"
        + "- WBMP\n"
        + "- PPM\n"
        + "\n"
        + "Enter a command followed by 'enter'...\n"
        + "Enter a command followed by 'enter'...\n"
        + "Failed to handle [test-throw]: testing throw\n"
        + "Supported Commands:\n"
        + "- load: load [image-path] [image-name]\n"
        + "- save: save [image-path] [image-name]\n"
        + "\n"
        + "Supported File Types:\n"
        + "- BMP\n"
        + "- GIF\n"
        + "- JPEG\n"
        + "- JPG\n"
        + "- PNG\n"
        + "- TIF\n"
        + "- TIFF\n"
        + "- WBMP\n"
        + "- PPM\n"
        + "\n"
        + "Enter a command followed by 'enter'...\n"
        + "Enter a command followed by 'enter'...\n";
    assertEquals(expectedCliOutput, cliOutput.toString());
  }

  @Test(expected = RuntimeException.class)
  public void throwsExceptionWhenWriteError() {
    // A mock appendable that always throws an error at write.
    class MockAppendable implements Appendable {

      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException();
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException();
      }
    }

    ImageProcessorCLI cli = getNewSampleCli(new StringReader("quit"), new MockAppendable());
    cli.run();
  }
}