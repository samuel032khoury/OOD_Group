package controller.handler;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import model.image.Image;

/**
 * This interface defines an arbitrary image process command handler. The goal of every handler is
 * to process a given command and delegate the execution to the appropriate implementation of {@link
 * model.image.operation.ImageOperation}.
 */
public interface ImageProcessCommandHandler {

  /**
   * Processes the given command for this handler.
   *
   * <p>As the current state of the application is maintained by {@link
   * controller.ImageProcessorController}, every handle should not be able to directly access the
   * currently cached images from the application state, as the command handler should be oblivious
   * to the internal implementations of the implementation state. Finding a specific image in a
   * state manager and passing it to a handler makes assumptions about handlers, and thus is not a
   * good design approach. Finally, passing all images leaves the application state vulnerable and
   * could potentially lead to unpredicted behaviour.
   *
   * <p>Thus, we chose to go with the callable implementation: every command handler receives two
   * callable functions: getImage and addImage, which the handler may (or may not) call upon to get
   * and to write a specific image from/to the current application state.
   *
   * @param args     The arguments to the handler, should not include the command name (typically
   *                 the first argument)
   * @param getImage A function to get an {@link Image} from a given name.
   * @param addImage A bi-function to add an {@link Image} with a given name.
   * @throws IllegalArgumentException if there are not enough arguments or arguments are otherwise
   *                                  invalid
   */
  void process(List<String> args, Function<String, Image> getImage,
      BiConsumer<String, Image> addImage)
      throws IllegalArgumentException;

  /**
   * The command name that corresponds to this handler.
   *
   * @return the command name for this handler
   */
  String getCommandName();

  /**
   * Return the help text for this handler, including expected arguments in the format
   * "[command-name] [arg1] [arg2] ...".
   *
   * @return a sample command structure
   */
  String getHelpText();
}
