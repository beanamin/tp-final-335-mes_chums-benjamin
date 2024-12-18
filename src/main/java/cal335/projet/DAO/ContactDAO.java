package cal335.projet.DAO;

import cal335.projet.DTO.AdresseDTO;
import cal335.projet.DTO.ContactDTO;
import cal335.projet.DTO.CoordoneesDTO;
import cal335.projet.Mapper.ContactMapper;
import cal335.projet.Modele.Adresse;
import cal335.projet.Modele.Contact;
import cal335.projet.Modele.Coordonnees;
import cal335.projet.Service.CacheService;
import cal335.projet.Service.CalculateurDistance;
import com.sun.net.httpserver.HttpExchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ContactDAO extends GenericDAO<ContactDTO>{
    CacheService cacheService = new CacheService();
    CalculateurDistance calculateurDistance = new CalculateurDistance();

    ContactMapper contactMapper = new ContactMapper();

    public List<ContactDTO> obtenirTousLesContacts(){
        List<ContactDTO> returnlist = new ArrayList<>();

        String getStatementString = "SELECT * FROM contact";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getStatementString)){

            ResultSet results = preparedStatement.executeQuery();
            while (results.next()){
                int contact_id = results.getInt("id");
                String nom = results.getString("nom");
                String prenom = results.getString("prenom");
                boolean isFavoris = results.getBoolean("isFavoris");
                List<AdresseDTO> adresseList = new ArrayList<>();
                String getStatementAdresses = "SELECT * FROM adresse WHERE contact_id = " + contact_id;
                PreparedStatement preparedStatementAdresse = connection.prepareStatement(getStatementAdresses);
                ResultSet adresseResults = preparedStatementAdresse.executeQuery();
                while (adresseResults.next()){
                    int adresse_id = adresseResults.getInt("id");
                    String rue = adresseResults.getString("rue");
                    String ville = adresseResults.getString("ville");
                    String codepostal = adresseResults.getString("codePostal");
                    String pays = adresseResults.getString("pays");

                    String getAdressCoords = "SELECT * FROM coordonees WHERE adresse_id = " + adresse_id;
                    PreparedStatement preparedStatementCoords = connection.prepareStatement(getAdressCoords);
                    ResultSet coordResults = preparedStatementCoords.executeQuery();
                    CoordoneesDTO coordonnees = new CoordoneesDTO(
                            coordResults.getDouble("latitude"),
                            coordResults.getDouble("longitude")
                    );
                    coordonnees.setId(coordResults.getInt("id"));
                    AdresseDTO adressetoadd = new AdresseDTO(rue, ville, codepostal, pays, coordonnees);
                    adressetoadd.setId_adresse(adresse_id);
                    adresseList.add(adressetoadd);
                }
                ContactDTO contajoute = new ContactDTO(nom, prenom, isFavoris, adresseList);
                contajoute.setId_contact(contact_id);
                returnlist.add(contajoute);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return returnlist;
    }

    @Override
    public ContactDTO trouverParId(Integer id) {
        List<ContactDTO> tousLesContacts = obtenirTousLesContacts();
        for (int i = 0 ; i < tousLesContacts.size(); i++){
            if (tousLesContacts.get(i).getId_contact().equals(id)){
                return tousLesContacts.get(i);
            }
        }
        return null;
    }

    public boolean ajouter(Contact contact) {
        String requeteSQL = "INSERT INTO Contact (nom, prenom, isFavoris) VALUES (?, ?, ?)";
        String requeteAdresse = "INSERT INTO Adresse (rue, ville, codePostal, pays, contact_id) VALUES (?, ?, ?, ?, ?)";
        String requeteCoord = "INSERT INTO Coordonees (latitude, longitude, adresse_id) VALUES (?, ?, ?)";

        try (Connection connexion = getConnection();
             PreparedStatement preparedContactStatement = connexion.prepareStatement(requeteSQL);
             PreparedStatement preparedAdressStatement = connexion.prepareStatement(requeteAdresse);
             PreparedStatement preparedCoordStatement = connexion.prepareStatement(requeteCoord)) {

            preparedContactStatement.setString(1, contact.getNom());
            preparedContactStatement.setString(2, contact.getPrenom());
            preparedContactStatement.setBoolean(3, contact.getIsFavoris());
            int resultcontact = preparedContactStatement.executeUpdate();
            System.out.println("Nombre de lignes insérées : " + resultcontact);

            ResultSet generatedContactKeys = preparedContactStatement.getGeneratedKeys();
            System.out.println("Key used : " + generatedContactKeys.getInt(1));

            for (int i = 0; i < contact.getAdresses().size(); i++) {
                Adresse currentAdresse = contact.getAdresses().get(i);
                Coordonnees currentCoords = currentAdresse.getCoordonnees();
                preparedAdressStatement.setString(1, currentAdresse.getRue());
                preparedAdressStatement.setString(2, currentAdresse.getVille());
                preparedAdressStatement.setString(3, currentAdresse.getCodePostal());
                preparedAdressStatement.setString(4, currentAdresse.getPays());
                ResultSet generatedCoordKeys = preparedContactStatement.getGeneratedKeys();
                preparedAdressStatement.setInt(5, generatedContactKeys.getInt(1));
                int resultadresse = preparedAdressStatement.executeUpdate();
                System.out.println("Nombre de lignes insérées : " + resultadresse);

                ResultSet generatedAdresseKeys = preparedAdressStatement.getGeneratedKeys();
                System.out.println("Key used : " + generatedAdresseKeys.getInt(1));

                preparedCoordStatement.setDouble(1, currentCoords.getLatitude());
                preparedCoordStatement.setDouble(2, currentCoords.getLongitude());
                preparedCoordStatement.setInt(3, generatedAdresseKeys.getInt(1));
                int resultcoord = preparedCoordStatement.executeUpdate();
                System.out.println("Nombre de lignes insérées : " + resultcoord);
            }



            ResultSet generatedKeys = preparedContactStatement.getGeneratedKeys();
            System.out.println("Key used : " + generatedKeys.getInt(1));
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la tâche : " + e.getMessage());
            return false;
        }
    }


    public boolean supprimer(int id) {
        String deleteStringRequest = "DELETE FROM Contact WHERE id = ?";
        if (trouverParId(id).getIsFavoris()){
            cacheService.toggleFavorite(contactMapper.DTOToContact(trouverParId(id)));
        }
        try(Connection connection = getConnection(); PreparedStatement deleteRequest = connection.prepareStatement(deleteStringRequest)){
            deleteRequest.setInt(1, id);
            int resultdelete = deleteRequest.executeUpdate();
            System.out.println("Nombre de lignes enlevés : " + resultdelete);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean toggleFavoris(int id){
        String updateStringRequest = "UPDATE Contact SET isFavoris = NOT isFavoris WHERE id = ?";
        try(Connection connection = getConnection(); PreparedStatement updateRequest = connection.prepareStatement(updateStringRequest)){
            updateRequest.setInt(1, id);
            Contact contact = contactMapper.DTOToContact(trouverParId(id));
            cacheService.toggleFavorite(contact);
            int resultdelete = updateRequest.executeUpdate();
            System.out.println("Nombre de lignes changés : " + resultdelete);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public List<ContactDTO> rechercheProximite(int id){
        List<ContactDTO> toutLesContacts = obtenirTousLesContacts();
        ContactDTO centermass = trouverParId(id);
        Map<ContactDTO, Double> mappedcontacts = new HashMap<>();
        for(int i = 0; i < toutLesContacts.size(); i++){
            double lowestdistancevalue = -100;
            for (int d = 0; d < toutLesContacts.get(i).getAdresses().size(); d++){
                for (int k = 0; k < toutLesContacts.get(i).getAdresses().size(); k++) {
                    double distancevalue = calculateurDistance.calculerDistance(contactMapper.DTOToCoordonees(toutLesContacts.get(i).getAdresses().get(d).getCoordonnees()), contactMapper.DTOToCoordonees(centermass.getAdresses().get(k).getCoordonnees()));
                    if (distancevalue < lowestdistancevalue || lowestdistancevalue < 0){
                        lowestdistancevalue = distancevalue;
                    }
                }
            }
            mappedcontacts.put(toutLesContacts.get(i), lowestdistancevalue);
        }
        List<ContactDTO> sortedList = new ArrayList<>();

        double largestsize = 0;
        for (int i = 0; i < mappedcontacts.values().size(); i++){
            if(largestsize < mappedcontacts.get(toutLesContacts.get(i))){
                largestsize = mappedcontacts.get(toutLesContacts.get(i));
            }
        }
        for (int i = 0; i < largestsize; i++){
            for (int d = 0; d < toutLesContacts.size(); d++){
                if (mappedcontacts.get(toutLesContacts.get(d)) == i){
                    sortedList.add(toutLesContacts.get(i));
                }
            }
        }

        return sortedList;
    }
}
