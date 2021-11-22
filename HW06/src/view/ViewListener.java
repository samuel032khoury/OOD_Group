package view;

public interface ViewListener {
  void loadEvent();
  void saveEvent();
  void adjustmentBrightnessEvent();
  void quitEvent();
  void sizeEvent();
  void blurEvent();
  void sharpenEvent();
  void sepiaEvent();
  void bComponentEvent();
  void rComponentEvent();
  void gComponentEvent();
  void iComponentEvent();
  void vFlipEvent();
  void hFlipEvent();
}