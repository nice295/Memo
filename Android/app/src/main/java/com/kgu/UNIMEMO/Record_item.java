package com.kgu.UNIMEMO;

import java.util.ArrayList;

/**
 * Created by PJH on 2016-11-07.
 */

public class Record_item {
    private int _id;
    private String _DATE;
    private ArrayList<String> paths;
    private String filetitle;
    private  String foldertitle;
    public ArrayList<String> getPaths() {
        return paths;
    }
    //private File file_;

    public void addNewPath(String newPath) {
        paths.add(newPath);
    }

    public void setfileTitle(String filetitle) {
        this.filetitle = filetitle;
    }
    public Record_item(int id, String title,String fileTitle, String folderTitle){
        _id = id;
        _DATE = title;
        filetitle=fileTitle;
        //file_=file;
        foldertitle=folderTitle;
        paths = new ArrayList<String >();
    }
public String getName(){return this.filetitle;}
    public int getId(){ return _id;}
    String getfileTitle(){
        return this.filetitle;
    }
    public String getDate(){return _DATE;}

    public String getfolder() {
        return foldertitle;
    }
}