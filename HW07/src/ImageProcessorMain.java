import controller.arguments.ImageProcessorArgument;
import controller.arguments.ImageProcessorArgumentType;
import controller.arguments.OutputRedirectArgument;
import controller.arguments.ScriptFileArgument;
import controller.arguments.TextArgument;
import controller.handler.colortransform.MosaicCommandHandler;
import view.ImageProcessorView;
import view.ImageProcessorGui;
import view.ImageProcessorCLI;
import controller.handler.ImageProcessCommandHandler;
import controller.handler.LoadCommandHandler;
import controller.handler.SaveCommandHandler;
import controller.handler.colortransform.BrightenCommandHandler;
import controller.handler.colortransform.ValueComponentCommandHandler;
import controller.handler.colortransform.linear.SepiaCommandHandler;
import controller.handler.colortransform.linear.grayscale.BlueComponentCommandHandler;
import controller.handler.colortransform.linear.grayscale.GreenComponentCommandHandler;
import controller.handler.colortransform.linear.grayscale.IntensityComponentCommandHandler;
import controller.handler.colortransform.linear.grayscale.LumaComponentCommandHandler;
import controller.handler.colortransform.linear.grayscale.RedComponentCommandHandler;
import controller.handler.colortransform.ranged.BlurCommandHandler;
import controller.handler.colortransform.ranged.SharpenCommandHandler;
import controller.handler.geometric.HorizontalFlipCommandHandler;
import controller.handler.geometric.VerticalFlipCommandHandler;
import controller.transfer.CommonImageTransferer;
import controller.transfer.ImageTransferType;
import controller.transfer.ImageTransferer;
import controller.transfer.PpmImageTransferer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 * Serves as the point-of-entry for the image processor application. The image processor is a simple
 * application that allows a user to interact and modify various types of images. Some of the
 * operations that could be performed on an image are filters, geometric transformations,
 * grayscaling, and more.
 */
public class ImageProcessorMain {

  private static final List<ImageProcessCommandHandler> SUPPORTED_HANDLERS = List.of(
      new LoadCommandHandler(),
      new SaveCommandHandler(),
      new RedComponentCommandHandler(),
      new BlueComponentCommandHandler(),
      new GreenComponentCommandHandler(),
      new ValueComponentCommandHandler(),
      new IntensityComponentCommandHandler(),
      new LumaComponentCommandHandler(),
      new BrightenCommandHandler(),
      new HorizontalFlipCommandHandler(),
      new VerticalFlipCommandHandler(),
      new SepiaCommandHandler(),
      new SharpenCommandHandler(),
      new BlurCommandHandler(),
      new MosaicCommandHandler()
  );

  private static final Map<ImageTransferType, ImageTransferer> SUPPORTED_TYPES = Map.of(
      ImageTransferType.PPM, new PpmImageTransferer(),
      ImageTransferType.COMMON, new CommonImageTransferer()
  );

  private static final Map<ImageProcessorArgumentType, ImageProcessorArgument<?>>
      SUPPORTED_ARGUMENTS =
      Map.of(
          ImageProcessorArgumentType.TEXT, new TextArgument(),
          ImageProcessorArgumentType.SCRIPT_FILE, new ScriptFileArgument(),
          ImageProcessorArgumentType.REDIRECT_OUTPUT, new OutputRedirectArgument()
      );

  /**
   * Runs the image processor.
   *
   * @param args running configuration. Supports the following flags:
   *             <ul>
   *             <li>-file [inputFilePath] redirects input to the given file</li>
   *             <li>--redirectOutput [outputFilePath] redirects output to the given file</li>
   *             <li>-text opens the program in text mode</li>
   *             </ul>
   */
  public static void main(String[] args) {
    main(Arrays.asList(args), SUPPORTED_ARGUMENTS);
  }

