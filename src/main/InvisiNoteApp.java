import org.audiveris.OMRProcessor;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class InvisiNoteApp extends JFrame {

    private JLabel titleLabel;
    private JLabel fileLabel;
    private JProgressBar progressBar;
    private OMRProcessor omrProcessor;
    private DefaultListModel<String> savedFilesModel;
    private JList<String> savedFilesList;

    public InvisiNoteApp() {
        omrProcessor = new OMRProcessor();
        initUI();
        loadSavedFiles();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 48)); // Dark background

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(45, 45, 48));
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        titlePanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        titleLabel = new JLabel("InvisiNote");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(new Color(135, 206, 250)); // Light blue
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(45, 45, 48));

        JButton processButton = createStyledButton("Process Sheet Music");
        processButton.addActionListener(e -> selectAndProcessFile());

        JButton exitButton = createStyledButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(processButton);
        buttonPanel.add(exitButton);

        // Progress bar setup
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(135, 206, 250));
        progressBar.setBackground(new Color(64, 64, 66));
        progressBar.setBorderPainted(false);
        progressBar.setVisible(false);

        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

        // Saved Files Panel
        JPanel savedFilesPanel = createSavedFilesPanel();
        add(savedFilesPanel, BorderLayout.EAST);

        setTitle("Invisi Note");
        setSize(800, 450); // Adjusted size for the new panel
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel createSavedFilesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(45, 45, 48));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel savedFilesLabel = new JLabel("Saved MusicXML Files:");
        savedFilesLabel.setForeground(Color.WHITE);
        savedFilesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        panel.add(savedFilesLabel, BorderLayout.NORTH);

        savedFilesModel = new DefaultListModel<>();
        savedFilesList = new JList<>(savedFilesModel);
        savedFilesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        savedFilesList.setBackground(new Color(64, 64, 66));
        savedFilesList.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(savedFilesList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton loadButton = createStyledButton("Load Selected File");
        loadButton.addActionListener(e -> loadSelectedFile());
        panel.add(loadButton, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        return button;
    }

    private void selectAndProcessFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            new Thread(() -> processSheetMusic(selectedFile)).start();
        }
    }

    private void processSheetMusic(File inputFile) {
        try {
            if (!inputFile.exists()) {
                JOptionPane.showMessageDialog(this, "File does not exist: " + inputFile.getAbsolutePath());
                return;
            }

            SwingUtilities.invokeLater(() -> {
                progressBar.setValue(0);
                progressBar.setVisible(true);
            });

            for (int i = 1; i <= 100; i++) {
                Thread.sleep(30);
                final int progressValue = i;
                SwingUtilities.invokeLater(() -> progressBar.setValue(progressValue));
            }

            String musicXmlFilePath = omrProcessor.process(inputFile);
            String generatedImagePath = getGeneratedImagePath(inputFile);

            displaySheetMusicWithNotes(generatedImagePath);

            SwingUtilities.invokeLater(() -> {
                progressBar.setValue(100);
                progressBar.setVisible(false);
                savedFilesModel.addElement(musicXmlFilePath); // Add new file to saved list
            });

        } catch (IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSavedFiles() {
        // Prompt user to select the directory containing the MusicXML files
        JFileChooser directoryChooser = new JFileChooser();
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = directoryChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File directory = directoryChooser.getSelectedFile();
            loadFilesFromDirectory(directory);
        } else {
            JOptionPane.showMessageDialog(this, "Directory selection canceled.");
        }
    }

    private void loadFilesFromDirectory(File directory) {
        // Check if the directory exists and is a directory
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".xml"));

            if (files != null) {
                // Clear the current model to prevent duplicates
                savedFilesModel.clear();

                // Add each MusicXML file to the list model
                for (File file : files) {
                    savedFilesModel.addElement(file.getName());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Directory not found or invalid path.");
        }
    }

    private void loadSelectedFile() {
        String selectedFile = savedFilesList.getSelectedValue();
        if (selectedFile != null) {
            JOptionPane.showMessageDialog(this, "Loading file: " + selectedFile);
            // Code to load and display selected file's contents
        } else {
            JOptionPane.showMessageDialog(this, "No file selected.");
        }
    }

    private String getGeneratedImagePath(File inputFile) {
        String parentDir = inputFile.getParent();
        String baseName = inputFile.getName().replaceFirst("[.][^.]+$", "");
        return parentDir + File.separator + baseName + ".png";
    }

    private void displaySheetMusicWithNotes(String imagePath) {
        List<Note> notes = List.of(
                new Note("C4", 100, 200),
                new Note("D4", 150, 220),
                new Note("E4", 200, 240),
                new Note("F4", 250, 260)
        );
        SwingUtilities.invokeLater(() -> new SheetMusicDisplay(imagePath, notes));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InvisiNoteApp app = new InvisiNoteApp();
            app.setVisible(true);
        });
    }
}
