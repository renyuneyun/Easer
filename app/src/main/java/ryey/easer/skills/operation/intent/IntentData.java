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

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ryey.easer.Utils;
import ryey.easer.skills.reusable.Extras;

//TODO: Make fields final
public class IntentData implements Parcelable {

    String action;
    List<String> category;
    String component;
    String type;
    Uri data;
    @Nullable
    Extras extras;

    IntentData() {

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof IntentData))
            return false;
        if (!Utils.nullableEqual(action, ((IntentData) obj).action))
            return false;
        if (!Utils.nullableEqual(category, ((IntentData) obj).category))
            return false;
        if (!Utils.nullableEqual(component, ((IntentData) obj).component))
            return false;
        if (!Utils.nullableEqual(type, ((IntentData) obj).type))
            return false;
        if (!Utils.nullableEqual(data, ((IntentData) obj).data))
            return false;
        if (!Utils.nullableEqual(extras, ((IntentData) obj).extras))
            return false;
        return true;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("action:%s category:%s component: %s type:%s data:%s", action, category, component, type, data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(action);
        dest.writeStringList(category);
        dest.writeString(component);
        dest.writeString(type);
        dest.writeParcelable(data, 0);
        dest.writeParcelable(extras, 0);
    }

    public static final Parcelable.Creator<IntentData> CREATOR
            = new Parcelable.Creator<IntentData>() {
        public IntentData createFromParcel(Parcel in) {
            return new IntentData(in);
        }

        public IntentData[] newArray(int size) {
            return new IntentData[size];
        }
    };

    private IntentData(Parcel in) {
        action = in.readString();
        List<String> cat = new ArrayList<>();
        in.readStringList(cat);
        if (cat.size() > 0)
            category = cat;
        component = in.readString();
        type = in.readString();
        data = in.readParcelable(Uri.class.getClassLoader());
        extras = in.readParcelable(Extras.class.getClassLoader());
    }
}
