package org.mat.eduvation;

/**
 * Created by ahmed on 8/24/16.
 */

public class NotificationModel {

    String message;
    String date;

    public NotificationModel(String message, String date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
