package models;

public class BaseRequest {

    private final String message;

    private final boolean isSuccess;

    public BaseRequest(String message, boolean isSuccess){
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public String getMessage() { return message; }

    public boolean getResult() { return isSuccess; }
}
