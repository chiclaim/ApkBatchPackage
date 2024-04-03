
import android.content.Context;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * APK 渠道工具类
 */
public class ChannelUtil {

    private ChannelUtil() {
    }

    private static final String START_FLAG = "META-INF/channel_";

    /**
     * 获取渠道名
     *
     * @return 返回渠道名，没有则返回 null
     */
    public static String getChannel(Context context) {
        String sourceDir = context.getApplicationInfo().sourceDir;
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.contains(START_FLAG)) {
                    return entryName.replaceAll(START_FLAG, "");
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
        return null;
    }

}
