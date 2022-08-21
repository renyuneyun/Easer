/*
 * Copyright (c) 2016 - 2022 Rui Zhao <renyuneyun@gmail.com>
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

package ryey.easer.skills.usource.do_not_disturb;

import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import ryey.easer.R;
import ryey.easer.commons.local_skill.InvalidDataInputException;
import ryey.easer.commons.local_skill.ValidData;
import ryey.easer.skills.SkillViewFragment;

@RequiresApi(api = Build.VERSION_CODES.M)
public class DoNotDisturbSkillViewFragment extends SkillViewFragment<DoNotDisturbUSourceData> {

    CheckBox[] doNotDisturbCBs = new CheckBox[DoNotDisturbUSourceData.doNotDisturbIDs.length];

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.skill_usource__do_not_disturb, container, false);

        doNotDisturbCBs[NotificationManager.INTERRUPTION_FILTER_UNKNOWN] = view.findViewById(R.id.cbUnknown);
        doNotDisturbCBs[NotificationManager.INTERRUPTION_FILTER_ALL] = view.findViewById(R.id.cbNormal);
        doNotDisturbCBs[NotificationManager.INTERRUPTION_FILTER_PRIORITY] = view.findViewById(R.id.cbPriority);
        doNotDisturbCBs[NotificationManager.INTERRUPTION_FILTER_NONE] = view.findViewById(R.id.cbNoInterruptions);
        doNotDisturbCBs[NotificationManager.INTERRUPTION_FILTER_ALARMS] = view.findViewById(R.id.cbAlarmsOnly);

        return view;
    }

    @Override
    protected void _fill(@ValidData @NonNull DoNotDisturbUSourceData data) {
        for (int i : DoNotDisturbUSourceData.doNotDisturbIDs) {
            doNotDisturbCBs[i].setChecked(data.doNotDisturbModes[i]);
        }
    }

    @ValidData
    @NonNull
    @Override
    public DoNotDisturbUSourceData getData() throws InvalidDataInputException {
        boolean[] modes = new boolean[DoNotDisturbUSourceData.doNotDisturbIDs.length];
        for (int i : DoNotDisturbUSourceData.doNotDisturbIDs) {
            modes[i] = doNotDisturbCBs[i].isChecked();
        }
        return new DoNotDisturbUSourceData(modes);
    }
}