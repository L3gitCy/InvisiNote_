import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import java.io.File;

public class MusicXMLParser {

    public static void parseMusicXML(String filePath) {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList noteList = doc.getElementsByTagName("note");
            for (int i = 0; i < noteList.getLength(); i++) {
                Node noteNode = noteList.item(i);
                if (noteNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element noteElement = (Element) noteNode;
                    if (noteElement.getElementsByTagName("pitch").getLength() > 0) {
                        String step = noteElement.getElementsByTagName("step").item(0).getTextContent();
                        String octave = noteElement.getElementsByTagName("octave").item(0).getTextContent();
                        String alter = noteElement.getElementsByTagName("alter").getLength() > 0
                                ? noteElement.getElementsByTagName("alter").item(0).getTextContent()
                                : "0";

                        // Calculate the note name with alteration
                        String noteName = step;
                        if (alter.equals("1")) {
                            noteName += "#";
                        } else if (alter.equals("-1")) {
                            noteName += "b";
                        }
                        noteName += octave;

                        System.out.println("Note: " + noteName);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

