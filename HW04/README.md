# Image Process Assignment

## Model

#### Library

This program uses a (hash)map for its model representing an image library so that it allows multiple images loaded during a single run. Taking the benefits of the map sturcture, all images stored can be identified and retrived by a key (in this case, the image name user assigned). 

#### ImageFile

The key of the map is the name of an image as a String assigned by users. The value can be objects who directly or indirectly implements `ImageFile` interface. 

#### Operation

#### R/W Capbility

Both `ImageLibModel` and `imageFile` are designed in a way that can limit write capability to avoid malicious/mistaken mutation/overwritten, by making these two interface respectively implement read-only interfaces `ImageLibModelState` and `ReadOnlyImageFile`. As overwritten an existing image in the image-library is allowed by design, all current methods are also restricted to not mutate a  ImageFile itself, but only generate a new copy of a modified object. Given the ability of getting the information/status of a library/image, the model doesn't tell any details about the concrete implementation as all the methods return abstractions and can only use public methods.

## View

## Controller

## How to Run

---

#### Resource Image Citation

Image "elephant" citation: Photo by https://unsplash.com/photos/qiPTr8GmhM0

Image "painting" citation: Photo by https://unsplash.com/photos/JLfem8ViKVA

