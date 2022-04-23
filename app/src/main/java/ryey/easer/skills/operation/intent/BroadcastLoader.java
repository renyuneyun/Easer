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

package ryey.easer.skills.operation.intent;

import android.content.Context;

import androidx.annotation.NonNull;

import ryey.easer.commons.local_skill.ValidData;

public class BroadcastLoader extends IntentLoader<BroadcastOperationData> {
    public BroadcastLoader(Context context) {
        super(context);
    }

    @Override
    public void _load(@ValidData @NonNull BroadcastOperationData data, @NonNull OnResultCallback callback) {
        context.sendBroadcast(this.getIntent(data));
        callback.onResult(true);
    }
}
