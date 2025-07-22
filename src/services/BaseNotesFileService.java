package services;

import interfaces.NotesFileService;
import interfaces.NotesFormatter;
import models.Note;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class BaseNotesFileService implements NotesFileService {

    private String filePath;
    private NotesFormatter formatter;

    public BaseNotesFileService(String filePath){

        setFilePath(filePath);
        formatter = new BaseNotesFormatter();
    }

    public void setFilePath(String newPath){

        if (newPath == null || newPath.isBlank()) throw new IllegalArgumentException();

        if (!Files.exists(Path.of(newPath))) throw new IllegalArgumentException();

        filePath = newPath;
    }

    public boolean writeNote(Note note, boolean append) {

        try(var writer = new OutputStreamWriter(new FileOutputStream(filePath, append))){

            var result = formatter.serializeNote(note);

            if (!result.getResult()) return false;

            writer.append(result.getSerializedObject());

            return true;
        }
        catch (FileNotFoundException _){
            System.out.println("File not found");

            return false;
        }
        catch (Exception ex){
            System.out.println("Unknown exception");

            return false;
        }
    }

    public boolean writeNotes(Note[] notes, boolean append){
        try(var writer = new OutputStreamWriter(new FileOutputStream(filePath, append))){

            var result = formatter.serializeNotes(notes);

            if (!result.getResult()) return false;

            writer.append(result.getSerializedObject());

            return true;
        }
        catch (FileNotFoundException _){
            System.out.println("File not found");

            return false;
        }
        catch (Exception ex){
            System.out.println("Unknown exception");

            return false;
        }
    }

    public Note[] getNotes(){

        try(var reader = new InputStreamReader(new FileInputStream(filePath))){

            var buffer = new char[4096];
            var builder = new StringBuilder();
            var readed = reader.read(buffer, 0, buffer.length);

            while (readed != -1){
                builder.append(Arrays.copyOfRange(buffer, 0, readed));

                readed = reader.read(buffer, 0, buffer.length);
            }

            var result = formatter.deserializeNotes(builder.toString());

            return result.getResult() ? result.getDeserializedObject() : null;
        }
        catch (FileNotFoundException _){
            System.out.println("File not found");

            return null;
        }
        catch (Exception ex){
            System.out.println("Unknown exception");

            return null;
        }

    }
}
