/*
 * Copyright (c) 2016 - 2019 Rui Zhao <renyuneyun@gmail.com>
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

package ryey.easer.skills.event;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.PatternMatcher;

import androidx.annotation.NonNull;

import com.orhanobut.logger.Logger;

import java.util.Locale;

import ryey.easer.commons.local_skill.ValidData;
import ryey.easer.commons.local_skill.eventskill.EventData;

public abstract class SelfNotifiableSlot<T extends EventData> extends AbstractSlot<T> {
    // Fields used in relevant Intent
    private static final String ACTION_SATISFIED = "ryey.easer.triggerlotus.abstractslot.SATISFIED";
    private static final String ACTION_UNSATISFIED = "ryey.easer.triggerlotus.abstractslot.UNSATISFIED";
    private static final String CATEGORY_NOTIFY_SLOT = "ryey.easer.triggerlotus.category.NOTIFY_SLOT";
    /*
     * Mechanisms and fields used to notify the slot itself, and then proceed to `onPositiveNotified()`.
     * This is because some system-level checking mechanisms (e.g. data/time) need a PendingIntent.
     */
    // This Uri is used to restrict the (internal to the slot) broadcasts are only received by the current slot.
    protected final Uri slotUri = Uri.parse(String.format(Locale.US, "slot://%s/%d", getClass().getSimpleName(), hashCode()));
    // After sent, this will trigger onPositiveNotified().
    // Meant to be used when the event is going to a positive state.
    protected final PendingIntent notifySelfIntent_positive;
    // After sent, this will trigger onNegativeNotified().
    // Meant to be used when the event is going to a negative state.
    protected final PendingIntent notifySelfIntent_negative;
    private final IntentFilter filter;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.d("self notifying Intent received. action: %s", intent.getAction());
            if (intent.getAction().equals(ACTION_SATISFIED)) {
                onPositiveNotified(intent);
            } else if (intent.getAction().equals(ACTION_UNSATISFIED)) {
                onNegativeNotified(intent);
            }
        }
    };

    {
        filter = new IntentFilter();
        filter.addAction(ACTION_SATISFIED);
        filter.addAction(ACTION_UNSATISFIED);
        filter.addCategory(CATEGORY_NOTIFY_SLOT);
        filter.addDataScheme(slotUri.getScheme());
        filter.addDataAuthority(slotUri.getAuthority(), null);
        filter.addDataPath(slotUri.getPath(), PatternMatcher.PATTERN_LITERAL);

        Intent intent = new Intent(ACTION_SATISFIED);
        intent.addCategory(CATEGORY_NOTIFY_SLOT);
        intent.setData(slotUri);
        notifySelfIntent_positive = PendingIntent.getBroadcast(context, 0, intent, 0);
        intent.setAction(ACTION_UNSATISFIED);
        notifySelfIntent_negative = PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    protected SelfNotifiableSlot(@NonNull Context context, @ValidData @NonNull T data, boolean retriggerable, boolean persistent) {
        super(context, data, retriggerable, persistent);
    }

    @Override
    public void listen() {
        context.registerReceiver(mReceiver, filter);
    }

    @Override
    public void cancel() {
        context.unregisterReceiver(mReceiver);
    }

    protected void onPositiveNotified(Intent intent) {
        Logger.v("onPositiveNotified");
        changeSatisfiedState(true);
    }

    protected void onNegativeNotified(Intent intent) {
        Logger.v("onNegativeNotified");
        changeSatisfiedState(false);
    }

    public static class NotifyIntentPrototype {

        public static Intent obtainPositiveIntent(Uri data) {
            Intent intent = new Intent(ACTION_SATISFIED);
            intent.addCategory(CATEGORY_NOTIFY_SLOT);
            intent.setData(data);
            return intent;
        }

        public static Intent obtainNegativeIntent(Uri data) {
            Intent intent = new Intent(ACTION_UNSATISFIED);
            intent.addCategory(CATEGORY_NOTIFY_SLOT);
            intent.setData(data);
            return intent;
        }
    }
}
