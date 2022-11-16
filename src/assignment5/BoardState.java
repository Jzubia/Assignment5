/* EE422C Assignment #2 submission by
 * <Jason Zubia>
 * <jgz279>
 */

package assignment5;

public class BoardState
{
    String playerResponse;
    private int numberOfGuessesRemaining;
    private final String secretCode;
    private int numberOfBlackPegs;
    private int numberOfWhitePegs;

    public BoardState(String playerResponse, int numberOfBlackPegs, int numberOfWhitePegs,
                      String secretCode, int numberOfGuessesRemaining)
    {
        this.playerResponse = playerResponse;
        this.numberOfBlackPegs = numberOfBlackPegs;
        this.numberOfWhitePegs = numberOfWhitePegs;
        this.secretCode = secretCode;
        this.numberOfGuessesRemaining = numberOfGuessesRemaining;
    }

    public int getNumberOfBlackPegs()
    {
        return numberOfBlackPegs;
    }

    public int getNumberOfWhitePegs()
    {
        return numberOfWhitePegs;
    }

    public int getNumberOfGuessesRemaining()
    {
        return numberOfGuessesRemaining;
    }

    public void updateNumberOfGuessesRemaining()
    {
        numberOfGuessesRemaining--;
    }
    public boolean playerWin()
    {
        resetPegs();

        if(playerResponse.equals(secretCode))
        {
            numberOfWhitePegs = 0;
            numberOfBlackPegs = 4;
            return true;
        }
        else
        {
            calculatePegs();
            return false;
        }
    }

    private void resetPegs()
    {
        numberOfWhitePegs = 0;
        numberOfBlackPegs = 0;
    }

    private void calculatePegs()
    {
        char[] tempSecretCode = secretCode.toCharArray();
        char[] tempPlayerResponse = playerResponse.toCharArray();
        calculateBlackPegs(tempSecretCode, tempPlayerResponse);
        calculateWhitePegs(tempSecretCode, tempPlayerResponse);
    }
    private void calculateBlackPegs(char[] tempSecretCode, char[] tempPlayerResponse)
    {
        for(int i = 0; i < GameConfiguration.pegNumber; i++)
        {
            if(tempSecretCode[i] == tempPlayerResponse[i])
            {
                numberOfBlackPegs++;
                tempSecretCode[i] = '_';
                tempPlayerResponse[i] = '-';
            }
        }
    }

    private void calculateWhitePegs(char[] tempSecretCode, char[] tempPlayerResponse)
    {
        for(int i = 0; i < GameConfiguration.pegNumber; i++)
        {
            for(int j = 0; j < GameConfiguration.pegNumber; j++)
            {
                if(tempSecretCode[i] == tempPlayerResponse[j])
                {
                    numberOfWhitePegs++;
                    tempSecretCode[i] = '_';
                    tempPlayerResponse[j] = '-';
                    break;
                }
            }
        }
    }

}
