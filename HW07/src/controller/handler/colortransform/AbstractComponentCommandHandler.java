package controller.handler.colortransform;

import controller.handler.AbstractCommandHandler;
import model.image.operation.ImageOperation;

/**
 * A superclass for all the command handlers that pertain to grayscale-based commands.
 *
 * <p>A grayscale-based filter sets all the channels in a pixel to the same value, which cane be
 * received in multiple ways. This transformation is applied to every single pixel in an image.
 *
 * <p>Currently supports the following commands:
 * <ul>
 *   <li>red-component</li>
 *   <li>green-component</li>
 *   <li>blue-component</li>
 *   <li>luma-component</li>
 *   <li>value-component</li>
 *   <li>intensity-component</li>
 * </ul>
 *
 * <p>Note that, however, this class is a command handler, and thus it actually will not
 * directly perform the operations on the images. Instead, it will parse the appropriate command
 * and delegate the actual transformation to the appropriate instance of {@link ImageOperation}.
 */
public abstract class AbstractComponentCommandHandler extends AbstractCommandHandler {

}
