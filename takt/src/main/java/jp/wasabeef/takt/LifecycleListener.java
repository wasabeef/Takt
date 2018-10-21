package jp.wasabeef.takt;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @author aleien on 21.10.18.
 */
public class LifecycleListener implements Application.ActivityLifecycleCallbacks {
    private int startedActivityCounter = 0;
    private LifecycleCallbackListener listener;

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
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityResumed(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}
}
