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

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.dynamics.Dynamics;
import ryey.easer.commons.local_skill.usource.USourceData;
import ryey.easer.plugin.PluginDataFormat;

public class AirplaneModeUSourceData implements USourceData {

    final boolean airplaneMode;

    private static final String K_MODE = "mode";

    AirplaneModeUSourceData(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        try {
            JSONObject jsonObject = new JSONObject(data);
            this.airplaneMode = jsonObject.getBoolean(K_MODE);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStorageDataException(e);
        }
    }

    AirplaneModeUSourceData(boolean airplaneMode) {
        this.airplaneMode = airplaneMode;
    }

    @NonNull
    @Override
    public String serialize(@NonNull PluginDataFormat format) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(K_MODE, airplaneMode);
        } catch (JSONException e) {
            Logger.e(e, "Error putting %s data", getClass().getSimpleName());
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof AirplaneModeUSourceData))
            return false;
        return ((AirplaneModeUSourceData) obj).airplaneMode == airplaneMode;
    }

    @Nullable
    @Override
    public Dynamics[] dynamics() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(airplaneMode);
    }

    public static final Creator<AirplaneModeUSourceData> CREATOR
            = new Creator<AirplaneModeUSourceData>() {
        public AirplaneModeUSourceData createFromParcel(Parcel in) {
            return new AirplaneModeUSourceData(in);
        }

        public AirplaneModeUSourceData[] newArray(int size) {
            return new AirplaneModeUSourceData[size];
        }
    };

    private AirplaneModeUSourceData(Parcel in) {
        this.airplaneMode = (boolean) in.readValue(null);
    }
}
