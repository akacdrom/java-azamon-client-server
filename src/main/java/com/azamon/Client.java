package com.azamon;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        /*Socket connection starting*/
        System.out.println("Client started");
        Socket socket  = new Socket("localhost",9806);

        /*Request sending to the server*/
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

        /*Get credentials from client and send to the server by socket*/
        out.println(signUpChoice());

        /*Clean previous data* */
        out.flush();
    }

    public static String signUpChoice() throws IOException {
        Scanner myObj = new Scanner(System.in);
        System.out.println("1- Create Person");
        System.out.println("2- Search Book");
        System.out.print("Enter a number from menu: ");

        while (!myObj.hasNextInt()){
            System.out.println("Input is not a number.");
            myObj.nextLine();
        }
        int userName = myObj.nextInt();

        /*User's request returning the wished method*/
        return switch (userName) {
            case 1 -> getCredentials();
            case 2 -> searchBook();
                    default -> null;
        };
    }

    //Method for create user account and returning required Credentials
    public static String getCredentials() throws IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("First Name: ");
        String admin_first_name = userInput.readLine();
        System.out.print("Last Name: ");
        String admin_last_name = userInput.readLine();
        System.out.print("Email: ");
        String admin_email = userInput.readLine();
        System.out.print("Password: ");
        String admin_password = userInput.readLine();
        userInput.close();
        return admin_first_name + "," + admin_last_name + "," + admin_email + "," + admin_password;
        }

    public static String searchBook() throws IOException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Search book:  ");
        String getBook = userInput.readLine();

        return getBook;
    }

}

