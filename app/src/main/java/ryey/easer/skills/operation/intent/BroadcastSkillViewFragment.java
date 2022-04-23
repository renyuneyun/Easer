package ryey.easer.skills.operation.intent;

import androidx.annotation.NonNull;

import ryey.easer.commons.local_skill.InvalidDataInputException;

public class BroadcastSkillViewFragment extends IntentSkillViewFragment<BroadcastOperationData> {
    @NonNull
    @Override
    public BroadcastOperationData getData() throws InvalidDataInputException {
        return new BroadcastOperationData(super.getIntentData());
    }
}
