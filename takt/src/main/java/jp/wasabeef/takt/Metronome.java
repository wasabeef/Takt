package jp.wasabeef.takt;

import android.view.Choreographer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

class Metronome implements Choreographer.FrameCallback {

  private final Choreographer choreographer;

  private long frameStartTime = 0;
  private int framesRendered = 0;

  private final List<Audience> listeners = new ArrayList<>();
  private int interval = 500;

  public Metronome() {
    choreographer = Choreographer.getInstance();
  }

  public void start() {
    choreographer.postFrameCallback(this);
  }

  public void stop() {
    frameStartTime = 0;
    framesRendered = 0;
    choreographer.removeFrameCallback(this);
  }

  public void addListener(Audience l) {
    listeners.add(l);
  }

  public void setInterval(int interval) {
    this.interval = interval;
  }

  @Override
  public void doFrame(long frameTimeNanos) {
    long currentTimeMillis = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos);

    if (frameStartTime > 0) {
      // take the span in milliseconds
      final long timeSpan = currentTimeMillis - frameStartTime;
      framesRendered++;

      if (timeSpan > interval) {
        final double fps = framesRendered * 1000 / (double) timeSpan;

        frameStartTime = currentTimeMillis;
        framesRendered = 0;

        for (Audience audience : listeners) {
          audience.heartbeat(fps);
        }
      }
    } else {
      frameStartTime = currentTimeMillis;
    }

    choreographer.postFrameCallback(this);
  }
}
