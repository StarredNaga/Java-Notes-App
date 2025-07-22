package interfaces;

import models.DeserializationResult;
import models.Note;
import models.SerializeResult;

public interface NotesFormatter {

    SerializeResult serializeNote(Note note);

    SerializeResult serializeNotes(Note[] notes);

    DeserializationResult deserializeNote(String noteString);

    DeserializationResult deserializeNotes(String notesString);
}
