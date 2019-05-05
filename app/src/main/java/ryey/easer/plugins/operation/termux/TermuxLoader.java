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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;

import ryey.easer.Utils;
import ryey.easer.commons.local_plugin.ValidData;
import ryey.easer.plugins.operation.OperationLoader;
import ryey.easer.plugins.reusable.PluginHelper;

import static ryey.easer.plugins.operation.termux.TermuxConstant.ACTION_NAME;
import static ryey.easer.plugins.operation.termux.TermuxConstant.CLASS_NAME;
import static ryey.easer.plugins.operation.termux.TermuxConstant.EXTRA_BACKGROUND;
import static ryey.easer.plugins.operation.termux.TermuxConstant.PACKAGE_NAME;
import static ryey.easer.plugins.operation.termux.TermuxConstant.URI_SCHEME;

public class TermuxLoader extends OperationLoader<TermuxOperationData> {
    public TermuxLoader(Context context) {
        super(context);
    }

    @Override
    public boolean load(@ValidData @NonNull TermuxOperationData data) {
        boolean success = true;
        String script = Utils.format(data.get());
        File f = new File(script);
        Logger.i("Running Termux command: " + f.getAbsolutePath());
        Uri scriptUri = new Uri.Builder().scheme(URI_SCHEME).path(f.getAbsolutePath()).build();
        Intent executeIntent = new Intent(ACTION_NAME, scriptUri);
        executeIntent.setClassName(PACKAGE_NAME, CLASS_NAME);
        executeIntent.putExtra(EXTRA_BACKGROUND, true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // https://developer.android.com/about/versions/oreo/background.html
            context.startForegroundService(executeIntent);
        } else {
            context.startService(executeIntent);
        }
        return success;
    }
}
