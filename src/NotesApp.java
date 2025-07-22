import interfaces.NotesManager;
import models.GetNotesResult;
import models.Note;
import models.OperationResult;
import models.UpdateRequest;

import java.util.Comparator;
import java.util.UUID;
import java.util.function.Predicate;

public class NotesApp {

    private final NotesManager container;

    public NotesApp(NotesManager container){

        this.container = container;
    }

    public OperationResult initialize(){

        var result = container.Initialize();

        if (!result) return new OperationResult("Initializing error.", false);

        return new OperationResult("Success Initializing", true);
    }

    public OperationResult addNote(Note note){

        var addResult = container.addNote(note);

        if (!addResult) return new OperationResult("Adding note error", false);

        var saveResult = container.save();

        if (!saveResult) return new OperationResult("Saving note error", false);

        return new OperationResult("Success adding note!", true);
    }

    public OperationResult removeNote(Note note){
        var removeResult = container.deleteNote(note);

        if (!removeResult) return new OperationResult("Note remove error.", false);

        var saveResult = container.save();

        if (!saveResult) return new OperationResult("Saving error.", false);

        return new OperationResult("Success removing note!", true);
    }

    public OperationResult removeNotes(Predicate<Note> filter){
        var result = container.deleteNotes(filter);

        if (!result) return new OperationResult("Delete notes error.", false);

        var saveResult = container.save();

        if (!saveResult) return new OperationResult("Save error.", false);

        return new OperationResult("Delete Success!", true);
    }

    public GetNotesResult getNote(Predicate<Note> filter){

        var note = container.getNotes(filter, null).getFirst();

        if (note == null) return new GetNotesResult("No notes found.", false, null);

        return new GetNotesResult("Success get note.", true, new Note[] {note});
    }

    public GetNotesResult getNotes(Predicate<Note> filter, Comparator<Note> orderBy){

        var notes = container.getNotes(filter, orderBy);

        if (notes == null|| notes.isEmpty()) return new GetNotesResult("Get notes error", false, null);

        var result = new Note[notes.size()];

        notes.toArray(result);

        return new GetNotesResult("Success get notes", true, result);
    }

    public OperationResult updateNote(UUID id, UpdateRequest request)
    {
        var result = container.updateNote(n -> n.getId() == id, request);

        if (!result) return new OperationResult("Update note error.", false);

        return new OperationResult("Success updating note.", true);
    }

    public OperationResult updateNotes(Predicate<Note> filter, UpdateRequest request)
    {
        var result = container.updateNotes(filter, request);

        if (!result) return new OperationResult("Update notes error.", false);

        return new OperationResult("Success updating notes.", true);
    }

    public OperationResult save(){

        var saveResult = container.save();

        if (!saveResult) return new OperationResult("Saving result", false);

        return new OperationResult("Success saving", true);
    }
}
