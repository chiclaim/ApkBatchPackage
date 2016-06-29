package com.hoolay.core.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ManifestUtil {

    private static final String SP_KEY = "device_channel";
    public static final String START_FLAG = "META-INF/channel_";

    /**
     * 获取META-INFO下面的渠道
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        SharedPreferences sp = context.getSharedPreferences("hoolay_sp", Context.MODE_PRIVATE);
        String channel = sp.getString(SP_KEY, null);
        if (!TextUtils.isEmpty(channel)) {
            return channel;
        }
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains(START_FLAG)) {
                    channel = entryName.replaceAll(START_FLAG, "");
                    sp.edit().putString(SP_KEY, channel).apply();
                    return channel;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";

    }
}