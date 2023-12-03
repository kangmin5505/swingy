package me.kangmin.swingy;

import me.kangmin.swingy.core.GameManager;
import me.kangmin.swingy.enums.ViewMode;

public class App
{
    private static final int ERROR_CODE = 2;
    public static void main( String[] args )
    {
        try {
            String upperCasedArg = args[0].toUpperCase();
            ViewMode viewMode = ViewMode.valueOf(upperCasedArg);

            GameManager gameManager = new GameManager(viewMode);
            gameManager.run();
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.err.println("usage: java -jar swingy.jar [console | gui]");
            System.exit(ERROR_CODE);
        }
    }
}
