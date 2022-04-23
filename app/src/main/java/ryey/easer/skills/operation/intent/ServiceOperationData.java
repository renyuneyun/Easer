package ryey.easer.skills.operation.intent;

import androidx.annotation.NonNull;

import ryey.easer.commons.local_skill.IllegalStorageDataException;
import ryey.easer.commons.local_skill.dynamics.SolidDynamicsAssignment;
import ryey.easer.commons.local_skill.operationskill.OperationData;
import ryey.easer.plugin.PluginDataFormat;

public class ServiceOperationData extends IntentOperationData {
    ServiceOperationData(IntentData data) {
        super(data);
    }

    public ServiceOperationData(@NonNull String data, @NonNull PluginDataFormat format, int version) throws IllegalStorageDataException {
        super(data, format, version);
    }

    @NonNull
    @Override
    public OperationData applyDynamics(SolidDynamicsAssignment dynamicsAssignment) {
        return new ServiceOperationData(((IntentOperationData) super.applyDynamics(dynamicsAssignment)).data);
    }
}
