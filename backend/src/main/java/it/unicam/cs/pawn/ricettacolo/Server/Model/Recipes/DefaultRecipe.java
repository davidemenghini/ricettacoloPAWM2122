package it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes;

import java.util.List;

/**
 * Questa classe &egrave; un'implemntazione standard dell'interfaccia Recipe.
 */
public class DefaultRecipe implements Recipe<Ingredients> {

    private final List<Ingredients> ingredients;


    private final int idUser;


    private final int idRecipe;

    private final String image;

    private final String title;

    private final String steps;




    public DefaultRecipe(int idRecipe, List<Ingredients> ingredients, String image, String title, String steps, int iduser) {
        this.idRecipe = idRecipe;
        this.ingredients = ingredients;
        this.image = image;
        this.title = title;
        this.steps = steps;
        this.idUser = iduser;
    }


    public DefaultRecipe(List<Ingredients> ingredients, String image, String title, String steps,int idUser) {
        this.ingredients = ingredients;
        this.image = image;
        this.title = title;
        this.steps = steps;
        this.idRecipe = generateUniqueId();
        this.idUser = idUser;
    }

    private int generateUniqueId() {
        return (int) (Math.random() * (21474836));
    }

    @Override
    public List<Ingredients> getIngredienti() {
        return this.ingredients;
    }

    @Override
    public String getImage() {
        return this.image;
    }

    @Override
    public String getSteps() {
        return this.steps;
    }

    @Override
    public int getIdRecipe() {
        return this.idRecipe;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getIdUser() {
        return this.idUser;
    }

}
