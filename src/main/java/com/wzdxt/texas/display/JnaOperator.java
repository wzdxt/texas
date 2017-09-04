package com.wzdxt.texas.display;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * Created by wzdxt on 2017/9/4.
 */
public class JnaOperator {

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary)
                Native.loadLibrary((Platform.isWindows() ? "msvcrt" : "c"),
                        CLibrary.class);

        void printf(String format, Object... args);
    }

    public void test() {
        CLibrary.INSTANCE.printf("Hello, World, %d, %d\n", 111,222);
    }

    public static void main(String[] args) {
        new JnaOperator().test();
    }
}
