package com.mycompany.cmtester;

import java.io.File;
import java.util.Vector;

/**
 * @author GN
 * @description
 * @date 2020/9/7
 */
public class TCList {

    protected static String getJavacArgForDir(String dir, String str, Vector targetFiles) {
        String result = str;
        String temp = "";

        File dirF = new File(dir);
        File[] javaF = dirF.listFiles(new ExtensionFilter("java"));
        if (javaF.length > 0) {
            result = result + dir + "/*.java ";

            for (int k = 0; k < javaF.length; k++) {
                temp = javaF[k].getAbsolutePath();
                temp.replace('\\', '/');
                targetFiles.add(temp.substring(dir.length() + 1, temp.length()));
            }
        }

        File[] sub_dir = dirF.listFiles(new DirFileFilter());
        if (sub_dir.length == 0) return result;

        for (int i = 0; i < sub_dir.length; i++) {
            result = getJavacArgForDir(sub_dir[i].getAbsolutePath(), result, targetFiles);
        }
        return result;
    }
}
