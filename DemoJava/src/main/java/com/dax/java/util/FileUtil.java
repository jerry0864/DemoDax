package com.dax.java.util;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


/**
 * Created by xielaobao on 1/4/16.
 */
public class FileUtil {
    private static final int BUFFER_SIZE = 8 * 1024;
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

    public static boolean write(String file, String content) {
        return write(new File(file), content);
    }

    public static boolean write(File file, String content) {
        return write(file, content, false);
    }

    public static boolean write(File file, String content, boolean append) {
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
                //BufferedWriter out = new BufferedWriter(new FileWriter(file, append));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), ENCODING));
                out.write(content);
                out.flush(); // 把缓存区内容压入文件
                out.close(); // 最后记得关闭文件
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
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
            byte[] buffer = new byte[BUFFER_SIZE];
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

    public static boolean copy(InputStream ins, File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        OutputStream os = new FileOutputStream(file);
        copy(ins, os);
        return file.exists();

    }

    public static boolean copy(InputStream ins, OutputStream os) throws IOException {
        int bytesRead = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        while ((bytesRead = ins.read(buffer, 0, BUFFER_SIZE)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return true;
    }


    public static boolean checkSameFile(File source, File target) {
        if (!source.exists() || !target.exists()) {
            return false;
        }
        if (source.length() != target.length()) {
            return false;
        }
        try {
            return checkSameFile(new FileInputStream(source), new FileInputStream(target));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkSameFile(InputStream ins, InputStream ins2) {
        int bytesRead = 0;
        int bytesRead2 = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        byte[] buffer2 = new byte[BUFFER_SIZE];
        try {
            while ((bytesRead = ins.read(buffer, 0, BUFFER_SIZE)) != -1) {
                bytesRead2 = ins2.read(buffer2, 0, BUFFER_SIZE);
                if (bytesRead2 != bytesRead) {
                    return false;
                }
                for (int i = 0; i < bytesRead; i++) {
                    if (buffer[i] != buffer2[i]) {
                        return false;
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
