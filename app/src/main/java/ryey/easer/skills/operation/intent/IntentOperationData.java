package ryey.easer.skills.operation.intent;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ryey.easer.Utils;
import ryey.easer.commons.ImproperImplementationError;
import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.Reused;
import ryey.easer.commons.local_skill.dynamics.SolidDynamicsAssignment;
import ryey.easer.commons.local_skill.operationskill.OperationData;
import ryey.easer.plugin.PluginDataFormat;
import ryey.easer.skills.reusable.ExtraItem;
import ryey.easer.skills.reusable.Extras;

public class IntentOperationData implements OperationData, Reused {
    private static final String ns = null;

    private static final String PACKAGE = "package";
    private static final String CLASS = "class";
    private static final String ACTION = "action";
    private static final String CATEGORY = "category";
    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String EXTRAS = "extras";

    private String skillID;

    final IntentData data;

    IntentOperationData(IntentData data) {
        this.data = data;
    }

    IntentOperationData(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        switch (format) {
            default:
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    IntentData intentData = new IntentData();
                    intentData.target_package = jsonObject.optString(PACKAGE, null);
                    intentData.target_class = jsonObject.optString(CLASS, null);
                    intentData.action = jsonObject.optString(ACTION, null);

                    JSONArray jsonArray = jsonObject.optJSONArray(CATEGORY);
                    if ((jsonArray != null) && (jsonArray.length() > 0)) {
                        intentData.category = new ArrayList<>(jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            intentData.category.add(jsonArray.getString(i));
                        }
                    }

                    intentData.type = jsonObject.optString(TYPE, null);

                    String uri = jsonObject.optString(DATA, null);
                    if (uri != null)
                        intentData.data = Uri.parse(uri);

                    String strExtras = jsonObject.optString(EXTRAS);
                    if (strExtras != null) {
                        intentData.extras = Extras.mayParse(strExtras, format, version);
                    }

                    this.data = intentData;
                } catch (JSONException e) {
                    e.printStackTrace();
                    throw new IllegalStorageDataException(e);
                }
        }
    }

    @NonNull
    @Override
    public String serialize(@NonNull PluginDataFormat format) {
        String res = "";
        switch (format) {
            default:
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(PACKAGE, data.target_package);
                    jsonObject.put(CLASS, data.target_class);
                    jsonObject.put(ACTION, data.action);

                    if (data.category != null && data.category.size() > 0) {
                        JSONArray jsonArray_category = new JSONArray();
                        for (String category : data.category) {
                            jsonArray_category.put(category);
                        }
                        jsonObject.put(CATEGORY, jsonArray_category);
                    }

                    if (!Utils.isBlank(data.type))
                        jsonObject.put(TYPE, data.type);
                    if (data.data != null)
                        jsonObject.put(DATA, data.data.toString());

                    if (data.extras != null) {
                        if (data.extras.extras.size() > 0) { // Safety check, because old versions may serialise null extras. Should be removed in future.
                            jsonObject.put(EXTRAS, data.extras.serialize(format));
                        }
                    }

                    res = jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        return res;
    }

    @SuppressWarnings({"SimplifiableIfStatement", "RedundantIfStatement"})
    @Override
    public boolean isValid() {
        if (Utils.isBlank(data.target_package) && !Utils.isBlank(data.target_class))  // If there is a target class, there must also be a target package.
            return false;
        if (!Utils.isBlank(data.action))
            return true;
        if (data.category != null && !data.category.isEmpty())
            return true;
        if (!Utils.isBlank(data.type))
            return true;
        if (data.data != null && !Utils.isBlank(data.data.toString()))
            return true;
        return false;
    }

    @SuppressWarnings({"SimplifiableIfStatement", "RedundantIfStatement"})
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof IntentOperationData))
            return false;
        return data.equals(((IntentOperationData) obj).data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, 0);
        dest.writeString(skillID());
    }

    public static final Parcelable.Creator<IntentOperationData> CREATOR
            = new Parcelable.Creator<IntentOperationData>() {
        public IntentOperationData createFromParcel(Parcel in) {
            return new IntentOperationData(in);
        }

        public IntentOperationData[] newArray(int size) {
            return new IntentOperationData[size];
        }
    };

    private IntentOperationData(Parcel in) {
        data = in.readParcelable(IntentData.class.getClassLoader());
        String _skillID = in.readString();
        setSkillID(_skillID);
    }

    @Nullable
    @Override
    public Set<String> placeholders() {
        Set<String> placeholders = new ArraySet<>();
        if (data.target_package != null)
            placeholders.addAll(Utils.extractPlaceholder(data.target_package));
        if (data.target_class != null)
            placeholders.addAll(Utils.extractPlaceholder(data.target_class));
        if (data.action != null)
            placeholders.addAll(Utils.extractPlaceholder(data.action));
        if (data.category != null) {
            for (String category : data.category)
                placeholders.addAll(Utils.extractPlaceholder(category));
        }
        if (data.type != null)
            placeholders.addAll(Utils.extractPlaceholder(data.type));
        if (data.data != null)
            placeholders.addAll(Utils.extractPlaceholder(data.data.toString()));
        if (data.extras != null) {
            for (ExtraItem extra : data.extras.extras) {
                placeholders.addAll(Utils.extractPlaceholder(extra.key));
                placeholders.addAll(Utils.extractPlaceholder(extra.value));
            }
        }
        return placeholders;
    }

    @NonNull
    @Override
    public OperationData applyDynamics(SolidDynamicsAssignment dynamicsAssignment) {
        IntentData intentData = new IntentData();
        if (data.action != null)
            intentData.action = Utils.applyDynamics(data.action, dynamicsAssignment);
        if (data.category != null) {
            intentData.category = new ArrayList<>(data.category.size());
            for (String category : data.category)
                intentData.category.add(Utils.applyDynamics(category, dynamicsAssignment));
        }
        if (data.type != null)
            intentData.type = Utils.applyDynamics(data.type, dynamicsAssignment);
        if (data.data != null)
            intentData.data = Uri.parse(Utils.applyDynamics(data.data.toString(), dynamicsAssignment));
        if (data.extras != null) {
            List<ExtraItem> extras = new ArrayList<>();
            for (ExtraItem extra : data.extras.extras) {
                String key = Utils.applyDynamics(extra.key, dynamicsAssignment);
                String value = Utils.applyDynamics(extra.value, dynamicsAssignment);
                String type = extra.type;
                extras.add(new ExtraItem(key, value, type));
            }
            intentData.extras = Extras.mayConstruct(extras);
        }
        IntentOperationData ret = new IntentOperationData(intentData);
        ret.setSkillID(skillID());
        return ret;
    }

    @Override
    public String skillID() {
        if (skillID == null)
            throw new ImproperImplementationError("The skillID should be set immediately after creating the object, but it didn't.");
        return skillID;
    }

    @Override
    public void setSkillID(String skillID) {
        this.skillID = skillID;
    }
}
