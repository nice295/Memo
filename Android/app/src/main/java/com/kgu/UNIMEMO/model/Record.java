package com.kgu.UNIMEMO.model;

/**
 * Created by fepan_000 on 2016-11-25.
 */

public class Record {
    private String title;
    private String path;

    public Record(String _title, String _path) {
        path = _path;
        title = _title;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }
}
