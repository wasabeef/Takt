package jp.wasabeef.takt;

import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.text.DecimalFormat;

/**
 * Copyright (C) 2015 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
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

  public static void finish() {
    program.stop();
  }

  public static class Program {
    private Metronome metronome;
    private boolean show = true;

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
      params.type = LayoutParams.TYPE_TOAST;
      params.flags = LayoutParams.FLAG_KEEP_SCREEN_ON | LayoutParams.FLAG_NOT_FOCUSABLE
          | LayoutParams.FLAG_NOT_TOUCH_MODAL;
      params.format = PixelFormat.TRANSLUCENT;
      params.gravity = Gravity.BOTTOM | Gravity.END;
      params.x = 10;

      wm = WindowManager.class.cast(application.getSystemService(Context.WINDOW_SERVICE));
      LayoutInflater inflater = LayoutInflater.from(application);
      stageView = inflater.inflate(R.layout.stage, new RelativeLayout(application));
      fpsText = (TextView) stageView.findViewById(R.id.takt_fps);

      listener(new Audience() {
        @Override public void heartbeat(double fps) {
          if (fpsText != null) {
            fpsText.setText(decimal.format(fps));
          }
        }
      });

      return this;
    }

    public void play() {
      metronome.start();

      if (show) {
        wm.addView(stageView, params);
      }
    }

    public void stop() {
      metronome.stop();

      if (show && stageView != null) {
        wm.removeView(stageView);
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

    /*
     * alpha from = 0.0, to = 1.0
     */
    public Program alpha(int alpha) {
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
      switch (seat) {
        case TOP_RIGHT:
          params.gravity = Gravity.TOP | Gravity.END;
          break;
        case TOP_LEFT:
          params.gravity = Gravity.TOP | Gravity.START;
          break;
        case TOP_CENTER:
          params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
          break;
        case CENTER:
          params.gravity = Gravity.CENTER;
          break;
        case RIGHT_CENTER:
          params.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
          break;
        case LEFT_CENTER:
          params.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
          break;
        case BOTTOM_RIGHT:
          params.gravity = Gravity.BOTTOM | Gravity.END;
          break;
        case BOTTOM_LEFT:
          params.gravity = Gravity.BOTTOM | Gravity.START;
          break;
        case BOTTOM_CENTER:
          params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
          break;
      }
      return this;
    }
  }
}