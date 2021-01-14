package vsu_archive.mysql;

import vsu_archive.dao.AbstractJDBCDao;
import vsu_archive.domain.Archive;
import vsu_archive.domain.ArchiveFile;
import vsu_archive.domain.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MySqlArchiveFileDao extends AbstractJDBCDao<ArchiveFile> {
    @Override
    public String getSelectQuery() {
        return "SELECT `id`, `archive_id`, `file_id` FROM `Archive_File` WHERE `id` = ?;";
    }

    @Override
    public String getSelect2Query() {
        return "SELECT `id`, `archive_id`, `file_id` FROM `Archive_File`;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO `Archive_File` (`archive_id`, `file_id`) VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE `Archive_File` SET `archive_id` = ?, `file_id` = ? WHERE `id` = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM `Archive_File` WHERE `id`= ?;";
    }

    public MySqlArchiveFileDao(Connection connection) {
        super(connection);
    }

    @Override
    public ArchiveFile insertNew() {
        var newObj = new ArchiveFile();
        return insert(newObj);
    }

    @Override
    protected List<ArchiveFile> parseResultSet(ResultSet rs) {
        LinkedList<ArchiveFile> result = new LinkedList<>();
        try {
            while (rs.next()) {
                ArchiveFile archiveFile = new ArchiveFile();
                archiveFile.setId(rs.getInt("id"));
                archiveFile.setArchive(
                        MySqlDaoFactory.instance.getDao(MySqlDaoFactory.instance.getContext(), Archive.class).
                                getObjectById(rs.getInt("archive_id"))
                );
                archiveFile.setFile(
                        MySqlDaoFactory.instance.getDao(MySqlDaoFactory.instance.getContext(), File.class).
                                getObjectById(rs.getInt("file_id"))
                );
                result.add(archiveFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return result;
    }

    @Override
    protected boolean prepareStatementForInsert(PreparedStatement statement, ArchiveFile object) {
        try {
            int archive_id = (object.getArchive() == null || object.getArchive().getId() == null) ? -1
                    : object.getArchive().getId();
            int file_id = (object.getFile()== null || object.getFile().getId() == null) ? -1
                    : object.getFile().getId();
            statement.setInt(1, archive_id);
            statement.setInt(2, file_id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected boolean prepareStatementForUpdate(PreparedStatement statement, ArchiveFile object) {
        try {
            int archive_id = (object.getArchive() == null || object.getArchive().getId() == null) ? -1
                    : object.getArchive().getId();
            int file_id = (object.getFile()== null || object.getFile().getId() == null) ? -1
                    : object.getFile().getId();
            statement.setInt(1, archive_id);
            statement.setInt(2, file_id);
            statement.setInt(3, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
