package vsu_archive.domain;

import vsu_archive.dao.MyObject;

public class ArchiveFile extends MyObject {
    private Integer id;
    private Archive archive;
    private File file;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
