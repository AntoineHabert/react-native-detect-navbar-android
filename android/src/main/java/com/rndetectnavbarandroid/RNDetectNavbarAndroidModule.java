package com.rndetectnavbarandroid;

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

  private float convertPixelsToDp(float px) {
    return px / (this.reactContext.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
  }

  private float _hasSoftKeys() {
    boolean hasSoftwareKeys;

    WindowManager window = getCurrentActivity().getWindowManager();

    if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
      Display d = getCurrentActivity().getWindowManager().getDefaultDisplay();

      DisplayMetrics realDisplayMetrics = new DisplayMetrics();
      d.getRealMetrics(realDisplayMetrics);

      int realHeight = realDisplayMetrics.heightPixels;
      int realWidth = realDisplayMetrics.widthPixels;

      //System.out.println("JAVA realHeight " + realHeight);
      //System.out.println("JAVA realWidth " + realWidth);

      DisplayMetrics displayMetrics = new DisplayMetrics();
      d.getMetrics(displayMetrics);

      int displayHeight = displayMetrics.heightPixels;
      int displayWidth = displayMetrics.widthPixels;

      System.out.println("JAVA displayHeight " + displayHeight);
      System.out.println("JAVA displayWidth " + displayWidth);

      hasSoftwareKeys = (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;

      //System.out.println("JAVA hasSoftwareKeys " + hasSoftwareKeys);

      return (this.convertPixelsToDp(realHeight - displayHeight));

    } else { // not supported anymore in our apps anyway
      boolean hasMenuKey = ViewConfiguration.get(this.reactContext).hasPermanentMenuKey();
      boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

      System.out.println("JAVA hasMenuKey " + hasMenuKey);
      System.out.println("JAVA hasBackKey " + hasBackKey);

      hasSoftwareKeys = !hasMenuKey && !hasBackKey;

      return 0;
    }
  }

  @ReactMethod
  public void hasSoftKeys(Promise promise) {
    promise.resolve(_hasSoftKeys());
  }
}
