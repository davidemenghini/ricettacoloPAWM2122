package it.unicam.cs.pawn.ricettacolo.Server.Servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class TryLogin extends HttpServlet {

    private LoginHandler lh;

    private User user;

    private AccessCookie ac;



    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        setCors(resp);
        setCharacterRequest(req);
        this.lh = getLoginHandler(req);
        int id = Objects.requireNonNull(lh).getId();
        this.lh.addSecretTopsw();
        this.lh.addSaltTopsw();
        if(this.lh.encodePassword() && this.lh.tryLogin()){
            Runnable thread1  = () -> addUserInfoToResp(resp,lh);
            thread1.run();
        }else{
            setNullResponse(resp);
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("appplication/json");
        try {
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setNullResponse(HttpServletResponse resp) {
        try {
            resp.getWriter().write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCharacterRequest(HttpServletRequest req) {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void addUserInfoToResp(HttpServletResponse resp, LoginHandler lh) {
        this.ac = new DefaultAccessCookie();
        this.ac.generateAccessCookie(lh.getId());
        String accessCookie = this.ac.getCookie();
        this.user = lh.getUser();
        try {
            Map<String,String> map = new HashMap<>();
            map.put("idUser", String.valueOf(this.user.getID()));
            map.put("user", this.user.getUser());
            map.put("access_token",accessCookie);
            String json = new ObjectMapper().writeValueAsString(map);
            resp.getWriter().write(json);
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private LoginHandler getLoginHandler(HttpServletRequest req) {
        try {
            Reader line = req.getReader();
            HashMap<String,String> result = new ObjectMapper().readValue(line, HashMap.class);
            String user =  result.get("idUser");
            String pass =  result.get("pass");
            return new DefaultLoginHandler(user,pass);
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
