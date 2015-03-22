package nuclibook.models;

/**
 * Represent a particular time of day.
 */
public class TimeOfDay {

    private static final int SECONDS_IN_DAY = 60 * 60 * 24;

    private int secondsPastMidnight;

    /**
     * Initialise time of day.
     *
     * @param secondsPastMidnight The number of seconds past midnight.
     */
    public TimeOfDay(Integer secondsPastMidnight) throws InvalidTimeOfDayException {
        if (secondsPastMidnight > SECONDS_IN_DAY) {
            throw new InvalidTimeOfDayException("There aren't that many seconds in a day");
        }

        this.secondsPastMidnight = secondsPastMidnight;
    }

    /**
     * Get the number of seconds past midnight to define the time of day.
     *
     * @return the number of seconds past midnight
     */
    public int getSecondsPastMidnight() {
        return secondsPastMidnight;
    }

    /**
     * Get the hour of the day.
     *
     * @return the hour of the day
     */
    public int getHour() {
        return Math.floorDiv(secondsPastMidnight, 60 * 60);
    }

    /**
     * Get the minute of the hour.
     *
     * @return the minute of the hour
     */
    public int getMinute() {
        return Math.floorDiv(secondsPastMidnight, 60) % 60;
    }

}
