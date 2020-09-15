package jp.wasabeef.takt;

import android.view.Gravity;

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

public enum Seat {
  TOP_RIGHT(Gravity.TOP | Gravity.END),
  TOP_LEFT(Gravity.TOP | Gravity.START),
  TOP_CENTER(Gravity.TOP | Gravity.CENTER_HORIZONTAL),

  RIGHT_CENTER(Gravity.END | Gravity.CENTER_VERTICAL),
  LEFT_CENTER(Gravity.START | Gravity.CENTER_VERTICAL),
  CENTER(Gravity.CENTER),

  BOTTOM_RIGHT(Gravity.BOTTOM | Gravity.END),
  BOTTOM_LEFT(Gravity.BOTTOM | Gravity.START),
  BOTTOM_CENTER(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

  private final int gravity;

  Seat(int gravity) {
    this.gravity = gravity;
  }

  public int getGravity() {
    return gravity;
  }
}
