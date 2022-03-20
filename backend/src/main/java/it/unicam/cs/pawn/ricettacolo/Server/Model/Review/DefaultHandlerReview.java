package it.unicam.cs.pawn.ricettacolo.Server.Model.Review;

import it.unicam.cs.pawn.ricettacolo.Persistence.Database;
import it.unicam.cs.pawn.ricettacolo.Persistence.DefaultDatabase;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Ingredients;

import java.util.List;

/**
 * Questa classe &egrave; un'implementazione di default dell'interfaccia "HandlerReview".
 */
public class DefaultHandlerReview implements HandlerReview {

    private Database<Ingredients> db;



    @Override
    public List<Review> getReviewsUser(int idUser) {
        try {
            db = new DefaultDatabase();
            return db.getReviewsOfUser(idUser);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Review> getReviewsRecipe(int idRecipe) {
        try {
            db = new DefaultDatabase();
            return db.getReviewsOfRecipe(idRecipe);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addLikeToReview(int idReview, int idUser) {
        try {
            db = new DefaultDatabase();
            int actual = db.getLikeReview(idReview);
            db.addLikeReview(idReview,actual+1,idUser);
            return actual+1;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addDisikeToReview(int idReview, int idUser) {
        try {
            db = new DefaultDatabase();
            int actual = db.getDislikeReview(idReview);
            db.addDislikeReview(idReview,actual+1,idUser);
            return actual+1;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean isLikedToUser(int idReview, int idUser) {
        try {
            this.db = new DefaultDatabase();
            return db.isLikedToUser(idReview,idUser);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isDislikedToUser(int idReview, int idUser) {
        try {
            this.db = new DefaultDatabase();
            return db.isDislikedToUser(idReview,idUser);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getLikesReview(int idReview) {
        try {
            this.db = new DefaultDatabase();
            return this.db.getLikeReview(idReview);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getDislikeReview(int idReview) {
        try {
            this.db = new DefaultDatabase();
            return this.db.getDislikeReview(idReview);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int removeLikeToReview(int idReview, int idUser) {
        try {
            db = new DefaultDatabase();
            int actual = db.getLikeReview(idReview);
            db.removeLikeReview(idReview,actual-1,idUser);
            return Math.max(actual - 1, 0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int removeDislikeToReview(int idReview, int idUser) {
        try {
            db = new DefaultDatabase();
            int actual = db.getDislikeReview(idReview);
            db.removeDislikeReview(idReview,actual-1,idUser);
            return Math.max(actual - 1, 0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Review getReview(int idReview) {
        try{
            db = new DefaultDatabase();
            return db.getReview(idReview);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void addReview(Review review) {
        try {
            this.db = new DefaultDatabase();
            this.db.addReview(review);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
