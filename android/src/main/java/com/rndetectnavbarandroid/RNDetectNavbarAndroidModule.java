package com.rndetectnavbarandroid;
/*
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.WindowManager;
import android.view.KeyCharacterMap;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.Display;
import android.content.Context;
import android.util.Log;
*/
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.UiThreadUtil;

import java.util.HashMap;
import java.util.Map;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * {@link NativeModule} that allows changing the appearance of the menu bar.
 */
public class RNDetectNavbarAndroidModule extends ReactContextBaseJavaModule { 

  ReactApplicationContext reactContext;

  public RNDetectNavbarAndroidModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNDetectNavbarAndroid";
  }

  /*
  @Override
  public Map<String, Object> getConstants() {
      Map<String, Object> constants = new HashMap<>();
  
      constants.put("has_soft_keys", hasSoftKeys());
  
      return constants;
  }
*/

  private boolean _hasSoftKeys() {
    boolean hasSoftwareKeys;

    /*
    Activity activity = getCurrentActivity();

    if (activity == null) {
        return true;
    }
*/
    WindowManager window = getCurrentActivity().getWindowManager();

    if(window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
        Display d = getCurrentActivity().getWindowManager().getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        hasSoftwareKeys =  (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    } else {
        boolean hasMenuKey = ViewConfiguration.get(this.reactContext).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        hasSoftwareKeys = !hasMenuKey && !hasBackKey;
    }

    return hasSoftwareKeys;
}

  @ReactMethod
  public void hasSoftKeys(Promise promise) {    
    promise.resolve(_hasSoftKeys());
  }  
/*
  private static boolean hasImmersive;
  private static boolean cached = false;

  @SuppressLint ("NewApi")
  private boolean hasImmersive() {

      if (!cached) {
          if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
              hasImmersive = false;
              cached = true;
              return false;
          }
          Display d = ((WindowManager) reactContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

          DisplayMetrics realDisplayMetrics = new DisplayMetrics();
          d.getRealMetrics(realDisplayMetrics);

          int realHeight = realDisplayMetrics.heightPixels;
          int realWidth = realDisplayMetrics.widthPixels;

          DisplayMetrics displayMetrics = new DisplayMetrics();
          d.getMetrics(displayMetrics);

          int displayHeight = displayMetrics.heightPixels;
          int displayWidth = displayMetrics.widthPixels;

          hasImmersive = (realWidth > displayWidth) || (realHeight > displayHeight);
          cached = true;
      }

      return hasImmersive;
  }*/
}
