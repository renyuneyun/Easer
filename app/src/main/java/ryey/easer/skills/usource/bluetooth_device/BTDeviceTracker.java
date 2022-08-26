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

package ryey.easer.skills.usource.bluetooth_device;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;

import ryey.easer.skills.condition.SkeletonTracker;

public class BTDeviceTracker extends SkeletonTracker<BTDeviceUSourceData> {

    private int matched_devices = 0;

    private final BroadcastReceiver connReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (is_target(device)) {
                    matched_devices++;
                    determine_satisfied();
                }
            } else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (is_target(device)) {
                    matched_devices--;
                    determine_satisfied();
                }
            } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                if (intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0) == BluetoothAdapter.STATE_OFF) {
                    matched_devices = 0;
                    determine_satisfied();
                }
            }
        }
    };

    private final IntentFilter filter;

    {
        filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
    }

    BTDeviceTracker(Context context, BTDeviceUSourceData data,
                    @NonNull PendingIntent event_positive,
                    @NonNull PendingIntent event_negative) {
        super(context, data, event_positive, event_negative);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            for (int profile : new int[]{BluetoothProfile.GATT, BluetoothProfile.GATT_SERVER}) {
                for (BluetoothDevice btDevice : bluetoothManager.getConnectedDevices(profile)) {
                    if (is_target(btDevice)) {
                        matched_devices++;
                    }
                }
            }
        }
        determine_satisfied();
    }

    @Override
    public void start() {
        context.registerReceiver(connReceiver, filter);
    }

    @Override
    public void stop() {
        context.unregisterReceiver(connReceiver);
    }

    private boolean is_target(BluetoothDevice device) {
        return data.hwAddresses.contains(device.getAddress());
    }

    private void determine_satisfied() {
        if (matched_devices < 0) matched_devices = 0;
        newSatisfiedState(matched_devices > 0);
    }
}
