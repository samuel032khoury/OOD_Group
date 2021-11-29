import static org.junit.Assert.assertEquals;

import controller.arguments.ImageProcessorArgument;
import controller.arguments.ImageProcessorArgumentType;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * All the tests for {@link ImageProcessorMain}.
 */
public class ImageProcessorMainTest {

  @Test
  public void itShouldAcceptInputAndOutputAsFlags() {
    List<String> args = List.of(
        "--redirectInput", "test-script.txt",
        "--redirectOutput", "test-output.txt"
    );
    Map<ImageProcessorArgumentType, List<String>> parsedArgs = ImageProcessorMain.parseArgs(args);
    Map<ImageProcessorArgumentType, List<String>> expectedArgs = Map.of(
        ImageProcessorArgumentType.REDIRECT_INPUT, List.of("test-script.txt"),
        ImageProcessorArgumentType.REDIRECT_OUTPUT, List.of("test-output.txt")
    );
    assertEquals(expectedArgs, parsedArgs);
  }

  @Test
  public void itShouldAcceptOnlyInputFlag() {
    List<String> args = List.of("--redirectInput", "test-script.txt");
    Map<ImageProcessorArgumentType, List<String>> parsedArgs = ImageProcessorMain.parseArgs(args);
    Map<ImageProcessorArgumentType, List<String>> expectedArgs = Map.of(
        ImageProcessorArgumentType.REDIRECT_INPUT, List.of("test-script.txt")
    );
    assertEquals(expectedArgs, parsedArgs);
  }

  @Test
  public void itShouldAcceptOnlyOutputFlag() {
    List<String> args = List.of("--redirectOutput", "test-output.txt");
    Map<ImageProcessorArgumentType, List<String>> parsedArgs = ImageProcessorMain.parseArgs(args);
    Map<ImageProcessorArgumentType, List<String>> expectedArgs = Map.of(
        ImageProcessorArgumentType.REDIRECT_OUTPUT, List.of("test-output.txt")
    );
    assertEquals(expectedArgs, parsedArgs);
  }

  @Test
  public void itShouldAcceptNoFlags() {
    List<String> args = List.of();
    Map<ImageProcessorArgumentType, List<String>> parsedArgs = ImageProcessorMain.parseArgs(args);
    Map<ImageProcessorArgumentType, List<String>> expectedArgs = Map.of();
    assertEquals(expectedArgs, parsedArgs);
  }

  @Test
  public void itShouldIgnoreUnsupportedFlags() {
    List<String> args = List.of("--weirdFlag", "noo", "why");
    Map<ImageProcessorArgumentType, List<String>> parsedArgs = ImageProcessorMain.parseArgs(args);
    Map<ImageProcessorArgumentType, List<String>> expectedArgs = Map.of();
    assertEquals(expectedArgs, parsedArgs);
  }

  @Test
  public void itShouldLoadInputAndOutputFilesWhenTextMode() {
    StringBuilder log = new StringBuilder();

    ImageProcessorArgument<Readable> mockInput = new ImageProcessorArgument<>() {
      @Override
      public ImageProcessorArgumentType getFlag() {
        return ImageProcessorArgumentType.REDIRECT_INPUT;
      }

      @Override
      public Readable convertArguments(List<String> additionalArguments) {
        log.append("Converting arguments for input");
        return new StringReader("q\n");
      }
    };

    ImageProcessorArgument<Appendable> mockOutput = new ImageProcessorArgument<>() {
      @Override
      public ImageProcessorArgumentType getFlag() {
        return ImageProcessorArgumentType.REDIRECT_OUTPUT;
      }

      @Override
      public Appendable convertArguments(List<String> additionalArguments) {
        log.append("Converting arguments for output");
        return new StringBuilder();
      }
    };

    ImageProcessorMain.main(
        List.of("--redirectInput", "sample-input.txt", "--redirectOutput", "sample-output.txt"),
        Map.of(
            ImageProcessorArgumentType.REDIRECT_INPUT, mockInput,
            ImageProcessorArgumentType.REDIRECT_OUTPUT, mockOutput
        )
    );

    assertEquals(
        "Converting arguments for inputConverting arguments for output",
        log.toString()
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void shouldRunWithNoListOfArguments() {
    // it won't be able to find the file, so it will throw an exception,
    // but it will still run the function correctly
    ImageProcessorMain.main(
        new String[]{"--redirectInput", "sample-input.txt", "--redirectOutput", "sample-output.txt"}
    );
  }

  @Test(expected = IllegalArgumentException.class)
  public void validFlagNotEnoughArguments() {
    List<String> args = List.of("--redirectOutput");
    Map<ImageProcessorArgumentType, List<String>> parsedArgs = ImageProcessorMain.parseArgs(args);
  }
}
