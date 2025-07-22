import color.Color;
import models.Note;
import models.UpdateRequest;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.console.ConsoleTextTerminal;
import services.BaseNoteManager;

import java.util.*;
import java.util.function.Predicate;

public class Main {

    private static NotesApp app;

    private static TextIO terminal;
    private static TextTerminal writer;

    private static boolean exitFlag;

    private static final String MENU = """
                $$\\   $$\\            $$\\                                $$$$$$\\
                $$$\\  $$ |           $$ |                              $$  __$$\\
                $$$$\\ $$ | $$$$$$\\ $$$$$$\\    $$$$$$\\   $$$$$$$\\       $$ /  $$ | $$$$$$\\   $$$$$$\\
                $$ $$\\$$ |$$  __$$\\\\_$$  _|  $$  __$$\\ $$  _____|      $$$$$$$$ |$$  __$$\\ $$  __$$\\
                $$ \\$$$$ |$$ /  $$ | $$ |    $$$$$$$$ |\\$$$$$$\\        $$  __$$ |$$ /  $$ |$$ /  $$ |
                $$ |\\$$$ |$$ |  $$ | $$ |$$\\ $$   ____| \\____$$\\       $$ |  $$ |$$ |  $$ |$$ |  $$ |
                $$ | \\$$ |\\$$$$$$  | \\$$$$  |\\$$$$$$$\\ $$$$$$$  |      $$ |  $$ |$$$$$$$  |$$$$$$$  |
                \\__|  \\__| \\______/   \\____/  \\_______|\\_______/       \\__|  \\__|$$  ____/ $$  ____/
                                                                                 $$ |      $$ |
                                                                                 $$ |      $$ |
                                                                                 \\__|      \\__|
                """;

    public static void main(String[] args) {
        app = new NotesApp(new BaseNoteManager());

        app.initialize();

        terminal = new TextIO(new ConsoleTextTerminal());

        writer = terminal.getTextTerminal();

        writer.println(MENU);

        while (!exitFlag){
            printMenu();

            var input = getOption(1, 6);

            writer.println("\n");

            proceedMenuOption(input);

            clearScreen();
        }

        printSuccess("Thank you for using app");
    }

    private static void proceedMenuOption(int input){

        switch (input){
            case 1: { getAllNotes(); break; }

            case 2: { getNote(); break; }

            case 3: { createNote(); break; }

            case 4: { updateNotes(); break; }

            case 5: { deleteNotes(); break; }

            case 6: {
                exitFlag = true;

                break;
            }

            default:
                writer.println("Incorrect option");
        }
    }

    private static void printSuccess(String message){

        writer.println(Color.colorize(Color.COLOR_GREEN, message + "\n"));
    }

    private static void printError(String message){
        writer.println(Color.colorize(Color.COLOR_RED, message + "\n"));
    }

    private static void getNote(){

        var filter = askFilterOptions();

        var result = app.getNote(filter);

        if (!result.getResult()){
            printError(result.getMessage());

            return;
        }

        printSuccess(result.getMessage());
        printNotes(result.getNotes());
    }

    private static void getAllNotes(){

        var filter = askFilterOptions();
        var orderBy = askOrderOptions();

        var result = app.getNotes(filter,orderBy);

        if (!result.getResult()){
            printError(result.getMessage());

            return;
        }

        printSuccess(result.getMessage());
        printNotes(result.getNotes());
    }

    private static void createNote(){

        var reader = terminal.newStringInputReader();

        var title = reader
                .withMinLength(2)
                .withMaxLength(100)
                .read(Color.colorize(Color.COLOR_BLUE, "Enter Title: "));

        var content = reader
                .withMinLength(2)
                .withMaxLength(250)
                .read(Color.colorize(Color.COLOR_BLUE, "Enter content: "));

        var tags = reader
                .withMinLength(2)
                .withMaxLength(15)
                .readList(Color.colorize(Color.COLOR_BLUE, "Enter tags (',' for separate): "));

        var note = new Note(UUID.randomUUID(), title, content, tags);

        var result = app.addNote(note);

        if (!result.getResult()) printError(result.getMessage());

        printSuccess(result.getMessage());
    }

