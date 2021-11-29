package controller.handler.geometric;

import controller.handler.AbstractCommandHandler;

/**
 * The super-class for all the command handlers that pertain to geometric transformations on
 * images.
 *
 * <p>Any geometric transformations should not modify the intrinsic pixel value in any way;
 * instead, geometric transformations must only manipulate the positions of pixels in the image.
 *
 * <p>Currently supports:
 * <ul>
 *  <li>Horizontal flip transformation</li>
 *  <li>Vertical flip transformation</li>
 * </ul>
 */
public abstract class AbstractGeometricCommandHandler extends AbstractCommandHandler {

}
