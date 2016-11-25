package com.nice295.memo;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by fepan_000 on 2016-11-25.
 */

public class Record_list {

    private String _title;
    private ArrayList<String> paths;

    public Record_list() {
        paths = new ArrayList<String >();
    }

    public String get_title() {
        return _title;
    }

    public ArrayList<String> getPaths() {
        return paths;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public void setPaths(ArrayList<String> paths) {
        this.paths = paths;
    }

    public void addNewPath(String newPath) {
        paths.add(newPath);
    }

}
