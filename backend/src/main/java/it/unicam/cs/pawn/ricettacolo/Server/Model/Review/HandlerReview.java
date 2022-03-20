package it.unicam.cs.pawn.ricettacolo.Server.Model.Review;

import java.util.List;

/**
 * Questa interfaccia ha il compito di rappresentare un gestore di recensioni. Un gestore di recensioni
 * permette di gestire le recensioni e comunica con il database.
 */
public interface HandlerReview {

    /**
     * Questo metodo permette di restituire una lista di recensioni scritte da un utente.
     * @param idUser id dell'utente.
     * @return lista contenente delle recensioni.
     */
    List<Review> getReviewsUser(int idUser);


    /**
     * Questo metodo permette di restituire una lista di recensioni di una ricetta.
     * @param idRecipe id della ricetta.
     * @return una lista contenente recensioni di una ricetta.
     */
    List<Review> getReviewsRecipe(int idRecipe);


    /**
     * Questo metodo permette di aggiungere un "mi piace" alla recensione.
     * @param idReview id della recensione.
     * @param idUser id dell'utente.
     * @return il nuovo numero di "mi piace" alla recensione.
     */
    int addLikeToReview(int idReview, int idUser);


    /**
     * Questo metodo permette di aggiungere un "non mi piace" alla recensione.
     * @param idReview id della recensione.
     * @param idUser id dell'utente.
     * @return il nuovo numero di "non mi piace" alla recensione.
     */
    int addDisikeToReview(int idReview, int idUser);


    /**
     * Questo metodo permette di conoscere se un utente ha messo un "mi piace" alla recensione.
     * @param idReview id della recensione.
     * @param idUser id dell'utente.
     * @return True se l'utente ha messo un "mi piace" alla recensione, False altrimenti.
     */
    boolean isLikedToUser(int idReview, int idUser);


    /**
     * Questo metodo permette di conoscere se un utente ha messo un "non mi piace" alla recensione.
     * @param idReview id della recensione.
     * @param idUser id dell'utente.
     * @return True se l'utente ha messo un "non mi piace" alla recensione, False altrimenti.
     */
    boolean isDislikedToUser(int idReview, int idUser);

    /**
     * Questo metodo restituisce l'attuale numero di "mi piace" della recensione.
     * @param idReview id della recensione
     * @return numero di "mi piace" della recensione.
     */
    int getLikesReview(int idReview);


    /**
     * Questo metodo restituisce l'attuale numero di "non mi piace" della recensione.
     * @param idReview id della recensione
     * @return numero di "non mi piace" della recensione.
     */
    int getDislikeReview(int idReview);

    /**
     * Questo metodo permette di rimuovere un "mi piace" dalla recensione.
     * @param idReview id della recensione.
     * @param idUser id dell'utente.
     * @return il nuovo numero di "mi piace" alla recensione.
     */
    int removeLikeToReview(int idReview, int idUser);


    /**
     * Questo metodo permette di rimuovere un "non mi piace" dalla recensione.
     * @param idReview id della recensione.
     * @param idUser id dell'utente.
     * @return il nuovo numero di "non mi piace" alla recensione.
     */
    int removeDislikeToReview(int idReview, int idUser);


    /**
     * Questo metodo restituisce una recensione a partire dall'id.
     * @param idReview id della recensione.
     * @return id della recensione.
     */
    Review getReview(int idReview);


    /**
     * Questo metodo permette di aggiungere una recensione.
     * @param review la recensione da aggiungere.
     */
    void addReview(Review review);
}
