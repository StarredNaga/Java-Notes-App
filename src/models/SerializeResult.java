package models;

public class SerializeResult {

    private boolean result;

    private String serializedObject;

    public SerializeResult(boolean result, String serializedObject){
        this.result = result;
        this.serializedObject = serializedObject;
    }

    public boolean getResult() { return result; }

    public String getSerializedObject() { return serializedObject; }
}
