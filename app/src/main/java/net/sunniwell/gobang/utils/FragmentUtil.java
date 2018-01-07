package net.sunniwell.gobang.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import net.sunniwell.jar.log.SWLogger;

/**
 * Created by lin on 2018/1/5.
 */

public class FragmentUtil {
    private static final SWLogger log = SWLogger.getLogger(FragmentUtil.class.getSimpleName());

    public static Fragment getFragment(FragmentManager fragmentManager, String tag){
        Fragment ret = null;
         log.d("SWGoBangLog ,SWGoBangLog ,fragmentManager = " + fragmentManager);
        if(fragmentManager != null && !TextUtils.isEmpty(tag)){
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
             log.d("SWGoBangLog ,SWGoBangLog ,fragment = " + fragment);
            if(fragment != null){
                ret = fragment;
            }
        }
        return ret;
    }

    public static void add(FragmentManager fragmentManager, int containerId, Fragment fragment){
        if(fragmentManager != null && containerId != -1 && fragment != null) {
             log.d("SWGoBangLog ,SWGoBangLog ,add in fragment = " + fragment);
            String randomTag = fragment.toString();
            add(fragmentManager, containerId, fragment, randomTag);
             log.d("SWGoBangLog ,add out");
        }
    }

    public static void add(FragmentManager fragmentManager, int containerId, Fragment fragment, String tag){
        if(fragmentManager != null && containerId != -1 && fragment != null && tag != null) {
             log.d("SWGoBangLog ,add in tag = " + tag);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(containerId, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commitAllowingStateLoss();
             log.d("SWGoBangLog ,add out");
        }
    }

    public static void remove(FragmentManager fragmentManager, String tag){
        if(fragmentManager != null && tag != null) {
             log.d("SWGoBangLog ,remove in tag = " + tag);
            remove(fragmentManager, getFragment(fragmentManager, tag));
             log.d("SWGoBangLog ,remove out");
        }
    }

    public static void remove(FragmentManager fragmentManager, Fragment fragment){
        if(fragmentManager != null && fragment != null){
             log.d("SWGoBangLog ,remove in fragment = " + fragment.toString());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commitAllowingStateLoss();
             log.d("SWGoBangLog ,remove out");
        }
    }

    public static void show(FragmentManager fragmentManager, String tag){
        if(fragmentManager != null && tag != null){
             log.d("SWGoBangLog ,show in tag = " + tag);
            show(fragmentManager, getFragment(fragmentManager, tag));
             log.d("SWGoBangLog ,show out");
        }
    }

    public static void show(FragmentManager fragmentManager, Fragment fragment){
        if(fragmentManager != null && fragment != null){
             log.d("SWGoBangLog ,show in fragment = " + fragment.toString());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.show(fragment);
            fragmentTransaction.commitAllowingStateLoss();
             log.d("SWGoBangLog ,show out");
        }
    }

    public static void hide(FragmentManager fragmentManager, String tag){
        if(fragmentManager != null && tag != null){
             log.d("SWGoBangLog ,hide in tag = " + tag);
            hide(fragmentManager, getFragment(fragmentManager, tag));
             log.d("SWGoBangLog ,hide out");
        }
    }

    public static void hide(FragmentManager fragmentManager, Fragment fragment){
        if(fragmentManager != null && fragment != null){
             log.d("SWGoBangLog ,hide in fragment = " + fragment.toString());
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.hide(fragment);
            fragmentTransaction.commitAllowingStateLoss();
             log.d("SWGoBangLog ,hide out");
        }
    }
}
