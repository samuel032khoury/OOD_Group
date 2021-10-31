package controller;

import java.util.HashMap;
import java.util.function.Supplier;

public class LoadManager {
  HashMap<String, Supplier<ILoader>> availableLoaders;

  public LoadManager() {
    availableLoaders = new HashMap<String, Supplier<ILoader>>();
    availableLoaders.put("ppm", PPMLoader::new);
  }

  ILoader provideLoader(String suffix){
    ILoader loader = null;
    Supplier<ILoader> sup = this.availableLoaders.getOrDefault(suffix, null);
    if (sup != null) {
      loader = sup.get();
    }
    if (loader == null) {
      throw new UnsupportedOperationException("this file extension is not supported");
    }
    return loader;
  }
}
