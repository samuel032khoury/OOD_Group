# Image Process Assignment

## Model

#### Library

This program uses a (hash)map for its model, representing an image library that allows multiple images loaded during a single run. By taking the benefits of the map structure, all images stored can be identified and retrieved by a key (in this case, the image name user assigned). 

#### ImageFile

The key of the map are names of images as Strings assigned by users. The value of the map are objects whom directly or indirectly implement the `ImageFile` interface. 

#### Operation

Operation package contains an interface `IChannelOperator` for enum classes. Every enum member should be the name of a particular operation to convert a Color, by some defined rules, to another, which may have a modified RGB(A) value. Rules can either be implemented by creating a new concrete class that implements the `IChannelFunction` interface and overwrite the `apply` method or use lambda to create an anonymous subclass that implements this interface. Eventually, the name and the mechanism of the operation need to be matched up and put in the `channelOperations` map located in `AImageFile` or its concrete classes.

#### R/W Capbility

Both `ImageLibModel` and `ImageFile` are designed in a way that can limit the capability of performing malicious/mistaken mutation or overwritten. Specifically, we make these two interfaces implement the read-only interfaces `ImageLibModelState` and `ReadOnlyImageFile` that only have methods for inspecting information about models. As overwritten an existing image in the image library is allowed by design, all current methods for `ImageFile` are restricted not to mutate the provided  `ImageFile` itself, but only generate a new copy with modification. On the other hand, given the ability to retrieve the information/status of a library/image, the model doesn't tell any details about the concrete implementation as all methods return abstraction and can only use public methods.

## View

## Controller

## How to Run

- If one wants to have a text-based script as input, they should put "-f" as the first argument, indicating they will pass a (f)ile to the program. The file path should be the second argument. Once the file is read by the program, it will perform load/process/save operation line by line, comment line starts with "#" will be ignored.
- 

---

#### Resource Image Citation

Image "elephant" citation: Photo by https://unsplash.com/photos/qiPTr8GmhM0

Image "painting" citation: Photo by https://unsplash.com/photos/JLfem8ViKVA

