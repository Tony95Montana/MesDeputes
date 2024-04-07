package com.example.mesdeputes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.Serializable;
import java.util.Objects;

public class Deputy implements Serializable {

    private int id;
    private String firstname;
    private String lastname;
    private String department;
    private String dateNaissance;
    private String lieuNaissance;
    private String sexe;
    private String twitter;
    private String facebook;
    private String instagram;
    private int numCirco;
    private String nameCirco;

    private String email;
    private String groupe;

    public Deputy(int id, String firstname, String lastname, String dateNaissance, String lieuNaissance, String sexe, @NonNull JSONArray link, String department, int numCirco, String nameCirco) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.sexe = sexe;
        for (int i = 0; i < link.length() ;i++) {
            try {
                String site = link.getString(i).split("\"")[3];
                if (site.contains("https")) {
                    site = site.replace("/", "");
                    site = site.replace("\\\\", "\\");
                    if (site.contains("facebook")) this.facebook = site;
                    else if (site.contains("twitter")) twitter = site;
                    else if (site.contains("instagram")) instagram = site;
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        this.department = department;
        this.numCirco = numCirco;
        this.nameCirco = nameCirco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateNaissance() {return "né le "+dateNaissance;}

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {return "à "+lieuNaissance;}

    public void setLieuNaissance(String lieuNaissance) {this.lieuNaissance = lieuNaissance;}

    public String getSexe() {
        if (Objects.equals(sexe, "H")) return "sex : Homme";
        else return "sex : Femme";
    }

    public void setSexe(String sexe) {this.sexe = sexe;}

    public String getTwitter() {return twitter;}

    public void setTwitter(String twitter) {this.twitter = twitter;}

    public String getFacebook() {return facebook;}

    public void setFacebook(String facebook) {this.facebook = facebook;}

    public String getInstagram() {return instagram;}

    public void setInstagram(String instagram) {this.instagram = instagram;}

    public String getGroupe() {return groupe;}

    public void setGroupe(String groupe) {this.groupe = groupe;}

    public String getDepartment() {return department;}

    public void setDepartment(String department) {this.department = department;}

    public int getNumCirco() {return numCirco;}

    public void setNumCirco(int numCirco) {this.numCirco = numCirco;}

    public String getNameCirco() {return nameCirco;}

    public void setNameCirco(String nameCirco) {this.nameCirco = nameCirco;}


    public String getNameForAvatar(){
        return firstname.replace(' ', '-').toLowerCase()+
                "-"+lastname.replace(' ', '-').toLowerCase();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Deputy d = (Deputy)obj;
        return id == d.getId();
    }
}
