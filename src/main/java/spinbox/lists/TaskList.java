package spinbox.lists;

import spinbox.DateTime;
import spinbox.Storage;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.items.tasks.Deadline;
import spinbox.items.tasks.Task;
import spinbox.items.tasks.Schedulable;
import spinbox.items.tasks.Event;
import spinbox.items.tasks.Todo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TaskList extends SpinBoxList<Task> {
    private static final String TASK_LIST_FILE_NAME = "/tasks.txt";
    private static final String DELIMITER_FILTER = " \\| ";

    public TaskList(String parentName) throws FileCreationException {
        super(parentName);
        localStorage = new Storage(DIRECTORY_NAME + this.getParentCode() + TASK_LIST_FILE_NAME);
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

    @Override
    public void loadData() throws DataReadWriteException {
        DateTime start;
        DateTime end;
        List<String> savedData = localStorage.loadData();

        for (String datum : savedData) {
            String[] arguments = datum.split(DELIMITER_FILTER);
            switch (arguments[0]) {
            case "T":
                this.add(new Todo(Integer.parseInt(arguments[1]), arguments[2]));
                break;
            case "D":
                start = new DateTime(arguments[3]);
                this.add(new Deadline(Integer.parseInt(arguments[1]), arguments[2], start));
                break;
            default:
                start = new DateTime(arguments[3]);
                end = new DateTime(arguments[4]);
                this.add(new Event(Integer.parseInt(arguments[1]), arguments[2], start, end));
            }
        }
    }

    @Override
    public void saveData() throws DataReadWriteException {
        List<String> dataToSave = new ArrayList<>();
        for (Task task: this.getList()) {
            dataToSave.add(task.storeString());
        }
        localStorage.saveData(dataToSave);
    }
}
