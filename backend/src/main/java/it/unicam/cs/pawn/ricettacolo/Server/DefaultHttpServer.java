package it.unicam.cs.pawn.ricettacolo.Server;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import it.unicam.cs.pawn.ricettacolo.Server.Servlet.*;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class DefaultHttpServer implements DefaultServer{


    private final ThreadPoolExecutor tpe = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    private Server server;

    private ServletContextHandler recipeServletContexHandler;


    private ServletContextHandler userServletContextHandler;

    private ServletContextHandler reviewServletContextHandler;

    public DefaultHttpServer() {

    }

    @Override
    public void createServer() throws Exception{
        server = new Server();
        ServerConnector sc = new ServerConnector(server);
        sc.setPort(8000);
        server.setConnectors(new Connector[]{sc});
        //aggiunta del package recipe
        this.recipeServletContexHandler = new ServletContextHandler(this.server,"/recipe");
        this.recipeServletContexHandler.addServlet(RecipeServlet.class, "/random");
        this.recipeServletContexHandler.addServlet(IngredientsListServlet.class,"/ingredientsList");
        this.recipeServletContexHandler.addServlet(AddRecipeServlet.class,"/addRecipe");
        this.recipeServletContexHandler.addServlet(SearchRecipeServlet.class,"/search");

        //aggiunta del package user
        userServletContextHandler = new ServletContextHandler(this.server,"/user");
        userServletContextHandler.setContextPath("/user");
        userServletContextHandler.addServlet(TryLogin.class,"/tryLogin");


        //aggiunta del package review
        this.reviewServletContextHandler = new ServletContextHandler(this.server,"/review");
        this.reviewServletContextHandler.setContextPath("/review");
        this.reviewServletContextHandler.addServlet(ReviewsRecipeServlet.class,"/allReview");
        this.reviewServletContextHandler.addServlet(AddLikeReview.class,"/addLike");
        this.reviewServletContextHandler.addServlet(AddDislikeReview.class,"/addDislike");
        this.reviewServletContextHandler.addServlet(RemoveLikeReview.class,"/removeLike");
        this.reviewServletContextHandler.addServlet(RemoveDislikeReview.class,"/removeDislike");
        this.reviewServletContextHandler.addServlet(AddReviewServlet.class,"/addReview");


        HandlerCollection hc = new ContextHandlerCollection();
        hc.addHandler(recipeServletContexHandler);
        hc.addHandler(userServletContextHandler);
        hc.addHandler(this.reviewServletContextHandler);
        this.server.setHandler(hc);
        this.server.start();
        this.server.join();
        System.out.println("server is started?"+this.server.isStarted());
    }


    @Override
    public void stopServer() {
        if(server!=null){
            try {
                this.server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
