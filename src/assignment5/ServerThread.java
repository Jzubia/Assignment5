package assignment5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Vector;

public class ServerThread extends Thread
{
    Socket serverSocket;
    Vector<ServerThread> threadVector;
    String secretCode;
    volatile boolean winner;
    String[] args;

    ServerThread(Socket socket, Vector<ServerThread> threadVector, String secretCode, String[] args)
    {
        serverSocket = socket;
        this.threadVector = threadVector;
        this.secretCode = secretCode;
        winner = false;
        this.args = args;
    }

    public void run()
    {
        synchronized (currentThread()) {
            try {
                // takes input from the client socket
                DataInputStream dis = new DataInputStream(serverSocket.getInputStream());

                DataOutputStream dout = new DataOutputStream(serverSocket.getOutputStream());


                // For me
//                System.out.println(currentThread().getName());


                // Creates the validator, validates user input
                Validator validator = new Validator(false);

                // Specifies if testingMode is enabled
                boolean testingMode = false;

                // Checks the CLA for testing mode
                if(args.length != 0)
                {
                    testingMode = args[0].equals("1");
                }

                // Dialogue class will display the game intro
                dout.writeUTF(Dialogue.initialGreeting());

                // Gets player response and saves it.
                String playGame = (String) dis.readUTF();

                if(!validator.validateYes(playGame))
                {
                    MyServer.threadVector.remove(currentThread());
                }

                // If player is not ready, terminate the game
                while (validator.validateYes(playGame)) {
//                // Generates secretCode
//                String secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();

                    MyServer.winner = false;
                    // Create a game
                    Game masterMind = new Game(testingMode, dis, validator, dout, MyServer.secretCode);

                    // Main game engine, runs the game
                    winner = masterMind.runGame();

                    MyServer.threadsDone++;

                    while (MyServer.threadsDone != MyServer.threadVector.size())
                    {

                    }

                    MyServer.threadsDone = 0;
                    // If a user won, update the secret code
                    if(winner)
                    {
                        // If user wants to play again, update game
                        MyServer.updateSecretCode();
                        secretCode = MyServer.secretCode;
                    }
                    // If no users won
                    else if(!MyServer.winner)
                    {
                        MyServer.updateSecretCode();
                        secretCode = MyServer.secretCode;
                    }

                    dout.writeUTF(Dialogue.playAgain());

                    // Ask for user input
                    playGame = (String) dis.readUTF();

                    if(!validator.validateYes(playGame))
                    {
                        MyServer.threadVector.remove(currentThread());
                    }

                }

                serverSocket.close();


//                System.out.println("Thread finished");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
