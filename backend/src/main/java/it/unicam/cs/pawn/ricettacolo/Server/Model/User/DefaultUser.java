package it.unicam.cs.pawn.ricettacolo.Server.Model.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * Questa classe &egrave; l'implementazione dell'interfaccia User.
 */
public class DefaultUser implements User {

    private String username;

    private final String password;

    private final URL urlSalt = getClass().getResource("salt.txt");

    private final int id;

    private  final URL urlSecret = getClass().getResource("secret.txt");


    public DefaultUser(String password,int id){
        this.id = id;
        this.password = password;
    }


    public DefaultUser(int id, String user, String password){
        this.id = id;
        this.username = user;
        this.password = password;
    }



    //@Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUser() {
        return this.username;
    }



    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void addSalt(String salt) {
    }

    @Override
    public void addSecret(String secret) {
    }


    @Override
    public void writeToFileSecret(List<String> text){
        try {
            writeToFile(text, Objects.requireNonNull(this.urlSecret).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFileSalt(List<String> text) {
        try {
            writeToFile(text, Objects.requireNonNull(this.urlSalt).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(List<String> text, URI uri){
        Path p;
        p = Paths.get(Objects.requireNonNull(uri));
        try {
            Files.write(p,text, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
