/*
 * Copyright (c) 2016 - 2018 Rui Zhao <renyuneyun@gmail.com>
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

package ryey.easer.skills.operation.toast;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import ryey.easer.skills.operation.OperationLoader;

public class ToastLoader extends OperationLoader<ToastOperationData> {
    ToastLoader(Context context) {
        super(context);
    }

    @Override
    public void _load(@NonNull ToastOperationData data, @NonNull OnResultCallback callback) {
        Toast.makeText(context, data.text.raw, Toast.LENGTH_SHORT).show();
        callback.onResult(true);
    }
}
