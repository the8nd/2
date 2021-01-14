package vsu_archive.mysql;

import vsu_archive.dao.AbstractJDBCDao;
import vsu_archive.domain.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlFileDao extends AbstractJDBCDao<File> {
    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `fileName` FROM `File` WHERE `id` = ?;";
    }

    @Override
    public String getSelect2Query() {
        return "SELECT `id`, `fileName` FROM `File`;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `File` (`fileName`) VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `File` SET `fileName` = ? WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `File` WHERE `id`= ?;";
    }

    public MySqlFileDao(Connection connection) {
        super(connection);
    }

    @Override
    public File insertNew() {
        var newObj = new File();
        return insert(newObj);
    }

    @Override
    protected List<File> parseResultSet(ResultSet rs) {
        LinkedList<File> result = new LinkedList<>();
        try {
            while (rs.next()) {
                File file = new File();
                file.setId(rs.getInt("id"));
                file.setFileName(rs.getString("fileName"));
                result.add(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    @Override
    protected boolean prepareStatementForInsert(PreparedStatement statement, File object) {
        try {
            statement.setString(1, object.getFileName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, File object) {
        try {
            statement.setString(1, object.getFileName());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
