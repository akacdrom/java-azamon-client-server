package com.azamon;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/*path for the secured resource*/
@Path("secured")
public class SecuredResource {
    @GET
    @Path("message")
    @Produces(MediaType.TEXT_PLAIN)

    /*Method for taking all books from the database for the authenticated user*/
    public String securedMethod() throws SQLException, IOException, ExecutionException, InterruptedException, TimeoutException {

        /*Data transferred by socket from Client to the Server*/
        String listBooksSQL = "SELECT title,description,author,genre FROM et_books WHERE title='"+SocketConnection.getConnection()+"';";
        Statement statement = DatabaseConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(listBooksSQL);
        while (resultSet.next()) {

            String bookTitle = resultSet.getString("title");
            String bookDescription = resultSet.getString("description");
            String bookAuthor = resultSet.getString("author");
            String bookGenre = resultSet.getString("genre");
            return ("Book Title: "+bookTitle+ " | "+
                    "Book Description: "+bookDescription+ " | "+
                    "Book Author: "+bookAuthor+ " | "+
                    "Book Genre: "+bookGenre);
        }
        return "Client is authorized! \n"  + "Data taken from Database written in Tomcat Console.";
    }
}