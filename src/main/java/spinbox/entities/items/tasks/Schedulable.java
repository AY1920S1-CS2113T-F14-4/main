package spinbox.entities.items.tasks;

import spinbox.DateTime;

public abstract class Schedulable extends Task {
    DateTime startDate;
    DateTime endDate;

    /**
     * Constructor to initialize default values of any instances of children of Task.
     * @param taskName name of the schedulable task
     */
    public Schedulable(String taskName) {
        super(taskName);
        this.startDate = null;
        this.endDate = null;
    }

    public Schedulable() {

    }

    @Override
    public void fromStoredString(String fromStorage) {
        String[] arguments = fromStorage.split(DELIMITER_FILTER);
        this.setStartDate(new DateTime(arguments[3]));
        if (arguments.length == 5) {
            this.setEndDate(new DateTime(arguments[4]));
        }
        int done = Integer.parseInt(arguments[1]);
        this.updateDone(done == 1);
        this.setName(arguments[2]);
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    abstract String getStartDateString();

    abstract String getEndDateString();

    /**
     * Compare if input date is equals.
     * @return default is to return false, only implemented properly in deadline and event task.
     */
    public boolean compareEquals(DateTime inputDate) {
        return false;
    }

    /**
     * Compare if time interval overlaps.
     * @param startTime the start of the interval
     * @param endTime the end of the interval
     * @return default to return false, only implemented in event task.
     */
    public Boolean isOverlapping(DateTime startTime, DateTime endTime) {
        return false;
    }

    public boolean isSchedulable() {
        return true;
    }
}
