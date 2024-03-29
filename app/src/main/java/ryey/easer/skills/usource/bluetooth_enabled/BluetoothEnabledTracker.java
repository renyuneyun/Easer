/*
 * Copyright (c) 2016 - 2019 Rui Zhao <renyuneyun@gmail.com>
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

package ryey.easer.skills.usource.bluetooth_enabled;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ryey.easer.skills.condition.SkeletonTracker;

public class BluetoothEnabledTracker extends SkeletonTracker<BluetoothEnabledUSourceData> {

    private BluetoothAdapter bluetoothAdapter;  //TODO 1 20220327: Make static?

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                switch (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1)) {
                    case BluetoothAdapter.STATE_ON:
                        newSatisfiedState(data.enabled);
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        newSatisfiedState(!data.enabled);
                        break;
                    default:
                        newSatisfiedState(null);
                }
            }
        }
    };
    private static IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

    BluetoothEnabledTracker(Context context, BluetoothEnabledUSourceData data,
                   @NonNull PendingIntent event_positive,
                   @NonNull PendingIntent event_negative) {
        super(context, data, event_positive, event_negative);

        if (bluetoothAdapter == null)
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        newSatisfiedState(bluetoothAdapter.isEnabled());
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
