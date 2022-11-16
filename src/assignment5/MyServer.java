package assignment5;

import java.io.*;
import java.net.*;
import java.util.Vector;

public class MyServer {
    static String secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
    static boolean winner = false;
    static int threadsDone = 0;
    static Vector<ServerThread> threadVector = new Vector<>();
    public static void main(String[] args)
    {
//        Vector<ServerThread> threadVector = new Vector<>();
        try
        {
            ServerSocket serverSocket = new ServerSocket(6666);
//            System.out.println("Server started");
            while (true)
            {
                Socket clientSocket = serverSocket.accept();//establishes connection
                ServerThread serverThread = new ServerThread(clientSocket, threadVector, secretCode, args);
                // Start thread
                threadVector.add(serverThread);
//                System.out.println(threadVector);
                if(threadVector.size() >= 3)
                {
                    // Start all threads once clients have joined
                    startThreads(threadVector);

                    // while there is no winner
//                    while (!winner)
//                    {
                        // Check if any player has won
//                        ServerThread winningThread = checkForWinner(threadVector);
//                        if(winningThread != null)
//                        {
//                            System.out.println("Win detected " + winningThread.getName());
//                            winner = true;
//                            break;
//                        }
//                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public static void startThreads(Vector<ServerThread> threadVector)
    {
        for(ServerThread sT: threadVector)
        {
            sT.start();
//            System.out.println("Starting thread: " + sT.getName());
        }
    }

    public static ServerThread checkForWinner(Vector<ServerThread> threadVector)
    {
        for(ServerThread sT: threadVector)
        {
            if(sT.winner)
            {
                return sT;
            }
        }
        return null;
    }

    public static void stopThreads(Vector<ServerThread> threadVector){
        for(ServerThread sT: threadVector)
        {
            if(!sT.winner)
            {
                System.out.println("Stopping thread: " + sT.getName());
            }
        }
    }

    public static boolean timeToStart(ServerThread serverThread){
        while (true)
        {
            synchronized (serverThread) {
                try {
                    serverThread.wait(60000);
                } catch (Exception e) {
                    System.out.println(e);
                }
                return true;
            }
        }
    }

    public static void updateSecretCode()
    {
        secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();
    }

}
