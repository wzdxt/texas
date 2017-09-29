package com.wzdxt.texas;

import org.junit.Ignore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/10.
 */
public class Test {
    @org.junit.Test
    @Ignore
    public void test() throws Exception {
        File img = new File("/Users/wzdxt/projects/texas/src/main/resources/static/train/totalCoin.bmp");
        List<String> cmd = new ArrayList<String>(); // 存放命令行参数的数组
        cmd.add("tesseract");
        cmd.add(img.getName());
        cmd.add(img.getParent()+"/out");
//        cmd.add("");
        cmd.add("-l");
//        cmd.add("");
        cmd.add("chi_sim");
//        cmd.add("");
        cmd.add("-psm");
//        cmd.add("");
        cmd.add("7");
        ProcessBuilder pb = new ProcessBuilder();
        pb.directory(img.getParentFile());

        pb.command(cmd); // 执行命令行
//        pb.command("cat /Users/wzdxt/projects/texas/src/main/resources/static/train/totalCoin.bmp"); // 执行命令行
//        pb.command("cat ./"); // 执行命令行
        pb.redirectErrorStream(true); // 通知进程生成器是否合并标准错误和标准输出,把进程错误保存起来。
        Process process = pb.start(); // 开始执行进程
        int w = process.waitFor(); // 当前进程停止,直到process停止执行，返回执行结果.
    }

}
