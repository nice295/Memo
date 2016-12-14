package com.kgu.UNIMEMO;

/**
 * Created by fepan_000 on 2016-08-11.
 */
public class Recycler_item {
    int image;
    String title;
    String desc;
    String DATE;

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
    }



}