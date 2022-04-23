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

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ryey.easer.commons.local_skill.SkillView;
import ryey.easer.commons.local_skill.operationskill.OperationDataFactory;
import ryey.easer.commons.local_skill.operationskill.OperationSkill;
import ryey.easer.commons.local_skill.operationskill.PrivilegeUsage;
import ryey.easer.plugin.operation.Category;


public abstract class IntentOperationSkill<T extends IntentOperationData> implements OperationSkill<T> {

    @Override
    public boolean isCompatible(@NonNull final Context context) {
        return true;
    }

    @NonNull
    @Override
    public PrivilegeUsage privilege() {
        return PrivilegeUsage.no_root;
    }

    @Override
    public int maxExistence() {
        return 0;
    }

    @NonNull
    @Override
    public Category category() {
        return Category.android;
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
    public abstract OperationDataFactory<T> dataFactory();

    @NonNull
    @Override
    public abstract SkillView<T> view();


}
