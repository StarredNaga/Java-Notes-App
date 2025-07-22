package interfaces;

import models.Note;

public interface NotesFileService {

    boolean writeNote(Note note, boolean append);

    boolean writeNotes(Note[] notes, boolean append);

    Note[] getNotes();
}
