package it.unicam.cs.pawn.ricettacolo.Server.Servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.DefaultRecipeHandler;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Ingredients;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Recipe;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.RecipeHandler;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


public class SearchRecipeServlet extends HttpServlet {



    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        setCors(resp);
        String title = getTitleFromReq(req);
        List<Recipe<Ingredients>> list = getListFromTitle(title);

        try {
            resp.getWriter().write(new Gson().toJson(list));
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private List<Recipe<Ingredients>> getListFromTitle(String title) {
        RecipeHandler<Ingredients> rh = new DefaultRecipeHandler();
        return rh.getRecipeListFromTitle(rh,title);
    }

    private String getTitleFromReq(HttpServletRequest req) {
        try {
            String line = req.getReader().readLine();
            JsonObject json = (JsonObject) JsonParser.parseString(line);
            String titleDoubleQuotes = json.get("title").toString();
            return titleDoubleQuotes.replace("\"","");
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse resp){
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }




    private void setCors(HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");

    }
}
