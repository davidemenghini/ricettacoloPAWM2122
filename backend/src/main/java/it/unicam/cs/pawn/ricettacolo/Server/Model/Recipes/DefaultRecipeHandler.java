package it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes;

import it.unicam.cs.pawn.ricettacolo.Persistence.Database;
import it.unicam.cs.pawn.ricettacolo.Persistence.DefaultDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe &egrave; un'implemntazione standard dell'interfaccia RecipeHandler.
 */
public class DefaultRecipeHandler implements RecipeHandler<Ingredients> {

    private static Database<Ingredients> db;

    private volatile static int  Unique_ID = 0;

    @Override
    public Recipe<Ingredients> getRandomRecipe() {
        try {
            db = new DefaultDatabase();
            return db.getRandomRecipe();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public Recipe<Ingredients> addNewRecipe(List<Ingredients> ingredients, String image,String title, String steps, int idUser) {
        Recipe<Ingredients> r = new DefaultRecipe(ingredients, image,title,steps,idUser);
        try {
            db = new DefaultDatabase();
            db.addRecipe(r);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Ingredients> getIngredientsList() {
        List<Ingredients> list = new ArrayList<>();
        try{
            db = new DefaultDatabase();
            list = db.getAllIngredients();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Recipe<Ingredients>> getRecipeListFromTitle(RecipeHandler<Ingredients> rh, String title) {
        List<Recipe<Ingredients>> list = new ArrayList<>();
        try {
            db = new DefaultDatabase();
            list = db.getRecipesFromTitle(title);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static synchronized int createId(){
        int check = Unique_ID++;
        try {
            db = new DefaultDatabase();
            if(db.checkifIdUserIsPresent(check)){
                return check;
            }else{
                return createId();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return createId();
    }







    }
