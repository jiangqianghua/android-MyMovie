package com.jqh.mymovie;

import android.support.v4.app.Fragment;

import com.jqh.mymovie.base.BaseFragment;

import java.util.HashMap;

/**
 * Created by jiangqianghua on 17/9/23.
 */

public class FragmentManagerWrapper {

    private volatile static FragmentManagerWrapper mInstance = null ;

    public static FragmentManagerWrapper getInstance(){
        if(mInstance == null)
        {
            synchronized (FragmentManagerWrapper.class){
                if(mInstance == null)
                {
                    mInstance = new FragmentManagerWrapper();
                }
            }
        }

        return mInstance ;
    }
    public Fragment createFragment(Class<?> clazz)
    {
        return createFragment(clazz,true);
    }
    private HashMap<String,Fragment> mHashMap = new HashMap<String,Fragment>();
    public Fragment createFragment(Class<?> clazz ,boolean isobtain){
        Fragment resultFragment = null ;
        String className = clazz.getName();
        if(mHashMap.containsKey(className)) {
            resultFragment = mHashMap.get(className);
        }
        else{
            try {
                resultFragment = (BaseFragment)Class.forName(className).newInstance();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        if(isobtain)
        {
            mHashMap.put(className,resultFragment);
        }
        return resultFragment ;
    }
}
