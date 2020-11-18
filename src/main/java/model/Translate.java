package model;

import java.io.*;

public class Translate {

    public static byte[] ObjectToByte(Object object){
        byte[] bytes=null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            bytes=byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static Object ByteToObject(byte[] bytes,int l,int r){
        Object o = null;
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes,l,r);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            o = objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

}
