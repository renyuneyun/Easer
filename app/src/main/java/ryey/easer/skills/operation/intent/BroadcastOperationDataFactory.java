package ryey.easer.skills.operation.intent;

import androidx.annotation.NonNull;

import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.operationskill.OperationDataFactory;
import ryey.easer.plugin.PluginDataFormat;

public class BroadcastOperationDataFactory implements OperationDataFactory<BroadcastOperationData> {

    @NonNull
    @Override
    public Class<BroadcastOperationData> dataClass() {
        return BroadcastOperationData.class;
    }

    @NonNull
    @Override
    public BroadcastOperationData dummyData() {
        return new BroadcastOperationData(IntentOperationDataUtil.getDummyIntentData());
    }

    @NonNull
    @Override
    public BroadcastOperationData parse(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        return new BroadcastOperationData(data, format, version);
    }
}
