package cal335.projet.Controller;

import cal335.projet.DAO.ContactDAO;
import cal335.projet.DAO.GenericDAO;
import cal335.projet.Mapper.ContactMapper;
import cal335.projet.Modele.Contact;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ChumsController implements HttpHandler {
    ObjectMapper objectMapper = new ObjectMapper();
    ContactDAO contactService = new ContactDAO();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String request = exchange.getRequestURI().getQuery();
        //get all
        if(method.equals("GET") && exchange.getRequestURI().getPath().equals("/mes_chums") && request == null){
            String resp = "";
            resp = objectMapper.writeValueAsString(contactService.obtenirTousLesContacts());
            envoyerReponse(exchange, resp, 200);
        }
        //get with id
        else if(method.equals("GET") && exchange.getRequestURI().getPath().equals("/mes_chums")){
            String[] params =  request.split("&");
            int id = -1;
            for(int i = 0; i < params.length; i++){
                if (params[i].split("=")[0].equals("id")){
                    id = Integer.parseInt(params[i].split("=")[1]);
                }
            }
            String resp = "";
            if (id >= 0){
                resp = objectMapper.writeValueAsString(contactService.trouverParId(id));
                envoyerReponse(exchange, resp, 200);
            }else{
                resp = objectMapper.writeValueAsString(contactService.obtenirTousLesContacts());
                envoyerReponse(exchange, resp, 200);
            }
        }
        //post new contact
        else if(method.equals("POST") && exchange.getRequestURI().getPath().equals("/mes_chums")){
            Contact contact = objectMapper.readValue(exchange.getRequestBody(), Contact.class);

            contactService.ajouter(contact);

            envoyerReponse(exchange, "Post oui", 200);
        }
        //Remove contact
        else if(method.equals("DELETE") && exchange.getRequestURI().getPath().equals("/mes_chums")){
            if (request == null){

            }else{
                String resp = "";
                resp = objectMapper.writeValueAsString(contactService.obtenirTousLesContacts());
                envoyerReponse(exchange, resp, 200);
            }
        }
        else if(method.equals("GET") && exchange.getRequestURI().getPath().equals("/mes_chums")){
            if (request == null){

            }else{
                String resp = "";
                resp = objectMapper.writeValueAsString(contactService.obtenirTousLesContacts());
                envoyerReponse(exchange, resp, 200);
            }
        }
        else if(method.equals("GET") && exchange.getRequestURI().getPath().equals("/mes_chums")){
            if (request == null){

            }else{
                String resp = "";
                resp = objectMapper.writeValueAsString(contactService.obtenirTousLesContacts());
                envoyerReponse(exchange, resp, 200);
            }
        }
    }//obtenir tout les contacts, obtenir un contact specifique, mettre un nouveau contact,
    // enlever un contact, toggle favoris sur un contact, recherche à proximité
    private void envoyerReponse(HttpExchange echangeAvecUtilisateur, String response, int statusCode) throws IOException {
        echangeAvecUtilisateur.sendResponseHeaders(statusCode, response.length());
        OutputStream os = echangeAvecUtilisateur.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
