/*
 * Copyright (c) 2016 - 2018 Rui Zhao <renyuneyun@gmail.com>
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

package ryey.easer.plugins;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ryey.easer.commons.CommonPluginHelper;
import ryey.easer.commons.local_plugin.PluginDef;
import ryey.easer.commons.local_plugin.PluginViewFragmentInterface;
import ryey.easer.commons.local_plugin.StorageData;
import ryey.easer.commons.local_plugin.conditionplugin.ConditionData;
import ryey.easer.commons.local_plugin.conditionplugin.ConditionPlugin;
import ryey.easer.commons.local_plugin.eventplugin.EventData;
import ryey.easer.commons.local_plugin.eventplugin.EventPlugin;
import ryey.easer.commons.local_plugin.operationplugin.OperationData;
import ryey.easer.commons.local_plugin.operationplugin.OperationPlugin;
import ryey.easer.plugins.condition.battery.BatteryConditionPlugin;
import ryey.easer.plugins.condition.bluetooth_device.BTDeviceConditionPlugin;
import ryey.easer.plugins.condition.bluetooth_enabled.BluetoothEnabledConditionPlugin;
import ryey.easer.plugins.condition.cell_location.CellLocationConditionPlugin;
import ryey.easer.plugins.condition.connectivity.ConnectivityConditionPlugin;
import ryey.easer.plugins.condition.date.DateConditionPlugin;
import ryey.easer.plugins.condition.day_of_week.DayOfWeekConditionPlugin;
import ryey.easer.plugins.condition.headset.HeadsetConditionPlugin;
import ryey.easer.plugins.condition.screen.ScreenConditionPlugin;
import ryey.easer.plugins.condition.time.TimeConditionPlugin;
import ryey.easer.plugins.condition.wifi.WifiConditionPlugin;
import ryey.easer.plugins.condition.wifi_enabled.WifiEnabledConditionPlugin;
import ryey.easer.plugins.event.battery.BatteryEventPlugin;
import ryey.easer.plugins.event.bluetooth_device.BTDeviceEventPlugin;
import ryey.easer.plugins.event.broadcast.BroadcastEventPlugin;
import ryey.easer.plugins.event.calendar.CalendarEventPlugin;
import ryey.easer.plugins.event.cell_location.CellLocationEventPlugin;
import ryey.easer.plugins.event.condition_event.ConditionEventEventPlugin;
import ryey.easer.plugins.event.connectivity.ConnectivityEventPlugin;
import ryey.easer.plugins.event.date.DateEventPlugin;
import ryey.easer.plugins.event.dayofweek.DayOfWeekEventPlugin;
import ryey.easer.plugins.event.headset.HeadsetEventPlugin;
import ryey.easer.plugins.event.nfc_tag.NfcTagEventPlugin;
import ryey.easer.plugins.event.notification.NotificationEventPlugin;
import ryey.easer.plugins.event.screen.ScreenEventPlugin;
import ryey.easer.plugins.event.sms.SmsEventPlugin;
import ryey.easer.plugins.event.tcp_trip.TcpTripEventPlugin;
import ryey.easer.plugins.event.time.TimeEventPlugin;
import ryey.easer.plugins.event.timer.TimerEventPlugin;
import ryey.easer.plugins.event.wifi.WifiEventPlugin;
import ryey.easer.plugins.operation.airplane_mode.AirplaneModeOperationPlugin;
import ryey.easer.plugins.operation.alarm.AlarmOperationPlugin;
import ryey.easer.plugins.operation.bluetooth.BluetoothOperationPlugin;
import ryey.easer.plugins.operation.brightness.BrightnessOperationPlugin;
import ryey.easer.plugins.operation.broadcast.BroadcastOperationPlugin;
import ryey.easer.plugins.operation.cellular.CellularOperationPlugin;
import ryey.easer.plugins.operation.command.CommandOperationPlugin;
import ryey.easer.plugins.operation.hotspot.HotspotOperationPlugin;
import ryey.easer.plugins.operation.launch_app.LaunchAppOperationPlugin;
import ryey.easer.plugins.operation.media_control.MediaControlOperationPlugin;
import ryey.easer.plugins.operation.network_transmission.NetworkTransmissionOperationPlugin;
import ryey.easer.plugins.operation.play_media.PlayMediaOperationPlugin;
import ryey.easer.plugins.operation.ringer_mode.RingerModeOperationPlugin;
import ryey.easer.plugins.operation.rotation.RotationOperationPlugin;
import ryey.easer.plugins.operation.send_notification.SendNotificationOperationPlugin;
import ryey.easer.plugins.operation.send_sms.SendSmsOperationPlugin;
import ryey.easer.plugins.operation.state_control.StateControlOperationPlugin;
import ryey.easer.plugins.operation.synchronization.SynchronizationOperationPlugin;
import ryey.easer.plugins.operation.termux.TermuxOperationPlugin;
import ryey.easer.plugins.operation.ui_mode.UiModeOperationPlugin;
import ryey.easer.plugins.operation.volume.VolumeOperationPlugin;
import ryey.easer.plugins.operation.wifi.WifiOperationPlugin;

/**
 * Used to tell the app what plugins can be used.
 *
 * To register a new plugin, simply write a new line in the constructor of this class.
 */
