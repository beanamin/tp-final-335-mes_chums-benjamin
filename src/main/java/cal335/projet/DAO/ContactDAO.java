package cal335.projet.DAO;

import cal335.projet.Modele.Adresse;
import cal335.projet.Modele.Contact;
import cal335.projet.Modele.Coordonnees;
import com.sun.net.httpserver.HttpExchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactDAO extends GenericDAO<Contact>{
    public List<Contact> obtenirTousLesContacts(){
        List<Contact> returnlist = new ArrayList<>();

        return returnlist;
    }

    @Override
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
        String deleteStringRequest = "DELETE FROM Contact WHERE id = ? CASCADE";
        try(Connection connection = getConnection(); PreparedStatement deleteRequest = connection.prepareStatement(deleteStringRequest)){
            deleteRequest.setInt(1, id);
            int resultdelete = deleteRequest.executeUpdate();
            System.out.println("Nombre de lignes insérées : " + resultdelete);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
