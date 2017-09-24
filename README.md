# AsciiAvatar

### To run the program, from root project directory, use -

`java -jar build/libs/AsciiAvatar-1.0-all.jar imagefilePath`

### If you wish to rebuild the jar, from root project directory, use -

`gradlew shadowjar`

NB - Using 'gradlew build' will build the jar file without the dependencies bundled.
This will work if your classpath is set correctly but if not, the app will fail to find
dependencies and not run.

### Suggested improvements

- Instead of hard coding the drawing set characters into their brightness order
  their brightness is calculated at runtime. This would allow the character set to be passed
  in or chosen dynamically from a file, based upon the image being processed. I suspect this would
  yield better results.

  #### OR

- Dynamically scaling the drawing set based upon the image brightness. For example, if the image
  is very light or very dark with little variation only a few of the drawing set characters will
  be used. If the entire drawing set is used for every image then it would be easier to discern
  detail in the output image.
