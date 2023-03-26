package com.yty.godbatis.utils;

import java.io.InputStream;

/**
 * @author yty
 * @since 1.0
 * @version 1.0
 */

public class Resources {
    private Resources(){}
    public static InputStream getResourceAsStream(String resource){
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
    }


}
