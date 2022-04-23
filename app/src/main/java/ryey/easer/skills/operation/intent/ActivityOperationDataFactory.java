package ryey.easer.skills.operation.intent;

import androidx.annotation.NonNull;

import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.operationskill.OperationDataFactory;
import ryey.easer.plugin.PluginDataFormat;

public class ActivityOperationDataFactory implements OperationDataFactory<ActivityOperationData> {
    @NonNull
    @Override
    public Class<ActivityOperationData> dataClass() {
        return ActivityOperationData.class;
    }

    @NonNull
    @Override
    public ActivityOperationData dummyData() {
        return new ActivityOperationData(IntentOperationDataUtil.getDummyIntentData());
    }

    @NonNull
    @Override
    public ActivityOperationData parse(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        return new ActivityOperationData(data, format, version);
    }
}
