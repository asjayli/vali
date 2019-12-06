package com.kitsrc.watt.util.time;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created with IntelliJ IDEA </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/11/21  </p>
 * @time : 10:42  </p>
 * Description:  </p>
 */
public enum  TimeUnitEnum {
    /**
     *
     */
    DAYS(ChronoUnit.DAYS, singular("d"), plural("day")),
    HOURS(ChronoUnit.HOURS, singular("h"), plural("hour")),
    MINUTES(ChronoUnit.MINUTES, singular("min"), plural("minute")),
    SECONDS(ChronoUnit.SECONDS, singular("s"), plural("sec"), plural("second")),
    MILLISECONDS(ChronoUnit.MILLIS, singular("ms"), plural("milli"), plural("millisecond")),
    MICROSECONDS(ChronoUnit.MICROS, singular("µs"), plural("micro"), plural("microsecond")),
    NANOSECONDS(ChronoUnit.NANOS, singular("ns"), plural("nano"), plural("nanosecond"));

    private static final String PLURAL_SUFFIX = "s";

    private List<String> labels;

    private ChronoUnit unit;

    TimeUnitEnum(ChronoUnit unit, String[]... labels) {
        this.unit = unit;
        List<String> list = new ArrayList<>();
        for (String[] labelArray : labels) {
            for (String label : labelArray) {
                list.add(label);
            }
        }
        this.labels = list;
    }

    /**
     * @param label the original label
     * @return the singular format of the original label
     */
    private static String[] singular(String label) {
        return new String[] {
                label
        };
    }

    /**
     * @param label the original label
     * @return both the singular format and plural format of the original label
     */
    private static String[] plural(String label) {
        return new String[] {
                label,
                label + PLURAL_SUFFIX
        };
    }

    public List<String> getLabels() {
        return this.labels;
    }

    public ChronoUnit getUnit() {
        return this.unit;
    }

    public static String getAllUnits() {
        StringJoiner joiner = new StringJoiner(", ");
        for (TimeUnitEnum timeUnitEnum : TimeUnitEnum.values()) {
            String timeUnitString = createTimeUnitString(timeUnitEnum);
            joiner.add(timeUnitString);
        }
        return joiner.toString();
    }

    private static String createTimeUnitString(TimeUnitEnum timeUnitEnum) {
        return timeUnitEnum.name() + ": (" + String.join(" | ", timeUnitEnum.getLabels()) + ")";
    }
}
