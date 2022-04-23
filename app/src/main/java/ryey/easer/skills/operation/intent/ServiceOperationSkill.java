package ryey.easer.skills.operation.intent;

import android.content.Context;

import androidx.annotation.NonNull;

import ryey.easer.R;
import ryey.easer.commons.local_skill.SkillView;
import ryey.easer.commons.local_skill.operationskill.OperationDataFactory;
import ryey.easer.skills.operation.OperationLoader;

public class ServiceOperationSkill extends IntentOperationSkill<ServiceOperationData> {
    @NonNull
    @Override
    public String id() {
        return "start_service";
    }

    @Override
    public int name() {
        return R.string.operation_start_service;
    }


    @NonNull
    @Override
    public OperationLoader<ServiceOperationData> loader(@NonNull Context context) {
        return new ServiceLoader(context);
    }

    @NonNull
    @Override
    public OperationDataFactory<ServiceOperationData> dataFactory() {
        return new ServiceOperationDataFactory();
    }

    @NonNull
    @Override
    public SkillView<ServiceOperationData> view() {
        return new ServiceSkillViewFragment();
    }
}
