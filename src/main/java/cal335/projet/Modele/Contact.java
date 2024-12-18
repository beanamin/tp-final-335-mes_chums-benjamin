package cal335.projet.Modele;

import java.util.List;

public class Contact {
    private Integer id_contact;
    private String nom;
    private String prenom;
    private boolean isFavoris;
    private List<Adresse> adresses;

    public Contact(String nom, String prenom, boolean isFavoris, List<Adresse> adresses){
        this.nom = nom;
        this.prenom = prenom;
        this.isFavoris = isFavoris;
        this.adresses = adresses;
    }

    public Integer getId_contact() {
        return id_contact;
    }

    public List<Adresse> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<Adresse> adresses) {
        this.adresses = adresses;
    }

    public void setIsFavoris(boolean favoris) {
        isFavoris = favoris;
    }

    public boolean getIsFavoris() {
        return isFavoris;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setId_contact(Integer id_contact) {
        this.id_contact = id_contact;
    }


}
