package com.nice295.memo;

import java.util.ArrayList;

/**
 * Created by fepan_000 on 2016-08-11.
 */
public class Recycler_item {
    int image;
    String title;
    String desc;
    String DATE;
    private ArrayList<String> paths;

    public ArrayList<String> getPaths() {
        return paths;
    }

    public void addNewPath(String newPath) {
        paths.add(newPath);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    int getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }
    String getdesc(){
        return this.desc;
    }
    String getDate(){return this.DATE;}


    Recycler_item(int image, String title, String desc,String DATE){
        this.image=image;
        this.title=title;
        this.desc=desc;
        this.DATE = DATE;
        paths = new ArrayList<String >();
    }



}