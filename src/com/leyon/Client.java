package com.leyon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String serverAddress = "localhost";
    private static final int serverPort = 8081;
    private static Socket socket = null;

    public static void main(String[] args) {

        BufferedReader inputFromServer = null;
        PrintWriter outputToServer = null;
        Scanner consoleInput = new Scanner(System.in);

        try {
            socket = new Socket(serverAddress, serverPort);
            //System.out.println("\nSucessfully connected to server");

            inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputToServer = new PrintWriter(socket.getOutputStream(), true);

            String errorMsg = inputFromServer.readLine();
            if (isServerFull(errorMsg)) {
                System.out.println("Sorry, server is full right now. Try again later");
                return;
            }

            if (socket.isConnected()) {
                System.out.println(
                        "Rock, Scissor, & Paper Game" + "\n"
                                + "---------------------------" + "\n"
                                + "Play with online CPU" + "\n"
                                + "Enter 'rock' 'paper' or 'scissor'"
                );
            }

            String userInput = "";
            while(socket.isConnected()) {

                System.out.print("Input > ");
                userInput = consoleInput.nextLine();
                outputToServer.println(userInput);
                //read 4lines sent from server
                System.out.println(inputFromServer.readLine());
                System.out.println(inputFromServer.readLine());
                System.out.println(inputFromServer.readLine());
                System.out.println(inputFromServer.readLine());
                System.out.println("***********************\n");
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error: Server not available :(");
        }
    }

    static boolean isServerFull(String msg) {
        if (msg.compareTo("SERVER_FULL") == 0) {
            return true;
        } else {
            return false;
        }
    }
}