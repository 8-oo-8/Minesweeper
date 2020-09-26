# Minesweeper
The famous little game: Minesweeper
# How to play
- Make sure you have the standard java development tool (jdk) Version 14.0.2, and have set up PATH VARIABLES for it.
- If you don't have it. The jdk (Version14) can be downloaded [here](https://adoptopenjdk.net/releases.html?variant=openjdk14&jvmVariant=hotspot). Choose the correct one according to your OS. Read this [guide](https://www.java.com/en/download/help/path.html) for setting up PATH VARIABLES.
- Open a terminal and type 

- *java --module-path PATH\TO\THIS\FOLDER\javafx-sdk-14.0.2.1\lib --add-modules=javafx.controls,javafx.fxml,javafx.media -jar Minesweeper.jar*
 
- (e.g. For Windows: *java --module-path F:\java_files\Minesweeper\javafx-sdk-14.0.2.1\lib --add-modules=javafx.controls,javafx.fxml,javafx.media -jar Minesweeper.jar*)
- Then hit enter. The gui of the game should then appear.
# Instruction
Enter two numbers from 1-99 (inclusive) which represent width and height. Also enter the number of bombs. It should be larger than 1 and smaller than the total number of tiles. Hit Start button to initialize the gameplay.