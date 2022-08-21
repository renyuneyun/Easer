/*
 * Copyright (c) 2016 - 2022 Rui Zhao <renyuneyun@gmail.com>
 *
 * This file is part of Easer.
 *
 * Easer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Easer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Easer.  If not, see <http://www.gnu.org/licenses/>.
 */

package ryey.easer.skills.usource.airplane_mode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import ryey.easer.skills.event.AbstractSlot;

public class AirplaneModeSlot extends AbstractSlot<AirplaneModeUSourceData> {

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
                changeSatisfiedState(intent.getBooleanExtra("state", false) == eventData.airplaneMode);
            }
        }
    };
    private static final IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);

    AirplaneModeSlot(@NonNull Context context, @NonNull AirplaneModeUSourceData data) {
        this(context, data, RETRIGGERABLE_DEFAULT, PERSISTENT_DEFAULT);
    }

    public AirplaneModeSlot(@NonNull Context context, @NonNull AirplaneModeUSourceData data, boolean retriggerable, boolean persistent) {
        super(context, data, retriggerable, persistent);
    }

    @Override
    public void listen() {
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void cancel() {
        context.unregisterReceiver(broadcastReceiver);
    }
}
