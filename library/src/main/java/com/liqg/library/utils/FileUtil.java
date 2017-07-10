package com.liqg.library.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * 文件操作类
 */
public class FileUtil {
    static String tag = FileUtil.class.getName();

    /**
     * 获取文件根目录
     *有SD卡并且有权限返回SD卡根目录/包名
     *没有有SD卡或者有卡没有权限返回 应用data目录
     * @param context activity
     * @return filePath
     */
    public static String getRootFilePath(Context context) {

        PackageInfo info;
        String packageNames;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            packageNames = info.packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            Log.e(tag, "getPackageManager   NameNotFoundException");
            return "";
        }

        if (LiEnvironment.isSdCardExist()) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {//有SD卡并且有权限
                return Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + "/" + packageNames + "/";
            }
//            else {//有SD卡没有权限
//                return Environment.getDataDirectory()
//                        .getAbsolutePath() + "/data/" + packageNames + "/"; // filePath:
//            }


        }
//        else {

          ////有SD卡没有权限
           return context.getFilesDir()+"/";

//        }
    }

    /**
     * 文件是否存在
     *
     * @param filePath filePath
     * @return boolean
     */
    public static boolean fileIsExist(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }

        File f = new File(filePath);
        return f.exists();
    }

    /**
     * 读取文件
     *
     * @param filePath filePath
     * @return 文件转换为流
     */
    public static InputStream readFile(String filePath) {
        if (null == filePath || filePath.isEmpty()) {
            Log.e(tag, "Invalid param. filePath is null");
            return null;
        }
        InputStream is;
        try {
             if (fileIsExist(filePath)) {
                File f = new File(filePath);
                is = new FileInputStream(f);
            } else {
               return null;
             }
        } catch (Exception ex) {
            Log.e(tag, "Exception, ex: " + ex.toString());
            return null;
        }
        return is;
    }

    /**
     * 创建目录 如果路径是现有文件，会将文件先删除再创建目录
     *
     * @param filePath filePath
     * @return boolean
     */
    public static boolean createDirectory(String filePath) {
        if (null == filePath) {
            return false;
        }

        File file = new File(filePath);

        if (file.exists()) {
            if (file.isDirectory()) {
                return true;
            } else {
                if (!file.delete()) {
                    return false;
                }
            }
        }

        try {
            return file.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 删除目录（仅限deleteFile方法使用）
     *
     * @param filePath 路径
     * @return 删除结果
     */
    private static boolean deleteDirectory(String filePath) {


        File file = new File(filePath);

        if (file.isDirectory()) {
            File[] list = file.listFiles();

            for (File _file : list) {
                if (_file.isDirectory()) {
                    deleteDirectory(_file.getAbsolutePath());
                } else {
                    if (!_file.delete()) {
                        return false;
                    }
                }
            }
        }

        return file.delete();

    }

    /**
     * 删除文件
     *
     * @param filePath 路径
     * @return 删除结果
     */
    public static boolean deleteFile(String filePath) {

        if (null == filePath) {
            Log.e(tag, "Invalid param. filePath is null");
            return false;
        }

        File file = new File(filePath);

        if (file.exists()) {
            if (file.isDirectory()) {
                return deleteDirectory(filePath);
            } else {
                return file.delete();
            }

        } else {
            return true;
        }

    }


    /**
     * bate数组写文件
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param content  上下文
     * @return boolean
     */
    public static boolean writeFile(String filePath, String fileName, byte[] content) {
        if (null == filePath || null == fileName || null == content) {
            Log.e(tag, " param is null");
            return false;
        }

        if (!createDirectory(filePath)) {
            return false;
        }


        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filePath + "/" + fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            return false;

        }
        try {
            fos.write(content);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;


    }

}