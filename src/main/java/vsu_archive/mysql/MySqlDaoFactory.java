package vsu_archive.mysql;

import vsu_archive.domain.ArchiveFile;
import vsu_archive.domain.File;
import vsu_archive.domain.Archive;
import vsu_archive.dao.DaoFactory;
import vsu_archive.dao.GenericDao;
import vsu_archive.dao.MyObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory implements DaoFactory<Connection> {
    private static final String user = "root";
    private static final String password = "";
    private static final String url = "jdbc:mysql://localhost:3306/archive?useUnicode=true&serverTimezone=UTC&characterEncoding=utf-8";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private final Map<Class<? extends MyObject>, DaoCreator<Connection>> creators;

    public static final MySqlDaoFactory instance = new MySqlDaoFactory();

    public Connection getContext() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return connection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends MyObject> GenericDao<T> getDao(Connection connection, Class<T> dtoClass) {
        var creator = creators.get(dtoClass);
        return (GenericDao<T>)creator.create(connection);
    }

    private MySqlDaoFactory() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        creators = new HashMap<>();
        creators.put(File.class, MySqlFileDao::new);
        creators.put(Archive.class, MySqlArchiveDao::new);
        creators.put(ArchiveFile.class, MySqlArchiveFileDao::new);
    }
}
