package com.logopek.desim;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.List;

public class Checker {
    public static boolean hasRootAccess() {
        try {
            java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(new String[]{"/system/bin/su","-c","cd / && ls"}).getInputStream()).useDelimiter("\\A");
            return !(s.hasNext() ? s.next() : "").equals("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getCommandOutput(String command){
        try {
            java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(new String[]{"/system/bin/su","-c",command}).getInputStream()).useDelimiter("\\A");
            return s.next();
        }
        catch (IOException e){
            e.printStackTrace();
        }


        return null;
    }

}
