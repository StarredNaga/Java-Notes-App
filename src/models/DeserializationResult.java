package models;

public class DeserializationResult {

    private boolean result;

    private Note[] deserializedObject;

    public DeserializationResult(boolean result, Note[] deserializedObject){
        this.result = result;
        this.deserializedObject = deserializedObject;
    }

    public boolean getResult() { return result; }

    public Note[] getDeserializedObject() { return deserializedObject; }
}
