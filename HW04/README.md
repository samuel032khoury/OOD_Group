# Image Process Assignment

## Model

#### Library

This program uses a (hash)map for its upper-level model, representing an image library that allows multiple images loaded and processed during a single run. By taking the benefits of the map structure, all images stored can be identified and can be retrieved by a key (in this case, image names user assigned). 

#### ImageFile

Keys of the map are names of images as Strings assigned by users. The values of the map are objects (lower-level model) that directly or indirectly implement the `ImageFile` interface. 

Current abstract implementation of `ImageFile`, `AImageFile`, uses a 2-D array of Color to store (RGB) image (with no alpha channel) information, regardless of their original image format. `AImageFile` will also record the maximum value for Color. There's a map in `AImageFile`, mapping from an `IChannelOperator` to `IChannelFunction` (see below Operation Section), which already contains several basic operations. Inherited concrete classes can expand the map per its demand.

As overwritten an existing image in the image library is allowed by design, all current methods for `ImageFile` are restricted not to mutate the provided  `ImageFile` itself, but only generate a new copy with modification. If users want to "mutate" the original image, they can simply overwrite the value of which the key they used to retrieve an imge to process.

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

The Controller is in charge of commands user inputs in and interacts with the view so that the view can correctly render feedback messages. Any additional command can be added by 1) first implementing the `ICommand` interface (or extending the current `ACommand` abstract class), and 2) putting the new command into the supported map by extending the current `ImageProcessControllerImpl` class. The controller is also designed to be open for more file types. To support a new suffix of an image, simply 1) implements ILoader (how to load an image, i.e., how to convert a particular image file to a 2-D color array), 2) implements IWritter (how to save an image, i.e., how to convert back given a 2-D Color array to the target file type), and finally 3) add the two implementations to `availableSuffix` map in the `AManager<T>` by directly or indirectly extends it. The `provide` method will supply the controller with a suitable way to convert back and forth from the human-readble file type to machine-processable data type.

---

## How to Run

#### Command Line Level Input

- If one wants to have a text-based script as input, they should put "-f" for the first argument, indicating they will pass a (f)ile to the program. The file path should be the second argument.
- If one wants to interact with the program by typing command in the program, they should put "-m" for the first argument, indicating they will (m)anually manage and process the image. 

##### Error Handling

- Insufficient command line level arguments will cause a system level exception with alerting message, user have to run the program again with correct inputs.

#### Program Level Input

- Image Processing Command
  - Currently this tool supports the following Image Processing commands, and user must follow the syntax to use them.
    - laod [sorce] [name]
    - save [dest] [name]
    - brighten [var] [name] [name for modified]
    - darken [var] [name] [name for modified]
    - [red/green/blue/value/intensity/luma]-component [name] [name for modified]
    - [horizontal/vertical]-flip  [name] [name for modified]
  - Image Processing Command can be typed in line by line, with comment line starts with `#` be ignored. User can also give multiple command on the same line, using splitter `&` to separate. In this case, the command are passed linearly, and if one fails, the error message will be thrown immediately, asking users to correct the syntax and try again, and no longer processe the following commands.
  - Notes: multiple spaces between arguments are allowed and will be ignored, number of arguments need to be exact, both extra/insufficient input will cause a complain from the program. 

- Library/Program State Command

  - Currently we support two commands

    - QUIT

      \# to quit the program

    - SIZE

      \# to inspect the size (number of image loaded) of the library

  - Library level command need to be capitalized and can only be perforemed on a new line, i.e., same line multiple command seperated by `&` would not apply here.

  - Current library level command does not expect any extra arguments and will complain if this restriction is failed.

- **Graders: can run the program followed by `-f ImageProcScript.txt` to run the script, or can also use `-m` to run the program and manually input commands provided.**

##### Error Handling - All print specific informative message without quit the program and allows users to try again.

- Insufficient arguments for a command.
- Too much arguments  for a command.
- User types in an unknown command.
- User doesn't specify the type of the image file when load.
- User gives a invalid fail name which starts with "." and immediately followed by a file type when load.
- User load a image with unsupported image type.
- User provide an image that cannot be found from the machine's file system.
- User try to processing an non-existing file in the library.
- When darkening/brightened an image, users fail to specify the value they want to adjust.
- Try to save a file to a read-only directory.
- Save is interrupted.

---

#### Resource Image Citation

Image "elephant" citation: Photo by https://unsplash.com/photos/qiPTr8GmhM0

Image "painting" citation: Photo by https://unsplash.com/photos/JLfem8ViKVA

