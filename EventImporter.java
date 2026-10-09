import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventImporter {

    private static final DateTimeFormatter FORMAT =
            DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static List<Event> importEvents(String filename)
            throws IOException {
        boolean pass;
        List<Event> events = new ArrayList<>();
        List<Event> wrongEvents = new ArrayList<>();
        List<String> lines = Files.readAllLines(Path.of(filename));

        List<String> possibleColors = new ArrayList<>();
        possibleColors.add("red");
        possibleColors.add("blue");
        possibleColors.add("green");
        for (String line : lines) {
            pass = true;
            ArrayList<String> Reasons = new ArrayList<>(); 
            String[] values = line.split(",");
            try {
                LocalDate date = LocalDate.parse(values[0].trim(), FORMAT);
                String title = values[1].trim();
                String color = values[2].trim();

                //Check if title is valid
                if (title.isBlank()){
                    pass = false;
                    Reasons.add("Invalid Title");
                }
                //Amount of values are not valid
                if (values.length != 3) {
                    pass = false;
                    Reasons.add("Not 3 values");
                }

                //Wrong colors
                if (!possibleColors.contains(color)){
                    pass = false;
                    Reasons.add("Invalid Color");
                }
                //Invalid date
                //Naisuuu
                if (pass){
                    events.add(
                            new Event(date, title, color)
                    );
                }
            }catch(Exception e){
                pass = false;
                Reasons.add("Invalid Date"); 
            }
            if (pass == false) {
                String errorMessage = String.join(", ", Reasons);
                
                System.out.println("Error at row: " + line);
                System.out.println("Due to: " + errorMessage + "\n");
                
            }
        }

        return events;
    }
}