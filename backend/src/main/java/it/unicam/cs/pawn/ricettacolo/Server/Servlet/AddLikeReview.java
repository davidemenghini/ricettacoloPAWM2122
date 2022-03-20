package it.unicam.cs.pawn.ricettacolo.Server.Servlet;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.JsonObject;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.DefaultHandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.HandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.AccessCookie;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.DefaultAccessCookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AddLikeReview extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        setCors(resp);
        setCharacterRequest(req);
        Map<String,String> mapRequest = getMap(req);
        if(mapRequest!=null){
            int idUser = Integer.parseInt(mapRequest.get("idUser")), idReview = Integer.parseInt(mapRequest.get("idReview"));
            String access_token = mapRequest.get("access_token");
            AccessCookie lh = new DefaultAccessCookie();
            if(lh.checkAccessCookie(access_token,idUser)){
                addTextToJson(resp,idUser,idReview);
            } else setNullResponse(resp);
        } else setNullResponse(resp);

    }

    private void setNullResponse(HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().write("");
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addTextToJson(HttpServletResponse resp, Integer idUser, Integer idReview) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HandlerReview hr = new DefaultHandlerReview();
        if(!hr.isLikedToUser(idReview,idUser) && !hr.isDislikedToUser(idReview,idUser)){
            int newLike = hr.addLikeToReview(idReview,idUser);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("newLike",newLike);
            String json = jsonObject.toString();
            try {
                resp.getWriter().write(json);
                resp.getWriter().flush();
                resp.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private Map<String, String> getMap(HttpServletRequest req) {

        try {
            String line = req.getReader().readLine();
            return new ObjectMapper().readValue(line, HashMap.class);
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


    private void setCharacterRequest(HttpServletRequest req) {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}