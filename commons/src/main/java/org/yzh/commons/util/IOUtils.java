package org.yzh.commons.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.LinkedList;
import java.util.function.Function;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class IOUtils {

    public static final String Separator = System.lineSeparator();

    public static String readIn(String classpath) {
        return readIn(classpath, StandardCharsets.UTF_8);
    }

    public static String readIn(String classpath, Charset charset) {
        return read(Thread.currentThread().getContextClassLoader().getResourceAsStream(classpath), charset);
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

    public static String read(InputStream is) {
        return read(is, StandardCharsets.UTF_8);
    }

    public static String read(InputStream is, Charset charset) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset))) {
            StringBuilder result = new StringBuilder(500);
            String line;
            while ((line = reader.readLine()) != null)
                result.append(line).append(Separator);

            int length = result.length();
            if (length == 0)
                return null;
            return result.substring(0, length - Separator.length());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(String source, File target) {
        write(source, StandardCharsets.UTF_8, target);
    }

    public static void write(String source, Charset charset, File target) {
        write(source.getBytes(charset), target);
    }

    public static void write(byte[] bytes, File target) {
        try (FileOutputStream os = new FileOutputStream(target)) {
            os.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(InputStream source, File target) {
        int buffer_size = 8192;
        try (BufferedInputStream bis = new BufferedInputStream(source, buffer_size);
             FileOutputStream fos = new FileOutputStream(target)) {

            byte[] buffer = new byte[buffer_size];
            int length;

            while ((length = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
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

            ByteBuffer buffer = ByteBuffer.allocate(8192);

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

    public static void delete(File file) {
        LinkedList<File> stack = new LinkedList<>();
        stack.add(file);
        while (!stack.isEmpty()) {
            File parent = stack.removeLast();
            if (parent.isDirectory()) {
                File[] children = parent.listFiles();
                if (children != null && children.length > 0) {
                    stack.add(parent);
                    Collections.addAll(stack, children);
                } else {
                    parent.delete();
                }
            } else {
                parent.delete();
            }
        }
    }

    public static void close(AutoCloseable a) {
        if (a != null)
            try {
                a.close();
            } catch (Exception ignored) {
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