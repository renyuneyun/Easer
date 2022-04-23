package ryey.easer.skills.operation.intent;

import android.content.Context;

import androidx.annotation.NonNull;

import ryey.easer.R;
import ryey.easer.commons.local_skill.SkillView;
import ryey.easer.commons.local_skill.operationskill.OperationDataFactory;
import ryey.easer.skills.operation.OperationLoader;

public class ActivityOperationSkill extends IntentOperationSkill<ActivityOperationData> {
    @NonNull
    @Override
    public String id() {
        return "start_activity";
    }

    @Override
    public int name() {
        return R.string.operation_start_activity;
    }


    @NonNull
    @Override
    public OperationLoader<ActivityOperationData> loader(@NonNull Context context) {
        return new ActivityLoader(context);
    }

    @NonNull
    @Override
    public OperationDataFactory<ActivityOperationData> dataFactory() {
        return new ActivityOperationDataFactory();
    }

    @NonNull
    @Override
    public SkillView<ActivityOperationData> view() {
        return new ActivitySkillViewFragment();
    }
}
