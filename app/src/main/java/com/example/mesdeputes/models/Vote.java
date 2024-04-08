package com.example.mesdeputes;

import androidx.annotation.Nullable;
import java.io.Serializable;

public class Vote implements Serializable {

    private int id;
    private String position;
    private String titre;
    private String sort;
    private String date;
    private int nombreVotants;
    private int nombrePours;
    private int nombreContres;
    private String demandeur;

    public Vote(int id, String position, String titre, String sort, String date, int nombreVotants, int nombrePours, int nombreContres, String demandeur) {
        this.id = id;
        this.position = position;
        this.titre = titre;
        this.sort = sort;
        this.date = date;
        this.nombreVotants = nombreVotants;
        this.nombrePours = nombrePours;
        this.nombreContres = nombreContres;
        this.demandeur = demandeur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNombreVotants() {
        return nombreVotants;
    }

    public void setNombreVotants(int nombreVotants) {
        this.nombreVotants = nombreVotants;
    }

    public int getNombrePours() {
        return nombrePours;
    }

    public void setNombrePours(int nombrePours) {
        this.nombrePours = nombrePours;
    }

    public int getNombreContres() {
        return nombreContres;
    }

    public void setNombreContres(int nombreContres) {
        this.nombreContres = nombreContres;
    }

    public String getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(String demandeur) {
        this.demandeur = demandeur;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Vote d = (Vote)obj;
        return id == d.getId();
    }
}
