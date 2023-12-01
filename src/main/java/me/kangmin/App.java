package me.kangmin;

import me.kangmin.core.GameManager;
import me.kangmin.enums.ViewMode;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            String upperCasedArg = args[0].toUpperCase();
            ViewMode viewMode = ViewMode.valueOf(upperCasedArg);

            GameManager gameManager = new GameManager(viewMode);
            gameManager.run();
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Please enter a valid argument: gui or console");
        }
    }
}
