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

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ryey.easer.R;
import ryey.easer.commons.local_skill.SourceCategory;
import ryey.easer.commons.local_skill.ValidData;
import ryey.easer.commons.local_skill.conditionskill.Tracker;
import ryey.easer.commons.local_skill.usource.USourceDataFactory;
import ryey.easer.commons.local_skill.usource.USourceSkill;
import ryey.easer.skills.SkillViewFragment;
import ryey.easer.skills.event.AbstractSlot;

public class AirplaneModeUSourceSkill implements USourceSkill<AirplaneModeUSourceData> {

    @NonNull
    @Override
    public String id() {
        return "airplane_mode";
    }

    @Override
    public int name() {
        return R.string.usource_airplane_mode;
    }

    @Override
    public boolean isCompatible(@NonNull final Context context) {
        return true;
    }

    @Nullable
    @Override
    public Boolean checkPermissions(@NonNull Context context) {
        return null;
    }

    @Override
    public void requestPermissions(@NonNull Activity activity, int requestCode) {
    }

    @NonNull
    @Override
    public USourceDataFactory<AirplaneModeUSourceData> dataFactory() {
        return new AirplaneModeUSourceDataFactory();
    }

    @NonNull
    @Override
    public SourceCategory category() {
        return SourceCategory.device;
    }

    @NonNull
    @Override
    public SkillViewFragment<AirplaneModeUSourceData> view() {
        return new AirplaneModeSkillViewFragment();
    }

    @Override
    public AbstractSlot<AirplaneModeUSourceData> slot(@NonNull Context context, @ValidData @NonNull AirplaneModeUSourceData data) {
        return new AirplaneModeSlot(context, data);
    }

    @Override
    public AbstractSlot<AirplaneModeUSourceData> slot(@NonNull Context context, @NonNull AirplaneModeUSourceData data, boolean retriggerable, boolean persistent) {
        return new AirplaneModeSlot(context, data, retriggerable, persistent);
    }

    @NonNull
    @Override
    public Tracker<AirplaneModeUSourceData> tracker(@NonNull Context context,
                                                    @ValidData @NonNull AirplaneModeUSourceData data,
                                                    @NonNull PendingIntent event_positive,
                                                    @NonNull PendingIntent event_negative) {
        return new AirplaneModeTracker(context, data, event_positive, event_negative);
    }

}