  /**
   * Convenience overload for making testing supported arguments easier.
   *
   * @param args               The arguments to the main method
   * @param supportedArguments The supported arguments for this processor
   */
  static void main(
      List<String> args,
      Map<ImageProcessorArgumentType, ImageProcessorArgument<?>> supportedArguments
  ) {
    // Scan args for supported flags
    Map<ImageProcessorArgumentType, List<String>> argumentNamesToValues = parseArgs(args);
    ImageProcessorView view = chooseViewFromArguments(argumentNamesToValues, supportedArguments);
    view.run();
  }

  // Chooses the view form the program arguments
  private static ImageProcessorView chooseViewFromArguments(
      Map<ImageProcessorArgumentType, List<String>> args,
      Map<ImageProcessorArgumentType, ImageProcessorArgument<?>> supportedArgs
  ) {
    // Parse optional input and output
    Optional<Readable> maybeInput = Optional
        .ofNullable(args.get(ImageProcessorArgumentType.REDIRECT_INPUT))
        .map(
            additionalArgs ->
                supportedArgs
                    .get(ImageProcessorArgumentType.REDIRECT_INPUT)
                    .convertArguments(additionalArgs)
        ).map(val -> (Readable) val)
        .or(() ->
            Optional
                .ofNullable(args.get(ImageProcessorArgumentType.SCRIPT_FILE))
                .map(
                    additionalArgs ->
                        supportedArgs
                            .get(ImageProcessorArgumentType.SCRIPT_FILE)
                            .convertArguments(additionalArgs)
                )
                .map(val -> (Readable) val)
        );

    Optional<Appendable> maybeOutput = Optional
        .ofNullable(args.get(ImageProcessorArgumentType.REDIRECT_OUTPUT))
        .map(
            additionalArgs ->
                supportedArgs
                    .get(ImageProcessorArgumentType.REDIRECT_OUTPUT)
                    .convertArguments(additionalArgs)
        )
        .map(val -> (Appendable) val);

    if (args.containsKey(ImageProcessorArgumentType.TEXT)) {
      // If it's in text mode, use the CLI view
      return new ImageProcessorCLI(
          SUPPORTED_HANDLERS,
          SUPPORTED_TYPES,
          maybeInput.orElse(null),
          maybeOutput.orElse(null)
      );
    } else if (maybeInput.isPresent()) {
      // If there's an input script file, use the CLI view with that input
      return new ImageProcessorCLI(
          SUPPORTED_HANDLERS,
          SUPPORTED_TYPES,
          maybeInput.get(),
          maybeOutput.orElse(null)
      );
    } else {
      // Use the GUI view
      return new ImageProcessorGui(SUPPORTED_HANDLERS, SUPPORTED_TYPES);
    }
  }

  /**
   * Parses all the arguments provided to this CLI.
   *
   * @param args The arguments passed to the CLI
   * @return A map of argument name to argument value.
   * @throws java.lang.IllegalArgumentException If an argument is not followed by the proper number
   *                                            of additional arguments.
   */
  static Map<ImageProcessorArgumentType, List<String>> parseArgs(List<String> args)
      throws IllegalArgumentException {
    Map<ImageProcessorArgumentType, List<String>> argMap = new HashMap<>();

    int i = 0;
    while (i < args.size()) {
      Optional<ImageProcessorArgumentType> maybeArgType =
          ImageProcessorArgumentType.fromArg(args.get(i));

      if (maybeArgType.isPresent()) {
        String flag = maybeArgType.get().getFlagName();
        int argCount = maybeArgType.get().getExpectedArguments();

        if (args.size() < i + 1 + argCount) {
          throw new IllegalArgumentException(String.format(
              "Failed to parse command line arguments, "
                  + "expected '%s' to be followed by %d additional arguments",
              flag,
              argCount
          ));
        }

        argMap.put(maybeArgType.get(), args.subList(i + 1, i + 1 + argCount));
        i += argCount;
      }

      i++;
    }

    return argMap;
  }
}
