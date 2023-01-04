package com.job.softclick_mobile.models;

import java.io.Serializable;

public class Client implements Serializable {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String nomEntreprise;
    private String ville;
    private String pays;

    public Client() {
    }

    public Client(String nom, String prenom, String email, String phone, String nomEntreprise, String ville, String pays) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.phone = phone;
        this.nomEntreprise = nomEntreprise;
        this.ville = ville;
        this.pays = pays;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getTele() {
        return phone;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public String getVille() {
        return ville;
    }

    public String getPays() {
        return pays;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTele(String phone) {
        this.phone = phone;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Override
    public String toString() {
        return "Client{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", nomEntreprise='" + nomEntreprise + '\'' +
                ", ville='" + ville + '\'' +
                ", pays='" + pays + '\'' +
                '}';
    }
}

