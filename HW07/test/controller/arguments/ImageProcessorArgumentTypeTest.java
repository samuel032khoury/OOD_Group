package controller.arguments;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.Set;
import org.junit.Test;

/**
 * Tests the enum {@link controller.arguments.ImageProcessorArgumentType}.
 */
public class ImageProcessorArgumentTypeTest {

  @Test
  public void itShouldParseFromArgumentString() {
    // Input
    Set<String> inputFlags = Set.of(
        "--redirectInput", "-redirectInput", "redirectInput", "rEdIrEctInPut"
    );
    for (String flag : inputFlags) {
      assertEquals(
          Optional.of(ImageProcessorArgumentType.REDIRECT_INPUT),
          ImageProcessorArgumentType.fromArg(flag)
      );
    }

    // Output
    Set<String> outputFlags = Set.of(
        "--redirectOutput", "-redirectOutput", "redirectOutput", "rEdIrEctOUtPut"
    );
    for (String flag : outputFlags) {
      assertEquals(
          Optional.of(ImageProcessorArgumentType.REDIRECT_OUTPUT),
          ImageProcessorArgumentType.fromArg(flag)
      );
    }

    // Not found
    Set<String> notFoundFlags = Set.of("", "no", "debug", "-----redirectInput");
    for (String flag : notFoundFlags) {
      assertEquals(Optional.empty(), ImageProcessorArgumentType.fromArg(flag));
    }

  }
}
