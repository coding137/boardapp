package com.hello.myapp.board_app;

/**
 * Created by Cloud on 2017-06-09.
 */

public class Item {
    String contents;
    String writer;
    String date;
    String topic;
    String tid;

    public String getContents() {
        return contents;
    }

    public String getWriter() {
        return writer;
    }

    public String getDate() {
        return date;
    }

    public String getTopic() {
        return topic;
    }

    public String getTid() {
        return tid;
    }

    public  Item(String contents, String writer, String date, String topic, String tid){
        this.contents = contents;
        this.writer = writer;
        this. date = date;
        this.topic = topic;
        this.tid = tid;
    }


}
