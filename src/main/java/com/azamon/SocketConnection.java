package com.azamon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketConnection {
    public static String getConnection() throws IOException, ExecutionException, InterruptedException, TimeoutException {
        ServerSocket server = null;

        /*Socket connections preferring*/
        System.out.println("Waiting for Socket connection");
        server = new ServerSocket(9806);
        server.setReuseAddress(true);

            /*Request from client getting accepted*/
            Socket client  = server.accept();
            System.out.println("Socket Connection is successful");

            /*I tried using Runneble instead of Callable because I was able to run Parallel Client program with the server in same time.
            But with Runneble, I wasn't able to return value. I spent time with ExecutorService but couldn't find solution.
            In Calleble ".get" method is preventing start new pool and thread until current one finishes.
            I spent lots of time in that but couldn't find solution.*/
            ExecutorService executorService = Executors.newCachedThreadPool();
            Callable<String> clientSock = new ClientHandler(client);
            Future<String> getCredentials = executorService.submit(clientSock);

            //Closing the socket connection with server for new client requests.
            //It will be prevent restarting the server program each time.
            server.close();
            return getCredentials.get();

    }

    /*Method for take all the data from send by client by socket in a Thread*/
    private static class ClientHandler implements Callable<String> {

        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public String call() {

            //Reading data which coming from socket
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            String credentials = null;
            try {
                credentials = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return credentials;
        }
    }
}

