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

package ryey.easer.skills.operation.send_sms;

import android.content.Context;
import android.telephony.SmsManager;

import androidx.annotation.NonNull;

import ryey.easer.commons.local_skill.ValidData;
import ryey.easer.skills.operation.OperationLoader;

public class SmsLoader extends OperationLoader<SmsOperationData> {
    public SmsLoader(Context context) {
        super(context);
    }

    @Override
    public void _load(@ValidData @NonNull SmsOperationData data, @NonNull OnResultCallback callback) {
        String destination = data.destination.raw;
        String content = data.content.raw;
        SmsManager smsManager = SmsManager.getDefault();
        if (smsManager == null) {
            callback.onResult(false);
            return;
        }
        smsManager.sendTextMessage(destination, null, content, null, null);
        callback.onResult(true);
    }
}
