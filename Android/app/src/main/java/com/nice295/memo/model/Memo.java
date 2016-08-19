package com.nice295.memo.model;

/**
 * Created by kyuholee on 2016. 8. 18..
 */
public class Memo {
    private String memo;
    private String title;

    public Memo(String _memo, String _title) {
        memo = _memo;
        title = _title;
    }

    public String getMemo() {
        return memo;
    }

    public String getTitle() {
        return title;
    }
}
