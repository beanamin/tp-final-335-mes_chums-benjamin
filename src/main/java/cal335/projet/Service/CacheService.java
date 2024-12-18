package cal335.projet.Service;

import cal335.projet.Modele.Adresse;
import cal335.projet.Modele.Contact;
import cal335.projet.Modele.Coordonnees;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class CacheService {
    private Map<Contact, List<Coordonnees>> contactsFavoris = new HashMap<>();

    public void ajouterContact(Contact contact){
        contactsFavoris.put(contact, null);
    }
    public void ajouterAdresse(Contact contact, Adresse adresse){
        Contact contachanger = contact;
        List<Adresse> newadresslist = contachanger.getAdresses();
        newadresslist.add(adresse);
        contachanger.setAdresses(newadresslist);
        List<Coordonnees> storedcoords = contactsFavoris.get(contact);
        contactsFavoris.remove(contact);
        contactsFavoris.put(contachanger, storedcoords);
    }
    public void toggleFavorite(Contact contact){
        Contact contachanger = contact;
        List<Coordonnees> storedcoords = contactsFavoris.get(contact);
        if (contact.getIsFavoris()){
            contachanger.setIsFavoris(false);
            contactsFavoris.remove(contact);
        }else {
            contachanger.setIsFavoris(true);
            contactsFavoris.remove(contact);
            contactsFavoris.put(contachanger, storedcoords);
        }
    }
    public Contact rechercherContact(Integer id_contact){
        Set<Contact> contactSet = contactsFavoris.keySet();
        List<Contact> contactList = contactSet.stream().collect(Collectors.toList());
        for (int i = 0; i < contactList.size(); i++){
            Contact currentContact;
            if(contactList.get(i).getId_contact() == id_contact){
                return contactList.get(i);
            }
        }
        return null;
    }
}
//Ajouter un contact
//Ajouter une adresse à un contact
//Marquer un contact comme favoris, ou enlever cette marque
//Rechercher un contact