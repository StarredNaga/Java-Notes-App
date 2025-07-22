package interfaces;

import models.Note;
import models.UpdateRequest;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface NotesManager {

    boolean Initialize();

    List<Note> getNotes(Predicate<Note> filter, Comparator<Note> orderBy);

    boolean addNote(Note note);

    boolean deleteNote(Note note);

    boolean deleteNotes(Predicate<Note> filter);

    boolean updateNote(Predicate<Note> filter, UpdateRequest request);

    boolean updateNotes(Predicate<Note> filter, UpdateRequest request);

    boolean save();
}
