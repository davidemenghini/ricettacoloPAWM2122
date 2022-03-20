package it.unicam.cs.pawn.ricettacolo.Server.Model.User;

import it.unicam.cs.pawn.ricettacolo.Persistence.Database;
import it.unicam.cs.pawn.ricettacolo.Persistence.DefaultDatabase;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Ingredients;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Questa classe &egrave; l'implementazione dell'interfaccia AccessCookie.
 */
public class DefaultAccessCookie implements AccessCookie{

    private String cookie;


    private Database<Ingredients> db;


    public DefaultAccessCookie(){
        try {
            this.db = new DefaultDatabase();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String generateAccessCookie(int idUser) {
        return encryptString(idUser);
    }

    private String encryptString(int idU) {
        boolean check = false;
        String rs = "";
        while(!check){
            rs = getRandomString();
            check = generateRandomAccessToken(rs,idU);
        }
        return rs;
    }

    private String getRandomString(){
        byte[] array = new byte[24];
        SecureRandom r = new SecureRandom();
        r.nextBytes(array);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(array);
    }

    @Override
    public boolean checkAccessCookie(String cookie, int id) {
        return this.db.checkAccessCookie(id,cookie);
    }

    @Override
    public void deleteAccessToken(int idUser) {
        this.db.deleteAccessCookie(idUser);
    }

    @Override
    public String getCookie() {
        return this.cookie;
    }


    private boolean generateRandomAccessToken(String randomString, int idU){
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] es = md.digest(randomString.getBytes(StandardCharsets.UTF_8));
                StringBuilder s = new StringBuilder();
                for (byte b : es) {
                    s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
                }
                this.cookie = s.toString();
                return this.db.addAccessTokenToDb(s.toString(),idU);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return false;
            }
    }
}
