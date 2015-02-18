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

	public int getSecondsPastNight() {
		return secondsPastMidnight;
	}

	public int getHour() {
		return Math.floorDiv(secondsPastMidnight, 60 * 60);
	}

	public int getMinute() {
		return Math.floorDiv(secondsPastMidnight, 60) % 60;
	}

}
