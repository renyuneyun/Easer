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

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import ryey.easer.Utils;
import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.dynamics.SolidDynamicsAssignment;
import ryey.easer.commons.local_skill.operationskill.OperationData;
import ryey.easer.plugin.PluginDataFormat;
import ryey.easer.skills.operation.Extras;

public class LaunchAppOperationData implements OperationData {
    private static final String K_APP_PACKAGE = "package";
    private static final String K_CLASS = "class";
    private static final String K_EXTRAS = "extras";

    final String app_package; //FIXME: @Nonnull???
    final @Nullable String app_class;
    final @NonNull Extras extras;

    LaunchAppOperationData(String app_package, @Nullable String app_class, @NonNull Extras extras) {
        this.app_package = app_package;
        this.app_class = app_class;
        this.extras = extras;
    }

    LaunchAppOperationData(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        switch (format) {
            default:
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    app_package = jsonObject.getString(K_APP_PACKAGE);
                    app_class = jsonObject.optString(K_CLASS);
                    extras = Extras.mayParse(jsonObject.optString(K_EXTRAS), format, version);
                } catch (JSONException e) {
                    throw new IllegalStorageDataException(e);
                }
        }
    }

    @NonNull
    @Override
    public String serialize(@NonNull PluginDataFormat format) {
        String ret;
        switch (format) {
            default:
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(K_APP_PACKAGE, app_package);
                    jsonObject.put(K_CLASS, app_class);
                    jsonObject.put(K_EXTRAS, extras.serialize(format));
                    ret = jsonObject.toString();
                } catch (JSONException e) {
                    throw new IllegalStateException(e);
                }
        }
        return ret;
    }

    @SuppressWarnings({"SimplifiableIfStatement", "RedundantIfStatement"})
    @Override
    public boolean isValid() {
        return !Utils.isBlank(app_package);
    }

    @SuppressWarnings({"SimplifiableIfStatement", "RedundantIfStatement"})
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof LaunchAppOperationData))
            return false;
        if (!Utils.nullableEqual(app_package, ((LaunchAppOperationData) obj).app_package))
            return false;
        if (!Utils.nullableEqual(app_class, ((LaunchAppOperationData) obj).app_class))
            return false;
        if (!extras.equals (((LaunchAppOperationData) obj).extras))
            return false;
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(app_package);
        dest.writeString(app_class);
        dest.writeParcelable(extras, 0);
    }

    public static final Creator<LaunchAppOperationData> CREATOR
            = new Creator<LaunchAppOperationData>() {
        public LaunchAppOperationData createFromParcel(Parcel in) {
            return new LaunchAppOperationData(in);
        }

        public LaunchAppOperationData[] newArray(int size) {
            return new LaunchAppOperationData[size];
        }
    };

    private LaunchAppOperationData(Parcel in) {
        app_package = in.readString();
        app_class = in.readString();
        Extras readedExtras = in.readParcelable(Extras.class.getClassLoader());
        extras = (readedExtras != null) ? readedExtras : new Extras();
    }

    @Nullable
    @Override
    public Set<String> placeholders() {
        return null;
    }

    @NonNull
    @Override
    public OperationData applyDynamics(SolidDynamicsAssignment dynamicsAssignment) {
        return this;
    }
}
