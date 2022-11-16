package assignment5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread extends Thread
{
    Socket clientSocket;

    public ClientThread(Socket clientSocket)
    {
        this.clientSocket = clientSocket;
    }

    public void run() {
        synchronized (this) {
            try {
                // sends output to the socket (starts output stream)
                DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());
                // reads input from server
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

                // Capture command line info from client user
                Scanner in = new Scanner(System.in);

                boolean active = true;

                // Prints initial greeting
                String str = (String) dis.readUTF();
                System.out.println(str);

                // Gets response to want to play
                String userInput = in.next();
                dout.writeUTF(userInput);

                // If response is no
                if (userInput.equals("N")) {
                    active = false;
                }

                // Main Gameplay
                while (active) {
                    while (!str.contains("You lose") && !str.contains("You win")) {
                        if (str.contains("Enter guess: ")) {
                            userInput = in.next();
                            dout.writeUTF(userInput);
                        }
                        str = (String) dis.readUTF() + (String) dis.readUTF();
                        System.out.println(str);
                    }

                    // Do you want to play again?
                    str = (String) dis.readUTF();
                    System.out.println(str);

                    userInput = in.next();
                    if (userInput.equals("N")) {
                        active = false;
                    }
                    // Sends result to server
                    dout.writeUTF(userInput);


                }

                // closes output stream
                dout.close();

                // closes scanner
                in.close();

                // closes the socket
                clientSocket.close();
//                System.out.println("Client closed");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
