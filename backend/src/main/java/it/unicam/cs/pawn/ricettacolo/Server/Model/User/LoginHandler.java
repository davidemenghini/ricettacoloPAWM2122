package it.unicam.cs.pawn.ricettacolo.Server.Model.User;

/**
 * Questa interfaccia rappresenta un gestore di login. Ha il compito di eseguire il login. Per eseguire il login
 * alla password bisogna aggiungere prima il secret e poi il salt e infine eseguire la funzione di hash "SHA-256".
 */
public interface LoginHandler {

    /**
     * Questo metodo prova ad eseguire il login
     * @return True se username e password sono corrette, False altrimenti.
     */
    boolean tryLogin();

    /**
     * Questo metodo aggiunge il salt alla password a cui &egrave; stata aggiunta la secret precedentemente.
     */
    void addSaltTopsw();


    /**
     * Questo metodo aggiunge la secret alla password.
     */
    void addSecretTopsw();


    /**
     * Questo metodo restituisce l'id dell'utente.
     * @return id dell'utente.
     */
    int getId();




    /**
     * Questo metodo permette di crittografare la password.
     * @return True se &egrave; andato tutto bene, false altrimenti.
     */
    boolean encodePassword();


    /**
     * Questo metodo restituisce l'utente.
     * @return utente.
     */
    User getUser();
}
