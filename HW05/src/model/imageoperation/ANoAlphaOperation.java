package model.imageoperation;

public abstract class ANoAlphaOperation extends RegularImageOperation {

  protected final boolean alphaRelated() {
    return false;
  }
}
