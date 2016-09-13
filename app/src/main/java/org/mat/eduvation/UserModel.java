package org.mat.eduvation;

/**
 * Created by ahmed on 7/18/2016.
 */
public class UserModel {
    public static String KEY;
    private String name;
    private String company;
    private String b_date;
    private String email;


    public UserModel(String name, String company, String b_date, String email) {
        this.name = name;
        this.company = company;
        this.b_date = b_date;
        this.email = email;
    }

    public UserModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getB_date() {
        return b_date;
    }

    public void setB_date(String b_date) {
        this.b_date = b_date;
    }


}
