package com.leyon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static ServerSocket server = null;
    private static final int port = 8081;
    private static final int numberOfPlayersInServerAllowed = 2; //will create this many threads in executorService
    private static ExecutorService executorService;


    private static int numberOfPlayersInServer = 0;

    public static void main(String[] args) {
        executorService = Executors.newFixedThreadPool(numberOfPlayersInServerAllowed);
        Socket socket = null;

        try {
            server = new ServerSocket(port);
            System.out.println("Sever have started!");

            while(true) {
                socket = server.accept();

                if (numberOfPlayersInServer >= numberOfPlayersInServerAllowed) {
                    PrintWriter out= new PrintWriter(socket.getOutputStream(), true);
                    out.println("SERVER_FULL");
                    socket.close();
                    continue;
                } else {
                    PrintWriter out= new PrintWriter(socket.getOutputStream(), true);
                    out.println("Server is alive! :)");
                }

                Socket tmpSocket = socket;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        startNewSocketConnection(tmpSocket);
                    }
                });
            }

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Error: Closing server");
        }
        executorService.shutdown();
    }

    public static void startNewSocketConnection(Socket newSocket) {
        try {
            Socket socket = newSocket;
            System.out.println("Client have connected!");
            numberOfPlayersInServer++;

            BufferedReader inputFromClient = null;
            PrintWriter outputToClient = null;

            RockPaperScissor game = new RockPaperScissor();

            inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputToClient = new PrintWriter(socket.getOutputStream(), true);

            while(socket.isConnected()) {
                String playerMove = inputFromClient.readLine(); //expecting client to give first input
                String cpuMove = game.getCpuInput();

                int winner = game.roundResult(playerMove, cpuMove);

                //Debug
                System.out.println("Player chose " + playerMove);
                System.out.println("CPU chose " + cpuMove);
                System.out.println("Result = " + winner);
                System.out.println(game.getWinnerText(winner));

                //Send result to client - 4lines
                outputToClient.println(
                        "Player Move: " + playerMove + "\n"
                                + "Cpu Move: " + cpuMove + "\n"
                                + "------------------------" + "\n"
                                + game.getWinnerText(winner)
                );
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Connection lost to client");
            numberOfPlayersInServer--;
        }
    }
}