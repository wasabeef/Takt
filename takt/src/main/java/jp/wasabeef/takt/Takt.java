package jp.wasabeef.takt;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Copyright (C) 2020 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class Takt {

  private final static Program program = new Program();

  private Takt() {
  }

  public static Program stock(Application application) {
    return program.prepare(application);
  }

  public static void play() {
    program.play();
  }

  public static void finish() {
    program.stop();
  }

  public static class Program implements LifecycleListener.LifecycleCallbackListener {
    private Metronome metronome;
    private boolean show = true;
    private boolean isPlaying = false;
    private boolean showSetting = true;
    private boolean customControl = false;

    private Application app;
    private WindowManager wm;
    private View stageView;
    private TextView fpsText;
    private LayoutParams params;

    private final DecimalFormat decimal = new DecimalFormat("#.0' fps'");

    public Program() {
    }

    private Program prepare(Application application) {
      metronome = new Metronome();
      params = new LayoutParams();
      params.width = LayoutParams.WRAP_CONTENT;
      params.height = LayoutParams.WRAP_CONTENT;
      application.registerActivityLifecycleCallbacks(new LifecycleListener(this));

      if (isOverlayApiDeprecated()) {
        params.type = LayoutParams.TYPE_APPLICATION_OVERLAY;
      } else {
        params.type = LayoutParams.TYPE_TOAST;
      }
      params.flags = LayoutParams.FLAG_KEEP_SCREEN_ON | LayoutParams.FLAG_NOT_FOCUSABLE
        | LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_TOUCHABLE;
      params.format = PixelFormat.TRANSLUCENT;
      params.gravity = Seat.BOTTOM_RIGHT.getGravity();
      params.x = 10;

      app = application;
      wm = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
      LayoutInflater inflater = LayoutInflater.from(app);
      stageView = inflater.inflate(R.layout.stage, new RelativeLayout(app));
      fpsText = stageView.findViewById(R.id.takt_fps);

      listener(fps -> {
        if (fpsText != null) {
          fpsText.setText(decimal.format(fps));
        }
      });

      return this;
    }

    @Override
    public void onAppForeground() {
      if (!customControl) play();
    }

    @Override
    public void onAppBackground() {
      if (!customControl) stop();
    }

    public void play() {
      if (!hasOverlayPermission()) {
        if (showSetting) {
          startOverlaySettingActivity();
        } else {
          Log.w("takt", "Application has no Overlay permission");
        }
        return;
      }

      metronome.start();

      if (show && !isPlaying) {
        wm.addView(stageView, params);
        isPlaying = true;
      }
    }

    public void stop() {
      metronome.stop();

      if (show && isPlaying) {
        wm.removeView(stageView);
        isPlaying = false;
      }
    }

    public Program color(int color) {
      fpsText.setTextColor(color);
      return this;
    }

    public Program size(float size) {
      fpsText.setTextSize(size);
      return this;
    }

    public Program useCustomControl() {
      customControl = true;
      return this;
    }

    /*
     * alpha from = 0.0, to = 1.0
     */
    public Program alpha(float alpha) {
      fpsText.setAlpha(alpha);
      return this;
    }

    public Program interval(int ms) {
      metronome.setInterval(ms);
      return this;
    }

    public Program listener(Audience audience) {
      metronome.addListener(audience);
      return this;
    }

    public Program hide() {
      show = false;
      return this;
    }

    public Program seat(Seat seat) {
      params.gravity = seat.getGravity();
      return this;
    }

    public Program showOverlaySetting(boolean enable) {
      showSetting = enable;
      return this;
    }

    private boolean isOverlayApiDeprecated() {
      return Build.VERSION.SDK_INT >= 26;
    }

    private boolean hasOverlayPermission() {
      return Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(app);
    }

    private void startOverlaySettingActivity() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        app.startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
          Uri.parse("package:" + app.getPackageName())).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
      }
    }
  }
}
