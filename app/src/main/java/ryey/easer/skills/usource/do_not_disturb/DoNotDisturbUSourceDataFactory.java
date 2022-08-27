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

package ryey.easer.skills.usource.do_not_disturb;

import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.util.HashSet;
import java.util.Set;

import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.ValidData;
import ryey.easer.commons.local_skill.usource.USourceDataFactory;
import ryey.easer.plugin.PluginDataFormat;

@RequiresApi(api = Build.VERSION_CODES.M)
class DoNotDisturbUSourceDataFactory implements USourceDataFactory<DoNotDisturbUSourceData> {
    @NonNull
    @Override
    public Class<DoNotDisturbUSourceData> dataClass() {
        return DoNotDisturbUSourceData.class;
    }

    @ValidData
    @NonNull
    @Override
    public DoNotDisturbUSourceData dummyData() {
        Set<Integer> data = new HashSet<>();
        data.add(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
        data.add(NotificationManager.INTERRUPTION_FILTER_NONE);
        data.add(NotificationManager.INTERRUPTION_FILTER_ALARMS);
        return new DoNotDisturbUSourceData(data);
    }

    @ValidData
    @NonNull
    @Override
    public DoNotDisturbUSourceData parse(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        return new DoNotDisturbUSourceData(data, format, version);
    }
}
