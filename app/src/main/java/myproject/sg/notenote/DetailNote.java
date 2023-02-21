package myproject.sg.notenote;

import java.io.Serializable;

public class DetailNote implements Serializable {
    private String uid;
    private String instant;
    private String title;
    private String message;
    private String status; //status of the message 0:Active, 1: Completed
    private int builderId;

    public DetailNote() {
    }

    public DetailNote(String uid, String instant, String title, String message, String status, int builderId) {
        this.uid = uid;
        this.instant = instant;
        this.title = title;
        this.message = message;
        this.status = status;
        this.builderId = builderId;
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

    public int getBuilderId() {
        return builderId;
    }

    public void setBuilderId(int builderId) {
        this.builderId = builderId;
    }
}
