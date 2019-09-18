import duke.tasks.Within;
import duke.tasks.Task;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WithinTest {

    @org.junit.jupiter.api.Test
    void testToString_createAndMarkDoneNewTask() {
        Date startDate = new Date(2019, 9, 19, 12, 0);
        Date endDate = new Date(2019, 9,20, 12, 0);
        Task task = new Within("Watch movie", startDate, endDate);
        assertEquals("[W][✗] Watch movie (between: 09/19/2019 12:00 and 09/20/2019 12:00)", task.toString());
        assertNotEquals("[W][✓] Watch movie (between: 09/19/2019 12:00 and 09/20/2019 12:00)", task.toString());
        task.markDone();
        assertEquals("[W][✓] Watch movie (between: 09/19/2019 12:00 and 09/20/2019 12:00)", task.toString());
        assertNotEquals("[W][✗] Watch movie (between: 09/19/2019 12:00 and 09/20/2019 12:00)", task.toString());
    }

    @org.junit.jupiter.api.Test
    void testStoreString_createAndMarkDoneNewTask() {
        Date startDate = new Date(2019, 9, 19, 12, 0);
        Date endDate = new Date(2019, 9,20, 12, 0);
        Task task = new Within("Watch movie", startDate, endDate);
        assertEquals("W | 0 | Watch movie | 09/19/2019 12:00 | 09/20/2019 12:00", task.storeString());
        assertNotEquals("W | 1 | Watch movie | 09/19/2019 12:00 | 09/20/2019 12:00", task.storeString());
        task.markDone();
        assertEquals("W | 1 | Watch movie | 09/19/2019 12:00 | 09/20/2019 12:00", task.storeString());
        assertNotEquals("W | 0 | Watch movie | 09/19/2019 12:00 | 09/20/2019 12:00", task.storeString());
    }

}
