/* EE422C Assignment #2 submission by
 * <Jason Zubia>
 * <jgz279>
 */

package assignment5;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Game
{
    // Specifies if secret code should be shown
    boolean gameMode;
    DataInputStream playerInput;
    DataOutputStream clientOutput;
    Validator validator;
    String secretCode;

    // Constructor
    public Game(boolean testingMode, DataInputStream input, Validator validator, DataOutputStream clientOutput,
                String secretCode)
    {
        gameMode = testingMode;
        playerInput = input;
        this.validator = validator;
        this.clientOutput = clientOutput;
        this.secretCode = secretCode;
    }
    public boolean runGame() throws IOException, InterruptedException {

        // Creates a gameBoard state
        BoardState gameBoard = new BoardState("", 0,
                0, secretCode, GameConfiguration.guessNumber);

        // Creates Game History
        History gameHistory = new History(GameConfiguration.guessNumber);

        // Generates secret code line with/without secret code depending on game mode
        clientOutput.writeUTF(Dialogue.generatingSecretCode(gameMode, secretCode));

        // Start of game begin to prompt the user for their guess
        clientOutput.writeUTF(Dialogue.firstGuess());

        int counter = 0;

        while(gameBoard.getNumberOfGuessesRemaining() > 0)
        {
            // Gets player response and saves it.
            gameBoard.playerResponse = (String)playerInput.readUTF();

            if(validator.validateHistory(gameBoard.playerResponse))
            {
                clientOutput.writeUTF(gameHistory.printHistory(counter));
                clientOutput.writeUTF(Dialogue.guessesLeft(gameBoard.getNumberOfGuessesRemaining()));
                gameBoard.playerResponse = (String)playerInput.readUTF();
            }

            // Validates user input
            validator.validatePlayerGuess(gameBoard.playerResponse);

            if(validator.getValidGuess())
            {
                if(gameBoard.playerWin())
                {
                    clientOutput.writeUTF(Dialogue.validFeedback(gameBoard));
                    clientOutput.writeUTF(Dialogue.youWin());
                    MyServer.winner = true;
                    return true;
                }

                clientOutput.writeUTF(Dialogue.validFeedback(gameBoard));

                gameHistory.updateHistory(gameBoard.playerResponse, gameBoard.getNumberOfBlackPegs(),
                        gameBoard.getNumberOfWhitePegs(), counter);
                counter++;
                gameBoard.updateNumberOfGuessesRemaining();

                if(gameBoard.getNumberOfGuessesRemaining() == 0)
                {
                    clientOutput.writeUTF(Dialogue.youLose());
                    return false;
                }
                if(MyServer.winner)
                {
                    clientOutput.writeUTF(Dialogue.otherPlayerWon());
                    return false;
                }

                clientOutput.writeUTF(Dialogue.guessesLeft(gameBoard.getNumberOfGuessesRemaining()));
            }
            else
            {
                clientOutput.writeUTF(Dialogue.invalidFeedback(gameBoard));
                clientOutput.writeUTF(Dialogue.invalidGuess());
            }
        }

        return false;
    }
}
