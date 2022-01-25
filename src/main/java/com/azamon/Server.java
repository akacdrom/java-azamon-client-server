package com.azamon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Server {

        public static void main(String[] args) throws Exception {
            while (true){
                choose();
            }
        }

        /*Method for user menu*/
        public static void choose() throws Exception {
            Scanner myObj = new Scanner(System.in);
            System.out.println("1- Create New Admin");
            System.out.println("2- Create New User");
            System.out.println("3- Create New Employee");
            System.out.println("4- Add book to system");
            System.out.print("Enter a number from menu: ");

            /*Check user input is int*/
            while (!myObj.hasNextInt()){
                System.out.println("Input is not a number.");
                myObj.nextLine();
            }

            int selection = myObj.nextInt();

            /*User's request returning the wished method*/
            switch (selection) {
                case 1 -> addAdmin();
                case 2 -> addUser();
                case 3 -> addEmployee();
                case 4 -> addBook();
                default -> throw new IllegalStateException("Unexpected value: " + selection);
            };
        }

        /*Method for create book row to database*/
        public static void addBook() throws Exception {
            System.out.println("Adding book..");

            /*Getting needed data for create book from user*/
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Book Title: ");
            String bookTitle = userInput.readLine();
            System.out.print("Book Description: ");
            String bookDesc = userInput.readLine();
            System.out.print("Book Author: ");
            String bookAuthor = userInput.readLine();
            System.out.print("Book Genre: ");
            String bookGenre = userInput.readLine();

            /* I used constructor for assign values*/
            Book book = new Book(bookTitle,bookDesc,bookAuthor,bookGenre);

            BookDao bookDao = new BookDao();

            /* Add new record*/
            bookDao.saveBook(book);
        }

        public static void addAdmin() throws Exception {
            /*Credentials getting from credentials method as an array*/
            String[] credentials = credentials();

            /*Parsed Credentials putting to different strings*/
            String name = credentials[0];
            String surname = credentials[1];
            String email = credentials[2];
            String password = credentials[3];

            /*Client's password, from input sending to the method for encryption
            and parsing the String to the array which include aes KEY, aes IV and encrypted password.*/
            String[] aesKeys = encryptionSignUp(password).split(",");
            String encryptedPassword = aesKeys[0];
            String secretKey = aesKeys[1];
            String iv = aesKeys[2];
            /* I used constructor for assign values*/

            Admin admin = new Admin(name,surname,email,encryptedPassword,secretKey,iv);

            PersonDao personDao = new PersonDao();

            /* Add new record*/
            personDao.saveRegisteredPerson(admin);
        }

        public static void addUser() throws Exception {
            /*Credentials getting from credentials method as an array*/
            String[] credentials = credentials();

            /*Parsed Credentials putting to different strings*/
            String name = credentials[0];
            String surname = credentials[1];
            String email = credentials[2];
            String password = credentials[3];

                /*Client's password, from input sending to the method for encryption
                and parsing the String to the array which include aes KEY, aes IV and encrypted password.*/
            String[] aesKeys = encryptionSignUp(password).split(",");
            String encryptedPassword = aesKeys[0];
            String secretKey = aesKeys[1];
            String iv = aesKeys[2];
            /* I used constructor for assign values*/

            User user = new User(name,surname,email,encryptedPassword,secretKey,iv);

            PersonDao personDao = new PersonDao();

            /* Add new record*/
            personDao.saveRegisteredPerson(user);
        }
    public static void addEmployee() throws Exception {
        /*Credentials getting from credentials method as an array*/
        String[] credentials = credentials();

        /*Parsed Credentials putting to different strings*/
        String name = credentials[0];
        String surname = credentials[1];
        String email = credentials[2];
        String password = credentials[3];

        /*Client's password, from input sending to the method for encryption
        and parsing the String to the array which include aes KEY, aes IV and encrypted password.*/
        String[] aesKeys = encryptionSignUp(password).split(",");
        String encryptedPassword = aesKeys[0];
        String secretKey = aesKeys[1];
        String iv = aesKeys[2];
        /* I used constructor for assign values*/

        Employee employee = new Employee(name,surname,email,encryptedPassword,secretKey,iv);

        PersonDao personDao = new PersonDao();

        /* Add new record*/
        personDao.saveRegisteredPerson(employee);
    }

        //Method for taking a string and sending to the AES class for encryption.
        //Returning the Encrypted text
        public static String encryptionSignUp(String password) throws Exception {

            //Creating an instance of class AES
            AES aes = new AES();
            aes.init();

            //Password sending to encrypt method inside of AES class.
            String encryptedText = aes.encryptSignUp(password);

            /*Returns 3 Strings in a String. I put comma, and it will parse when this method called. */
            return encryptedText +","+ aes.exportKey() + "," + aes.exportIV();
        }

        public static String[] credentials() throws IOException, ExecutionException, InterruptedException, TimeoutException {

            //Credentials what came from client socket, getting parsed.
            return SocketConnection.getConnection().split(",");
        }

}
