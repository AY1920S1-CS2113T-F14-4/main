package duke;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTime {
    private Date dateTime;

    /**
     * Constructor for simple dateTime object.
     * @param dateTime A Date Object with date and time.
     */
    public DateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Converts the Date object back to the string version in the format of dd/MM/yyyy HHmm
     * This can be reused to create an identical dateTime object.
     * @return String equivalent of Date object.
     */
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HHmm");
        String dateString = dateFormat.format(dateTime);
        return dateString;
    }

    /**
     * Compares DateTime object to a java.util.Date object
     * @param date a java.util.Date object to be compared against
     * @return -1 if given date is before, 0 if given data is same, 1 if given date is after
     */
    public int compareDate(Date date) {
        if (date.before(dateTime)) {
            return -1;
        } else if (date == dateTime) {
            return 0;
        } else {
            return 1;
        }
    }
}
