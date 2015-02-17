package nuclibook.models;

import java.sql.Time;

/**
 * Represent a particular time of day.
 */
public class TimeOfDay {

    private static final Integer SECONDS_IN_DAY = 60 * 60 * 24;

    private Integer secondsPastMidnight;

    /**
     * Initialise time of day.
     * @param secondsPastMidnight The seconds past midnight.
     */
    public TimeOfDay(Integer secondsPastMidnight) throws InvalidTimeOfDayException {
        if (secondsPastMidnight > SECONDS_IN_DAY) {
            throw new InvalidTimeOfDayException("There isn't that many seconds in a day");
        }

        this.secondsPastMidnight = secondsPastMidnight;
    }

    public Integer getSecondsPastNight() {
        return this.secondsPastMidnight;
    }

}
