# Image Process Assignment

## Model

#### Library

This program uses a (hash)map for its upper-level model, representing an image library that allows multiple images loaded during a single run. By taking the benefits of the map structure, all images stored can be identified and retrieved by a key (in this case, image names user assigned). 

#### ImageFile

The keys of the map are names of images as Strings assigned by users. The values of the map are objects (lower-level model) that directly or indirectly implement the `ImageFile` interface. 

Current abstract implementation of `ImageFile`, `AImageFile`, uses a 2-D array of Color to store (RGB) image (no alpha channel) information, regardless of their original image format. `AImageFile` will also record the max value of Color. AImageFile also has a map, mapping from an `IChannelOperator` to `IChannelFunction` (see below Operation Section), which already contains several basic operations. Inherited concrete classes can expand the map per its demand.

As overwritten an existing image in the image library is allowed by design, all current methods for `ImageFile` are restricted not to mutate the provided  `ImageFile` itself, but only generate a new copy with modification.

#### Operation

Operation package contains an interface `IChannelOperator` for enum classes. Every enum member should be the name of a particular operation to convert a Color, by some defined rules, to another, which may have a modified RGB(A) value. Rules can either be implemented by creating a new concrete class that implements the `IChannelFunction` interface and overwrite the `apply` method or use lambda to create an anonymous subclass that implements this interface. Eventually, the name and the mechanism of the operation need to be matched up and put in the `channelOperations` map located in `AImageFile` or its concrete classes.

Notes: It’s (safely) assumed that implementors would expand the map correctly, meaning the new operation added must make sense to the images stored in a particular inherited class. For example, implementors should realize it's meaningless to put an alpha-related operation into the map of the class that represents images that only have RBG information.

#### R/W Capbility

Both `ImageLibModel` and `ImageFile` are designed in a way that can limit (specifically in view package) the capability of performing malicious/mistaken mutation or overwritten. Specifically, we make these two interfaces implement the read-only interfaces `ImageLibModelState` and `ReadOnlyImageFile` that only have methods for inspecting information about models. On the other hand, given the ability to retrieve the information/status of a library/image, the model doesn't tell any details about the concrete implementation as all methods return abstraction and can only use public methods.

---

## View

The current implementation for view is only responsible to render prompts after each operation and errors if user provide bad input, the error will point out which step goes wrong and ask users to try again.

---

## Controller

---

## How to Run

#### Command Line Level Input

- If one wants to have a text-based script as input, they should put "-f" for the first argument, indicating they will pass a (f)ile to the program. The file path should be the second argument.
- If one wants to interact with the program by typing command in the program, they should put "-m" for the first argument, indicating they will (m)anually manage and process the image. 

##### Error Handling

- Insufficient command line level arguments will cause a system level exception with alerting message, user have to run the program again with correct inputs.

#### Program Level Input

##### Error Handling

---

#### Resource Image Citation

Image "elephant" citation: Photo by https://unsplash.com/photos/qiPTr8GmhM0

Image "painting" citation: Photo by https://unsplash.com/photos/JLfem8ViKVA

