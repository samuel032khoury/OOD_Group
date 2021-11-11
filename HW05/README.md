# Image Process Assignment

## Model

#### Library

This program uses a (hash)map for its upper-level model, representing an image library that allows multiple images loaded and processed during a single run. By taking the benefits of the map structure, all images stored can be identified and can be retrieved by a key (in this case, image names user assigned). 

#### ImageFile

Keys of the map are names of images as Strings assigned by users. The values of the map are objects (lower-level model) that directly or indirectly implement the `ImageFile` interface. 

Current implementation of `ImageFile`, `ImageFileImpl`, uses a 2-D array of Color to store 
(RGBA) color information, with the option if a ImageFileImpl object should be considered has a alpha channel, regardless of their original image format. It will also record the possible maximum value for Color. 
Currently `IImageOperation` contains all the operations that can be preformed on the `ImageFile` which the interface have a method to take in a operation and produce a new image.

As overwritten an existing image in the `ImageLib` is allowed by design, all current methods for `ImageFile` are restricted not to mutate the provided  `ImageFile` itself, but only generate a new copy with modification. All modification is performed on a ==**deep copy**== of the original `ImageFile`, so the original  `ImageFile` and the information stored in it will not be mutated by all means. If users want to replace the value corresponding to the identifiable key, on the other hand, they can simply re-assign the value of which the key they used to retrieve an image to process.
#### Operation

`Operation.operator.colortrans` package contains an interface `IColorTransOperator` for enum classes. 
Every enum member should be the name of a particular operation to convert a Color, by some defined rules, to another, which may have a modified RGB(A) value.
Each `IColorTransOperator` contains a matrix that records the color transform for a particular pixels. The matrix is defined in the enum creation and can be retrieved by calling getMatrix().
Currently, `GreyscaleOperation` are able to use `SimpleArithmeticGreyscaleOperator` and `SingleChannelGreyscaleOperator` declared.
`TingingOperation` are able to use `TiltingOperator` declared.

Operation.operator.filter package contains an interface `IFilterOperator` for enum classes.
Every enum member should be the name of a particular operation to convert a Color, by some defined rules and using pixels around it, to another, which may have a modified RGB(A) value.
Each `IFilterOperator` contains an odd matrix that declared the value of the center pixels with respect to the surrounding pixels.
The matrix is defined in the enum creation and can be retrieved by calling getMatrix().
Currently, `FilterOperation` are able to use `IFilterOperator` declared.

~~Rules can either be implemented by creating a new concrete class that implements the `IColorTransOperator` interface and overwrite the `apply` method or use lambda to create an anonymous subclass that implements this interface.~~
~~Eventually, the name and the mechanism of the operation need to be matched up and put in the `channelOperations` map located in `AImageFile` or its concrete classes.~~


Notes: It’s (safely) assumed that implementors would expand the map correctly, meaning the new operation added must make sense to the images stored in a particular inherited class. For example, implementors should realize it's meaningless to put an alpha-related operation into the map of the class that represents images that only have RBG information.

#### R/W Capability

Both `ImageLibModel` and `ImageFile` are designed in a way that can limit (specifically in view package) the capability of performing malicious/mistaken mutation or overwritten. Specifically, we make these two interfaces implement the read-only interfaces `ImageLibModelState` and `ReadOnlyImageFile` that only have methods for inspecting information about models. On the other hand, given the ability to retrieve the information/status of a library/image, the model doesn't tell any details about the concrete implementation as all methods return abstraction and can only use public methods.

---

## View

The current implementation for view is only responsible to render prompts after each operation and errors if user provide bad input, the error will point out which step goes wrong and ask users to try again.

---

## Controller

