# Minesweeper
The famous little game: Minesweeper
# Preparation
- Make sure you have the standard java development tool (jdk) Version 14.0.2, and have set up PATH VARIABLES for it.
- If you don't have it. The jdk (Version14) can be downloaded [here](https://adoptopenjdk.net/releases.html?variant=openjdk14&jvmVariant=hotspot). Choose the correct one according to your OS. Read this [guide](https://www.java.com/en/download/help/path.html) for setting up PATH VARIABLES.
- Then you need to download the javafx libraries from [Windows](https://gluonhq.com/download/javafx-14-sdk-windows/), [Mac](https://gluonhq.com/download/javafx-14-sdk-mac/), [Linux](https://gluonhq.com/download/javafx-14-sdk-linux/).
- Set up the PATH VARIABLES for javafx according to this [guide](https://openjfx.io/openjfx-docs/#install-javafx).

# How to play
- Open a terminal and type 
- Mac/Linux: *java --module-path ${PATH_TO_FX} --add-modules=javafx.controls,javafx.fxml,javafx.media -jar Minesweeper.jar*
- Windows: *java --module-path %PATH_TO_FX% --add-modules=javafx.controls,javafx.fxml,javafx.media -jar Minesweeper.jar*
- Then hit enter. The gui of the game should then appear.
# Instruction
Enter two numbers from 1-99 (inclusive) which represent width and height. Also enter the number of bombs. It should be larger than 1 and smaller than the total number of tiles. Hit Start button to initialize the gameplay.