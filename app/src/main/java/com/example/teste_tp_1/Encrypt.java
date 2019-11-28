package com.example.teste_tp_1;

import java.security.MessageDigest;

public class Encrypt {

public Encrypt(){

    }
    public String encryptar(String data) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());
        byte[] digest = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : digest){
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
