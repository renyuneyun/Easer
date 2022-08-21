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

package ryey.easer.skills;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ryey.easer.commons.CommonSkillUtils;
import ryey.easer.commons.local_skill.Reused;
import ryey.easer.commons.local_skill.Skill;
import ryey.easer.commons.local_skill.SkillView;
import ryey.easer.commons.local_skill.StorageData;
import ryey.easer.commons.local_skill.conditionskill.ConditionData;
import ryey.easer.commons.local_skill.conditionskill.ConditionSkill;
import ryey.easer.commons.local_skill.eventskill.EventData;
import ryey.easer.commons.local_skill.eventskill.EventSkill;
import ryey.easer.commons.local_skill.operationskill.OperationData;
import ryey.easer.commons.local_skill.operationskill.OperationSkill;
import ryey.easer.commons.local_skill.usource.USourceSkill;
import ryey.easer.skills.condition.ringer_mode.RingerModeConditionSkill;
import ryey.easer.skills.event.broadcast.BroadcastEventSkill;
import ryey.easer.skills.event.condition_event.ConditionEventEventSkill;
import ryey.easer.skills.event.nfc_tag.NfcTagEventSkill;
import ryey.easer.skills.event.notification.NotificationEventSkill;
import ryey.easer.skills.event.sms.SmsEventSkill;
import ryey.easer.skills.event.tcp_trip.TcpTripEventSkill;
import ryey.easer.skills.event.timer.TimerEventSkill;
import ryey.easer.skills.event.widget.WidgetEventSkill;
import ryey.easer.skills.operation.airplane_mode.AirplaneModeOperationSkill;
import ryey.easer.skills.operation.alarm.AlarmOperationSkill;
import ryey.easer.skills.operation.bluetooth.BluetoothOperationSkill;
import ryey.easer.skills.operation.bluetooth_connect.BluetoothConnectOperationSkill;
import ryey.easer.skills.operation.brightness.BrightnessOperationSkill;
import ryey.easer.skills.operation.intent.IntentOperationSkill;
import ryey.easer.skills.operation.cellular.CellularOperationSkill;
import ryey.easer.skills.operation.command.CommandOperationSkill;
import ryey.easer.skills.operation.hotspot.HotspotOperationSkill;
import ryey.easer.skills.operation.http_request.HttpRequestOperationSkill;
import ryey.easer.skills.operation.intent.operations.ActivityOperationSkill;
import ryey.easer.skills.operation.intent.operations.BroadcastOperationSkill;
import ryey.easer.skills.operation.intent.operations.ServiceOperationSkill;
import ryey.easer.skills.operation.launch_app.LaunchAppOperationSkill;
import ryey.easer.skills.operation.media_control.MediaControlOperationSkill;
import ryey.easer.skills.operation.network_transmission.NetworkTransmissionOperationSkill;
import ryey.easer.skills.operation.play_media.PlayMediaOperationSkill;
import ryey.easer.skills.operation.ringer_mode.RingerModeOperationSkill;
import ryey.easer.skills.operation.rotation.RotationOperationSkill;
import ryey.easer.skills.operation.send_notification.SendNotificationOperationSkill;
import ryey.easer.skills.operation.send_sms.SendSmsOperationSkill;
import ryey.easer.skills.operation.state_control.StateControlOperationSkill;
import ryey.easer.skills.operation.synchronization.SynchronizationOperationSkill;
import ryey.easer.skills.operation.ui_mode.UiModeOperationSkill;
import ryey.easer.skills.operation.volume.VolumeOperationSkill;
import ryey.easer.skills.operation.wifi.WifiOperationSkill;
import ryey.easer.skills.operation.wireguard.WireguardOperationSkill;
import ryey.easer.skills.usource.airplane_mode.AirplaneModeUSourceSkill;
import ryey.easer.skills.usource.battery_level.BatteryLevelUSourceSkill;
import ryey.easer.skills.usource.bluetooth_device.BTDeviceUSourceSkill;
import ryey.easer.skills.usource.bluetooth_enabled.BluetoothEnabledUSourceSkill;
import ryey.easer.skills.usource.calendar.CalendarUSourceSkill;
import ryey.easer.skills.usource.call.CallUSourceSkill;
import ryey.easer.skills.usource.cell_location.CellLocationUSourceSkill;
import ryey.easer.skills.usource.connectivity.ConnectivityUSourceSkill;
import ryey.easer.skills.usource.date.DateUSourceSkill;
import ryey.easer.skills.usource.day_of_week.DayOfWeekEventSkill;
import ryey.easer.skills.usource.headset.HeadsetUSourceSkill;
import ryey.easer.skills.usource.location.LocationUSourceSkill;
import ryey.easer.skills.usource.power.PowerUSourceSkill;
import ryey.easer.skills.usource.screen.ScreenUSourceSkill;
import ryey.easer.skills.usource.time.TimeUSourceSkill;
import ryey.easer.skills.usource.wifi.WifiUSourceSkill;
import ryey.easer.skills.usource.wifi_enabled.WifiEnabledUSourceSkill;

