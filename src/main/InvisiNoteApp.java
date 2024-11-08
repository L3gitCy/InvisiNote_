import org.audiveris.OMRProcessor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InvisiNoteApp extends JFrame {

    private JLabel titleLabel;
    private JLabel fileLabel;
    private OMRProcessor omrProcessor;

    public InvisiNoteApp() {
        // Initialize the OMRProcessor
        omrProcessor = new OMRProcessor();

        // Setup your GUI components here
        initUI();
    }

    private void initUI() {
        // Set a background color or image
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240)); // Light grey background

        // Create a title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent white
        titlePanel.setLayout(new FlowLayout());

        titleLabel = new JLabel("InvisiNote");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(Color.BLUE);
        titlePanel.add(titleLabel);

        // Create a button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent white

        JButton processButton = new JButton("Process Sheet Music");
        processButton.setFont(new Font("Arial", Font.PLAIN, 18));
        processButton.addActionListener(e -> selectAndProcessFile());

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 18));
        exitButton.addActionListener(e -> System.exit(0)); // Exit button

        buttonPanel.add(processButton);
        buttonPanel.add(exitButton);

        // Add panels to the frame
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setTitle("Invisi Note");
        setSize(600, 400); // Adjusted size
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void selectAndProcessFile() {
        // Use a file chooser to select an image file
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            processSheetMusic(selectedFile);
        }
    }

    private void processSheetMusic(File inputFile) {
        try {
            // Check if the file exists before starting
            if (!inputFile.exists()) {
                JOptionPane.showMessageDialog(this, "File does not exist: " + inputFile.getAbsolutePath());
                return;
            }

            // Process the file using the OMRProcessor
            String musicXmlFilePath = omrProcessor.process(inputFile);

            // After processing, get the generated image path
            String generatedImagePath = getGeneratedImagePath(inputFile);

            // Display the sheet music with the letter notes overlay
            displaySheetMusicWithNotes(generatedImagePath);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "I/O error during processing: " + e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(this, "Processing was interrupted: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getGeneratedImagePath(File inputFile) {
        // Get the parent directory of the .mxl file
        String parentDir = inputFile.getParent();

        // Get the base name of the file (without extension)
        String baseName = inputFile.getName().replaceFirst("[.][^.]+$", ""); // Remove the extension

        // Build the image file path (e.g., same name, different extension)
        String imagePath = parentDir + File.separator + baseName + ".png"; // Assuming the image is a PNG

        return imagePath; // Return the path as a String
    }

    private void displaySheetMusicWithNotes(String imagePath) {
        // For now, we'll simulate that the notes are extracted and their positions are known
        // This would be dynamic based on the actual parsing of the MusicXML file
        java.util.List<Note> notes = java.util.List.of(
                new Note("C4", 100, 200),
                new Note("D4", 150, 220),
                new Note("E4", 200, 240),
                new Note("F4", 250, 260)
        );

        // Create and display the sheet music with letter notes overlay
        SwingUtilities.invokeLater(() -> new SheetMusicDisplay(imagePath, notes));
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(() -> {
            InvisiNoteApp app = new InvisiNoteApp();
            app.setVisible(true);
        });
    }
}