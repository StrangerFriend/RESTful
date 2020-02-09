package com.gnnt.net;

import java.util.HashMap;

public class Test {


    private void ttesg(){
        HttpCommunicate httpCommunicate = new HttpCommunicate("fsfsf00");
        httpCommunicate.getParams("fsf", new HashMap<String, String>(), String.class, new Callback<String>() {
            @Override
            public void response(int result, String failMessage, String o) {

            }
        });
    }

}
