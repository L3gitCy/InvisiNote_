package org.audiveris;


import org.audiveris.omr.Main;
import org.audiveris.omr.score.Score;

import java.io.File;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class OMRProcessor {
    public String process(File imageFile) throws Exception {
        String[] args = new String[] { "-batch", imageFile.getAbsolutePath() };
        System.out.println("Running Audiveris with arguments: " + Arrays.toString(args));
        Main.main(args); // Start Audiveris processing

        // Define the output directory and OMR file
        String baseName = imageFile.getName().replace(".png", "");
        File outputDir = new File(imageFile.getParent(), "Audiveris\\" + baseName);
        File omrFile = new File(outputDir, baseName + ".omr");

        // Check if the OMR file was generated
        if (!omrFile.exists()) {
            throw new IOException("OMR file not found at path: " + omrFile.getAbsolutePath());
        }

        // Convert the OMR file to MusicXML
        String[] convertArgs = new String[] { "-batch", omrFile.getAbsolutePath() };
        System.out.println("Converting OMR file to MusicXML with arguments: " + Arrays.toString(convertArgs));
        Main.main(convertArgs); // Attempt to convert to MusicXML

        // Check if the MusicXML file was generated
        String musicXmlPath = outputDir.getAbsolutePath() + "\\" + baseName + ".musicxml";
        File musicXmlFile = new File(musicXmlPath);
        if (!musicXmlFile.exists()) {
            throw new IOException("MusicXML file not found at path: " + musicXmlPath);
        }

        // Return MusicXML content or path
        return new String(Files.readAllBytes(musicXmlFile.toPath()));
    }

}


