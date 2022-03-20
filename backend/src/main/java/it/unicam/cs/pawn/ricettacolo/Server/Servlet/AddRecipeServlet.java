package it.unicam.cs.pawn.ricettacolo.Server.Servlet;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unicam.cs.pawn.ricettacolo.DefaultIngredients;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.DefaultRecipeHandler;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Ingredients;

import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.RecipeHandler;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.AccessCookie;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.DefaultAccessCookie;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import java.util.List;

public class AddRecipeServlet extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        setCors(resp);
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonObject map = getParameterFromJson(req);
        if(map!=null){
            String acDoubleQuotes = map.get("access_token").toString();
            String ac = acDoubleQuotes.replace("\"","");
            int idUser = map.get("idUser").getAsInt();
            AccessCookie accessCookie = new DefaultAccessCookie();
            if(accessCookie.checkAccessCookie(ac,idUser)){
                RecipeHandler<Ingredients> rh = new DefaultRecipeHandler();
                addRecipeToHandler(rh,map,idUser);
            }else setNullResp(resp);
        }else setNullResp(resp);
        try{
            resp.getWriter().flush();
            resp.getWriter().close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addRecipeToHandler(RecipeHandler<Ingredients> rh, JsonObject map, int idUser) {
        JsonArray jsonArray = map.getAsJsonArray("ingredientList");
        List<Ingredients> il = new ArrayList<>();
        String name;
        String nameWithQuotes;
        for(int i=0;i<jsonArray.size();i++){
            nameWithQuotes = jsonArray.get(i).toString();
            name = nameWithQuotes.replace("\"","");
            il.add(new DefaultIngredients(name));
        }

        String title = map.get("title").toString().replace("\"","");
        String description = map.get("description").toString().replace("\"","");
        String image = map.get("image").toString().replace("\"","");

        rh.addNewRecipe(il,image,title,description,idUser);
    }

    private void setNullResp(HttpServletResponse resp) {
        try{
            resp.getWriter().write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private JsonObject getParameterFromJson(HttpServletRequest req) {
        JsonObject jsonObject;
        String line;
        try {
            line = req.getReader().readLine();
            jsonObject = (JsonObject) JsonParser.parseString(line);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
