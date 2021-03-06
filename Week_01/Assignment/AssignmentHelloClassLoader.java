package Assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

public class AssignmentHelloClassLoader extends ClassLoader {

    private byte[] bytes;

    public static void main(String[] args) {
        try {

            String fileName = "/Users/zhangshuang/git/JAVA-000/Week_01/Assignment/Hello.xlass";
            AssignmentHelloClassLoader classLoader = new AssignmentHelloClassLoader();
            classLoader.getContent(fileName);
            Class<?> helloClass = classLoader.findClass("Hello");
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
        //mac下 base64 Hello.xclass得到的内容
//        String helloBase64 = "NQFFQf///8v/4/X/+f/x9v/w/+/3/+71/+3/7Pj/6/j/6v7/+cOWkZaLwf7//NfWqf7/+7yQm5r+//CzlpGasYqSnZqNq56dk5r+//qXmpOTkP7/9ayQio2cmrmWk5r+//W3mpOTkNGVnome8//4//f4/+nz/+j/5/7/7Leak5OQ09+ck56MjLOQnpuajd74/+bz/+X/5P7/+reak5OQ/v/vlZ6JntCTnpGY0LCdlZqci/7/75WeiZ7Qk56RmNCshoyLmpL+//yQiov+/+qzlZ6JntCWkNCvjZaRi6yLjZqeksT+/+yVnome0JaQ0K+NlpGLrIuNmp6S/v/4j42WkYuTkf7/6tezlZ6JntCTnpGY0KyLjZaRmMTWqf/e//r/+f///////f/+//j/9//+//b////i//7//v////rVSP/+Tv////7/9f////n//v////7//v/0//f//v/2////2v/9//7////2Tf/97fxJ//tO/////v/1////9f/9////+//3//r//v/z/////f/y";
//        byte[] bytes = decode(helloBase64);

        for (int i = 0; i < this.bytes.length; i++) {
            this.bytes[i] = (byte) (255 - this.bytes[i]);
        }
        return defineClass(name, this.bytes, 0, this.bytes.length);
    }


    private void getContent(String filePath) throws IOException {
        File file = new File(filePath);
        long fileSize = file.length();
        if (fileSize > Integer.MAX_VALUE) {
            System.out.println("file too big...");
            return;
        }
        FileInputStream fi = new FileInputStream(file);
        byte[] buffer = new byte[(int) fileSize];
        int offset = 0;
        int numRead = 0;
        while (offset < buffer.length
                && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += numRead;
        }
        // 确保所有数据均被读取
        if (offset != buffer.length) {
            throw new IOException("Could not completely read file "
                    + file.getName());
        }
        fi.close();
        this.bytes = buffer;
    }

}
