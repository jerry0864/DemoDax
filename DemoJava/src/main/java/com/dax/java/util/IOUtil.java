package com.dax.java.util;

import java.io.Closeable;

/**
 * Created by n5149 on 2017/11/22.
 */

public class IOUtil {
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
