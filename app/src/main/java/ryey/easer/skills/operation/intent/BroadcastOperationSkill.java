package ryey.easer.skills.operation.intent;

import android.content.Context;

import androidx.annotation.NonNull;

import ryey.easer.R;
import ryey.easer.commons.local_skill.SkillView;
import ryey.easer.commons.local_skill.operationskill.OperationDataFactory;
import ryey.easer.skills.operation.OperationLoader;

public class BroadcastOperationSkill extends IntentOperationSkill<BroadcastOperationData> {
    @NonNull
    @Override
    public String id() {
        return "send_broadcast";
    }

    @Override
    public int name() {
        return R.string.operation_broadcast;
    }


    @NonNull
    @Override
    public OperationLoader<BroadcastOperationData> loader(@NonNull Context context) {
        return new BroadcastLoader(context);
    }

    @NonNull
    @Override
    public OperationDataFactory<BroadcastOperationData> dataFactory() {
        return new BroadcastOperationDataFactory();
    }

    @NonNull
    @Override
    public SkillView<BroadcastOperationData> view() {
        return new BroadcastSkillViewFragment();
    }

}
