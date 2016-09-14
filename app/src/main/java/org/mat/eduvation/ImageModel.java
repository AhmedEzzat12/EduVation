package org.mat.eduvation;

/**
 * Created by ahmed on 9/14/16.
 */

public class ImageModel {
    private String imagestr;
    private String email;

    public ImageModel(String imagestr, String email) {
        this.imagestr = imagestr;
        this.email = email;
    }

    public ImageModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getImagestr() {
        return imagestr;
    }

    public void setImagestr(String imagestr) {
        this.imagestr = imagestr;
    }
}
