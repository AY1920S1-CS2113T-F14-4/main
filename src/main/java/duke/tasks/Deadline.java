package duke.tasks;

import duke.DateTime;
import duke.exceptions.DukeException;

import java.util.Date;

public class Deadline extends Task {

    public Deadline(String description, Date startDate) {
        super(description);
        this.startDate = new DateTime(startDate);
    }

    /**
     * This constructor is used for recreation of Duke.Tasks.Deadline from storage.
     * @param done 1 if task has been marked complete, 0 otherwise.
     * @param description the name or description of the deadline.
     * @param startDate the due date/time of the deadline.
     */
    public Deadline(int done, String description, Date startDate) {
        super(description);
        this.isDone = (done == 1);
        this.startDate = new DateTime(startDate);
    }

    @Override
    public String storeString() {
        return "D | " + super.storeString() + " | " + this.getByString();
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.startDate + ")";
    }

    private String getByString() {
        return this.startDate.toString();
    }

    /**
     * Check if given date is equal to deadline date.
     * @param inputDate the date to be compared.
     * @return true if equal, false if not equal.
     */
    @Override
    public boolean compareEquals(DateTime inputDate) {
        return (this.startDate.compareTo(inputDate) == 0);
    }

    public void setStartDate(Date startDate) {
        this.startDate = new DateTime(startDate);
    }
}
