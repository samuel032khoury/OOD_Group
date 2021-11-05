package model.library;

import model.imagefile.ImageFile;

/**
 * To represent an Image Library that stored all the images has been loaded into the program.
 */
public interface ImageLibModel extends ImageLibState {
  /**
   * load an {@link ImageFile} with an identifiable name into the library.
   *
   * @param imageName the image name assigned to the {@link ImageFile}
   * @param imageFile a {@link ImageFile} being loaded
   */
  void loadImage(String imageName, ImageFile imageFile);

  /**
   * get a copiable {@link ImageFile} from the library by the provided identifiable image name.
   *
   * @param imageName an identifiable image name to search a particular {@link ImageFile} in the
   *                  image library.
   * @return a {@link ImageFile} linked to the provided {@code imageName} if such an image exists.
   * @throws IllegalStateException if the image cannot be found.
   */
  ImageFile get(String imageName) throws IllegalStateException;
}
