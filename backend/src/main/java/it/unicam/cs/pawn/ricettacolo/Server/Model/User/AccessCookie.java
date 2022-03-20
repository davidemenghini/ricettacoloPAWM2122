package it.unicam.cs.pawn.ricettacolo.Server.Model.User;

/**
 * Questa interfaccia ha la responsabilità di gestire un cookie di accesso.
 */
public interface AccessCookie {


    /**
     * Questo metodo serve per generare un cookie di accesso per l'utente che vuole eseguire il login.
     * @param idUser id dell'utente che vuole accedere.
     * @return un nuovo cookie sotto forma di stringa.
     */
    String generateAccessCookie(int idUser);

    /**
     * Questo metodo serve per controllare un utente che ha già effettuato l'accesso, se il cookie che possiede corrisponde con
     * quello che gli &egrave; stato dato quando ha effetutato il login.
     * @param cookie il cookie da controllare.
     * @param id id dell'utente.
     * @return True se i cookie corrispondono, false altrimenti.
     */
    boolean checkAccessCookie(String cookie, int id);

    /**
     * Questo metodo serve per eliminare un cookie di accesso per un utente.
     * @param idUser id dell'utente.
     */
    void deleteAccessToken(int idUser);


    /**
     * Questo metodo restituisce il cookie immagazzinato.
     * @return stringa contenente il cookie immagazzinato.
     */
    String getCookie();






}
