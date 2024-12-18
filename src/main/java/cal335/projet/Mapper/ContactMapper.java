package cal335.projet.Mapper;

import cal335.projet.DTO.AdresseDTO;
import cal335.projet.DTO.ContactDTO;
import cal335.projet.DTO.CoordoneesDTO;
import cal335.projet.Modele.Adresse;
import cal335.projet.Modele.Contact;
import cal335.projet.Modele.Coordonnees;

import java.util.ArrayList;
import java.util.List;

public class ContactMapper {
    public ContactDTO contactToDTO(Contact contact){
        List<AdresseDTO> adresseDTOS= new ArrayList<>();

        for (int i = 0; i < contact.getAdresses().size(); i++){
           adresseDTOS.add(adresseToDTO(contact.getAdresses().get(i)));
        }

        ContactDTO returnDTO = new ContactDTO(contact.getNom(), contact.getPrenom(), contact.getIsFavoris(), adresseDTOS);
        return returnDTO;
    }

    public AdresseDTO adresseToDTO(Adresse adresse){


        AdresseDTO returnDTO = new AdresseDTO(adresse.getRue(), adresse.getVille(), adresse.getCodePostal(), adresse.getPays(), coordoneesToDTO(adresse.getCoordonnees()));

        return returnDTO;
    }

    public CoordoneesDTO coordoneesToDTO(Coordonnees coordonnees){
        CoordoneesDTO returnDTO = new CoordoneesDTO(coordonnees.getLatitude(), coordonnees.getLongitude());
        returnDTO.setId(coordonnees.getId());
        return returnDTO;
    }

    public Contact DTOToContact(ContactDTO contact){
        List<Adresse> adresses= new ArrayList<>();

        for (int i = 0; i < contact.getAdresses().size(); i++){
            adresses.add(DTOToadresse(contact.getAdresses().get(i)));
        }

        Contact returnContact = new Contact(contact.getNom(), contact.getPrenom(), contact.getIsFavoris(), adresses);
        return returnContact;
    }

    public Adresse DTOToadresse(AdresseDTO adresse){

        Adresse returnaddress = new Adresse(adresse.getRue(), adresse.getVille(), adresse.getCodePostal(), adresse.getPays(), DTOToCoordonees(adresse.getCoordonnees()));

        return returnaddress;
    }

    public Coordonnees DTOToCoordonees(CoordoneesDTO coordonnees){
        Coordonnees returncoords = new Coordonnees(coordonnees.getLatitude(), coordonnees.getLongitude());
        returncoords.setId(coordonnees.getId());
        return returncoords;
    }
}
