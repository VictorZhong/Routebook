package me.routebook.routebook.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Victor on 2017/6/27.
 */

public class Util {
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /* 获取设备的唯一标识码 */
    public static String getDeviceId (Context context) {
        String ID = null;
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String DeviceID = tm.getDeviceId();
            String AndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            UUID uuid = new UUID(AndroidID.hashCode(), DeviceID.hashCode());
            ID = uuid.toString();
            ID = ID.replaceAll("-", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ID;
    }

    /* 将传入字符的首个字符换成大写 */
    public static String ucfirst (String str) {
        String first = str.substring(0, 1).toUpperCase();
        return first + str.substring(1);
    }

    /* 将传入字符全部换成大写 */
    public static String ucwords (String str) {
        return str.toUpperCase();
    }

    /* 将传入字符的首个字符换成小写 */
    public static String lcfirst (String str) {
        String first = str.substring(0, 1).toLowerCase();
        return first + str.substring(1);
    }

    /* 将传入字符全部换成小写 */
    public static String lcwords (String str) {
        return str.toLowerCase();
    }


    public static String md5 (String source) {
        byte[] digest;
        try {
            digest = MessageDigest.getInstance("MD5").digest(source.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return toHexString(digest);
    }

    public static String md5FromFile (String path) {
        return md5FromFile(new File(path));
    }

    public static String md5FromFile (File file) {
        if (file != null && file.exists()) {
            InputStream fis;
            byte[] buffer = new byte[1024];
            int numRead = 0;
            MessageDigest md5;
            try{
                fis = new FileInputStream(file);
                md5 = MessageDigest.getInstance("MD5");
                while ((numRead = fis.read(buffer)) > -1) {
                    md5.update(buffer, 0, numRead);
                }
                fis.close();
                return toHexString(md5.digest());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String toHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    public static boolean isInApp (Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return context.getPackageName().equals(rti.get(0).topActivity.getPackageName());
    }

    public static String getBaseUrl(String url){
        int index = url.indexOf("?");
        if (index != -1) {
            String result = url.substring(0, index);
            return result;
        } else {
            return url;
        }
    }

    /**
     * 获取APK签名信息
     * @param context
     * @return
     */
    public static String getSign(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);
        Iterator<PackageInfo> iter = apps.iterator();
        while(iter.hasNext()) {
            PackageInfo packageinfo = iter.next();
            String packageName = packageinfo.packageName;
            if (packageName.equals(context.getPackageName())) {
                return packageinfo.signatures[0].toCharsString();
            }
        }
        return null;
    }

    /**
     * 随机指定范围内N个不重复的数
     * 在初始化的无重复待选数组中随机产生一个数放入结果中，
     * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换
     * 然后从len-2里随机产生下一个随机数，如此类推
     * @param max  指定范围最大值
     * @param min  指定范围最小值
     * @param n  随机数个数
     * @return int[] 随机数结果集
     */
    public static int[] randomArray(int min,int max,int n){
        int len = max-min+1;

        if(max < min || n > len){
            return null;
        }

        //初始化给定范围的待选数组
        int[] source = new int[len];
        for (int i = min; i < min+len; i++){
            source[i-min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }

}
