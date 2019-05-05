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

package ryey.easer.plugins.operation.termux;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import ryey.easer.R;
import ryey.easer.commons.local_plugin.PluginViewFragmentInterface;
import ryey.easer.commons.local_plugin.operationplugin.OperationDataFactory;
import ryey.easer.commons.local_plugin.operationplugin.OperationPlugin;
import ryey.easer.commons.local_plugin.operationplugin.PrivilegeUsage;
import ryey.easer.plugin.operation.Category;
import ryey.easer.plugins.operation.OperationLoader;
import ryey.easer.plugins.reusable.PluginHelper;

public class TermuxOperationPlugin implements OperationPlugin<TermuxOperationData> {

    @NonNull
    @Override
    public String id() {
        return "termux";
    }

    @Override
    public int name() {
        return R.string.operation_termux;
    }

    @Override
    public boolean isCompatible(@NonNull final Context context) {
        return true;
    }

    @NonNull
    @Override
    public PrivilegeUsage privilege() {
        return PrivilegeUsage.prefer_root;
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

    @Override
    public boolean checkPermissions(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PluginHelper.checkPermission(context, "com.termux.permission.TERMUX_SERVICE");
        } else {
            return true;
        }
    }

    @Override
    public void requestPermissions(@NonNull Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PluginHelper.requestPermission(activity, requestCode, "com.termux.permission.TERMUX_SERVICE");
        }
    }

    @NonNull
    @Override
    public OperationDataFactory<TermuxOperationData> dataFactory() {
        return new TermuxOperationDataFactory();

    }

    @NonNull
    @Override
    public PluginViewFragmentInterface<TermuxOperationData> view() {
        return new TermuxPluginViewFragment();
    }

    @NonNull
    @Override
    public OperationLoader<TermuxOperationData> loader(@NonNull Context context) {
        return new TermuxLoader(context);
    }

}
