package models;

public class GetNotesResult extends BaseRequest{

    private final Note[] notes;

    public GetNotesResult(String message, boolean isSuccess, Note[] notes) {
        super(message, isSuccess);

        this.notes = notes;
    }

    public Note[] getNotes() { return notes; }
}