/**
 * Used to tell the app what skills can be used.
 *
 * To register a new plugin, simply write a new line in the constructor of this class.
 */
final public class LocalSkillRegistry {

    private final Registry<ConditionSkill, ConditionData> conditionSkillRegistry = new Registry<>(CommonSkillUtils.TYPE_CONDITION, new String[][]{
            {"battery", "power_status"}, // v0.7.8
            {"calendar_condition", "calendar"}, // v0.8 (unified calendar condition & event)
    });
    private final Registry<EventSkill, EventData> eventSkillRegistry = new Registry<>(CommonSkillUtils.TYPE_EVENT, new String[][]{
            {"battery", "power_status"}, // v0.7.8
    });
    private final Registry<OperationSkill, OperationData> operationSkillRegistry = new Registry<>(CommonSkillUtils.TYPE_OPERATION, new String[][]{
            {"event control", "state control"}, // (don't remember when)
    });
    private final OverallRegistry overallRegistry = new OverallRegistry(new SkillLookupper<?, ?>[] {
            eventSkillRegistry, operationSkillRegistry, conditionSkillRegistry,
    });

    {
        for (USourceSkill skill : new USourceSkill[] {
                new AirplaneModeUSourceSkill(),
                new BatteryLevelUSourceSkill(),
                new BTDeviceUSourceSkill(),
                new BluetoothEnabledUSourceSkill(),
                new CalendarUSourceSkill(),
                new CallUSourceSkill(),
                new CellLocationUSourceSkill(),
                new ConnectivityUSourceSkill(),
                new DateUSourceSkill(),
                new DayOfWeekEventSkill(),
                new HeadsetUSourceSkill(),
                new LocationUSourceSkill(),
                new PowerUSourceSkill(),
                new ScreenUSourceSkill(),
                new TimeUSourceSkill(),
                new WifiUSourceSkill(),
                new WifiEnabledUSourceSkill(),
        }) {
            condition().registerSkill(skill.condition());
            event().registerSkill(skill.event());
        }

        condition().registerSkill(RingerModeConditionSkill.class);

        event().registerSkill(BroadcastEventSkill.class);
        event().registerSkill(ConditionEventEventSkill.class);
        event().registerSkill(NfcTagEventSkill.class);
        event().registerSkill(NotificationEventSkill.class);
        event().registerSkill(SmsEventSkill.class);
        event().registerSkill(TcpTripEventSkill.class);
        event().registerSkill(TimerEventSkill.class);
        event().registerSkill(WidgetEventSkill.class);

        operation().registerSkill(AirplaneModeOperationSkill.class);
        operation().registerSkill(AlarmOperationSkill.class);
        operation().registerSkill(BluetoothOperationSkill.class);
        operation().registerSkill(BluetoothConnectOperationSkill.class);
        operation().registerSkill(BrightnessOperationSkill.class);
        operation().registerSkill(CellularOperationSkill.class);
        operation().registerSkill(CommandOperationSkill.class);
        operation().registerSkill(LaunchAppOperationSkill.class);
        operation().registerSkill(HotspotOperationSkill.class);
        operation().registerSkill(HttpRequestOperationSkill.class);
        operation().registerSkill(MediaControlOperationSkill.class);
        operation().registerSkill(NetworkTransmissionOperationSkill.class);
        operation().registerSkill(PlayMediaOperationSkill.class);
        operation().registerSkill(RingerModeOperationSkill.class);
        operation().registerSkill(RotationOperationSkill.class);
        operation().registerSkill(SendNotificationOperationSkill.class);
        operation().registerSkill(SendSmsOperationSkill.class);
        operation().registerSkill(StateControlOperationSkill.class);
        operation().registerSkill(SynchronizationOperationSkill.class);
        operation().registerSkill(UiModeOperationSkill.class);
        operation().registerSkill(VolumeOperationSkill.class);
        operation().registerSkill(WifiOperationSkill.class);
        operation().registerSkill(WireguardOperationSkill.class);
        operation().registerSkill(ActivityOperationSkill.class);
        operation().registerSkill(BroadcastOperationSkill.class);
        operation().registerSkill(ServiceOperationSkill.class);

        //TODO: write more skills
    }

    private static final LocalSkillRegistry instance = new LocalSkillRegistry();

    public static LocalSkillRegistry getInstance() {
        return instance;
    }

    private LocalSkillRegistry() {}

    public Registry<ConditionSkill, ConditionData> condition() {
        return conditionSkillRegistry;
    }

    public Registry<EventSkill, EventData> event() {
        return eventSkillRegistry;
    }

    public Registry<OperationSkill, OperationData> operation() {
        return operationSkillRegistry;
    }

    public SkillLookupper<Skill, StorageData> all() {
        return overallRegistry;
    }

