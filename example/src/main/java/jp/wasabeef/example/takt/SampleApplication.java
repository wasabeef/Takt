package jp.wasabeef.example.takt;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;
import jp.wasabeef.takt.Audience;
import jp.wasabeef.takt.Seat;
import jp.wasabeef.takt.Takt;

/**
 * Copyright (C) 2017 Wasabeef
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

public class SampleApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    Takt.stock(this)
        .seat(Seat.TOP_RIGHT)
        .interval(250)
        .color(Color.WHITE)
        .size(14f)
        .alpha(.5f)
        .listener(new Audience() {
          @Override public void heartbeat(double fps) {
            Log.d("Excellent!", fps + " fps");
          }
        })
        .showOverlaySetting(true)
        .play();
  }

  @Override public void onTerminate() {
    Takt.finish();
    super.onTerminate();
  }
}
