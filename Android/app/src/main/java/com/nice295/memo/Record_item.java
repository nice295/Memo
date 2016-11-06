package com.nice295.memo;

/**
 * Created by PJH on 2016-11-07.
 */

public class Record_item {
    private int _id;
    private String _DATE;

    public Record_item(int id, String title){
        _id = id;
        _DATE = title;
    }

    public int getId(){ return _id;}

    public String getDate(){return _DATE;}

}
