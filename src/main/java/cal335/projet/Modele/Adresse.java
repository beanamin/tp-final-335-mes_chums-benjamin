package cal335.projet.Modele;

public class Adresse {
    private Integer id_adresse;
    private String rue;
    private String ville;
    private String codePostal;
    private String pays;
    private Coordonnees coordonnees;


    public Coordonnees getCoordonnees() {
        return coordonnees;
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
}
