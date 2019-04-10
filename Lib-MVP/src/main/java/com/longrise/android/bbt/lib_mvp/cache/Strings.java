package com.longrise.android.bbt.lib_mvp.cache;

/**
 * Created by godliness on 2019/1/10.
 * From the BaoBao project
 *
 * @author godliness
 * @see StringBuffer
 */

public class Strings {

    private static final StringBuffer BUFFERS = new StringBuffer();

    public static synchronized StringBuffer getStrings() {
        if (BUFFERS.length() > 0) {
            BUFFERS.setLength(0);
        }
        return BUFFERS;
    }

    public static synchronized void clear() {
        BUFFERS.setLength(0);
    }
}
