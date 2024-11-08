import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SheetMusicDisplay extends JFrame {

    private String sheetMusicImagePath;
    private List<Note> notes;

    public SheetMusicDisplay(String sheetMusicImagePath, List<Note> notes) {
        this.sheetMusicImagePath = sheetMusicImagePath;
        this.notes = notes;

        setTitle("Sheet Music with Letter Notes Overlay");
        setSize(800, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Use absolute positioning for overlays

        // Panel for sheet music image
        JPanel sheetPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(sheetMusicImagePath);
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        sheetPanel.setBounds(0, 0, 800, 1000);
        add(sheetPanel);

        // Overlay note labels
        for (Note note : notes) {
            JLabel noteLabel = new JLabel(note.getLetter());
            noteLabel.setFont(new Font("Arial", Font.BOLD, 12));
            noteLabel.setForeground(Color.RED);
            noteLabel.setBounds(note.getX(), note.getY(), 20, 20); // Set position and size
            sheetPanel.add(noteLabel);
        }

        setVisible(true);
    }
}

 class Note {
    private String letter;
    private int x, y;

    public Note(String letter, int x, int y) {
        this.letter = letter;
        this.x = x;
        this.y = y;
    }

    public String getLetter() {
        return letter;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
