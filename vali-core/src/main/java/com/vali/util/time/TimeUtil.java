package com.vali.util.time;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.vandream.hamster.core.util.Preconditions.checkArgument;
import static com.vandream.hamster.core.util.Preconditions.checkNotNull;


/**
 * Collection of utilities about time intervals.
 */
public class TimeUtil {

	private static final Map<String, ChronoUnit> LABEL_TO_UNIT_MAP = Collections.unmodifiableMap(initMap());

	/**
	 * Parse the given string to a java {@link Duration}.
	 * The string is in format "{length value}{time unit label}", e.g. "123ms", "321 s".
	 * If no time unit label is specified, it will be considered as milliseconds.
	 *
	 * <p>Supported time unit labels are:
	 * <ul>
	 *     <li>DAYS： "d", "day"</li>
	 *     <li>HOURS： "h", "hour"</li>
	 *     <li>MINUTES： "min", "minute"</li>
	 *     <li>SECONDS： "s", "sec", "second"</li>
	 *     <li>MILLISECONDS： "ms", "milli", "millisecond"</li>
	 *     <li>MICROSECONDS： "µs", "micro", "microsecond"</li>
	 *     <li>NANOSECONDS： "ns", "nano", "nanosecond"</li>
	 * </ul>
	 *
	 * @param text string to parse.
	 */
	public static Duration parseDuration(String text) {
		checkNotNull(text);

		final String trimmed = text.trim();
		checkArgument(!trimmed.isEmpty(), "argument is an empty- or whitespace-only string");

		final int len = trimmed.length();
		int pos = 0;

		char current;
		while (pos < len && (current = trimmed.charAt(pos)) >= '0' && current <= '9') {
			pos++;
		}

		final String number = trimmed.substring(0, pos);
		final String unitLabel = trimmed.substring(pos).trim().toLowerCase(Locale.US);

		if (number.isEmpty()) {
			throw new NumberFormatException("text does not start with a number");
		}

		final long value;
		try {
			value = Long.parseLong(number); // this throws a NumberFormatException on overflow
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("The value '" + number +
				"' cannot be re represented as 64bit number (numeric overflow).");
		}

		if (unitLabel.isEmpty()) {
			return Duration.of(value, ChronoUnit.MILLIS);
		}

		ChronoUnit unit = LABEL_TO_UNIT_MAP.get(unitLabel);
		if (unit != null) {
			return Duration.of(value, unit);
		} else {
			throw new IllegalArgumentException("Time interval unit label '" + unitLabel +
                                               "' does not match any of the recognized units: " + TimeUnitEnum.getAllUnits());
		}
	}

	private static Map<String, ChronoUnit> initMap() {
		Map<String, ChronoUnit> labelToUnit = new HashMap<>();
        TimeUnitEnum[] values = TimeUnitEnum.values();
        for (TimeUnitEnum timeUnitEnum : values) {
			for (String label : timeUnitEnum.getLabels()) {
				labelToUnit.put(label, timeUnitEnum.getUnit());
			}
		}
		return labelToUnit;
	}

	/**
	 * @param duration to convert to string
	 * @return duration string in millis
	 */
	public static String getStringInMillis(final Duration duration) {
		return duration.toMillis() + TimeUnitEnum.MILLISECONDS.getLabels().get(0);
	}


}
