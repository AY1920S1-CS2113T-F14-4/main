package spinbox.lists;

import spinbox.DateTime;
import spinbox.Storage;
import spinbox.exceptions.SpinBoxException;
import spinbox.items.tasks.Schedulable;
import spinbox.items.tasks.Task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskList extends SpinBoxItemList<Task> {
    private static final String TASK_LIST_FILE_NAME = "tasks.txt";

    public TaskList(String parentName) throws SpinBoxException {
        super(parentName);
        localStorage = new Storage(this.getParentCode() + TASK_LIST_FILE_NAME);
    }

    public TaskList(List<Task> tasks, String parentName) throws SpinBoxException {
        super(tasks, parentName);
        localStorage = new Storage(this.getParentCode() + TASK_LIST_FILE_NAME);
    }

    static class StartDateComparator implements Comparator<Task> {
        @Override
        public int compare(Task a, Task b) {
            DateTime startDateA = null;
            DateTime startDateB = null;

            if (a.isSchedulable()) {
                startDateA = ((Schedulable)a).getStartDate();
            }
            if (b.isSchedulable()) {
                startDateB = ((Schedulable)b).getStartDate();
            }

            if (startDateA == null && startDateB == null) {
                return 0;
            } else if (startDateA == null) {
                return 1;
            } else if (startDateB == null) {
                return -1;
            } else {
                return startDateA.compareTo(startDateB);
            }
        }
    }

    public void sort() {
        Collections.sort(list, new StartDateComparator());
    }
}
