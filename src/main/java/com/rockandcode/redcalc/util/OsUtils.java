package com.rockandcode.redcalc.util;

public class OsUtils {

    private static final OsUtils instance = new OsUtils();

    public static OsUtils getInstance() {
        return instance;
    }

    public OS getOS() {
        String operSys = System.getProperty("os.name").toLowerCase();
        if (operSys.contains("win")) {
            return OS.WINDOWS;
        } else if (operSys.contains("nix") || operSys.contains("nux")
                || operSys.contains("aix")) {
            return OS.LINUX;
        } else if (operSys.contains("mac")) {
            return OS.MAC;
        } else {
            return OS.OTHER;
        }

    }

}
