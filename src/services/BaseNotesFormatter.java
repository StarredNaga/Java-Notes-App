package services;

import adapters.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import interfaces.NotesFormatter;
import models.DeserializationResult;
import models.Note;
import models.SerializeResult;

import java.time.LocalDate;

public class BaseNotesFormatter implements NotesFormatter {

    private Gson formatter;

    public BaseNotesFormatter(){
        formatter = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .create();
    }

    public SerializeResult serializeNote(Note note){

        try{
            var result = formatter.toJson(note);

            return new SerializeResult(true, result);
        }
        catch (Exception ex){
            return new SerializeResult(false, "");
        }

    }

    public SerializeResult serializeNotes(Note[] notes){

        try{
            var result = formatter.toJson(notes);

            return new SerializeResult(true, result);
        }
        catch (Exception ex){
            return new SerializeResult(false, "");
        }
    }

    public DeserializationResult deserializeNote(String noteString){

        try{
            var note = formatter.fromJson(noteString, Note.class);

            return new DeserializationResult(true, new Note[]{note});
        }
        catch (Exception ex){
            return new DeserializationResult(false, new Note[] {});
        }
    }

    public DeserializationResult deserializeNotes(String notesString){

        try{
            var notes = formatter.fromJson(notesString, Note[].class);

            return new DeserializationResult(true, notes);
        }
        catch (Exception ex){

            System.out.println(ex.getMessage());
            return new DeserializationResult(false, new Note[] {});
        }
    }
}
