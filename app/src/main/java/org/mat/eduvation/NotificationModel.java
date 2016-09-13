package org.mat.eduvation;

/**
 * Created by ahmed on 8/24/16.
 */

public class NotificationModel {

    private String message;
    private String date;
    private Integer _id;


    public NotificationModel(String message, String date, Integer _id) {
        this.message = message;
        this.date = date;
        this._id = _id;
    }

    public NotificationModel() {

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

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

}
