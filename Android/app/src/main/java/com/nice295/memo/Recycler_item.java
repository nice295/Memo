package com.nice295.memo;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by fepan_000 on 2016-08-11.
 */
public class Recycler_item {
    int image;
    String title;
    String desc;

    int getImage(){
        return this.image;
    }
    String getTitle(){
        return this.title;
    }
    String getdesc(){
        return this.desc;
    }

    Recycler_item(int image, String title, String desc){
        this.image=image;
        this.title=title;
        this.desc=desc;
    }
}