package myproject.sg.notenote;

public class Notif {
    private String uid;
    private String instant;
    private String title;
    private String message;
    private String status; //status of the message 0:Active, 1: Completed

    public Notif(){
        //default constructor
    }

    public Notif(String uid, String instant, String title, String message, String status) {
        this.uid = uid;
        this.instant = instant;
        this.title = title;
        this.message = message;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getInstant() {
        return instant;
    }

    public void setInstant(String instant) {
        this.instant = instant;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
