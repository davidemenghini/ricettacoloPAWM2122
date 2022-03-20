package it.unicam.cs.pawn.ricettacolo.Server.Servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.DefaultRecipeHandler;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Ingredients;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Recipe;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.RecipeHandler;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class RecipeServlet extends HttpServlet{


    private final Gson json = new Gson();




    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        setStandardCors(resp);
        RecipeHandler<Ingredients> rh = new DefaultRecipeHandler();
        Recipe<Ingredients> recipe = rh.getRandomRecipe();
        String recipeToJson = this.json.toJson(recipe);
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out.print(recipeToJson);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    static void setStandardCors(HttpServletResponse resp){
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }


}
