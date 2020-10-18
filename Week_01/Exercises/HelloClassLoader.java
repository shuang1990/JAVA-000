package Exercises;

import java.lang.reflect.Method;
import java.util.Base64;

public class HelloClassLoader extends ClassLoader{
    public static void main(String[] args) {
        try {
            Class helloClass = new HelloClassLoader().findClass("Hello");
            Object helloInstance = helloClass.newInstance();

            Method helloMethod = helloClass.getMethod("hello");
            helloMethod.setAccessible(true);
            helloMethod.invoke(helloInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String helloBase64 = "yv66vgAAADQAHwoABwAQCQARABIIABMKABQAFQgAFgcAFwcAGAEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFibGUBAAVoZWxsbwEACDxjbGluaXQ+AQAKU291cmNlRmlsZQEACkhlbGxvLmphdmEMAAgACQcAGQwAGgAbAQATSGVsbG8sIGNsYXNzTG9hZGVyIQcAHAwAHQAeAQAYSGVsbG8gQ2xhc3MgSW5pdGlhbGl6ZWQhAQAFSGVsbG8BABBqYXZhL2xhbmcvT2JqZWN0AQAQamF2YS9sYW5nL1N5c3RlbQEAA291dAEAFUxqYXZhL2lvL1ByaW50U3RyZWFtOwEAE2phdmEvaW8vUHJpbnRTdHJlYW0BAAdwcmludGxuAQAVKExqYXZhL2xhbmcvU3RyaW5nOylWACEABgAHAAAAAAADAAEACAAJAAEACgAAAB0AAQABAAAABSq3AAGxAAAAAQALAAAABgABAAAAAQABAAwACQABAAoAAAAlAAIAAQAAAAmyAAISA7YABLEAAAABAAsAAAAKAAIAAAAHAAgACAAIAA0ACQABAAoAAAAlAAIAAAAAAAmyAAISBbYABLEAAAABAAsAAAAKAAIAAAADAAgABAABAA4AAAACAA8=";
        byte[] bytes = decode(helloBase64);
        return defineClass(name, bytes, 0, bytes.length);
    }

    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
