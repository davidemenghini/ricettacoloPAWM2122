package it.unicam.cs.pawn.ricettacolo.Server.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.DefaultHandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.HandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.Review;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.AccessCookie;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.DefaultAccessCookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ReviewsRecipeServlet extends HttpServlet {


    private int idUser = -1;
    private String access_token;


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        setCors(resp);
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }
        List<Review> hr = getReviewList(req);
        AccessCookie ac = new DefaultAccessCookie();
        String json = "";
        if(this.idUser!=-1 && ac.checkAccessCookie(access_token,this.idUser)){
            json = generateJson(hr,true,new DefaultHandlerReview());
        }else{
            json = generateJson(hr,false,new DefaultHandlerReview());
        }
        try {
            resp.getWriter().write(json);
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateJson(List<Review> hr, boolean isLogged, HandlerReview handlerReview) {
        JsonArray jsonArray = new JsonArray();
        JsonObject jso = new JsonObject();
            for(Review r : Objects.requireNonNull(hr)){
                jso.addProperty("user",r.getUser());
                jso.addProperty("title",r.getTitle());
                jso.addProperty("description",r.getDescription());
                jso.addProperty("likesNumber",r.getLikeNumber());
                jso.addProperty("dislikesNumber",r.getDislikeNumber());
                jso.addProperty("idReview",r.getIDReview());
                jso.addProperty("idRecipe",r.getIDRecipe());
                if(isLogged){
                    jso.addProperty("likedUser",handlerReview.isLikedToUser(r.getIDReview(),this.idUser));
                    jso.addProperty("dislikedUser",handlerReview.isDislikedToUser(r.getIDReview(),this.idUser));
                }else{
                    jso.addProperty("likedUser",false);
                    jso.addProperty("dislikedUser",false);
                }
                jsonArray.add(jso);
                jso = new JsonObject();
            }
        Gson gson = new Gson();
        return gson.toJson(jsonArray);
    }


    private List<Review> getReviewList(HttpServletRequest req){
        try {
            String line = req.getReader().readLine();

            Map<String,String> result = new ObjectMapper().readValue(line, Map.class);

            int id = Integer.parseInt(result.get("idRecipe"));
            String access_token = "";
            if(result.containsKey("idUser") && result.containsKey("access_token")){
                this.idUser = Integer.parseInt(result.get("idUser"));
                access_token = result.get("access_token");
            }
            if(this.idUser!=-1 && !access_token.equals("")){

                this.access_token = access_token;
            }
            HandlerReview hr = new DefaultHandlerReview();
            return hr.getReviewsRecipe(id);
        } catch (IOException e) {
            e.printStackTrace();
            e.getCause().printStackTrace();
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
