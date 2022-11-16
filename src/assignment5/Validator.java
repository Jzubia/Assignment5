/* EE422C Assignment #2 submission by
 * <Jason Zubia>
 * <jgz279>
 */

package assignment5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator
{
    private boolean validGuess;

    public Validator(boolean validGuess)
    {
        this.validGuess = validGuess;
    }

    public boolean getValidGuess()
    {
        return validGuess;
    }

    public boolean validateYes(String playerResponse)
    {
        // Regex pattern to check if input is Y or N
        Pattern no = Pattern.compile("^Y$");

        Matcher matcher = no.matcher(playerResponse);

        return matcher.find();
    }

    public boolean validateHistory(String playerResponse)
    {
        return playerResponse.equals("HISTORY");
    }
    public void validatePlayerGuess(String playerResponse)
    {
        validGuess = !checkCasing(playerResponse) && checkGuessLength(playerResponse) &&
                checkGuessColors(playerResponse);
    }

    private boolean checkCasing(String playerResponse)
    {
        // Regex pattern to check if any input is lower-case
        Pattern lowerCase = Pattern.compile("[a-z]");

        Matcher matcher = lowerCase.matcher(playerResponse);

        // False if invalid guess, True if valid guess
        return matcher.find();
    }
    private boolean checkGuessLength(String playerResponse)
    {
        return playerResponse.length() == GameConfiguration.pegNumber;
    }

    private boolean checkGuessColors(String playerResponse)
    {
        String pegColors = String.join("", GameConfiguration.colors);
        Pattern colors = Pattern.compile("^[" + pegColors + "]{" + GameConfiguration.pegNumber + "}$");

        Matcher matcher = colors.matcher(playerResponse);

        return matcher.find();
    }
}
