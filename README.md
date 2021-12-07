# Rock-Paper-Scissor Socket Programming

This project was created to practice socket programming using Java.  

Used standard java libraries to create server and client.  

ServerSocket - to start a server on a given port  

Socket - to establish connection between server and client

Created a separate class RockPaperScissor to handle game logic  

I used ExecutorService with Executors.newFixedThreadPool to create threads which would handle each client connection concurrently, allowing multiple clients to connect and disconnect at any time as long as a thread is free. If no threads are free, the server sends a error message which is read by the client, a message stating server is full is displayed to the user and the connection is closed.
