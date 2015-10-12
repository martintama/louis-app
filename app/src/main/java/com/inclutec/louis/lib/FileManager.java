package com.inclutec.louis.lib;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by martin on 12/10/2015.
 */
public class FileManager {

    public void FileManager(){

    }
    public void copyFile(String src, String dst) {
        try {
            FileChannel Filesrc = new FileInputStream(src).getChannel();
            FileChannel Filedst = new FileOutputStream(dst).getChannel();
            Filedst.transferFrom(Filesrc, 0, Filesrc.size());
            Filedst.close();
            Filesrc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dirChecker(String dir) {
        File f = new File(dir);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }
}
