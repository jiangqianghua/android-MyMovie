package com.jqh.mymovie.utils;

/**
 * Created by jiangqianghua on 18/1/16.
 */

public class StrUtils {
    public static String getStrByRange(String str , int lenght)
    {
        if(str == null || str.isEmpty())
        {
            return "";
        }

        if(str.length() <= lenght)
            return str ;
        return str.substring(0,lenght);
    }
}
