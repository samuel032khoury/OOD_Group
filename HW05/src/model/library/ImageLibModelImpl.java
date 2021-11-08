package model.library;

import java.util.HashMap;
import java.util.Map;

import model.imagefile.ImageFile;
import model.imagefile.ImageFileImpl;
import model.imagefile.ReadOnlyImageFile;
import model.imageoperation.CopyImageOperation;

/**
 * To represent a concrete Image Library implementation using a Map mapping from String (image name)
 * to a {@link ImageFile}.
 */
public class ImageLibModelImpl implements ImageLibModel {
  private final Map<String, ImageFile> imageLib;

  /**
   * A simple constructor.
   */
  public ImageLibModelImpl() {
    this.imageLib = new HashMap<>();
  }

  /**
   * A constructor that can set the map object of the library.
   * @param map the map to be set initially.
   */
  public ImageLibModelImpl(HashMap<String, ImageFile> map) {
    if (map == null) {
      throw new IllegalArgumentException("map is null");
    }
    this.imageLib = map;
  }

  /**
   * load an {@link ImageFile} with an identifiable name into the {@link #imageLib}.
   *
   * @param imageName the image name assigned to the {@link ImageFile}
   * @param imageFile a {@link ImageFile} being loaded
   */
  @Override
  public void loadImage(String imageName, ImageFile imageFile){
    imageLib.put(imageName, imageFile);
  }

  /**
   * get a copiable {@link ImageFile} from the {@link #imageLib} by the provided identifiable image
   * name.
   *
   * @param imageName an identifiable image name to search a particular {@link ImageFile} in the
   *                  {@link #imageLib}.
   * @return a {@link ImageFile} linked to the provided {@code imageName} if such an image exists.
   * @throws IllegalStateException if the image cannot be found.
   */
  @Override
  public ImageFile get(String imageName) throws IllegalStateException {
    if (!this.imageLib.containsKey(imageName)) {
      throw new IllegalStateException("No such an image named " + imageName + " can be found!");
    }
    return imageLib.get(imageName).applyOperation(new CopyImageOperation());
  }

  /**
   * get the size of (the number of image loaded to) the {@link #imageLib}.
   *
   * @return the size of the {@link #imageLib}
   */
  @Override
  public int getLibSize() {
    return imageLib.size();
  }

  /**
   * get a {@link ReadOnlyImageFile} object stored in the {@link #imageLib} by the image name.
   *
   * @param imageName the image name of the {@link ReadOnlyImageFile} being looking for.
   * @return a {@link ReadOnlyImageFile} if the search succeed, null if there isn't such an image.
   */
  @Override
  public ReadOnlyImageFile peek(String imageName) {
    return imageLib.get(imageName);
  }
}
