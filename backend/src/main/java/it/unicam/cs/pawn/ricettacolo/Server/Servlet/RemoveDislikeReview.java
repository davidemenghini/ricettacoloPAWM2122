package it.unicam.cs.pawn.ricettacolo.Server.Servlet;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.DefaultHandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.HandlerReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.AccessCookie;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.DefaultAccessCookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class RemoveDislikeReview extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        setCors(resp);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        JsonObject jsonObject = getMap(req);
        AccessCookie lh = new DefaultAccessCookie();
        String acWithDoubleQuotes = Objects.requireNonNull(jsonObject).get("access_token").toString();
        String access_token = acWithDoubleQuotes.replace("\"","");
        int idUser = Objects.requireNonNull(jsonObject).get("idUser").getAsInt();
        int idReview = jsonObject.get("idReview").getAsInt();
        if(lh.checkAccessCookie(access_token,idUser)){
            removeDislike(resp,idReview,idUser);
        }else setNullResp(resp);
        try {
            resp.getWriter().flush();
            resp.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeDislike(HttpServletResponse resp, int idReview, int idUser) {
        HandlerReview hr = new DefaultHandlerReview();
        if(hr.isDislikedToUser(idReview, idUser) && !hr.isLikedToUser(idReview, idUser)){
            String s = getnewDislike(idReview, idUser);
            try {
                resp.getWriter().write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else setNullResp(resp);

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

    private JsonObject getMap(HttpServletRequest req) {
        try {
            String line = req.getReader().readLine();
            return (JsonObject) JsonParser.parseString(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonObject();
    }

    private String getnewDislike(int idReview, int idUser) {
        HandlerReview hr = new DefaultHandlerReview();
        int newDislike = hr.removeDislikeToReview(idReview,idUser);
        JsonObject json = new JsonObject();
        json.addProperty("newDislike",newDislike);
        return json.toString();
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
