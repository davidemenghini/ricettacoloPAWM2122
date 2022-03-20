package it.unicam.cs.pawn.ricettacolo.Persistence;

import it.unicam.cs.pawn.ricettacolo.DefaultIngredients;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Ingredients;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.DefaultRecipe;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Recipes.Recipe;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.DefaultReview;
import it.unicam.cs.pawn.ricettacolo.Server.Model.Review.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;

import it.unicam.cs.pawn.ricettacolo.Server.Model.User.DefaultUser;
import it.unicam.cs.pawn.ricettacolo.Server.Model.User.User;

public class DefaultDatabase implements Database<Ingredients> {


    private Connection connection;
    private ResultSet rs;


    public DefaultDatabase() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ricettacolo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET",
                    "root","davide98");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("SQLException: " + throwables.getMessage());
            System.out.println("SQLState: " + throwables.getSQLState());
            System.out.println("VendorError: " + throwables.getErrorCode());
        }
    }




    @Override
    public boolean tryLogin(String username, String password) {
        if(this.connection!=null && !username.equals("") && !password.equals("")){
            try {
                PreparedStatement ps =connection.prepareStatement("SELECT * FROM ricettacolo.users WHERE username=? AND password=?");
                ps.setString(1,username);
                ps.setString(2,password);
                rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public Recipe<Ingredients> getRecipe(String nameRecipe) {
        return null;
    }

    @Override
    public List<Recipe<Ingredients>> getAllRecipesUser(int user) {
        if(connection!=null){
            List<Recipe<Ingredients>> listRecipesUser = new ArrayList<>();
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM ricettacolo.recipes WHERE idUser=?");
                ps.setInt(1,user);
                rs = ps.executeQuery();
                while(rs.next()){
                    listRecipesUser.add(getRecipe(rs));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return listRecipesUser;
        }
        return null;
    }



    @Override
    public List<Review> getReviewsOfRecipe(int id) {
        List<Review> list = new ArrayList<>();
        if(this.connection!=null){
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM ricettacolo.Reviews WHERE idRecipe=?");
                ps.setInt(1,id);
                readReviewsQuery(ps,list);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public List<Review> getReviewsOfUser(int idUser) {
        List<Review> list = new ArrayList<>();
        if(this.connection!=null){
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM ricettacolo.Reviews WHERE idUser=?");
                ps.setInt(1,idUser);
                readReviewsQuery(ps,list);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }


    private void readReviewsQuery(PreparedStatement ps, List<Review> list){
        try {
            rs = ps.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        while(true){
            try {
                boolean x = rs.next();
                if (!x) break;

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            addReviewToList(list,rs);
        }
    }

    private void addReviewToList(List<Review> list, ResultSet rs) {
        int idU,idReview,idRecipe,  likesNumber,  dislikesNumber;
        String title, description;
        try {
            idU = rs.getInt("idUser");
            likesNumber = rs.getInt("LikeNumber");
            dislikesNumber = rs.getInt("Dislike");
            title = rs.getString("title");
            description = rs.getString("Description");
            idReview = rs.getInt("idReviews");
            idRecipe = rs.getInt("idRecipe");
            list.add(new DefaultReview(idReview,idRecipe,idU,title,description,likesNumber,dislikesNumber));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Recipe<Ingredients> getRandomRecipe() {
        if(this.connection!=null){
            try{
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM ricettacolo.recipes ORDER BY RAND() LIMIT 1");
                rs = ps.executeQuery();
                if(rs.next()){
                    return getRecipe(rs);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    private Recipe<Ingredients> getRecipe(ResultSet rs) {
        int idR, idU;
        String image;
        String title, steps;
        try {
            idR = rs.getInt("idRecipe");
            image = rs.getString("image");
            title = rs.getString("Title");
            steps = rs.getString("Steps");
            List<Ingredients> il = getIngredientsFromRecipe(idR);
            idU = rs.getInt("idUser");
            return new DefaultRecipe(idR,il,image,title,steps,idU);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int retriveIdFromUser(String username) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT idUsers FROM ricettacolo.users WHERE username=?");
                ps.setString(1,username);
                rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getInt("idUsers");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public List<Ingredients> getIngredientsFromRecipe(int id) {
        List<Ingredients> ingredientsList = new ArrayList<>();
        if(this.connection!=null){
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM ricettacolo.ingredients i INNER JOIN ricettacolo.recipeingredients ri ON i.idIngredient=ri.idI WHERE ri.idR=?");
                ps.setInt(1,id);
                rs = ps.executeQuery();
                while(rs.next()){
                    addIngredient(ingredientsList,rs);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return ingredientsList;
    }

    @Override
    public void addLikeReview(int idReview, int actual, int idUser) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("UPDATE ricettacolo.reviews SET `LikeNumber` = ? WHERE idReviews=?");
                ps.setInt(1,actual);
                ps.setInt(2,idReview);
                ps.executeUpdate();
                addLikeToLikereview(idReview,idUser);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void addLikeToLikereview(int idReview, int idUser) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ricettacolo.likesreview VALUES (?,?)");
            ps.setInt(1,idUser);
            ps.setInt(2,idReview);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int getLikeReview(int idReview) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT  `LikeNumber` FROM ricettacolo.reviews WHERE idReviews=?");
                ps.setInt(1,idReview);
                rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getInt("LikeNumber");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public int getDislikeReview(int idReview) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT Dislike FROM ricettacolo.reviews WHERE idReviews=?");
                ps.setInt(1,idReview);
                rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getInt("Dislike");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return 0;
    }

    @Override
    public void addDislikeReview(int idReview, int actual, int idUser) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("UPDATE ricettacolo.reviews SET Dislike = ? WHERE idReviews=?");
                ps.setInt(1,actual);
                ps.setInt(2,idReview);
                ps.executeUpdate();
                addDislikeTodislikereview(idReview,idUser);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public boolean isLikedToUser(int idReview, int idUser) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM ricettacolo.likesreview WHERE user_id=?  AND review_id=?");
                ps.setInt(1,idUser);
                ps.setInt(2,idReview);
                this.rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean isDislikedToUser(int idReview, int idUser) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM ricettacolo.dislikereview WHERE id_user=? AND id_review=?");
                ps.setInt(1,idUser);
                ps.setInt(2,idReview);
                this.rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Review getReview(int idReview) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM ricettacolo.recipes WHERE idRecipe=? LIMIT 1");
                ps.setInt(1,idReview);
                rs = ps.executeQuery();
                if(rs.next()){
                    List<Review> list = new ArrayList<>();
                    addReviewToList(list,rs);
                    return list.get(0);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void removeDislikeReview(int idReview, int i, int idUser) {
        if(this.connection!=null){
            try{
                PreparedStatement ps = this.connection.prepareStatement("DELETE FROM ricettacolo.dislikereview WHERE id_user=? AND id_review=?");
                ps.setInt(1,idUser);
                ps.setInt(2,idReview);
                ps.executeUpdate();
                ps = this.connection.prepareStatement("UPDATE ricettacolo.reviews SET Dislike=? WHERE idReviews = ?");
                ps.setInt(1,i);
                ps.setInt(2,idReview);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void removeLikeReview(int idReview, int i, int idUser) {
        if(this.connection!=null){
            try{
                PreparedStatement ps = this.connection.prepareStatement("DELETE FROM ricettacolo.likesreview WHERE user_id=? AND review_id=?");
                ps.setInt(1,idUser);
                ps.setInt(2,idReview);
                ps.executeUpdate();
                ps = this.connection.prepareStatement("UPDATE ricettacolo.reviews SET LikeNumber=? WHERE idReviews = ?");
                ps.setInt(1,i);
                ps.setInt(2,idReview);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public void addRecipe(Recipe<Ingredients> r) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("INSERT INTO ricettacolo.recipes(idRecipe, image, Title, Steps, idUser) VALUE (?,?,?,?,?)");
                ps.setInt(1,r.getIdRecipe());
                ps.setString(2,r.getImage());
                ps.setString(3,r.getTitle());
                ps.setString(4,r.getSteps());
                ps.setInt(5,r.getIdUser());
                int i = ps.executeUpdate();
                ps = this.connection.prepareStatement("INSERT INTO ricettacolo.recipeingredients (idRecipeIngredients, idR, idI) values (?,?,?)");
                for(Ingredients x : r.getIngredienti()){
                    int y = generateUniqueId();
                    ps.setInt(1,y);
                    ps.setInt(2,r.getIdRecipe());
                    ps.setInt(3,x.getId());
                    int yy = ps.executeUpdate();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private int generateUniqueId() {
        return (int) (Math.random() * (21474836));
    }

    @Override
    public boolean checkifIdUserIsPresent(int check) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public User getUser(String username, int id) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM ricettacolo.users WHERE idUsers=? AND username=? LIMIT 1");
                ps.setInt(1,id);
                ps.setString(2,username);
                rs = ps.executeQuery();
                if(rs.next()){
                    return new DefaultUser(rs.getInt("idUsers"),
                            rs.getString("username"),
                            rs.getString("password"));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean addAccessTokenToDb(String cookie, int idU) {
        if(!cookie.equals("")){
            boolean ret = checkifCookieIsPresent(cookie);
            if(this.connection!=null && !ret){
                try {
                    PreparedStatement ps = this.connection.prepareStatement("UPDATE ricettacolo.users SET Access_token=?  WHERE idUsers=? ");
                    ps.setString(1,cookie);
                    ps.setInt(2,idU);
                    ps.executeUpdate();
                    return true;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return false;
    }

    @Override
    public boolean checkAccessCookie(int idUser, String cookie) {
        if (this.connection != null && !cookie.equals("")) {
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT Access_token FROM ricettacolo.users WHERE idUsers=? LIMIT 1");
                ps.setInt(1, idUser);
                this.rs = ps.executeQuery();
                if (rs.next()) {
                    String at = rs.getString("Access_token");
                    return at.equals(cookie);
                } else return false;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public void deleteAccessCookie(int idUser) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("UPDATE ricettacolo.users SET Access_token=? WHERE idUsers=?");
                ps.setNull(1,Types.VARCHAR);
                ps.setInt(2,idUser);
                ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public int retriveIdFromNameIngredients(String name) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT ingredients.idIngredient FROM ricettacolo.ingredients WHERE name=?");
                ps.setString(1,name);
                this.rs = ps.executeQuery();
                if(rs.next()){
                    return rs.getInt("idIngredient");
                }else return -1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return -1;
            }
        }else return -1;
    }

    @Override
    public List<Ingredients> getAllIngredients() {
        List<Ingredients> list = new ArrayList<>();
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM ricettacolo.ingredients");
                this.rs = ps.executeQuery();
                while(rs.next()){
                    addIngredient(list,rs);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void addReview(Review review) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("INSERT INTO ricettacolo.reviews VALUE (?,?,?,?,?,?,?)");
                ps.setInt(1,review.getIDReview());
                ps.setInt(2,review.getIDRecipe());
                ps.setInt(3,review.getUser());
                ps.setString(4,review.getTitle());
                ps.setInt(5,review.getLikeNumber());
                ps.setInt(6,review.getDislikeNumber());
                ps.setString(7,review.getDescription());
                int x = ps.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public List<Recipe<Ingredients>> getRecipesFromTitle(String title) {
        List<Recipe<Ingredients>> list = new ArrayList<>();
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM ricettacolo.recipes WHERE Title LIKE CONCAT('%',?,'%')");
                ps.setString(1,title);

                this.rs = ps.executeQuery();
                boolean check = rs.next();
                while(check){
                    int idRecipe = rs.getInt("idRecipe");
                    String image = rs.getString("image");
                    int idUser = rs.getInt("idUser");
                    String titleR = rs.getString("title");
                    String steps = rs.getString("steps");
                    list.add(new DefaultRecipe(idRecipe,
                            getIngredientsFromRecipe(idRecipe),
                            image,
                            titleR,
                            steps,
                            idUser));
                    check = rs.next();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return list;
    }

    private boolean checkifCookieIsPresent(String cookie) {
        if(this.connection!=null){
            try {
                PreparedStatement ps = this.connection.prepareStatement("SELECT Access_token FROM ricettacolo.users WHERE Access_token=?");
                ps.setString(1,cookie);
                this.rs = ps.executeQuery();
                return rs.next();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private void addIngredient(List<Ingredients> ingredientsList, ResultSet rs) {
        int id;
        String name;
        try {
            id = rs.getInt("idIngredient");
            name = rs.getString("name");
            ingredientsList.add(new DefaultIngredients(id,name));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    private void addDislikeTodislikereview(int idReview, int idUser){
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO ricettacolo.dislikereview VALUE (?,?)");
            ps.setInt(1,idUser);
            ps.setInt(2,idReview);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
