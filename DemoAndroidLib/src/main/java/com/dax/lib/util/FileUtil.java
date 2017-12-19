package com.dax.lib.util;

import org.apache.commons.compress.utils.IOUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    public static final String ENCODING = "UTF-8";

    public static String readFileString(File f) throws IOException {
        return new String(readFileByte(f), ENCODING);
    }

    public static String readStreamString(InputStream is) throws IOException {
        return new String(readStreamByte(is), ENCODING);
    }

    public static byte[] readFileByte(File f) throws IOException {
        return readStreamByte(new FileInputStream(f));
    }

    public static byte[] readStreamByte(InputStream is) throws IOException {
        byte[] ret = IOUtils.toByteArray(is);
        is.close();
        return ret;
    }

    public static long getFolderSizeInByte(File folder) {
        if (folder == null) {
            return 0;
        }
        long size = 0;
        if (!folder.isDirectory()) {
            return size;
        }
        File[] fileList = folder.listFiles();
        if (fileList == null) {
            return 0;
        }
        for (int i = 0; i < fileList.length; i++) {
            if (fileList[i].isDirectory()) {
                size += getFolderSizeInByte(fileList[i]);
            } else {
                size += fileList[i].length();
            }
        }
        return size;
    }

    public static void deleteDir(File fileOrDirectory) {
        if (fileOrDirectory == null) {
            return;
        }
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteDir(child);

        fileOrDirectory.delete();
    }

    public static void deleteFileInDir(File fileOrDirectory) {
        if (fileOrDirectory == null) {
            return;
        }
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteDir(child);
    }

    public static void write(String file, String content) {
        write(new File(file), content);
    }

    public static void write(File file, String content) {
        write(file, content, false);
    }

    public static void write(File file, String content, boolean append) {
        boolean fileExist = file.exists();
        if (!fileExist) {
            File dir = file.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                fileExist = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fileExist) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(file, append));
                out.write(content);
                out.flush(); // 把缓存区内容压入文件
                out.close(); // 最后记得关闭文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean copy(File source, File dest) {
        if (dest.exists()) {
            dest.delete();
        }
        File file = dest.getParentFile();
        if (!file.exists()) {
            file.mkdirs();
            if (!file.exists()) {
                return false;
            }
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            return dest.exists() && source.length() == dest.length();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.close(is);
            IOUtil.close(os);
        }
        return false;
    }
}
