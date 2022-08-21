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
import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.dynamics.Dynamics;
import ryey.easer.commons.local_skill.usource.USourceData;
import ryey.easer.plugin.PluginDataFormat;

@RequiresApi(api = Build.VERSION_CODES.M)
public class DoNotDisturbUSourceData implements USourceData {

    static int[] doNotDisturbIDs = {
            NotificationManager.INTERRUPTION_FILTER_UNKNOWN,
            NotificationManager.INTERRUPTION_FILTER_ALL,
            NotificationManager.INTERRUPTION_FILTER_PRIORITY,
            NotificationManager.INTERRUPTION_FILTER_NONE,
            NotificationManager.INTERRUPTION_FILTER_ALARMS,
    };
    boolean[] doNotDisturbModes = new boolean[doNotDisturbIDs.length];

    private static final String T_doNotDisturb = "doNotDisturb";

    DoNotDisturbUSourceData(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray modes = jsonObject.optJSONArray(T_doNotDisturb);
            if (modes != null) {
                for (int i = 0; i < doNotDisturbIDs.length; i++) {
                    this.doNotDisturbModes[doNotDisturbIDs[i]] = (modes.getInt(i + doNotDisturbIDs.length) == 1);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStorageDataException(e);
        }
    }

    DoNotDisturbUSourceData(boolean[] doNotDisturbModes) {
        this.doNotDisturbModes = doNotDisturbModes;
    }

    @NonNull
    @Override
    public String serialize(@NonNull PluginDataFormat format) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray modes = new JSONArray();
            for (int i = 0; i < doNotDisturbIDs.length; i++) {
                modes.put(i, doNotDisturbIDs[i]);
                modes.put(i + doNotDisturbIDs.length, (this.doNotDisturbModes[doNotDisturbIDs[i]] ? 1 : 0));
            }
            jsonObject.put(T_doNotDisturb, modes);
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
        if (!(obj instanceof DoNotDisturbUSourceData))
            return false;
        return Arrays.equals(((DoNotDisturbUSourceData) obj).doNotDisturbModes, doNotDisturbModes);
    }

    public boolean match(int doNotDisturbMode) {
        return this.doNotDisturbModes[doNotDisturbMode];
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
        dest.writeValue(doNotDisturbModes);
    }

    public static final Creator<DoNotDisturbUSourceData> CREATOR
            = new Creator<DoNotDisturbUSourceData>() {
        public DoNotDisturbUSourceData createFromParcel(Parcel in) {
            return new DoNotDisturbUSourceData(in);
        }

        public DoNotDisturbUSourceData[] newArray(int size) {
            return new DoNotDisturbUSourceData[size];
        }
    };

    private DoNotDisturbUSourceData(Parcel in) {
        this.doNotDisturbModes = (boolean[]) in.readValue(null);
    }
}
