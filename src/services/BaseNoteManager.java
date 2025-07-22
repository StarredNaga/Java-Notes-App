package services;

import interfaces.NotesManager;
import interfaces.NotesFileService;
import models.Note;
import models.UpdateRequest;

import java.util.*;
import java.util.function.Predicate;

public class BaseNoteManager implements NotesManager {

    private ArrayList<Note> notes;
    private final NotesFileService fileService;

    public BaseNoteManager(){

        fileService = new BaseNotesFileService("App.json");

        notes = new ArrayList<>();
    }

    public boolean Initialize(){
        var result = fileService.getNotes();

        if (result == null) return false;

        notes = new ArrayList<Note>(Arrays.asList(result));

        return true;
    }

    public List<Note> getNotes(Predicate<Note> filter, Comparator<Note> orderBy) {

        var result = notes.stream();

        if (filter != null) result = result.filter(filter);

        if (orderBy != null){

            var list = new ArrayList<>(result.toList());

            list.sort(orderBy);

            return new ArrayList<>(list);
        }

        return new ArrayList<>(result.toList());
    }

    private boolean isNoteExist(Note note){

        return !notes.stream().filter(n -> n.equals(note)).toList().isEmpty();
    }

    public boolean addNote(Note note){
        if (isNoteExist(note)) return false;

        notes.add(note);

        return true;
    }

    public boolean deleteNote(Note note){

        if (!isNoteExist(note)) return false;

        notes.remove(note);

        return true;
    }

    @Override
    public boolean deleteNotes(Predicate<Note> filter) {
        var notesToRemove = getNotes(filter, null);

        if (notesToRemove.isEmpty()) return false;

        notes.removeAll(notesToRemove);

        return true;
    }

    @Override
    public boolean updateNote(Predicate<Note> filter, UpdateRequest request) {
        for (Note note : notes) {

            if (!filter.test(note)) continue;

            if (request.Title != null && !request.Title.isEmpty()) note.setTitle(request.Title);

            if (request.Content != null && !request.Content.isEmpty()) note.setContent(request.Content);

            if (request.Tags != null && !request.Tags.isEmpty()) note.setTags(request.Tags);

            if (request.CreationTime != null) note.setCreationDate(request.CreationTime);

            break;
        }

        return true;
    }

    @Override
    public boolean updateNotes(Predicate<Note> filter, UpdateRequest request){

        for (Note note : notes) {

            if (!filter.test(note)) continue;

            if (request.Title != null) note.setTitle(request.Title);

            if (request.Content != null) note.setContent(request.Content);

            if (request.Tags != null) note.setTags(request.Tags);

            if (request.CreationTime != null) note.setCreationDate(request.CreationTime);
        }

        return true;
    }

    public boolean save(){
        var notesToWrite = new Note[notes.size()];

        notes.toArray(notesToWrite);

        return fileService.writeNotes(notesToWrite, false);
    }
}
