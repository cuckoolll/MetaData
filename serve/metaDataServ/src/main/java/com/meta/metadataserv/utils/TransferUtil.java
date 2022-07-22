package com.meta.metadataserv.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TransferUtil {
    public static Object byteToObject( byte[] bytes) throws Exception {
        java.lang.Object obj;
        //bytearray to object
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);

        obj = oi.readObject();

        bi.close();
        oi.close();
        return obj;
    }


    public static byte[] objectToByte(Object obj) throws Exception {
        byte[] bytes;
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);

        bytes = bo.toByteArray();

        bo.close();
        oo.close();
        return(bytes);
    }
}
