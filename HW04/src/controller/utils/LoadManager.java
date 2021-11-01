package controller.utils;

public class LoadManager extends AManager<ILoader>{
  public LoadManager() {
    availableLoaders.put("ppm", PPMLoader::new);
  }
}