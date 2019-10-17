package spinbox.entities;

import spinbox.DateTime;
import spinbox.containers.lists.TaskList;
import spinbox.exceptions.CalendarSelectorException;

import java.util.List;

public class Calendar {
    DateTime startDate;
    DateTime endDate;
    int modifier;

    public Calendar(int modifier, String date) throws CalendarSelectorException {
        this.modifier = modifier;
        setDates(date);
    }

    private void setDates(String date) throws CalendarSelectorException {
        switch (modifier) {
        case 1:
            startDate = new DateTime(date + " 00:00");
            endDate = new DateTime(date + " 23:59");
            break;
        case 2:
            startDate = new DateTime(date + " 00:00").getStartOfTheWeek();
            endDate = new DateTime(date + " 23:59").getEndOfTheWeek();
            break;
        case 3:
            startDate = new DateTime(date + " 00:00").getStartOfTheMonth();
            endDate = new DateTime(date + " 23:59").getEndOfTheMonth();
            break;
        default:
            throw new CalendarSelectorException();
        }
    }

    public List<String> tasksInCalendar(TaskList taskList) {
        return taskList.viewListInterval(startDate, endDate);
    }
}