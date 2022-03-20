package it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes;

import java.util.List;

/**
 * Questa interfaccia ha il compito di rappresentare un gestore di ricette. Un gestore di ricette
 * permette di gestire le ricette e comunica con il database.
 * @param <I> ingredienti delle ricette.
 */
public interface RecipeHandler<I> {

    /**
     * Questo metodo restituisce una ricetta random dal database.
     * @return ricetta random dal database.
     */
    Recipe<I> getRandomRecipe();





    /**
     * Questo metodo permette di aggiungere una ricetta al database.
     * @param ingredients ingredienti della ricetta.
     * @param image immagine della ricetta.
     * @param title titolo della ricetta.
     * @param steps descrizione della ricetta, ovvero come si realizza.
     * @param idUser id dell'utente che ha scritto la ricetta.
     * @return la ricetta appena aggiunta.
     */
    Recipe<I> addNewRecipe(List<Ingredients> ingredients, String image,String title, String steps, int idUser);


    /**
     * Questo metodo restituisce tutti gli ingredienti presenti nel database.
     * @return una lista contenente tutti gli ingredienti presenti nel database.
     */
    List<I> getIngredientsList();

    /**
     * Questo metodo restituisce una lista contenente tutte le ricette che nel titolo hanno il parametro passato.
     * @param rh recipe handler
     * @param title stringa da ricercare nel titolo.
     * @return una lista di ricette il cui titolo contiene la stringa title.
     */
    List<Recipe<I>> getRecipeListFromTitle(RecipeHandler<I> rh,String title);
}
