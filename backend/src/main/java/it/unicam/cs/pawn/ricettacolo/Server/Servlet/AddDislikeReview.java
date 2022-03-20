package it.unicam.cs.pawn.ricettacolo.Server.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.DefaultHandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.HandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.AccessCookie;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.DefaultAccessCookie;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.DefaultLoginHandler;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.LoginHandler;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddDislikeReview extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        setCors(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Map<String,String> map = getMap(req);
        if(map!=null){
            String idUser = Objects.requireNonNull(map).get("idUser");
            String idReview = map.get("idReview");
            String access_token = map.get("access_token");
            AccessCookie ac = new DefaultAccessCookie();
            if(ac.checkAccessCookie(access_token,Integer.parseInt(idUser))){
                addTextToJson(resp,Integer.parseInt(idUser),Integer.parseInt(idReview));
            }else{
                setNullResponse(resp);
            }
        }else setNullResponse(resp);
        try {
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
        resp.addHeader("Access-Control-Allow-Methods", "PATCH, GET, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
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


    private void setNullResponse(HttpServletResponse resp) {
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


    private void addTextToJson(HttpServletResponse resp, Integer idUser, Integer idReview) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HandlerReview hr = new DefaultHandlerReview();
        if(!hr.isDislikedToUser(idReview,idUser) && !hr.isLikedToUser(idReview,idUser)){
            int newDislike = hr.addDisikeToReview(idReview,idUser);
            Map<String, String> map = new HashMap<>();
            map.put("newDislike",Integer.toString(newDislike));
            String json = new Gson().toJson(map);

            try {
                resp.getWriter().write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void setCors(HttpServletResponse resp) {
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Methods", "PATCH, GET, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");

    }




}
