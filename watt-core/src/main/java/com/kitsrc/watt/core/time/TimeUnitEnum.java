package com.kitsrc.watt.core.time;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA </p>
 *
 * @author : LiJie  </p>
 * @date : 2019/11/21  </p>
 * @time : 10:42  </p>
 * Description:  </p>
 */
public enum TimeUnitEnum {
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

    private final List<String> labels;

    private final ChronoUnit unit;

    TimeUnitEnum(ChronoUnit unit, String[]... labels) {
        this.unit = unit;
        this.labels = Arrays.stream(labels)
                            .flatMap(ls -> Arrays.stream(ls))
                            .collect(Collectors.toList());
    }

    /**
     * @param label the original label
     * @return the singular format of the original label
     */
    private static String[] singular(String label) {
        return new String[]{
                label
        };
    }

    /**
     * @param label the original label
     * @return both the singular format and plural format of the original label
     */
    private static String[] plural(String label) {
        return new String[]{
                label,
                label + PLURAL_SUFFIX
        };
    }

    public List<String> getLabels() {
        return labels;
    }

    public ChronoUnit getUnit() {
        return unit;
    }

    public static String getAllUnits() {
        StringJoiner joiner = new StringJoiner(", ");
        for (TimeUnit timeUnitEnum : TimeUnit.values()) {
            String timeUnitString = createTimeUnitString(timeUnitEnum);
            joiner.add(timeUnitString);
        }
        return joiner.toString();
    }

    private static String createTimeUnitString(TimeUnit timeUnitEnum) {
        return timeUnitEnum.name() + ": (" + String.join(" | ", timeUnitEnum.getLabels()) + ")";
    }
}
