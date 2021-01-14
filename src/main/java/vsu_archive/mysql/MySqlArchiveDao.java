package vsu_archive.mysql;

import vsu_archive.dao.AbstractJDBCDao;
import vsu_archive.domain.Archive;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlArchiveDao extends AbstractJDBCDao<Archive> {
    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `title`, `creation_time` FROM `Archive` WHERE id = ?;";
    }

    @Override
    public String getSelect2Query() {
        return "SELECT `id`, `title`, `creation_time` FROM `Archive`;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `Archive` (`title`, `creation_time`) VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `Archive` SET `title` = ?, `creation_time` = ? WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `Archive` WHERE `id`= ?;";
    }

    public MySqlArchiveDao(Connection connection) {
        super(connection);
    }

    @Override
    public Archive insertNew() {
        var newObj = new Archive();
        return insert(newObj);
    }

    @Override
    protected List<Archive> parseResultSet(ResultSet rs) {
        LinkedList<Archive> result = new LinkedList<>();
        try {
            while (rs.next()) {
                Archive Archive = new Archive();
                Archive.setId(rs.getInt("id"));
                Archive.setTitle(rs.getString("title"));
                Archive.setCreationTime(rs.getDate("creation_time"));
                result.add(Archive);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    @Override
    protected boolean prepareStatementForInsert(PreparedStatement statement, Archive object) {
        try {
            Date sqlDate = convert(object.getCreationTime());
            statement.setString(1, object.getTitle());
            statement.setDate(2, sqlDate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, Archive object) {
        try {
            Date sqlDate = convert(object.getCreationTime());
            statement.setString(1, object.getTitle());
            statement.setDate(2, sqlDate);
            statement.setInt(3, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    protected java.sql.Date convert(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }
}
