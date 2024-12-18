package cal335.projet.DTO;

import cal335.projet.Modele.Adresse;

import java.util.List;

public class ContactDTO {
    private Integer id_contact;
    private String nom;
    private String prenom;
    private boolean isFavoris;
    private List<AdresseDTO> adresses;

    public ContactDTO(String nom, String prenom, boolean isFavoris, List<AdresseDTO> adresses){
        this.nom = nom;
        this.prenom = prenom;
        this.isFavoris = isFavoris;
        this.adresses = adresses;
    }

    public Integer getId_contact() {
        return id_contact;
    }

    public List<AdresseDTO> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<AdresseDTO> adresses) {
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
