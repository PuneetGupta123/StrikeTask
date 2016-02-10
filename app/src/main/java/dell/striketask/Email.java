package dell.striketask;

/**
 * Created by dell on 2/9/2016.
 */
public class Email {
    String id;
    String name;
    String timeStamp;
    String description;

    public Email(String id, String name, String timeStamp, String description) {
        this.id = id;
        this.name = name;
        this.timeStamp = timeStamp;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
