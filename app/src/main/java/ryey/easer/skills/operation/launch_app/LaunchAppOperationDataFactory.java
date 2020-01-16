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

package ryey.easer.skills.operation.launch_app;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.ValidData;
import ryey.easer.commons.local_skill.operationskill.OperationDataFactory;
import ryey.easer.plugin.PluginDataFormat;
import ryey.easer.skills.operation.Extras;

class LaunchAppOperationDataFactory implements OperationDataFactory<LaunchAppOperationData> {
    @NonNull
    @Override
    public Class<LaunchAppOperationData> dataClass() {
        return LaunchAppOperationData.class;
    }

    @ValidData
    @NonNull
    @Override
    public LaunchAppOperationData dummyData() {
        return new LaunchAppOperationData("com.dummy.app.package", "com.dummy.app.package.Activity1", new Extras());
    }

    @ValidData
    @NonNull
    @Override
    public LaunchAppOperationData parse(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        return new LaunchAppOperationData(data, format, version);
    }
}
