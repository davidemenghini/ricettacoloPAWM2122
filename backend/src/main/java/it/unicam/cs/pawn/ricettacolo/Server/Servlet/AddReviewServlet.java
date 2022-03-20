package it.unicam.cs.pawn.ricettacolo.Server.Servlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.DefaultHandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.DefaultReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.HandlerReview;

import it.unicam.cs.pawn.ricettacolo.Server.Model.User.AccessCookie;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.DefaultAccessCookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddReviewServlet extends HttpServlet {



    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        setCors(resp);
        JsonObject jsonObject = getJsonObject(req);
        if(jsonObject!=null){
            String atTemp = jsonObject.get("access_cookie").getAsString();
            String at = atTemp.replace("\"","");
            int idUser = jsonObject.get("idUser").getAsInt();
            AccessCookie ac = new DefaultAccessCookie();
            if(ac.checkAccessCookie(at,idUser)){

                addResponse(resp,jsonObject,idUser);
            }
        }
    }

    private void addResponse(HttpServletResponse resp, JsonObject jsonObject,int idUser) {
        HandlerReview hr = new DefaultHandlerReview();
        hr.addReview(new DefaultReview(jsonObject.get("idRecipe").getAsInt(),
                idUser,
                jsonObject.get("title").toString(),
                jsonObject.get("description").toString(),
                0,
                0));
    }

    private JsonObject getJsonObject(HttpServletRequest req) {
        try {
            String line = req.getReader().readLine();
            return (JsonObject) JsonParser.parseString(line);
        } catch (IOException e) {
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
