package com.bit.safetycommunity.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Environment;

import java.io.File;


public class FileUtils {

    public static File getAppRootPath(Context context) {
        if (sdCardIsAvailable()) {
            return Environment.getExternalStorageDirectory();
        } else {
            return context.getFilesDir();
        }
    }

    public static boolean sdCardIsAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            return sd.canWrite();
        } else
            return false;
    }

    /**
     * Copy to clipboard.
     */
    public static boolean copyTextToClipboard(Context context, CharSequence content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            ClipData clipData = ClipData.newPlainText(content, content);
            clipboardManager.setPrimaryClip(clipData);
            return true;
        }
        return false;
    }
}
