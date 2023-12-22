package me.kangmin.swingy.repository;

import me.kangmin.swingy.entity.Game;
import me.kangmin.swingy.exception.GameException;
import org.h2.jdbcx.JdbcDataSource;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBGameRepository implements GameRepository {

    private JdbcDataSource ds;
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "";
    private static final int MAX_SAVED_GAME = 10;

    public DBGameRepository() {
        this.initConnectionInfo();
        this.createGameTable();
    }

    private void initConnectionInfo() {
        String jdbcUrl = this.getJdbcUrl();

        this.ds = new JdbcDataSource();
        this.ds.setURL(jdbcUrl);
        this.ds.setUser(JDBC_USER);
        this.ds.setPassword(JDBC_PASSWORD);
    }

    private void createGameTable() {
        try (Connection connection = this.ds.getConnection()) {
            String sql = "CREATE TABLE IF NOT EXISTS game (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "game BLOB)";

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            throw new GameException(e.getMessage());
        }
    }

    private String getJdbcUrl() {
        String separator = File.separator;
        String dirPath = System.getProperty("user.dir") + separator +
                "src" + separator +
                "main" + separator +
                "resources" + separator +
                "db";
        String absolutePath = dirPath + separator;

        return "jdbc:h2:" + absolutePath + "swingy";
    }

    @Override
    public List<Game> findAllSavedGame() {
        List<Game> games = new ArrayList<>();

        try (Connection conn = this.ds.getConnection()) {
            String sql = "SELECT * FROM game";

            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Blob blob = rs.getBlob("game");
                InputStream binaryStream = blob.getBinaryStream();
                ObjectInputStream ois = new ObjectInputStream(binaryStream);
                Game game = (Game) ois.readObject();
                games.add(game);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new GameException(e.getMessage());
        }

        return games;
    }

    @Override
    public void saveGame(Game game) {
        if (this.getSavedGameCount() >= MAX_SAVED_GAME) {
            return ;
        }

        try (Connection connection = this.ds.getConnection()) {
            String sql = "INSERT INTO game (game) VALUES (?)";

            try (PreparedStatement pstmt = connection.prepareStatement(sql);
                 ByteArrayOutputStream baos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(baos);
                ) {
                oos.writeObject(game);

                byte[] byteArray = baos.toByteArray();

                pstmt.setBytes(1, byteArray);
                pstmt.execute();
            }
        } catch (SQLException | IOException e) {
            throw new GameException(e.getMessage());
        }
    }

    @Override
    public int getSavedGameCount() {
        try (Connection conn = this.ds.getConnection()) {
            String sql = "SELECT COUNT(*) FROM game";

            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new GameException(e.getMessage());
        }

        return 0;
    }

    @Override
    public void resetData() {
        try (Connection conn = this.ds.getConnection()) {
            String sql = "DELETE FROM game";

            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }

        } catch (SQLException e) {
            throw new GameException(e.getMessage());
        }
    }

    @Override
    public Optional<Game> findGameByIndex(int idx) {
        List<Game> allSavedGame = this.findAllSavedGame();

        return Optional.ofNullable(allSavedGame.get(idx));
    }
}
