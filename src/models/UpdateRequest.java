package models;

import java.time.LocalDate;
import java.util.List;

public class UpdateRequest {

    public String Title;

    public String Content;

    public List<String> Tags;

    public LocalDate CreationTime;
}
