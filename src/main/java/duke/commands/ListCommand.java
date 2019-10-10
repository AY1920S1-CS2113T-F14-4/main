package duke.commands;

import duke.Storage;
import duke.lists.SpinBoxList;
import duke.lists.TaskList;
import duke.items.tasks.Task;
import duke.Ui;

import java.util.ArrayList;
import java.util.List;

public class ListCommand extends Command {

    /**
     * Generates a list of tasks currently within the task list.
     * @param taskList TaskList instance.
     * @param storage Storage instance.
     * @param ui Ui instance.
     */
    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) {
        taskList.sort();
        List<Task> tasks = taskList.getList();
        List<String> formattedOutput = new ArrayList<>();
        formattedOutput.add("Here are the tasks in your list:");
        for (int i = 0; i < taskList.getList().size(); i++) {
            formattedOutput.add((Integer.toString(i + 1) + ". " + tasks.get(i).toString()));
        }
        return ui.showFormatted(formattedOutput);
    }
}
