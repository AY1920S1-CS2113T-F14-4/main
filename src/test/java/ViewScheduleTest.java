import duke.DateTime;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ViewScheduleTest {

    @org.junit.jupiter.api.Test
    void testDateCheckDeadline() {
        Date inputDate = new Date(2019, 9,20);
        Task task = new Deadline("Test", inputDate);
        assertEquals(0, ((Deadline) task).compareDate(new DateTime(inputDate)));
        Date inputDateTwo = new Date(2019, 9,21);
        assertNotEquals(0, ((Deadline) task).compareDate(new DateTime(inputDateTwo)));
    }

    @org.junit.jupiter.api.Test
    void testDateCheckEvent() {
        Date inputDate = new Date(2019, 9,20);
        Date startDate = new Date(2019, 9,19);
        Date endDate = new Date(2019, 9,21);

        Task task = new Event("Test", startDate, endDate);
        assertEquals(0, ((Event) task).compareDate(new DateTime(inputDate)));
        assertEquals(0, ((Event) task).compareDate(new DateTime(startDate)));
        assertEquals(0, ((Event) task).compareDate(new DateTime(endDate)));
        Date inputDateTwo = new Date(2019, 9,23);
        assertNotEquals(0, ((Event) task).compareDate(new DateTime(inputDateTwo)));
    }
}
