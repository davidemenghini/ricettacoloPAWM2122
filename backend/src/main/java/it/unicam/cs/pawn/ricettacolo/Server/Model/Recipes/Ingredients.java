package it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes;

/**
 * Questa interfaccia rappresenta un ingrediente di una ricetta.
 */
public interface Ingredients {

    /**
     * Questo metodo restituisce il nome dell'ingrediente.
     * @return il nome dell'ingrediente.
     */
    String getName();

    /**
     * Questo metodo restituisce l'id dell'ingrediente.
     * @return id dell'ingrediente
     */
    int getId();

}
