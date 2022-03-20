package it.unicam.cs.pawn.ricettacolo.Server.Servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.DefaultRecipeHandler;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Ingredients;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.RecipeHandler;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IngredientsListServlet extends HttpServlet {



    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        setCors(resp);
        RecipeHandler<Ingredients> rh = new DefaultRecipeHandler();
        List<Ingredients> list = rh.getIngredientsList();
        List<String> listNameIngredients = new ArrayList<>();
        list.forEach(x->listNameIngredients.add(x.getName()));
        String json = new Gson().toJson(listNameIngredients);
        try{
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse resp){
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }


    private void setCors(HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");

    }
}
