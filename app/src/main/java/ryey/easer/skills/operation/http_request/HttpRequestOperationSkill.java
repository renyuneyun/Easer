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

package ryey.easer.skills.operation.http_request;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ryey.easer.R;
import ryey.easer.commons.local_skill.SkillView;
import ryey.easer.commons.local_skill.operationskill.OperationDataFactory;
import ryey.easer.commons.local_skill.operationskill.OperationSkill;
import ryey.easer.commons.local_skill.operationskill.PrivilegeUsage;
import ryey.easer.plugin.operation.Category;
import ryey.easer.skills.SkillUtils;
import ryey.easer.skills.operation.OperationLoader;

public class HttpRequestOperationSkill implements OperationSkill<HttpRequestOperationData> {

    @NonNull
    @Override
    public String id() {
        return "http_request";
    }

    @Override
    public int name() {
        return R.string.operation_http_request;
    }

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
        return Category.misc;
    }

    @Nullable
    @Override
    public Boolean checkPermissions(@NonNull Context context) {
        return SkillUtils.checkPermission(context, Manifest.permission.INTERNET);
    }

    @Override
    public void requestPermissions(@NonNull Activity activity, int requestCode) {
        SkillUtils.requestPermission(activity, requestCode, Manifest.permission.INTERNET);
    }

    @NonNull
    @Override
    public OperationDataFactory<HttpRequestOperationData> dataFactory() {
        return new HttpRequestOperationDataFactory();
    }

    @NonNull
    @Override
    public SkillView<HttpRequestOperationData> view() {
        return new HttpRequestSkillViewFragment();
    }

    @NonNull
    @Override
    public OperationLoader<HttpRequestOperationData> loader(@NonNull Context context) {
        return new HttpRequestLoader(context);
    }

}
