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
import java.util.Optional;
import java.util.stream.Stream;

public class FileGameRepository implements GameRepository {
    private static final String SAVE_GAME_PATH = "src/main/resources/save_game";
    private static final String SAVE_GAME_EXTENSION = ".ser";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private final Path dirPath = Paths.get(SAVE_GAME_PATH);

    public FileGameRepository() {
        try {
            Files.createDirectories(this.dirPath);
        } catch (IOException e) {
            throw new GameException(Response.Message.FAIL_TO_CREATE_SAVE_GAME_DIR);
        }
    }

    @Override
    public List<Game> findAllSavedGame() {
        List<Game> savedGames = new ArrayList<>();

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
        String filename = this.getFileFullPath();

        try (FileOutputStream fos = new FileOutputStream(filename);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(game);
        } catch (IOException e) {
            throw new GameException(Response.Message.FAIL_TO_LOAD_GAME);
        }
    }

    @Override
    public int getSavedGameCount() {
        return this.findAllSavedGame().size();
    }

    @Override
    public void resetData() {
        if (Files.isDirectory(this.dirPath)) {
            try (Stream<Path> paths = Files.walk(this.dirPath)) {
                paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(SAVE_GAME_EXTENSION))
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                throw new GameException(Response.Message.FAIL_TO_RESET_DATA);
                            }
                        });

            } catch (IOException e) {
                throw new GameException(Response.Message.FAIL_TO_RESET_DATA);
            }
        }
    }

    @Override
    public Optional<Game> findGameByIndex(int idx) {
        return Optional.ofNullable(this.findAllSavedGame().get(idx));
    }

    private String getFileFullPath() {
        return String.format("%s/swingy_%s%s", SAVE_GAME_PATH, getFormattedDateTime(), SAVE_GAME_EXTENSION);
    }

    private String getFormattedDateTime() {
        return LocalDateTime.now()
                            .format(formatter);
    }
}
