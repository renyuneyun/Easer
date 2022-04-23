package ryey.easer.skills.operation.intent;

import androidx.annotation.NonNull;

import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.operationskill.OperationDataFactory;
import ryey.easer.plugin.PluginDataFormat;

public class ServiceOperationDataFactory implements OperationDataFactory<ServiceOperationData> {
    @NonNull
    @Override
    public Class<ServiceOperationData> dataClass() {
        return ServiceOperationData.class;
    }

    @NonNull
    @Override
    public ServiceOperationData dummyData() {
        return new ServiceOperationData(IntentOperationDataUtil.getDummyIntentData());
    }

    @NonNull
    @Override
    public ServiceOperationData parse(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        return new ServiceOperationData(data, format, version);
    }
}
