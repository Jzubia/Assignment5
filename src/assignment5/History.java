/* EE422C Assignment #2 submission by
 * <Jason Zubia>
 * <jgz279>
 */

package assignment5;

public class History
{
    String[] guessHistory;

    public History(int guessNumber)
{
    guessHistory = new String[guessNumber];
}

    public void updateHistory(String playerResponse, int numberOfBlackPegs, int numberOfWhitePegs, int index)
    {
        guessHistory[index] = playerResponse + "\t\t" + numberOfBlackPegs + "B_" +
                numberOfWhitePegs +"W";
    }

    public String printHistory(int counter)
    {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < counter; i++)
        {
            result.append("\n").append(guessHistory[i]);
        }

        return result.toString();
    }
}
