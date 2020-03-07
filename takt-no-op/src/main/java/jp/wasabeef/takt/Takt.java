package jp.wasabeef.takt;

import android.app.Application;

/**
 * Copyright (C) 2019 Wasabeef
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
    }

    public static void finish() {
    }

    public static class Program implements LifecycleListener.LifecycleCallbackListener {
        public Program() {
        }

        private Program prepare(Application application) {
            return this;
        }

        @Override
        public void onAppForeground() {
        }

        @Override
        public void onAppBackground() {
        }

        public void play() {
        }

        public void stop() {
        }

        public Program color(int color) {
            return this;
        }

        public Program size(float size) {
            return this;
        }

        public Program useCustomControl() {
            return this;
        }

        public Program alpha(float alpha) {
            return this;
        }

        public Program interval(int ms) {
            return this;
        }

        public Program listener(Audience audience) {
            return this;
        }

        public Program hide() {
            return this;
        }

        public Program seat(Seat seat) {
            return this;
        }

        public Program showOverlaySetting(boolean enable) {
            return this;
        }
    }
}
