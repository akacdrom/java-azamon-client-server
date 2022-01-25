package com.azamon;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

    /*String for data for what comes from header*/
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic";
    private static final String SECURED_URL_PREFIX = "secured";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        /* Condition for checking URL is secured  */
        if (requestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)){

            /*Get the header value*/
            List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER);

            /* Condition for checking header value is contains Authorization*/
            if (authHeader != null && authHeader.size() > 0 ){
                try {

                /* Get the first token from header*/
                String authToken = authHeader.get(0);

                /* Taking out "Basic" and putting the Base64 token to the string.*/
                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");

                /*Decode the Base64 token.*/
                byte[] decodedBytes = Base64.getMimeDecoder().decode(authToken);

                /*Convert decoded bytes to the string*/
                String decodedString = new String(decodedBytes);

                /*Parse the email and password from decoded token string*/
                String [] credentialsClient = decodedString.split(":");
                String emailClient = credentialsClient[0];
                String passwordClient = credentialsClient[1];

                /*Check credentials' admin login*/
                if (adminLogin(emailClient,passwordClient) != null){
                    String [] credentialsDbAdmin = adminLogin(emailClient,passwordClient).split(",");
                    String emailDb = credentialsDbAdmin[0];
                    String passwordDb = credentialsDbAdmin[1];

                    /*Last time check the admin input and data in database is match*/
                    if (emailClient.equals(emailDb) && passwordClient.equals(passwordDb)){
                        System.out.println("ADMIN Authentication is successful");
                        return;
                    }
                }
                /*Check credentials' user login*/
                else if (userLogin(emailClient,passwordClient) != null){
                    String [] credentialsDbUser = userLogin(emailClient,passwordClient).split(",");
                    String emailDb = credentialsDbUser[0];
                    String passwordDb = credentialsDbUser[1];

                    /*Last time check the user input and data in database is match*/
                    if (emailDb.equals(emailClient) && passwordDb.equals(passwordClient)){
                        System.out.println("USER Authentication is successful");
                        return;
                    }
                }
                /*Check credentials' employee login*/
                else if (employeeLogin(emailClient,passwordClient) != null){
                    String [] credentialsDbEmp = employeeLogin(emailClient,passwordClient).split(",");
                    String emailDb = credentialsDbEmp[0];
                    String passwordDb = credentialsDbEmp[1];

                    /*Last time check the employee input and data in database is match*/
                    if (emailDb.equals(emailClient) && passwordDb.equals(passwordClient)){
                        System.out.println("EMPLOYEE Authentication is successful");
                        return;
                    }
                }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /*If user input is not matches with data in database, abort. */
            Response unauthorizedStatus = Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("User cannot access the source")
                    .build();
            requestContext.abortWith(unauthorizedStatus);
        }
    }

    public static String adminLogin(String strEmail, String strPassword) throws Exception {

        PersonDao personDao = new PersonDao();
        Person person = personDao.getAdminByEmail(strEmail);
        if (person != null){
            String adminEmail = person.getEmail();
            String adminSecretKey = person.getSecret_key();
            String adminIV = person.getIv();
            String adminPassword = strPassword;

            String getPassword = encryptionLogInCheck(adminIV,adminSecretKey,adminPassword);

            /*Read the data coming from query and put strings*/
            if (person.getEmail() != null && person.getPassword().equals(getPassword)) {
                    return adminEmail+","+encryptionLogIn(adminIV,adminSecretKey,adminPassword);
            }
        }
        return null;
    }

    public static String userLogin(String strEmail, String strPassword) throws Exception {

        PersonDao personDao = new PersonDao();
        Person person = personDao.getUserByEmail(strEmail);
        if (person != null){
            String userEmail = person.getEmail();
            String userSecretKey = person.getSecret_key();
            String userIV = person.getIv();
            String userPassword = strPassword;

            String getPassword = encryptionLogInCheck(userIV,userSecretKey,userPassword);

            /*Read the data coming from query and put strings*/
            if (person.getPassword().equals(getPassword)) {
                return userEmail+","+encryptionLogIn(userIV,userSecretKey,userPassword);
            }
        }
        return null;
    }

    public static String employeeLogin(String strEmail, String strPassword) throws Exception {

        PersonDao personDao = new PersonDao();
        Person person = personDao.getEmployeeByEmail(strEmail);
        if (person != null){
            String employeeEmail = person.getEmail();
            String employeeSecretKey = person.getSecret_key();
            String employeeIV = person.getIv();
            String employeePassword = strPassword;

            String getPassword = encryptionLogInCheck(employeeIV,employeeSecretKey,employeePassword);

            /*Read the data coming from query and put strings*/
            if (person.getPassword().equals(getPassword)) {
                return employeeEmail+","+encryptionLogIn(employeeIV,employeeSecretKey,employeePassword);
            }
        }
        return null;
    }


    public static String encryptionLogInCheck(String IV, String secretKey, String password) throws Exception {

        /*Creating an instance of class AES*/
        AES aes = new AES();

        /*Get encrypted password by using secretKey and IV form Database*/
        aes.initFromStrings(secretKey,IV);
        String encryptedPassword = aes.encryptLogIn(password);
        return encryptedPassword;
    }

    public static String encryptionLogIn(String IV, String secretKey, String password) throws Exception {

        /*Creating an instance of class AES*/
        AES aes = new AES();
        aes.initFromStrings(secretKey,IV);

        /*Get encrypted password by using secretKey and IV form Database*/
        String encryptedPassword = aes.encryptLogIn(password);

        /*return data from the decryption method for decryption*/
        return decryption(IV,secretKey,encryptedPassword);
    }

    public static String decryption(String IV, String secretKey, String password) throws Exception {

        /*Creating an instance of class AES*/
        AES aes = new AES();
        aes.initFromStrings(secretKey,IV);

        /*Get decrypted password by using secretKey and IV form Database*/
        String decryptedPassword = aes.decrypt(password);
        return decryptedPassword;
    }
}