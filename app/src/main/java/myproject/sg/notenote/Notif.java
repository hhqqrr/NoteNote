package myproject.sg.notenote;

public class Notif {
    private String uid;
    private String message;
    private int status; //status of the message 0:Active, 1: Completed

    public Notif(){
        //default constructor
    }

    public Notif(String uid, String message, int status) {
        this.uid = uid;
        this.message = message;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
