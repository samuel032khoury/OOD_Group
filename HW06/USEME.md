# USEME

[TOC]

### Command Line Level Input

- If one wants to have a text-based script as input, they should put "-file" for the first argument, indicating they will pass a script to the program. The file path should be the second argument.
- If one wants to interact with the program by typing command during the session, they should put "-text" for the first argument in the command line, indicating they will interactively manage and process the image through inputing text.
- If one wants to interact with the program through GUI, run the program without any arguments.

##### †Error Handling

- Insufficient <u>command line level arguments</u> will cause a system level exception with alerting message, user have to run the program again with correct inputs.

---

### Text-mode : Program Level Input

- Supported Commands

  - Currently, this program supports the following commands, and user must follow the syntax to use them.

    - load [source] [name]

    - save [destination] [name]

    - brighten [value] [name] [new name for modified]

    - darken [value] [name] [new name for modified]

    - [red/green/blue/value/intensity/luma]-component [name] [new name for modified]

    - [horizontal/vertical]-flip  [name] [new name for modified]

    - blur  [name] [new name for modified]

    - sharpen  [name] [new name for modified]

    - greyscale  [name] [new name for modified]

    - sepia  [name] [new name for modified]

    - size

  - An image can be processed, obviously, only if it's been loaded to the library using `load` command, with its identifiable name.
- Commands above can be typed in line by line, with comment lines start with `#` be ignored. User can also give multiple command on the same line, using splitter `&` to separate. In this case, the command will be executed linearly, and if one fails, the error message will be thrown immediately and no longer process the remaining commands, asking users to correct the syntax and try again.
  - Restrictions: Inspired by the terminal.app on macOS, we do the same thing to ask user not to put splitter at the beginning of the line as it does not make sense to do nothing and then do something else, yet we allowed the splitter is put at the end of a line as users may want to execute a series of command but put the remaining of commands (after the splitter) on a different line.
- Notes: multiple spaces between arguments are allowed and will be ignored, number of arguments need to be exact, both extra/insufficient input will cause a complaint from the program. 
- Quit Command

  - To minimize the chance that user accidentally quit the program or throw away the unperformed operations, we ask user to use the capitalized `QUIT ` to quit the program, and this operation has to be the **last** argument on a line.

##### † Error Handling 

- All the following will cause a complaint from the program. The program will print specific informative message without quitting itself and allows users to try again.
    - Insufficient arguments for a command.
    - Too many arguments  for a command.
    - User types in an unknown command.
    - User doesn't specify the extension of the image file when loading.
    - User gives an invalid file name which starts with "." and immediately followed by a file extension 
      when loading.
    - User load an image with unsupported extension.
    - User provide an image that cannot be found from the machine's file system.
    - User try to process a non-existing file in the library.
    - When darkening/brightened an image, users fail to specify the value of the adjustment.
    - Try to save a file to a read-only directory.
    - The process of loading a file is interrupted.
    - The process of saving a file is interrupted.

---

### Script-mode

The command script and example images are saved (and zipped) in the `res/imagesAndScript/` directory. The text script is named as `ImageProcScript.txt`. Graders can run this file <u>**under the ``res/imagesAndScript/`**</u> by inputting in the command line 

``imagesAndScript % java -jar ../HW06.jar -file ImageProcScript.txt``

##### † Error Handling 

- Same as §Text-mode, see above.

---

### GUI-mode: Tutorial

##### † Error Handling 

- Errors will be thrown under the same circumstances as the §Text-mode mentioned, yet GUI supports to render an error with a pop-up alert window.