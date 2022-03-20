package it.unicam.cs.pawn.ricettacolo;

import it.unicam.cs.pawn.ricettacolo.Persistence.Database;
import it.unicam.cs.pawn.ricettacolo.Persistence.DefaultDatabase;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Ingredients;

public class DefaultIngredients implements Ingredients {

    private int id;

    private final String name;


    public DefaultIngredients(int id, String name){
        this.id = id;
        this.name = name;
    }

    public DefaultIngredients(String name){
        this.name = name;
        try {
            Database<Ingredients> db = new DefaultDatabase();
            this.id = db.retriveIdFromNameIngredients(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            this.id = -1;
        }

    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getId() {
        return this.id;
    }
}
