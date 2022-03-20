package it.unicam.cs.pawn.ricettacolo.Server.Servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.DefaultHandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.HandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.Review;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.AccessCookie;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.DefaultAccessCookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class RemoveLikeReview extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        setCors(resp);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        JsonObject jsonObject = getJsonObject(req);
        int idUser = jsonObject.get("idUser").getAsInt();
        String access_token = jsonObject.get("access_token").getAsString();
        int idReview = jsonObject.get("idReview").getAsInt();
        AccessCookie ac = new DefaultAccessCookie();
        if(ac.checkAccessCookie(access_token,idUser)){
            removeLike(resp,idReview,idUser);
        }else setNullResp(resp);
        try{
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setNullResp(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().write("non c'e nulla qui");
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeLike(HttpServletResponse resp, int idReview, int idUser) {
        HandlerReview hr = new DefaultHandlerReview();
        int newLike = hr.getLikesReview(idReview);
        if(hr.isLikedToUser(idReview,idUser) && !hr.isDislikedToUser(idReview,idUser)){
            newLike = hr.removeLikeToReview(idReview,idUser);
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("newLike",Integer.toString(newLike));
        try {
            resp.getWriter().write(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject getJsonObject(HttpServletRequest req) {
        JsonObject json = new JsonObject();
        try {
            String line = req.getReader().readLine();
            return (JsonObject) JsonParser.parseString(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void doOptions(HttpServletRequest req, HttpServletResponse resp){
        setCors(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }




    private void setCors(HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");

    }
}
