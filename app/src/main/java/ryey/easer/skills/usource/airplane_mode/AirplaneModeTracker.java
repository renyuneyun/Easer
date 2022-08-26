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

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.annotation.NonNull;

import ryey.easer.skills.condition.SkeletonTracker;

public class AirplaneModeTracker extends SkeletonTracker<AirplaneModeUSourceData> {

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(intent.getAction())) {
                newSatisfiedState(intent.getBooleanExtra("state", false) == data.airplaneMode);
            }
        }
    };
    private static final IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);

    AirplaneModeTracker(Context context, AirplaneModeUSourceData data,
                        @NonNull PendingIntent event_positive,
                        @NonNull PendingIntent event_negative) {
        super(context, data, event_positive, event_negative);
        boolean curMode = (Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0);
        newSatisfiedState(curMode == this.data.airplaneMode);
    }

    @Override
    public void start() {
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void stop() {
        context.unregisterReceiver(broadcastReceiver);
    }
}
