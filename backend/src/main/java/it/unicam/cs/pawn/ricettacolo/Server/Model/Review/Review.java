package it.unicam.cs.pawn.ricettacolo.Server.Model.Review;


/**
 * Questa interfaccia ha il compito di rappresentare una recensione di un ricetta. Una recensione &egrave;
 * un feedback alla ricetta e pu&ograve; essere aggiunta solo da un'utente. Una recensione &egrave; formata da
 * un titolo, una descrizione, il numero di "mi piace" alla recensione e il numero di "non mi piace".
 */
public interface Review {

    /**
     * Questo metodo restituisce l'id dell'utente che ha scritto la recensione.
     * @return id dell'utente
     */
    int getUser();


    /**
     * Questo metodo restituisce il titolo della recensione.
     * @return stringa contenente il titolo della recensione.
     */
    String getTitle();


    /**
     * Questo metodo restituisce la descrizione (ovvero il modo in cui si realizza) della recensione.
     * @return stringa contenente la descrizione della recensione.
     */
    String getDescription();

    /**
     * Questo metodo restituisce il numero di "mi piace" della recensione.
     * @return numero di "mi piace" della recensione.
     */
    int getLikeNumber();


    /**
     * Questo metodo restituisce il numero di "non mi piace" della recensione.
     * @return numero di "non mi piace" della recensione.
     */
    int getDislikeNumber();

    /**
     * Questo metodo restituisce l'id della recensione.
     * @return id della recensione
     */
    int getIDReview();

    /**
     * Questo metodo restituisce l'id della ricetta di cui questa recensione fa parte.
     * @return id della ricetta.
     */
    int getIDRecipe();









}