The Controller is in charge of commands user inputs in and interacts with the view so that the view can correctly render feedback messages. Any additional command can be added by 1) first implementing the `ICommand` interface (or extending the current `ACommand` abstract class), and 2) putting the new command into the supported map by extending the current `ImageProcessControllerImpl` class. The controller is also designed to be open for more file types. To support a new suffix of an image, simply 1) implements ILoader (how to load an image, i.e., how to convert a particular image file to a 2-D color array), 2) implements IWriter (how to save an image, i.e., how to convert back given a 2-D Color array to the target file type), and finally 3) add the two implementations to `availableSuffix` map in the `AManager<T>` by directly or indirectly extends it. The `provide` method will supply the controller with a suitable way to convert back and forth from the human-readable file type to machine-processable data type.

---

## How to Run

#### Command Line Level Input

- If one wants to have a text-based script as input, they should put "-f" for the first argument, indicating they will pass a (f)ile to the program. The file path should be the second argument.
- If one wants to interact with the program by typing command in the program, they should put no argument in the command line, indicating they will manually manage and process the image. 

##### Error Handling

- Insufficient command line level arguments will cause a system level exception with alerting message, user have to run the program again with correct inputs.

#### Program Level Input

- Supported Commands
  - Currently, this program supports the following commands, and user must follow the syntax to use them.
    - load [source] [name]

    - save [dest] [name]

    - brighten [var] [name] [name for modified]

    - darken [var] [name] [name for modified]

    - [red/green/blue/value/intensity/luma]-component [name] [name for modified]

    - [horizontal/vertical]-flip  [name] [name for modified]

    - blur  [name] [name for modified]

    - sharpen  [name] [name for modified]

    - greyscale  [name] [name for modified]
    
    - sepia  [name] [name for modified]

    - size
    
      \# This will inform users the size of the library.
  - Commands above can be typed in line by line, with comment line starts with `#` be ignored. User can also give multiple command on the same line, using splitter `&` to separate. In this case, the command will be executed linearly, and if one fails, the error message will be thrown immediately and no longer process the remaining commands, asking users to correct the syntax and try again.
  - Notes: multiple spaces between arguments are allowed and will be ignored, number of arguments need to be exact, both extra/insufficient input will cause a complaint from the program. 

- †Quit Command

  - To minimize the chance that user accidentally quit the program or throw away the unperformed operations, we ask user to use the capitalized `QUIT ` to quit the program, and this operation has to be the **last** argument on a line.

- **Graders: can run the program followed by `-f ImageProcScript.txt` to run the script, or can also use `-m` to run the program and manually input commands provided.**

##### Error Handling - All the following will cause a complaint from the program. The program will print specific informative message without quitting itself and allows users to try again.

- Insufficient arguments for a command.
- Too many arguments  for a command.
- User types in an unknown command.
- User doesn't specify the type of the image file when loading.
- User gives an invalid fail name which starts with "." and immediately followed by a file type 
  when loading.
- User load an image with unsupported image type.
- User provide an image that cannot be found from the machine's file system.
- User try to process a non-existing file in the library.
- When darkening/brightened an image, users fail to specify the value they want to adjust.
- Try to save a file to a read-only directory.
- Save is interrupted.

---

#### Resource Image Citation

Image "elephant" citation: Photo by https://unsplash.com/photos/qiPTr8GmhM0

Image "painting" citation: Photo by https://unsplash.com/photos/JLfem8ViKVA

---

#### Changes

ImagesFile do not include the operations within the object, the operations now moved to IImageOperation interface.
The reason of it is that it is weird that the pictures contains the operations by themselves, and when new functions are added,
new interfaces are declared and the ImageFile will be changed, which means a lot of the methods will be changed. In this way,
it will be much better to understand and extend further actions. This changes does not violate the nvc pattern.

restructure the package into smaller packages. So that it will be easier for people to understand the function of it.

changed the modifier in ImageProcessControllerImpl from private to protected. So that it can be extended and reused.

change the code fo the main function to meet the criteria for starting a session

further, changed test class a bit to configure the controller used. In this way, when new controller is written, it will be easier to write tests.

