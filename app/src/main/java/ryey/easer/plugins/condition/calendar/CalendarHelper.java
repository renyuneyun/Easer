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

package ryey.easer.plugins.condition.calendar;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.format.DateUtils;

import java.util.Calendar;

@SuppressLint("MissingPermission")
class CalendarHelper {

    static String getCalendarName(ContentResolver contentResolver, long calendar_id) {
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] PROJECTION = new String[]{
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
        };
        String SELECTION = "((" + CalendarContract.Calendars._ID + " = ?))";
        String[] ARGS = new String[]{
                String.valueOf(calendar_id),
        };
        Cursor cur = contentResolver.query(uri, PROJECTION, SELECTION, ARGS, null);
        if (cur == null)
            return null;
        String res;
        if (cur.moveToNext())
            res = cur.getString(1);
        else
            res = null;
        cur.close();
        return res;
    }

    static Long nextEvent_match_start(ContentResolver contentResolver, long calendar_id, String pattern) {
        CalendarEvent[] events = getEvents(contentResolver, calendar_id, pattern, 3);
        if (events.length == 0)
            return null;
        Long res = events[0].beginMs;
        return res;
    }

    static Long currentEvent_match_end(ContentResolver contentResolver, long calendar_id, String pattern) {
        CalendarEvent[] events = getEvents(contentResolver, calendar_id, pattern, 0, CalendarContract.Instances.END);
        if (events.length == 0)
            return null;
        Long res = events[0].endMs;
        return res;
    }

    static int activeEventsCount(ContentResolver contentResolver, long calendar_id, String pattern) {
        return getEvents(contentResolver, calendar_id, pattern, 0).length;
    }

    private static CalendarEvent[] getEvents(ContentResolver cr, long calendarID, String matchTitle, int days) {
        return getEvents(cr, calendarID, matchTitle, days, CalendarContract.Instances.BEGIN);
    }
    private static CalendarEvent[] getEvents(ContentResolver cr, long calendarID, String matchTitle, int days, String sortMethod) {
        long current_time = Calendar.getInstance().getTimeInMillis();

        Uri.Builder instance_uri = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(instance_uri, current_time);
        ContentUris.appendId(instance_uri, current_time + DateUtils.DAY_IN_MILLIS * days);

        final String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.TITLE,
                CalendarContract.Instances.BEGIN,
                CalendarContract.Instances.END,
                CalendarContract.Instances.ALL_DAY,
        };
        String instance_selection = "((" + CalendarContract.Instances.CALENDAR_ID + " IS ?)" +
                " AND (" + CalendarContract.Instances.TITLE + " LIKE ?)" +
                ")";
        String[] instance_selectionArgs = new String[] {
                String.valueOf(calendarID),
                matchTitle,
        };
        Cursor cur = cr.query(instance_uri.build(), INSTANCE_PROJECTION, instance_selection, instance_selectionArgs,
                sortMethod);
        if (cur == null)
            return null;

        int len = cur.getCount();
        CalendarEvent[] res = new CalendarEvent[len];
        cur.moveToFirst();
        for (int i = 0; i < len; i++) {
            res[i] = new CalendarEvent(
                    cur.getString(0),
                    cur.getLong(1),
                    cur.getLong(2),
                    cur.getString(3)
            );
            cur.moveToNext();
        }
        cur.close();
        return res;
    }

    private static class CalendarEvent {
        public String eventTitle;
        public Long beginMs;
        public Long endMs;
        public String allDay;

        CalendarEvent(String eventTitle, Long beginMs, Long endMs, String allDay) {
            this.eventTitle = eventTitle;
            this.beginMs = beginMs;
            this.endMs = endMs;
            this.allDay = allDay;
        }
    }
}
