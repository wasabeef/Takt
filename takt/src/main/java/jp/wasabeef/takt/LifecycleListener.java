package jp.wasabeef.takt;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

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

public class LifecycleListener implements Application.ActivityLifecycleCallbacks {
  private int startedActivityCounter = 0;
  private final LifecycleCallbackListener listener;

  public LifecycleListener(LifecycleCallbackListener listener) {
    this.listener = listener;
  }

  public interface LifecycleCallbackListener {
    void onAppForeground();

    void onAppBackground();
  }

  @Override
  public void onActivityStarted(Activity activity) {
    synchronized (this) {
      startedActivityCounter++;
      if (startedActivityCounter == 1 && listener != null) {
        listener.onAppForeground();
      }
    }
  }

  @Override
  public void onActivityStopped(Activity activity) {
    synchronized (this) {
      startedActivityCounter--;
      if (startedActivityCounter == 0 && listener != null) {
        listener.onAppBackground();
      }
    }
  }

  @Override
  public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
  }

  @Override
  public void onActivityResumed(Activity activity) {
  }

  @Override
  public void onActivityPaused(Activity activity) {
  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
  }

  @Override
  public void onActivityDestroyed(Activity activity) {
  }
}
