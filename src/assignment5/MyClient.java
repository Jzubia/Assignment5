package assignment5;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args)
    {
        try
        {
            Socket clientSocket = new Socket("localhost",6666);
//            System.out.println("Server connected");

            ClientThread clientThread = new ClientThread(clientSocket);
            clientThread.start();

        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
