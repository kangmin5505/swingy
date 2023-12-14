package me.kangmin.swingy.repository;

import me.kangmin.swingy.controller.response.Response;
import me.kangmin.swingy.entity.Game;
import me.kangmin.swingy.exception.GameException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileGameRepository implements GameRepository {
    private static final String SAVE_GAME_PATH = "src/main/resources/save_game";
    private static final String SAVE_GAME_EXTENSION = ".ser";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private List<Game> savedGames = null;

    @Override
    public List<Game> findAllSavedGame() {
        this.savedGames = new ArrayList<>();

        File savedDir = new File(SAVE_GAME_PATH);
        if (savedDir.exists() && savedDir.isDirectory()) {
            File[] savedGameFiles = savedDir.listFiles((dir, name) -> name.endsWith(SAVE_GAME_EXTENSION));

            if (savedGameFiles != null) {
                for (File savedGameFile : savedGameFiles) {
                    try (FileInputStream fis = new FileInputStream(savedGameFile);
                         BufferedInputStream bis = new BufferedInputStream(fis);
                         ObjectInputStream in = new ObjectInputStream(bis)) {
                        Game game = (Game) in.readObject();
                        savedGames.add(game);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new GameException(Response.Message.FAIL_TO_LOAD_GAME);
                    }
                }
            }
        }

        return savedGames;
    }

    @Override
    public void saveGame(Game game) {
        Path path = Paths.get(SAVE_GAME_PATH);
        String filename = this.getFileFullPath();

        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            Files.createDirectories(path);
            out.writeObject(game);
        } catch (IOException e) {
            throw new GameException(Response.Message.FAIL_TO_LOAD_GAME);
        }
        this.savedGames = null;
    }

    @Override
    public int getSavedGameCount() {
        assert savedGames != null;
        return this.savedGames.size();
    }

    @Override
    public void resetData() {
        // TODO: reset Data
    }
    
    // TODO : clear data

    private String getFileFullPath() {
        return String.format("%s/swingy_%s%s", SAVE_GAME_PATH, getFormattedDateTime(), SAVE_GAME_EXTENSION);
    }

    private String getFormattedDateTime() {
        return LocalDateTime.now()
                            .format(formatter);
    }
}