    private static void updateNotes(){

        var filter = askFilterOptions();

        writer.println("\n");

        var request = new UpdateRequest();
        var reader = terminal.newStringInputReader();
        
        request.Title = reader
                .withDefaultValue("")
                .read(Color.colorize(Color.COLOR_PURPLE, "Enter new title ot nothing: "));

        request.Content = reader
                .withDefaultValue("")
                .read(Color.colorize(Color.COLOR_PURPLE, "Enter new content ot nothing: "));

        request.Tags = reader
                .withDefaultValue("")
                .readList(Color.colorize(Color.COLOR_PURPLE, "Enter new tags or nothing: "));

        var result = app.updateNotes(filter, request);

        if (!result.getResult()) printError(result.getMessage());

        printSuccess(result.getMessage());
    }

    private static void deleteNotes(){

        var filter = askFilterOptions();

        writer.println("\n");

        var result = app.removeNotes(filter);

        if (!result.getResult()) printError(result.getMessage());

        printSuccess(result.getMessage());
    }

    private static void printMenu(){
        writer = terminal.getTextTerminal();

        printOptions(Color.colorize(Color.COLOR_CYAN, "Options:\n"),
                "1) Get All Notes",
                "2) Get Note",
                "3) Create note",
                "4) Update Note/Notes",
                "5) Delete Note/Notes",
                "6) Exit");

        writer.println("\n");
    }

    private static void printOptions(String ...options){
        writer.println(Arrays.asList(options));
    }

    private static void printFilterOptions(){
        printOptions(Color.colorize(Color.COLOR_CYAN, "Filter options:\n"), "1) By Title", "2) By content", "3) By tags", "4) Without filter");

        writer.println("\n");
    }

    private static Predicate<Note> askFilterOptions(){
        printFilterOptions();

        var option = getOption(1, 4);

        Predicate<Note> filter;

        var reader = terminal.newStringInputReader();

        switch (option){
            case 1:{
                var title = reader.withMinLength(2).withMaxLength(100).read("Enter title to search: ").toLowerCase();

                filter = n -> n
                        .getTitle()
                        .toLowerCase()
                        .contains(title);

                break;
            }

            case 2: {
                var content = reader.withMinLength(2).withMaxLength(100).read("Enter content to search: ").toLowerCase();

                filter = n -> n
                        .getContent()
                        .toLowerCase()
                        .contains(content);

                break;
            }

            case 3:{
                var tags = reader.withMinLength(2).withMaxLength(15).readList("Enter tags to search: ");

                filter = n -> n.getTags().stream().anyMatch(tags::contains);

                break;
            }

            default: { filter = null; break; }
        }

        writer.println("\n");

        return filter;
    }

    private static Comparator<Note> askOrderOptions(){

        Comparator<Note> orderBy;

        printOptions(Color.colorize(Color.COLOR_CYAN,"Order by options:\n") ,"1) By Id", "2) By Title", "3) By content", "4) By tags", "5) Creation time", "6) Without order");

        writer.println("\n");

        var option = getOption(1, 6);

        switch (option){
            case 1: {orderBy = Comparator.comparing(Note::getId); break;}

            case 2: {orderBy = Comparator.comparing(Note::getTitle); break;}

            case 3: {orderBy = Comparator.comparing(Note::getContent); break;}

            case 4: {orderBy = Comparator.comparingInt(n -> n.getTags().size()); break;}

            case 5: {orderBy = Comparator.comparing(Note::getCreationDate); break;}

            default: { orderBy = null; break; }
        }

        writer.println("\n");

        return orderBy;
    }

    private static void printNotes(Note ...notes){

        if (notes.length == 0) {
            writer.println("\n\nNo notes find...");

            return;
        }

        for (var note : notes){

            writer.println("\n" + Color.colorize(Color.COLOR_RED, "Id: ") + note.getId());
            writer.println(Color.colorize(Color.COLOR_PURPLE, "Title: ") + note.getTitle());
            writer.println(Color.colorize(Color.COLOR_GREEN, "Content: ") + note.getContent());
            writer.println(Color.colorize(Color.COLOR_CYAN, "Tags: ") + String.join(", ", note.getTags()));
            writer.println(Color.colorize(Color.COLOR_BLUE, "Date: ") + note.getCreationDate().toString());
        }
    }

    public static void clearScreen() {
        for (int i = 0; i < 10; ++i) {
            writer.println();
        }
    }

    private static int getOption(int min, int max){
        var reader = terminal.newIntInputReader().withPropertiesConfigurator(props -> props.setPromptColor("red"));

        return reader.withMinVal(min).withMaxVal(max).read(Color.colorize(Color.COLOR_CYAN, "Enter option: "));
    }
}
