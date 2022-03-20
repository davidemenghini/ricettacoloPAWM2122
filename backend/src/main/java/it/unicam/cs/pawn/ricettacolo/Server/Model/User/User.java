package it.unicam.cs.pawn.ricettacolo.Server.Model.User;


import java.util.List;

/**
 * Questa interfaccia permette di rappresentare un utente.
 */
public interface User {


    /**
     * Questo metodo restituisce l'username dell'utente.
     * @return username dell'utente.
     */
    String getUser();

    /**
     * Questo metodo restituisce l'id dell'utente.
     * @return id dell'utente.
     */
    int getID();

    /**
     * Questo metodo aggiunge il salt alla password.
     * @param salt il salt da aggiungere.
     */
    void addSalt(String salt);

    /**
     * Questo metodo aggiunge il secret alla password.
     * @param secret il secret da aggiungere.
     */
    void addSecret(String secret);


    /**
     * Questo metodo permette di scrivere il secret nel file in cui &egrave; salvato.
     * @param text la secret da aggiungere.
     */
    void writeToFileSecret(List<String> text);


    /**
     * Questo metodo permette di scrivere il salt nel file in cui &egrave; salvato.
     * @param text la salt da aggiungere.
     */
    void writeToFileSalt(List<String> text);




    








}
