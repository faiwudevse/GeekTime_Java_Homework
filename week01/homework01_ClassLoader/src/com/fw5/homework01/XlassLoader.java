package com.fw5.homework01;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class XlassLoader extends ClassLoader {
    public static void main(String[] arg)  throws  Exception {
        final String className = "Hello";
        final String methodName = "hello";

        // create a customized loader class
        ClassLoader classLoader = new XlassLoader();

        // create the hello class
        Class<?> helloClass = classLoader.loadClass(className);

        Object helloObject = helloClass.newInstance();

        helloClass.getMethod(methodName).invoke(helloObject);

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        final String suffix = ".xlass";
        InputStream inputstream = this.getClass().getClassLoader().getResourceAsStream(name + suffix);
        byte[] bytes = null;
        try{
            int size = inputstream.available();
            bytes = new byte[size];
            inputstream.read(bytes);
            bytes = decode(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                inputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        return defineClass(name, bytes, 0, bytes.length);
    }

    private static byte[] decode(byte[] byteArray) {
        for(int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte) (255 - byteArray[i]);
        }
        return byteArray;
    }

    private static void close(Closeable res) {

    }
}
