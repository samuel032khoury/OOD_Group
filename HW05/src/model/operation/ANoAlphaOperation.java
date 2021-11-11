package model.operation;

import java.awt.Color;

/**
 * To represent all {@link RegularImageOperation} that does not change the alpha value.
 * Of an image in the method of {@link #process(Color[][])};
 */
public abstract class ANoAlphaOperation extends RegularImageOperation {

  /**
   * To determine if a {@link RegularImageOperation} is an alpha related operation.
   *
   * @return false all the time as all the extending class should not process alpha channel value.
   */
  protected final boolean alphaRelated() {
    return false;
  }
}
