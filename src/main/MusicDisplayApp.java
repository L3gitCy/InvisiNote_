import javax.swing.*;
import java.awt.*;
import java.io.File;

class MusicDisplayApp {
    private JFrame frame;
    private JLabel imageLabel;
    private JButton convertButton;
    private String omrFilePath;

    public MusicDisplayApp(String omrFilePath) {
        this.omrFilePath = omrFilePath;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Music Recognition Result");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        // Load and display OMR data
        loadOmrData();

        convertButton = new JButton("Convert to MusicXML");
        convertButton.addActionListener(e -> convertToMusicXML());

        frame.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
        frame.add(convertButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void loadOmrData() {
        // Display the OMR result image
        File omrFile = new File(omrFilePath);
        if (omrFile.exists() && omrFile.isFile()) {
            // Create an ImageIcon from the OMR file
            ImageIcon icon = new ImageIcon(omrFile.getAbsolutePath());
            imageLabel.setIcon(icon);
            imageLabel.setText(""); // Clear any text if an image is shown
        } else {
            imageLabel.setText("OMR file not found: " + omrFilePath);
        }
    }

    private void convertToMusicXML() {
        // Logic to convert the OMR file to MusicXML
        // You can implement this as per your requirements
        JOptionPane.showMessageDialog(frame, "Converting OMR to MusicXML...");
    }
}
