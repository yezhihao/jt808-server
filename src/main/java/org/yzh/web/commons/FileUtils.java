package org.yzh.web.commons;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Stack;
import java.util.function.Function;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class FileUtils {

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
        try {
            return read(new FileInputStream(file), charset);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String read(InputStream is) {
        return read(is, StandardCharsets.UTF_8);
    }

    public static String read(InputStream is, Charset charset) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                result.append(line).append(Separator);

            return result.substring(0, result.length() - Separator.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write(File file, String text) {
        return write(file, text, StandardCharsets.UTF_8);
    }

    public static boolean write(File file, String text, Charset charset) {
        try {
            return write(new FileOutputStream(file), text, charset);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean write(OutputStream os, String text, Charset charset) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, charset))) {
            writer.write(text);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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

    public static void foreach(File file, Function<String, Boolean> mappingFunction) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (Boolean.FALSE.equals(mappingFunction.apply(line)))
                    break;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static void eachInterl(File root, Callback call) {
        File current = root;
        int depth = 1;

        while (depth > 0) {
            File[] files = current.listFiles();
            if (files.length > 0) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        current = file;
                        depth++;
                        break;
                    }
                    file.delete();
                }
            } else {
                current.delete();
                current = current.getParentFile();
                depth--;
            }
        }
    }

    public static void deleteAll(File root) {
        Stack<File> stack = new Stack<>();
        File current = root;
        while (!stack.empty() || current != null) {
            if (isDelete(current)) {
                current.delete();
                current = null;
            } else {
                stack.push(current);
                for (File file : current.listFiles()) {
                    if (isDelete(file)) {
                        file.delete();
                    } else {
                        stack.push(file);
                    }
                }
            }
            if (!stack.empty())
                current = stack.pop();
        }
    }

    public static boolean isDelete(File file) {
        return (file.isFile() || file.list() == null || file.list().length == 0);
    }

    public static void close(Closeable a) {
        if (a != null)
            try {
                a.close();
            } catch (IOException e) {
            }
    }

    @FunctionalInterface
    public interface Callback {
        boolean call(String line);
    }
}