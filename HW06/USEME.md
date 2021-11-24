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

1. StartPage:
  ![StartPage](https://tva1.sinaimg.cn/large/008i3skNgy1gwpwskpng9j31ai0u00uh.jpg)

2. Press "load" to import an image:
  ![Load pop up](https://tva1.sinaimg.cn/large/008i3skNgy1gwpwtfqpzoj310s0u0gqf.jpg)

3. Import painting.ppm from res/:
  ![Rename at loading](https://tva1.sinaimg.cn/large/008i3skNgy1gwpwujwrx1j30mw0ee3z2.jpg)
  -  Empty input will leads to an alert window saying "Input cannot be empty"
  	![Screen Shot 9](https://tva1.sinaimg.cn/large/008i3skNgy1gwpwvsogl4j30se0fuq3h.jpg)

4. Use the default name for the newly imported image "painting":
  ![Load succeed](https://tva1.sinaimg.cn/large/008i3skNgy1gwpwwrwrsej311g0fgjsl.jpg)

5. Image Preview & Histogram: 
   ![original image preview](https://tva1.sinaimg.cn/large/008i3skNgy1gwpwz0a27tj31ai0u0wh1.jpg)
   - program will always auto select to the newly imported image after a load operation

6. Press green-component button, rename the newly created image:

   ![green-component rename window](https://tva1.sinaimg.cn/large/008i3skNgy1gwpx0bc4guj30mw0ee0tg.jpg)

   - Program always append the current operation to the name of the current processing image and use it as the default name of the newly created image.

7. Green-component image preview:

   ![green-component image preview](https://tva1.sinaimg.cn/large/008i3skNgy1gwpx2z806ej314c0q1ac2.jpg)

   - program will always, again, auto select to the newly created image after a operation.

8. brighten image "painting":

   ![input adjustment maginitude](https://tva1.sinaimg.cn/large/008i3skNgy1gwpx9ypl1bj30sw0eedgm.jpg)

   - No default value for magnitude as it highly depends on user's decition

   - If the input is empty, alert window will pop up:

   - ![Screen Shot 9](https://tva1.sinaimg.cn/large/008i3skNgy1gwpxcsj308j30se0fuq3h.jpg)

     ​	

   - If the input is not pure numer, another alert window will pop up:

     ![Screen Shot 10](https://tva1.sinaimg.cn/large/008i3skNgy1gwpxcwu96kj30se0fuwf5.jpg)

9. Rename the brightened image, autonaming includes the adjustment magnitude:

   ![Brighten Renaming](https://tva1.sinaimg.cn/large/008i3skNgy1gwpx9f6p17j30mw0ee3z6.jpg)

10. Feedback after brighten:

   ![brighten succeed](https://tva1.sinaimg.cn/large/008i3skNgy1gwpxe95czdj311g0dot9i.jpg)

11. After doing sepia on the latest image, we have the view window as follows:

    ![painting-brighten10-sepia preview](https://tva1.sinaimg.cn/large/008i3skNgy1gwpy3td82kj31ai0u040y.jpg)

12. Try to save this image (by pressing save button):

    ![dialogue](https://tva1.sinaimg.cn/large/008i3skNgy1gwpya1skscj30ie0f0gmt.jpg)

    - Image will always have a default name "untitled.png" as the file name, yet user can edit the file name however they want, as well as the file extension as long as the it's supported by the program (currently, we support png/jpg/ppm/bmp)

13. Save succeed feedback:

    ![Screen Shot 2](https://tva1.sinaimg.cn/large/008i3skNgy1gwpyanecgfj30iq06uglq.jpg)

14. Inspect saved image:

    ​	![Screen Shot 3](https://tva1.sinaimg.cn/large/008i3skNgy1gwpyas31bpj30so0f8dgu.jpg)

##### † Error Handling 

- The tutorial example above has already presented a few.

- Basically, errors will be thrown under the same circumstances as the §Text-mode mentioned, yet GUI supports to render an error with a pop-up alert window.