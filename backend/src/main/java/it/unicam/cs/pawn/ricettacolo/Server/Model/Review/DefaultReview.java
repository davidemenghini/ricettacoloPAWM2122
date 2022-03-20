package it.unicam.cs.pawn.ricettacolo.Server.Model.Review;


import java.util.concurrent.ThreadLocalRandom;

/**
 * Questa classe &egrave; un'implementazione di default dell'interfaccia Review.
 */
public class DefaultReview implements Review {
    private final int user;
    private final String title;
    private final String description;
    private final int likesNumber;
    private final int dislikesNumber;
    private final int idReview;
    private final int idRecipe;

    public DefaultReview(int idReview,int idRecipe,int id, String title, String description, int likesNumber, int dislikesNumber) {
        this.user = id;
        this.title = title;
        this.description = description;
        this.likesNumber = likesNumber;
        this.dislikesNumber = dislikesNumber;
        this.idReview = idReview;
        this.idRecipe = idRecipe;
    }

    public DefaultReview(int idRecipe,int id, String title, String description, int likesNumber, int dislikesNumber) {
        this.user = id;
        this.title = title;
        this.description = description;
        this.likesNumber = likesNumber;
        this.dislikesNumber = dislikesNumber;
        this.idReview = generateUniqueId();
        this.idRecipe = idRecipe;
    }

    private int generateUniqueId() {
        return ThreadLocalRandom.current().nextInt();
    }

    @Override
    public int getUser() {
        return this.user;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getLikeNumber() {
        return likesNumber;
    }

    @Override
    public int getDislikeNumber() {
        return this.dislikesNumber;
    }

    @Override
    public int getIDReview() {
        return this.idReview;
    }

    @Override
    public int getIDRecipe() {
        return this.idRecipe;
    }
}
