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

package ryey.easer.skills.operation;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nonnull;

import ryey.easer.Utils;
import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.plugin.PluginDataFormat;

/**
 * TODO: implements {@link ryey.easer.commons.local_skill.operationskill.OperationData} ? (especially placeholders)
 */
public class Extras implements Parcelable {

    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final String V_TYPE = "type";

    @Nullable
    public static Extras mayParse(@Nullable String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        if (Utils.isBlank(data))
            return null;
        return new Extras(data, format, version);
    }

    @Nonnull
    public final List<ExtraItem> extras;

    public Extras(@NonNull List<ExtraItem> extras) {
        this.extras = extras;
    }

    public Extras(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        if (Utils.isBlank(data)) {
            extras = new ArrayList<>();
            return;
        }
        switch (format) {
            default:
                try {
                    JSONArray jsonArray_extras = new JSONArray(data);
                    extras = new ArrayList<>(jsonArray_extras.length());
                    for (int i = 0; i < jsonArray_extras.length(); i++) {
                        JSONObject jsonObject_extra = jsonArray_extras.getJSONObject(i);
                        String key = jsonObject_extra.getString(KEY);
                        String value = jsonObject_extra.getString(VALUE);
                        String type = jsonObject_extra.getString(V_TYPE);
                        extras.add(new ExtraItem(key, value, type));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new IllegalStorageDataException(e);
                }
        }
    }

    @NonNull
    public String serialize(@NonNull PluginDataFormat format) {
        String res = "";
        switch (format) {
            default:
                try {
                    if (extras.size() > 0) {
                        JSONArray jsonArray_extras = new JSONArray();
                        for (ExtraItem item : extras) {
                            JSONObject jsonObject_extra = new JSONObject();
                            jsonObject_extra.put(KEY, item.key);
                            jsonObject_extra.put(VALUE, item.value);
                            jsonObject_extra.put(V_TYPE, item.type);
                            jsonArray_extras.put(jsonObject_extra);
                        }
                        res = jsonArray_extras.toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new IllegalStateException(e);
                }
        }
        return res;
    }

    @Nonnull
    public Bundle asBundle() {
        Bundle bundle = new Bundle();
        for (ExtraItem item : extras) {
            switch (item.type) {
                case "string":
                    bundle.putString(item.key, item.value);
                    break;
                case "int":
                    bundle.putInt(item.key, Integer.parseInt(item.value));
                    break;
            }
        }
        return bundle;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Extras))
            return false;
        if (!extras.equals(((Extras) obj).extras))
            return false;
        return true;
    }

    private Extras(Parcel in) {
        extras = Objects.requireNonNull(in.createTypedArrayList(ExtraItem.CREATOR));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(extras);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Extras> CREATOR = new Creator<Extras>() {
        @Override
        public Extras createFromParcel(Parcel in) {
            return new Extras(in);
        }

        @Override
        public Extras[] newArray(int size) {
            return new Extras[size];
        }
    };
}
