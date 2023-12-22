package me.kangmin.swingy;

import me.kangmin.swingy.core.GameManager;
import me.kangmin.swingy.enums.ViewMode;
import me.kangmin.swingy.exception.GameException;

public class App
{
    private static final int ERROR_CODE = 2;
    private static final String ARGUMENT_ERROR_MESSAGE = "usage: java -jar swingy.jar [console | gui]";
    public static void main(String[] args)
    {
        ViewMode viewMode = getViewMode(args);

        try {
            GameManager gameManager = new GameManager(viewMode);
            gameManager.run();
        } catch (GameException e) {
            System.err.println(e.getMessage());
            System.exit(ERROR_CODE);
        }
    }

    private static ViewMode getViewMode(String[] args) {
        try {
            String upperCasedArg = args[0].toUpperCase();

            return ViewMode.valueOf(upperCasedArg);
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            System.err.println(ARGUMENT_ERROR_MESSAGE);
            System.exit(ERROR_CODE);
        }
        return null;
    }
}
