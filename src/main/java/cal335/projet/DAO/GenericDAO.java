package cal335.projet.DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

    public class GenericDAO <T>{
    private static GenericDAO instance;
    private static Connection connexion;
    private static String URL = "jdbc:sqlite:src/main/resources/contacts.db";

    private static HikariDataSource dataSource;

        static {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:sqlite:src/main/resources/contacts.db");
            config.setUsername("");
            config.setPassword("");
            config.setMaximumPoolSize(10);
            dataSource = new HikariDataSource(config);
        }

    public static boolean connecter(){
        try {
            Path cheminVersBase = Paths.get("src", "main", "resources", "taches.db");
            URL = "jdbc:sqlite:" + cheminVersBase.toString();
            connexion = getConnection();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'initialisation de la connexion : " + e.getMessage());
            return false;
        }
    }
        public static Connection getConnection() throws SQLException {
            return dataSource.getConnection();
        }



    public boolean ajouter(T objet){
        return false;
    }
    public boolean supprimer(T objet){
        return false;
    }
    public boolean mettreAJour(T objet){
        return false;
    }
    public boolean trouverParId(Integer id){
        return false;
    }
}
