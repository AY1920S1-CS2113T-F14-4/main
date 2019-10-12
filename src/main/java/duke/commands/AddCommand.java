package duke.commands;

import duke.DateTime;
import duke.exceptions.DukeException;
import duke.exceptions.InputException;
import duke.Storage;
import duke.Ui;
import duke.items.tasks.After;
import duke.items.tasks.Recurring;
import duke.items.tasks.Todo;
import duke.items.tasks.Fixed;
import duke.items.tasks.Deadline;
import duke.items.tasks.Task;
import duke.items.tasks.Tentative;
import duke.items.tasks.Event;
import duke.items.tasks.Within;
import duke.lists.TaskList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddCommand extends Command {
    private String type;
    private String fullCommand;

    /**
     * Constructor for creation of Task objects, does some input checking.
     * @param components Components of the full command
     * @param fullCommand the full command
     * @throws DukeException Can throw from invalid input or storage errors
     */
    public AddCommand(String[] components, String fullCommand) throws DukeException {
        this.type = components[0];
        this.fullCommand = fullCommand;

        if (components.length == 1) {
            throw new InputException("☹ OOPS!!! The description of a task cannot be empty.");
        } else if (this.type.equals("deadline") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of a deadline cannot be empty.");
        } else if (this.type.equals("do-after") && components[1].equals("/after")) {
            throw new InputException("☹ OOPS!!! The description of a do-after event or time cannot be empty.");
        } else if (this.type.equals("event") && components[1].equals("/by")) {
            throw new InputException("☹ OOPS!!! The description of an event cannot be empty.");
        } else if (this.type.equals("fixed") && components[1].equals("/needs")) {
            throw new InputException("☹ OOPS!!! The description of a fixed duration task cannot be empty.");
        } else if (this.type.equals("within") && components[1].equals("/between")) {
            throw new InputException("☹ OOPS!!! The description of a within task cannot be empty.");
        } else if (this.type.equals("recurring") && components[1].equals("/at")) {
            throw new InputException("☹ OOPS!!! The description of a recurring task cannot be empty.");
        }
    }

    /**
     * Add Task to task list.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     * @throws DukeException invalid input or storage error.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws DukeException {
        List<String> formattedOutput = new ArrayList<>();
        Task added;
        List dates;
        String fixedDuration;
        String doAfter;
        DateTime start;
        DateTime end;

        try {
            switch (this.type) {
            case "todo":
                added = taskList.add(new Todo(fullCommand.replaceFirst("todo ", "")));
                formattedOutput.add("Got it. I've added this todo:");
                formattedOutput.add(added.toString());
                break;

            case "deadline":
<<<<<<< HEAD
                parser = new com.joestelmach.natty.Parser();
                dates = parser.parse(fullCommand.split("/by ")[1]).get(0).getDates();
                Date by = (Date) dates.get(0);
=======
                start = new DateTime(fullCommand.split("/by ")[1]);
>>>>>>> 3d141ea8e35037ac3c80a5acbb40d1ef3e369ebd
                added = taskList.add(new Deadline(fullCommand.substring(0, fullCommand.lastIndexOf(" /by"))
                        .replaceFirst("deadline ", ""),
                        start));
                formattedOutput.add("Got it. I've added this deadline:");
                formattedOutput.add(added.toString());
                break;

            case "fixed":
                fixedDuration = fullCommand.split("/needs ")[1];
                added = taskList.add(new Fixed(fullCommand.substring(0, fullCommand.lastIndexOf(" /needs"))
                        .replaceFirst("fixed ", ""),
                        fixedDuration));
                formattedOutput.add("Got it. I've added this fixed duration task:");
                formattedOutput.add(added.toString());
                break;

            case "do-after":
                doAfter = fullCommand.split("/after ")[1];
                added = taskList.add(new After(fullCommand.substring(0, fullCommand.lastIndexOf(" /after"))
                        .replaceFirst("do-after ", ""),
                        doAfter));
                formattedOutput.add("Got it. I've added this do-after task:");
                formattedOutput.add(added.toString());
                break;

            case "do-within":
<<<<<<< HEAD
                parser = new com.joestelmach.natty.Parser();
                dates = parser.parse(fullCommand.split("/between ")[1]).get(0).getDates();
                start = (Date) dates.get(0);
                end = (Date) dates.get(1);
=======

                start = new DateTime(fullCommand.split("/between ")[1], 0);
                end = new DateTime(fullCommand.split("/between ")[1], 1);
>>>>>>> 3d141ea8e35037ac3c80a5acbb40d1ef3e369ebd
                added = taskList.add(new Within(fullCommand.substring(0, fullCommand.lastIndexOf(" /between"))
                        .replaceFirst("do-within ", ""),
                        start, end));
                formattedOutput.add("Got it. I've added this do-within task:");
                formattedOutput.add(added.toString());
                break;

            case "tentative":
<<<<<<< HEAD
                parser = new com.joestelmach.natty.Parser();
                dates = parser.parse(fullCommand.split("/around ")[1]).get(0).getDates();
                start = (Date) dates.get(0);
                end = (Date) dates.get(1);
=======
                start = new DateTime(fullCommand.split("/around ")[1], 0);
                end = new DateTime(fullCommand.split("/around ")[1], 1);
>>>>>>> 3d141ea8e35037ac3c80a5acbb40d1ef3e369ebd
                added = taskList.add(new Tentative(fullCommand.substring(0, fullCommand.lastIndexOf(" /around"))
                        .replaceFirst("tentative ", ""),
                        start, end));
                formattedOutput.add("Got it. I've added this tentative event:");
                formattedOutput.add(added.toString());
                break;

            case "recurring":
                String[] partials = fullCommand.split("/every ");
                start = new DateTime(partials[0].split("/at ")[1], 0);
                end = new DateTime(partials[0].split("/at ")[1], 1);

                String[] frequencies = partials[1].split(":");
                long minutes = (Long.parseLong(frequencies[0]) * 60 * 24)
                        + (Long.parseLong(frequencies[1]) * 60)
                        + Long.parseLong(frequencies[2]);

                added = taskList.add(new Recurring(fullCommand.substring(0, fullCommand.lastIndexOf(" /at"))
                        .replaceFirst("recurring ", ""),
                        start, end, minutes));

                formattedOutput.add("Got it. I've added this recurring task:");
                formattedOutput.add(added.toString());
                formattedOutput.add("Use the done command to advance to the next instance of the task.");
                break;

            default:
                start = new DateTime(fullCommand.split("/at ")[1], 0);
                end = new DateTime(fullCommand.split("/at ")[1], 1);

                List<Task> tasks = taskList.getList();
                for (int i = 0; i < tasks.size(); i++) {
                    Task currentTask = tasks.get(i);
                    if (currentTask.isOverlapping(start, end)) {
                        throw new InputException("Time conflicting with:\n"
                                + "    " + (i + 1) + "." + currentTask.toString() + "\n"
                                + "Please choose another time interval.");
                    }
                }
                added = taskList.add(new Event(fullCommand.substring(0, fullCommand.lastIndexOf(" /at"))
                        .replaceFirst("event ", ""),
                        start, end));
                formattedOutput.add("Got it. I've added this event:");
                formattedOutput.add(added.toString());
            }
        } catch (IndexOutOfBoundsException e) {
            throw new InputException("Please ensure that you enter the full command.\n"
                    + "Duke.Tasks.Deadline: deadline <task name> /by <MM/DD/YYYY HH:MM>\n"
                    + "Duke.Tasks.Do-After: do-after <task name> /after <do-after event or time>\n"
                    + "Duke.Tasks.Event: event <task name> /at <start as MM/DD/YYYY HH:MM> "
                    + "to <end as MM/DD/YYYY HH:MM>\n"
                    + "Duke.Tasks.Fixed: fixed <task name> /needs <fixed task duration>\n"
                    + "Duke.Tasks.Tentative: tentative <task name> /around <start as MM/DD/YYYY HH:MM> "
                    + "to <end as MM/DD/YYYY HH:MM>\n"
                    + "Duke.Tasks.Within: do-within <task name> /between <start as MM/DD/YYYY HH:MM> "
                    + "and <end as MM/DD/YYYY HH:MM>\n"
                    + "Duke.Tasks.Recurring: recurring <task name> /at <start as MM/DD/YYYY HH:MM> "
                    + "to <end as MM/DD/YYYY HH:MM> /every DD:HH:MM"
            );
        }
        formattedOutput.add("You currently have " + taskList.getList().size()
                + ((taskList.getList().size() == 1) ? " task in the list." : " tasks in the list."));
        storage.setData(taskList.getList());
        taskList.sort();
        return ui.showFormatted(formattedOutput);
    }
}