    public interface SkillLookupper<T extends Skill, T_data extends StorageData> {
        List<T> getEnabledSkills(@NonNull Context context);
        List<T> getAllSkills();
        boolean hasSkill(String id);
        @Nullable T findSkill(T_data data);
        @Nullable T findSkill(String id);
        @Nullable T findSkill(SkillView view);
    }

    public static class Registry<T extends Skill, T_data extends StorageData> implements SkillLookupper<T, T_data> {
        final int type;
        final List<String> skillIdList = new ArrayList<>();
        final List<T> skillList = new ArrayList<>();
        //TODO: use Set instead of List for the above two variables && add an "ordered" method to return a List
        final Map<String, String> backwardNameMap = new ArrayMap<>(); // Backward-compatible name conversion

        private Registry(int type) {
            this.type = type;
        }

        private Registry(int type, String[][] backwardNameMap) {
            this(type);
            for (String[] pair : backwardNameMap) {
                this.backwardNameMap.put(pair[0], pair[1]);
            }
        }

        synchronized void registerSkill(T skill) {
            String id = skill.id();
            for (String eid : skillIdList) {
                if (id.equals(eid))
                    return;
            }
            skillList.add(skill);
            skillIdList.add(skill.id());
        }

        synchronized void registerSkill(Class<? extends T> skillClass) {
            try {
                T skill = skillClass.newInstance();
                skillList.add(skill);
                skillIdList.add(skill.id());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        public List<T> getEnabledSkills(@NonNull Context context) {
            List<T> enabledPlugins = new ArrayList<>(skillList.size());
            SharedPreferences settingsPreference =
                    PreferenceManager.getDefaultSharedPreferences(context);
            for (T plugin : skillList) {
                if (settingsPreference.getBoolean(CommonSkillUtils.pluginEnabledKey(plugin), true)
                        && plugin.isCompatible(context)) {
                    enabledPlugins.add(plugin);
                }
            }
            return enabledPlugins;
        }

        @Override
        public List<T> getAllSkills() {
            return skillList;
        }

        /**
         * Test if plugin is available as local plugin
         * TODO: optimize performance
         * @param id
         * @return
         */
        @Override
        public boolean hasSkill(String id) {
            if (findSkill(id) == null)
                return false;
            return true;
        }

        @Nullable
        public T findSkill(T_data data) {
            if (data instanceof Reused)
                return findSkill(((Reused) data).skillID());
            for (T plugin : getAllSkills()) {
                if (data.getClass() == plugin.dataFactory().dataClass()) {
                    return plugin;
                }
            }
            return null;
        }

        @Nullable
        public T findSkill(String id) {
            if (backwardNameMap.containsKey(id))
                id = backwardNameMap.get(id);
            for (T plugin : getAllSkills()) {
                if (id.equals(plugin.id())) {
                    return plugin;
                }
            }
            return null;
        }

        @Nullable
        @Override
        public T findSkill(SkillView view) {
            if (view instanceof Reused)
                return findSkill(((Reused) view).skillID());
            for (T plugin : getAllSkills()) {
                if (view.getClass().equals(plugin.view().getClass()))
                    return plugin;
            }
            return null;
        }

    }

    public static class OverallRegistry implements SkillLookupper<Skill, StorageData> {

        final SkillLookupper<? extends Skill, ? extends StorageData>[] lookupers;

        OverallRegistry(SkillLookupper<? extends Skill, ? extends StorageData>[] lookupers) {
            this.lookupers = lookupers;
        }

        public List<Skill> getEnabledSkills(@NonNull Context context) {
            List<Skill> list = new ArrayList<>();
            for (SkillLookupper<? extends Skill, ? extends StorageData> lookupper : lookupers) {
                list.addAll(lookupper.getEnabledSkills(context));
            }
            return list;
        }

        @Override
        public List<Skill> getAllSkills() {
            List<Skill> list = new ArrayList<>();
            for (SkillLookupper<? extends Skill, ? extends StorageData> lookupper : lookupers) {
                list.addAll(lookupper.getAllSkills());
            }
            return list;
        }

        @Override
        public boolean hasSkill(String id) {
            if (findSkill(id) == null)
                return false;
            return true;
        }

        @Nullable
        @Override
        public Skill findSkill(StorageData storageData) {
            for (Skill plugin : getAllSkills()) {
                if (storageData.getClass().equals(plugin.dataFactory().dataClass()))
                    return plugin;
            }
            return null;
        }

        @Nullable
        @Override
        public Skill findSkill(String id) {
            for (Skill plugin : getAllSkills()) {
                if (id.equals(plugin.id()))
                    return plugin;
            }
            throw new IllegalAccessError();
        }

        @Nullable
        @Override
        public Skill findSkill(SkillView view) {
            for (Skill plugin : getAllSkills()) {
                if (view.getClass().equals(plugin.view().getClass()))
                    return plugin;
            }
            throw new IllegalAccessError();
        }
    }
}
