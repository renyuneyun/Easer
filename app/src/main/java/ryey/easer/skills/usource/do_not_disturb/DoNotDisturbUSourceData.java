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

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.dynamics.Dynamics;
import ryey.easer.commons.local_skill.usource.USourceData;
import ryey.easer.plugin.PluginDataFormat;

public class DoNotDisturbUSourceData implements USourceData {

    Set<Integer> doNotDisturbModes = new HashSet<>();

    private static final String K_MODE = "mode";

    DoNotDisturbUSourceData(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray modes = jsonObject.getJSONArray(K_MODE);
            for (int i = 0; i < modes.length(); i++) {
                doNotDisturbModes.add(modes.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new IllegalStorageDataException(e);
        }
    }

    DoNotDisturbUSourceData(Set<Integer> doNotDisturbModes) {
        this.doNotDisturbModes = doNotDisturbModes;
    }

    @NonNull
    @Override
    public String serialize(@NonNull PluginDataFormat format) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray modes = new JSONArray();
            for (int mode : doNotDisturbModes) {
                modes.put(mode);
            }
            jsonObject.put(K_MODE, modes);
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
        return doNotDisturbModes.equals(((DoNotDisturbUSourceData) obj).doNotDisturbModes);
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
        dest.writeInt(doNotDisturbModes.size());
        for (int mode : doNotDisturbModes) {
            dest.writeInt(mode);
        }
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
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            doNotDisturbModes.add(in.readInt());
        }
    }
}
