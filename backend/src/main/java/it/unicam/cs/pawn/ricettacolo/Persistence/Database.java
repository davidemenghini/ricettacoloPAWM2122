package it.unicam.cs.pawn.ricettacolo.Persistence;

import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Ingredients;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Recipe;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.Review;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.User;

import java.util.List;

/**
 * Questa interfaccia ha la responsabilità di comunicare con il database.
 * @param <I> Il tipo di dato usato come ingrediente.
 */
public interface Database<I>{


        /**
         * Questo metodo serve per controllare se l'username e la password dell'utente corrispondono.
         * @param user username
         * @param password password
         * @return True se l'utente ha inserito l'username e la password giusta, false altrimenti.
         */
        boolean tryLogin(String user, String password);


        /**
         * Questo metodo restituisce una ricetta a partire dal titolo.
         * @param nameRecipe il nome della ricetta.
         * @return la ricetta cercata. Se la ricetta non esiste restituisce null.
         */
        Recipe<I> getRecipe(String nameRecipe);


        /**
         * Questo metodo restituisce tutte le ricette scritte da un utente.
         * @param idUser id dell'utente.
         * @return una lista contenente tutte le ricette di un utente.
         */
        List<Recipe<I>> getAllRecipesUser(int idUser);


        /**
         * Questo metodo restituisce tutte le recensioni di una ricetta.
         * @param idRecipe id della ricetta.
         * @return una lista contenente tutte le recensioni di una ricetta.
         */
        List<Review> getReviewsOfRecipe(int idRecipe);

        /**
         * Questo metodo restituisce tutte le recensioni scritte da un utente.
         * @param idUser id dell'utente.
         * @return una lista contenente tutte le recensioni scritte da un utente.
         */
        List<Review> getReviewsOfUser(int idUser);

        /**
         * Questo metodo restituisce una ricetta random.
         * @return una ricetta casuale.
         */
        Recipe<I> getRandomRecipe();

        /**
         * Questo metodo permette di recuperare l'id dell'utente a partire dall'username.
         * @param username l'username dell'utente.
         * @return id dell'utente.
         */
        int retriveIdFromUser(String username);


        /**
         * Questo metodo restituisce una lista di ingredienti di una ricetta.
         * @param id id della ricetta.
         * @return una lista contenente gli ingredienti di una ricetta.
         */
        List<Ingredients> getIngredientsFromRecipe(int id);


        /**
         * Questo metodo permette di aggiungere un "mi piace" ad una recensione da parte di un utente.
         * @param idReview id della recensione.
         * @param actual attuali numero di "mi piace" alla recensione.
         * @param users id dell'utente.
         */
        void addLikeReview(int idReview, int actual, int users);

        /**
         * Questo metodo permette di conoscere il numero di "mi piace" di una recensione.
         * @param idReview id della recensione.
         * @return numero di "mi piace" della recensione.
         */
        int getLikeReview(int idReview);

        /**
         * Questo metodo permette di conoscere il numero di "non mi piace" di una recensione.
         * @param idReview id della recensione.
         * @return numero di "non mi piace" della recensione.
         */
        int getDislikeReview(int idReview);

        /**
         * Questo metodo permette di aggiungere un "non mi piace" ad una recensione da parte di un utente.
         * @param idReview id della recensione.
         * @param actual attuali numero di "non mi piace" alla recensione.
         * @param users id dell'utente.
         */
        void addDislikeReview(int idReview, int actual, int users);

        /**
         * Questo metodo serve per conoscere se l'utente ha gia messo "mi piace" alla recensione.
         * @param idReview id della recensione.
         * @param idUser id dell'utente.
         * @return True se l'utente ha messo "mi piace "alla recensione, False altrimenti.
         */
        boolean isLikedToUser(int idReview, int idUser);

        /**
         * Questo metodo serve per conoscere se l'utente ha gia messo "non mi piace" alla recensione.
         * @param idReview id della recensione.
         * @param idUser id dell'utente.
         * @return True se l'utente ha messo "non mi piace "alla recensione, False altrimenti.
         */
        boolean isDislikedToUser(int idReview, int idUser);

        /**
         * Questo metodo restituisce una recensione.
         * @param idReview id della recensione.
         * @return una recensione.
         */
        Review getReview(int idReview);

        /**
         * Questo metodo permette di rimuovere un "non mi piace" ad una recensione da parte di un utente.
         * @param idReview id della recensione.
         * @param newDislike il nuovo numero di "non mi piace" alla recensione.
         * @param idUser id dell'utente.
         */
        void removeDislikeReview(int idReview, int newDislike, int idUser);

        /**
         * Questo metodo permette di rimuovere un "mi piace" ad una recensione da parte di un utente.
         * @param idReview id della recensione.
         * @param newLike il nuovo numero di "mi piace" alla recensione.
         * @param idUser id dell'utente.
         */
        void removeLikeReview(int idReview, int newLike, int idUser);

        /**
         * Questo metodo permette di aggiungere una ricetta.
         * @param r la ricetta da aggiungere.
         */
        void addRecipe(Recipe<I> r);

        /**
         * Questo metodo serve per controllare se l'id di un utente &egrave; gi&agrave; occupato.
         * @param idUser id dell'utente da controllare.
         * @return True se &egrave; gi&agrave; occupato, False altrimenti.
         */
        boolean checkifIdUserIsPresent(int idUser);

        /**
         * Questo metodo restituisce un utente a partire dall'username e dall'id.
         * @param username username dell'utente.
         * @param id id dell'utente.
         * @return utente.
         */
        User getUser(String username, int id);

        /**
         * Questo metodo permette di aggiungere un token di accesso ad un utente.
         * @param cookie il cookie da aggiungere.
         * @return True se il token &egrave; stato aggiunto, False altrimenti.
         */
        boolean addAccessTokenToDb(String cookie, int idU);


        /**
         * Questo metodo controlla se il cookie passato corrisponde a quello presente nel database.
         * @param idUser id dell'utente.
         * @param cookie cookie.
         * @return True se i cookie sono uguali, false altrimenti.
         */
        boolean checkAccessCookie(int idUser, String cookie);


        /**
         * Questo metodo serve per eliminare un cookie di accesso.
         * @param idUser id dell'utente da cui eliminare i cookie.
         */
        void deleteAccessCookie(int idUser);

        /**
         * Questo metodo restituisce l'id dell'ingrediente a partire dal nome.
         * @param name nome dell'ingrediente.
         * @return id dell'ingrediente.
         */
        int retriveIdFromNameIngredients(String name);

        /**
         * Questo metodo restituisce una lista contenente tutti gli ingredienti presenti nel database.
         * @return lista di ingredienti presenti nel database.
         */
        List<I> getAllIngredients();

        /**
         * Questo metodo aggiunge una recensione nel database.
         * @param review la recensione da aggiungere.
         */
        void addReview(Review review);


        /**
         * Questo metodo retituisce una lista di ricette a partire dal titolo.
         * @param title il titolo da cercare nel database.
         * @return una lista di ricette.
         */
        List<Recipe<I>> getRecipesFromTitle(String title);

}
