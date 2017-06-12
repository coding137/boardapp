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


    public  Item(String contents,String writer, String date, String topic,String tid){
        this.contents = contents;
        this.writer = writer;
        this. date = date;
        this.topic = topic;
        this.tid = tid;
    }


}