final public class LocalPluginRegistry {

    private final Registry<EventPlugin, EventData> eventPluginRegistry = new Registry<>(CommonPluginHelper.TYPE_EVENT);
    private final Registry<OperationPlugin, OperationData> operationPluginRegistry = new Registry<>(CommonPluginHelper.TYPE_OPERATION, new String[][]{
            {"event control", "state control"},
    });
    private final Registry<ConditionPlugin, ConditionData> conditionPluginRegistry = new Registry<>(CommonPluginHelper.TYPE_CONDITION);
    private final OverallRegistry overallRegistry = new OverallRegistry(new PluginLookupper<?, ?>[] {
            eventPluginRegistry, operationPluginRegistry, conditionPluginRegistry,
    });

    {
        event().registerPlugin(ConditionEventEventPlugin.class);
        event().registerPlugin(TimeEventPlugin.class);
        event().registerPlugin(DateEventPlugin.class);
        event().registerPlugin(WifiEventPlugin.class);
        event().registerPlugin(CellLocationEventPlugin.class);
        event().registerPlugin(BatteryEventPlugin.class);
        event().registerPlugin(DayOfWeekEventPlugin.class);
        event().registerPlugin(BTDeviceEventPlugin.class);
        event().registerPlugin(ConnectivityEventPlugin.class);
        event().registerPlugin(CalendarEventPlugin.class);
        event().registerPlugin(BroadcastEventPlugin.class);
        event().registerPlugin(SmsEventPlugin.class);
        event().registerPlugin(NotificationEventPlugin.class);
        event().registerPlugin(TimerEventPlugin.class);
        event().registerPlugin(NfcTagEventPlugin.class);
        event().registerPlugin(HeadsetEventPlugin.class);
        event().registerPlugin(TcpTripEventPlugin.class);
        event().registerPlugin(ScreenEventPlugin.class);

        condition().registerPlugin(BatteryConditionPlugin.class);
        condition().registerPlugin(BTDeviceConditionPlugin.class);
        condition().registerPlugin(CellLocationConditionPlugin.class);
        condition().registerPlugin(ConnectivityConditionPlugin.class);
        condition().registerPlugin(DateConditionPlugin.class);
        condition().registerPlugin(DayOfWeekConditionPlugin.class);
        condition().registerPlugin(HeadsetConditionPlugin.class);
        condition().registerPlugin(ScreenConditionPlugin.class);
        condition().registerPlugin(TimeConditionPlugin.class);
        condition().registerPlugin(WifiConditionPlugin.class);
        condition().registerPlugin(WifiEnabledConditionPlugin.class);
        condition().registerPlugin(BluetoothEnabledConditionPlugin.class);

        operation().registerPlugin(WifiOperationPlugin.class);
        operation().registerPlugin(CellularOperationPlugin.class);
        operation().registerPlugin(BluetoothOperationPlugin.class);
        operation().registerPlugin(RotationOperationPlugin.class);
        operation().registerPlugin(BroadcastOperationPlugin.class);
        operation().registerPlugin(BrightnessOperationPlugin.class);
        operation().registerPlugin(RingerModeOperationPlugin.class);
        operation().registerPlugin(CommandOperationPlugin.class);
        operation().registerPlugin(HotspotOperationPlugin.class);
        operation().registerPlugin(SynchronizationOperationPlugin.class);
        operation().registerPlugin(NetworkTransmissionOperationPlugin.class);
        operation().registerPlugin(MediaControlOperationPlugin.class);
        operation().registerPlugin(AirplaneModeOperationPlugin.class);
        operation().registerPlugin(SendSmsOperationPlugin.class);
        operation().registerPlugin(SendNotificationOperationPlugin.class);
        operation().registerPlugin(AlarmOperationPlugin.class);
        operation().registerPlugin(StateControlOperationPlugin.class);
        operation().registerPlugin(VolumeOperationPlugin.class);
        operation().registerPlugin(LaunchAppOperationPlugin.class);
        operation().registerPlugin(UiModeOperationPlugin.class);
        operation().registerPlugin(PlayMediaOperationPlugin.class);
        //TODO: write more plugins
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            TODO define it as external plugin ? or check if termux is installed
            operation().registerPlugin(TermuxOperationPlugin.class);

        }
    }

    private static final LocalPluginRegistry instance = new LocalPluginRegistry();

    public static LocalPluginRegistry getInstance() {
        return instance;
    }

    private LocalPluginRegistry() {}

    public Registry<EventPlugin, EventData> event() {
        return eventPluginRegistry;
    }

    public Registry<OperationPlugin, OperationData> operation() {
        return operationPluginRegistry;
    }

    public Registry<ConditionPlugin, ConditionData> condition() {
        return conditionPluginRegistry;
    }

    public PluginLookupper<PluginDef, StorageData> all() {
        return overallRegistry;
    }

    public interface PluginLookupper<T extends PluginDef, T_data extends StorageData> {
        List<T> getEnabledPlugins(@NonNull Context context);
        List<T> getAllPlugins();
        boolean hasPlugin(String id);
        @Nullable T findPlugin(T_data data);
        @Nullable T findPlugin(String id);
        @Nullable T findPlugin(PluginViewFragmentInterface view);
    }

    public static class Registry<T extends PluginDef, T_data extends StorageData> implements PluginLookupper<T, T_data> {
        final int type;
        final List<Class<? extends T>> pluginClassList = new ArrayList<>();
        final List<T> pluginList = new ArrayList<>();
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

        synchronized void registerPlugin(Class<? extends T> pluginClass) {
            for (Class<? extends T> klass : pluginClassList) {
                if (klass == pluginClass)
                    return;
            }
            pluginClassList.add(pluginClass);
            try {
                T plugin = pluginClass.newInstance();
                pluginList.add(plugin);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        public List<Class<? extends T>> getPluginClasses() {
            return pluginClassList;
        }

        public List<T> getEnabledPlugins(@NonNull Context context) {
            List<T> enabledPlugins = new ArrayList<>(pluginList.size());
            SharedPreferences settingsPreference =
                    PreferenceManager.getDefaultSharedPreferences(context);
            for (T plugin : pluginList) {
                if (settingsPreference.getBoolean(CommonPluginHelper.pluginEnabledKey(plugin), true)
                        && plugin.isCompatible(context)) {
                    enabledPlugins.add(plugin);
                }
            }
            return enabledPlugins;
        }

        @Override
        public List<T> getAllPlugins() {
            return pluginList;
        }

        /**
         * Test if plugin is available as local plugin
         * TODO: optimize performance
         * @param id
         * @return
         */
        @Override
        public boolean hasPlugin(String id) {
            if (findPlugin(id) == null)
                return false;
            return true;
        }

        @Nullable
        public T findPlugin(T_data data) {
            for (T plugin : getAllPlugins()) {
                if (data.getClass() == plugin.dataFactory().dataClass()) {
                    return plugin;
                }
            }
            return null;
        }

        @Nullable
        public T findPlugin(String id) {
            if (backwardNameMap.size() > 0)
                Logger.d(backwardNameMap);
            if (backwardNameMap.containsKey(id))
                id = backwardNameMap.get(id);
            for (T plugin : getAllPlugins()) {
                if (id.equals(plugin.id())) {
                    return plugin;
                }
            }
            return null;
        }

        @Nullable
        @Override
        public T findPlugin(PluginViewFragmentInterface view) {
            for (T plugin : getAllPlugins()) {
                if (view.getClass().equals(plugin.view().getClass()))
                    return plugin;
            }
            return null;
        }

    }

    public static class OverallRegistry implements PluginLookupper<PluginDef, StorageData> {

        final PluginLookupper<? extends PluginDef, ? extends StorageData>[] lookupers;

        OverallRegistry(PluginLookupper<? extends PluginDef, ? extends StorageData>[] lookupers) {
            this.lookupers = lookupers;
        }

        public List<PluginDef> getEnabledPlugins(@NonNull Context context) {
            List<PluginDef> list = new ArrayList<>();
            for (PluginLookupper<? extends PluginDef, ? extends StorageData> lookupper : lookupers) {
                list.addAll(lookupper.getEnabledPlugins(context));
            }
            return list;
        }

        @Override
        public List<PluginDef> getAllPlugins() {
            List<PluginDef> list = new ArrayList<>();
            for (PluginLookupper<? extends PluginDef, ? extends StorageData> lookupper : lookupers) {
                list.addAll(lookupper.getAllPlugins());
            }
            return list;
        }

        @Override
        public boolean hasPlugin(String id) {
            if (findPlugin(id) == null)
                return false;
            return true;
        }

        @Nullable
        @Override
        public PluginDef findPlugin(StorageData storageData) {
            for (PluginDef plugin : getAllPlugins()) {
                if (storageData.getClass().equals(plugin.dataFactory().dataClass()))
                    return plugin;
            }
            return null;
        }

        @Nullable
        @Override
        public PluginDef findPlugin(String id) {
            for (PluginDef plugin : getAllPlugins()) {
                if (id.equals(plugin.id()))
                    return plugin;
            }
            throw new IllegalAccessError();
        }

        @Nullable
        @Override
        public PluginDef findPlugin(PluginViewFragmentInterface view) {
            for (PluginDef plugin : getAllPlugins()) {
                if (view.getClass().equals(plugin.view().getClass()))
                    return plugin;
            }
            throw new IllegalAccessError();
        }
    }
}
