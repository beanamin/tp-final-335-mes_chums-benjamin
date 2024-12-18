package cal335.projet.DTO;

import cal335.projet.Modele.Coordonnees;

public class AdresseDTO {
    private Integer id_adresse;
    private String rue;
    private String ville;
    private String codePostal;
    private String pays;
    private CoordoneesDTO coordonnees;

    public AdresseDTO(String rue, String ville, String codePostal, String pays, CoordoneesDTO coordonnees){
        this.rue = rue;
        this.ville = ville;
        this.codePostal = codePostal;
        this.pays = pays;
        this.coordonnees = coordonnees;
    }

    public CoordoneesDTO getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(CoordoneesDTO coordonnees) {
        this.coordonnees = coordonnees;
    }

    public Integer getId_adresse() {
        return id_adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getPays() {
        return pays;
    }

    public String getRue() {
        return rue;
    }

    public String getVille() {
        return ville;
    }

    public void setId_adresse(Integer id_adresse) {
        this.id_adresse = id_adresse;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
