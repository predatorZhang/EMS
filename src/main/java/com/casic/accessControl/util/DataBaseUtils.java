package com.casic.accessControl.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataBaseUtils {
    public static void delSchTask(String tn) {
        try {
            String cmd = "schtasks /delete /tn \"" + tn + "\" /f";
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addSchTask(String tn, String tr, String mo) {
        try {
            String cmd = "schtasks /create /tn \"" + tn + "\" /tr " + tr
                    + " /sc minute /mo " + mo + " /ru \"System\"";
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String doExp(String userName, String password, String sid, String owner, String filePath) throws IOException, InterruptedException {
        String info = "导出成功！";
        String[] cmds = new String[3];
        cmds[0] = "cmd";
        cmds[1] = "/C";
        cmds[2] = "exp " + userName + "/" + password + "@" + sid
                + " file=" + filePath + " owner=" + owner;

        Process process = null;
        process = Runtime.getRuntime().exec(cmds);

        boolean shouldClose = false;

        InputStreamReader isr = new InputStreamReader(
                process.getErrorStream());
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            if (line.indexOf("????") != -1) {
                shouldClose = true;
                break;
            }
        }

        if (shouldClose) {
            process.destroy();
        }

        int exitVal;

        exitVal = process.waitFor();
        System.out.println(exitVal);

        return info;
    }

    public static void doImp(String userName, String password, String orcSid,
                             String fileName, String tbl) throws IOException, InterruptedException {
        String[] cmds = new String[3];
        cmds[0] = "cmd";
        cmds[1] = "/C";
        cmds[2] = "imp " + userName + "/" + password + "@" + orcSid
                + " fromuser=scott touser=" + userName + " file=" + fileName
                + " ignore=y destroy=y tables=(" + tbl + ")";

        Process process = null;
        process = Runtime.getRuntime().exec(cmds);

        boolean shouldClose = false;

        InputStreamReader isr = new InputStreamReader(
                process.getErrorStream());
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            if (line.indexOf("????") != -1) {
                shouldClose = true;
                break;
            }
        }

        if (shouldClose) {
            process.destroy();
        }

        int exitVal;

        exitVal = process.waitFor();
        System.out.println(exitVal);
    }

}
