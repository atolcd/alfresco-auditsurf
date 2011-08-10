/**
 * Copyright (C) 2011 Atol Conseils et Développements.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA. **/

package com.atolcd.alfresco.audit.web.scripts;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.atolcd.alfresco.audit.cmr.EnumPeriod;

public abstract class DateInterval {

	public static GregorianCalendar setInterval(Date date, String period, int interval) {
		return offset(date, period, interval);
	}

	private static GregorianCalendar offset(Date date, String period, int interval) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		switch (EnumPeriod.valueOf(period)) {
		case day:
			calendar.add(Calendar.DATE, -interval);
			break;

		case week:
			calendar.add(Calendar.WEEK_OF_YEAR, -interval);
			break;

		case month:
			calendar.add(Calendar.MONTH, -interval);
			break;

		case year:
			calendar.add(Calendar.YEAR, -interval);
			break;
		}

		return calendar;
	}

	public static Calendar[] startEndPeriod(Calendar calendar, String period) {
		Calendar[] extremities = new Calendar[2];

		switch (EnumPeriod.valueOf(period)) {
		case day:
			extremities = new Calendar[] { calendar };
			extremities[0].set(Calendar.MINUTE, 0);
			extremities[0].set(Calendar.SECOND, 0);
			extremities[0].set(Calendar.MILLISECOND, 0);
			break;

		case week:
			extremities[0] = new GregorianCalendar();
			extremities[0].setTime(calendar.getTime());
			extremities[0].set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			extremities[0].set(Calendar.HOUR_OF_DAY, 0);
			extremities[0].set(Calendar.MINUTE, 0);
			extremities[0].set(Calendar.SECOND, 0);
			extremities[0].set(Calendar.MILLISECOND, 0);
			extremities[1] = new GregorianCalendar();
			extremities[1].setTime(calendar.getTime());
			// if week begins Sunday
			if (calendar.getFirstDayOfWeek() == 1) {
				extremities[1].set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
			}
			// if week begins Monday
			if (calendar.getFirstDayOfWeek() == 2) {
				extremities[1].set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));

			}
			extremities[1].set(Calendar.HOUR_OF_DAY, 23);
			extremities[1].set(Calendar.MINUTE, 59);
			extremities[1].set(Calendar.SECOND, 59);
			break;

		case month:
			extremities[0] = new GregorianCalendar();
			extremities[0].setTime(calendar.getTime());
			extremities[0].set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			extremities[0].set(Calendar.HOUR_OF_DAY, 0);
			extremities[0].set(Calendar.MINUTE, 0);
			extremities[0].set(Calendar.SECOND, 0);
			extremities[1] = new GregorianCalendar();
			extremities[1].setTime(calendar.getTime());
			extremities[1].set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			extremities[1].set(Calendar.HOUR_OF_DAY, 23);
			extremities[1].set(Calendar.MINUTE, 59);
			extremities[1].set(Calendar.SECOND, 59);
			break;

		case year:
			extremities[0] = new GregorianCalendar();
			extremities[0].setTime(calendar.getTime());
			extremities[0].set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
			extremities[0].set(Calendar.HOUR_OF_DAY, 0);
			extremities[0].set(Calendar.MINUTE, 0);
			extremities[0].set(Calendar.SECOND, 0);
			extremities[1] = new GregorianCalendar();
			extremities[1].setTime(calendar.getTime());
			extremities[1].set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
			extremities[1].set(Calendar.HOUR_OF_DAY, 23);
			extremities[1].set(Calendar.MINUTE, 59);
			extremities[1].set(Calendar.SECOND, 59);
			break;
		}

		return extremities;
	}

	public static Map<Date, Integer> createPeriod(List<Object[]> listres, String period, Calendar[] calendars) {
		Map<Date, Integer> datesOfPeriod = new TreeMap<Date, Integer>();
		Iterator<Object[]> it = listres.iterator();
		Object[] row;
		int previousHour = 0;
		int currentHour = 0;
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(calendars[0].getTime());
		int count = 0;

		switch (EnumPeriod.valueOf(period)) {
		case day:
			while (it.hasNext()) {
				row = it.next();
				currentHour = (Integer) row[0];
				count = (Integer) row[1];
				for (int i = previousHour; i < currentHour; i++) {
					calendar.set(Calendar.HOUR_OF_DAY, i);
					calendar.set(Calendar.MINUTE, 0);
					calendar.set(Calendar.SECOND, 0);
					calendar.set(Calendar.MILLISECOND, 0);
					datesOfPeriod.put(calendar.getTime(), 0);
				}
				calendar.set(Calendar.HOUR_OF_DAY, currentHour);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				datesOfPeriod.put(calendar.getTime(), count);
				previousHour = currentHour + 1;
			}
			for (int i = previousHour; i < 23; i++) {
				calendar.set(Calendar.HOUR_OF_DAY, i);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				datesOfPeriod.put(calendar.getTime(), 0);
			}
			break;

		case week:
			datesOfPeriod = generateMap(calendars, listres);
			break;

		case month:
			datesOfPeriod = generateMap(calendars, listres);
			break;

		case year:
			for (int i = calendar.getActualMinimum(Calendar.MONTH); i <= calendar.getActualMaximum(Calendar.MONTH); i++) {
				Calendar date = new GregorianCalendar(calendar.get(Calendar.YEAR), i, 1, 0, 0, 0);
				datesOfPeriod.put(date.getTime(), 0);
			}

			while (it.hasNext()) {
				row = it.next();
				Calendar date = new GregorianCalendar(calendar.get(Calendar.YEAR), (Integer) row[0], 1, 0, 0, 0);
				datesOfPeriod.put(date.getTime(), (Integer) row[1]);
			}

			break;

		case none:
			datesOfPeriod = generateMap(calendars, listres);

			break;

		}

		return datesOfPeriod;
	}

	public static Map<Date, Integer> generateMap(Calendar[] calendars, List<Object[]> listres) {
		Map<Date, Integer> datesOfPeriod = new TreeMap<Date, Integer>();
		Iterator<Object[]> it = listres.iterator();
		Object[] row;
		Calendar previousDate = new GregorianCalendar();
		Calendar currentDate = new GregorianCalendar();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(calendars[0].getTime());
		int count = 0;

		previousDate.setTime(calendars[0].getTime());
		previousDate.set(Calendar.HOUR_OF_DAY, 0);
		previousDate.set(Calendar.MINUTE, 0);
		previousDate.set(Calendar.SECOND, 0);
		previousDate.set(Calendar.MILLISECOND, 0);

		while (it.hasNext()) {
			row = it.next();
			currentDate.setTime((Date) row[0]);
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			currentDate.set(Calendar.MILLISECOND, 0);
			count = (Integer) row[1];

			while (!previousDate.getTime().equals(currentDate.getTime())) {
				datesOfPeriod.put(previousDate.getTime(), 0);
				if (previousDate.get(Calendar.DAY_OF_YEAR) == previousDate.getActualMaximum(Calendar.DAY_OF_YEAR)) {
					previousDate.set(Calendar.YEAR, previousDate.get(Calendar.YEAR) + 1);
					previousDate.set(Calendar.DAY_OF_YEAR, previousDate.getActualMinimum(Calendar.DAY_OF_YEAR));
				} else
					previousDate.set(Calendar.DAY_OF_YEAR, previousDate.get(Calendar.DAY_OF_YEAR) + 1);
				previousDate.set(Calendar.HOUR_OF_DAY, 0);
				previousDate.set(Calendar.MINUTE, 0);
				previousDate.set(Calendar.SECOND, 0);
				previousDate.set(Calendar.MILLISECOND, 0);
			}

			datesOfPeriod.put(currentDate.getTime(), count);
			if (currentDate.get(Calendar.DAY_OF_YEAR) == currentDate.getActualMaximum(Calendar.DAY_OF_YEAR)) {
				previousDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR) + 1);
				previousDate.set(Calendar.DAY_OF_YEAR, previousDate.getActualMinimum(Calendar.DAY_OF_YEAR));
			} else
				previousDate.set(Calendar.DAY_OF_YEAR, currentDate.get(Calendar.DAY_OF_YEAR) + 1);
			previousDate.set(Calendar.HOUR_OF_DAY, 0);
			previousDate.set(Calendar.MINUTE, 0);
			previousDate.set(Calendar.SECOND, 0);
			previousDate.set(Calendar.MILLISECOND, 0);
		}

		calendar.setTime(calendars[1].getTime());
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		while (!previousDate.getTime().equals(calendar.getTime())) {
			datesOfPeriod.put(previousDate.getTime(), 0);
			if (previousDate.get(Calendar.DAY_OF_YEAR) == previousDate.getActualMaximum(Calendar.DAY_OF_YEAR)) {
				previousDate.set(Calendar.YEAR, previousDate.get(Calendar.YEAR) + 1);
				previousDate.set(Calendar.DAY_OF_YEAR, previousDate.getActualMinimum(Calendar.DAY_OF_YEAR));
			} else
				previousDate.set(Calendar.DAY_OF_YEAR, previousDate.get(Calendar.DAY_OF_YEAR) + 1);
			previousDate.set(Calendar.HOUR_OF_DAY, 0);
			previousDate.set(Calendar.MINUTE, 0);
			previousDate.set(Calendar.SECOND, 0);
			previousDate.set(Calendar.MILLISECOND, 0);
		}

		return datesOfPeriod;
	}

}