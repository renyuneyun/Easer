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

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import ryey.easer.R;
import ryey.easer.commons.local_skill.SourceCategory;
import ryey.easer.commons.local_skill.ValidData;
import ryey.easer.commons.local_skill.conditionskill.Tracker;
import ryey.easer.commons.local_skill.usource.USourceDataFactory;
import ryey.easer.commons.local_skill.usource.USourceSkill;
import ryey.easer.skills.SkillViewFragment;
import ryey.easer.skills.event.AbstractSlot;

public class DoNotDisturbUSourceSkill implements USourceSkill<DoNotDisturbUSourceData> {

    @NonNull
    @Override
    public String id() {
        return "do_not_disturb";
    }

    @Override
    public int name() {
        return R.string.usource_do_not_disturb;
    }

    @Override
    public boolean isCompatible(@NonNull final Context context) {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    @Nullable
    @Override
    public Boolean checkPermissions(@NonNull Context context) {
        return null;
    }

    @Override
    public void requestPermissions(@NonNull Activity activity, int requestCode) {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public USourceDataFactory<DoNotDisturbUSourceData> dataFactory() {
        return new DoNotDisturbUSourceDataFactory();
    }

    @NonNull
    @Override
    public SourceCategory category() {
        return SourceCategory.device;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public SkillViewFragment<DoNotDisturbUSourceData> view() {
        return new DoNotDisturbSkillViewFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public AbstractSlot<DoNotDisturbUSourceData> slot(@NonNull Context context, @ValidData @NonNull DoNotDisturbUSourceData data) {
        return new DoNotDisturbSlot(context, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public AbstractSlot<DoNotDisturbUSourceData> slot(@NonNull Context context, @NonNull DoNotDisturbUSourceData data, boolean retriggerable, boolean persistent) {
        return new DoNotDisturbSlot(context, data, retriggerable, persistent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Tracker<DoNotDisturbUSourceData> tracker(@NonNull Context context,
                                                    @ValidData @NonNull DoNotDisturbUSourceData data,
                                                    @NonNull PendingIntent event_positive,
                                                    @NonNull PendingIntent event_negative) {
        return new DoNotDisturbTracker(context, data, event_positive, event_negative);
    }

}
