package com.example.handler;


import android.os.Handler;
import android.os.Message;

public class ChangeUI {
    private Handler handler;

    public ChangeUI(Handler handler) {
        this.handler = handler;
    }
    public void changeUI(){
        Message message = new Message();
        message.what = 0;
        handler.sendMessage(message);
    }
}
