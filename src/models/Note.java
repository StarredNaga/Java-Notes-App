package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Note {

    private UUID id;

    private String title;

    private String content;

    private List<String> tags;

    private LocalDate creationDate;

    public Note(UUID id, String title, String content, List<String> tags){

        setId(id);
        setTitle(title);
        setContent(content);
        setTags(tags);
        setCreationDate(LocalDate.now());
    }

    public UUID getId(){ return id; }

    public void setId(UUID newId){

        if (newId == null) throw new IllegalArgumentException("Id cant be null");

        id = newId;
    }

    public String getTitle() { return title; }

    public void setTitle(String newTitle){

        if (newTitle == null || newTitle.isBlank()) throw new IllegalArgumentException("Title cant be null");

        title = newTitle;
    }

    public String getContent() { return content; }

    public void setContent(String newContent){

        if (newContent == null || newContent.isBlank()) throw new IllegalArgumentException("Content cant be null");

        if (newContent.length() > 50) throw new IllegalArgumentException("Content cant bigger than 50 symbols");

        content = newContent;
    }

    public List<String> getTags() { return new ArrayList<>(tags); }

    public void setTags(List<String> newTags){

        if (newTags == null || newTags.isEmpty()) throw new IllegalArgumentException("Tags cant be null or empty");

        tags = newTags;
    }

    public LocalDate getCreationDate() { return creationDate; }

    public void setCreationDate(LocalDate newCreationDate){

        if (newCreationDate == null) throw new IllegalArgumentException("Creation date cant be null");

        creationDate = newCreationDate;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        return Objects.equals(id, note.id) && Objects.equals(title, note.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
