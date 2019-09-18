package duke.tasks;

import duke.DateTime;
import duke.exceptions.DukeException;

import java.util.Date;

public class Deadline extends Task {
    private DateTime by;

    public Deadline(String description, Date by) throws DukeException {
        super(description);
        this.by = new DateTime(by);
    }

    /**
     * This constructor is used for recreation of Duke.Tasks.Deadline from storage.
     * @param done 1 if task has been marked complete, 0 otherwise.
     * @param description the name or description of the deadline.
     * @param by the due date/time of the deadline.
     */
    public Deadline(int done, String description, Date by) throws DukeException {
        super(description);
        this.isDone = (done == 1);
        this.by = new DateTime(by);
    }

    @Override
    public String storeString() {
        return "D | " + super.storeString() + " | " + this.getBy();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }

    private String getBy() {
        return this.by.toString();
    }

    /**
     * Check if given date is before, during, or after deadline
     * @param inputDate the date to be compared
     * @return -1 if given date is before, 0 if given data is same, 1 if given date is after
     */
    public int compareDate(Date inputDate) {
        return by.compareDate(inputDate);
    }
}
