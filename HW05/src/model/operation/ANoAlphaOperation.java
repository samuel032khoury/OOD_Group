package model.operation;

public abstract class ANoAlphaOperation extends RegularImageOperation {

  protected final boolean alphaRelated() {
    return false;
  }
}
