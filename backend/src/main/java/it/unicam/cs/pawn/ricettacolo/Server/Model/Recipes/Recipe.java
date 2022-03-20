package it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes;

import java.util.List;

/**
 * Questa interfaccia ha il compito di rappresentare una ricetta.
 * @param <I> ingredienti
 */
public interface Recipe<I> {

    /**
     * Questo metodo restituisce la lista degli ingredienti della ricetta.
     * @return lista degli ingredienti della ricetta.
     */
    List<I> getIngredienti();

    /**
     * Questo metodo restituisce un'immagine della ricetta.
     * @return immagine della ricetta
     */
    String getImage();

    /**
     * Questo metodo restituisce il procedimento della ricetta.
     * @return il procedimento della ricetta
     */
    String getSteps();

    /**
     * Questo metodo restituisce l'id della ricetta.
     * @return id della ricetta.
     */
    int getIdRecipe();

    /**
     * Questo metodo restituisce il titolo della ricetta.
     * @return titolo della ricetta.
     */
    String getTitle();

    /**
     * Questo metodo restituisce l'id dell'utente che ha scritto la ricetta.
     * @return id dell'utente che ha scritto la ricetta.
     */
    int getIdUser();









}
