package vsu_archive.domain;

import vsu_archive.dao.MyObject;

public class File extends MyObject {
    private Integer id;
    private String fileName;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
