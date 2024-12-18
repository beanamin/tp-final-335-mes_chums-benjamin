package cal335.projet;

import cal335.projet.Controller.ChumsController;
import cal335.projet.DAO.GenericDAO;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ChumsServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);

        server.createContext("/chums", new ChumsController());

        server.setExecutor(null);

        server.start();

        System.out.println("Serveur démarré sur le port " + PORT);
    }
}
