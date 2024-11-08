package org.audiveris;

import org.audiveris.omr.Main;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class OMRProcessor {
    public String process(File imageFile) throws Exception {
        // Arguments to process the image file in batch mode
        String[] args = { "-batch", "-export", imageFile.getAbsolutePath() };
        System.out.println("Running Audiveris with arguments: " + Arrays.toString(args));
        Main.main(args);

        // Define expected directory and file names
        String baseName = imageFile.getName().replace(".png", "");
        File outputDir = new File("C:\\Users\\s_cyi\\OneDrive\\Documents\\Audiveris", baseName);

        // Locate .mxl or .musicxml file
        File musicXmlFile = new File(outputDir, baseName + ".mxl");
        if (!musicXmlFile.exists()) {
            musicXmlFile = new File(outputDir, baseName + ".musicxml");
            if (!musicXmlFile.exists()) {
                throw new IOException("MusicXML file not found at path: " + musicXmlFile.getAbsolutePath());
            }
        }

        // Return MusicXML content for display in your app
        return new String(Files.readAllBytes(musicXmlFile.toPath()));
    }
}

