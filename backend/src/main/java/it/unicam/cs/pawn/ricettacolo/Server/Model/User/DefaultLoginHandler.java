package it.unicam.cs.pawn.ricettacolo.Server.Model.User;

import it.unicam.cs.pawn.ricettacolo.Persistence.Database;
import it.unicam.cs.pawn.ricettacolo.Persistence.DefaultDatabase;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Ingredients;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;


/**
 * Questa classe &egrave; l'implementazione dell'interfaccia LoginHandler.
 */
public class DefaultLoginHandler implements LoginHandler {


    private String passWithSaltAndSecret;

    private String passWithSecret;

    private String passWithoutAnything;

    private int id = 1;

    private Database<Ingredients> database;

    private String username;

    private String hashPass = "";

    public DefaultLoginHandler(String username, String passWithoutAnything){
        try {
            database = new DefaultDatabase();
            this.username = username;
            this.id = database.retriveIdFromUser(this.username);
            this.passWithoutAnything = passWithoutAnything;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean tryLogin() {
        if((this.id!=-1)&& (!this.passWithSaltAndSecret.equals(""))){
            try {
                Database<Ingredients> db = new DefaultDatabase();
                return db.tryLogin(this.username,this.hashPass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }




    @Override
    public void addSaltTopsw(){
        this.passWithSaltAndSecret = this.passWithSecret+getSalt();
    }

    @Override
    public void addSecretTopsw() {
        this.passWithSecret = this.passWithoutAnything+getSecret();
    }

    @Override
    public int getId() {
        return this.id;
    }



    @Override
    public boolean encodePassword() {
        MessageDigest msg;
        try {
            msg = MessageDigest.getInstance("SHA-256");
            byte[] hash = msg.digest(this.passWithSaltAndSecret.getBytes(StandardCharsets.UTF_8));
            StringBuilder s = new StringBuilder();
            for (byte b : hash) {
                s.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            this.hashPass = s.toString();
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser() {
        if(this.database!=null){
            return this.database.getUser(this.username,this.id);
        }
        return null;
    }


    private String getSalt(){
        try {
            Path url = Paths.get(ClassLoader.getSystemResource("salt.txt").toURI());
            Stream<String> stream = Files.lines(url);
            return stream.parallel()
                    .map(x->x.split(" "))
                    .filter(x->x[0].equals(this.username))
                    .map(x->x[1]).findFirst().orElse("");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    private String getSecret(){
        try {
            Path url = Paths.get(ClassLoader.getSystemResource("secret.txt").toURI());
            Stream<String> stream = Files.lines(url);
            return stream.findAny().orElse("");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }


}
