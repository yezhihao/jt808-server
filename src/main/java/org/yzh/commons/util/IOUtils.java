package org.yzh.commons.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class IOUtils {

    public static final String Separator = System.lineSeparator();

    public static String readIn(String classpath) {
        return readIn(classpath, StandardCharsets.UTF_8);
    }

    public static String readIn(String classpath, Charset charset) {
        return read(Thread.class.getResourceAsStream(classpath), charset);
    }

    public static String read(File file) {
        return read(file, StandardCharsets.UTF_8);
    }

    public static String read(File file, Charset charset) {
        try (FileInputStream is = new FileInputStream(file)) {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            return new String(bytes, charset);
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String read(InputStream is, Charset charset) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset))) {
            StringBuilder result = new StringBuilder(500);
            String line;
            while ((line = reader.readLine()) != null)
                result.append(line).append(Separator);
            return result.substring(0, result.length() - Separator.length());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(File file, String text) {
        write(file, text, StandardCharsets.UTF_8);
    }

    public static void write(File file, String text, Charset charset) {
        byte[] bytes = text.getBytes(charset);
        try (FileOutputStream os = new FileOutputStream(file)) {
            os.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copy(String sourcePath, String targetPath) {
        copy(new File(sourcePath), new File(targetPath));
    }

    public static void copy(File source, File target) {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(target);

             FileChannel ifc = fis.getChannel();
             FileChannel ofc = fos.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (ifc.read(buffer) != -1) {
                buffer.flip();//切换为读模式 设置limit为position，并重置position为0
                ofc.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void foreach(File file, Function<String, Boolean> function) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (Boolean.FALSE == function.apply(line))
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(AutoCloseable a) {
        if (a != null)
            try {
                a.close();
            } catch (Exception e) {
            }
    }

    public static void close(AutoCloseable a1, AutoCloseable a2) {
        close(a1);
        close(a2);
    }

    public static void close(AutoCloseable a1, AutoCloseable a2, AutoCloseable a3) {
        close(a1);
        close(a2);
        close(a3);
    }
}