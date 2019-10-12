package spinbox.commands;

import spinbox.exceptions.DukeException;
import spinbox.exceptions.InputException;
import spinbox.Storage;
import spinbox.lists.TaskList;
import spinbox.Ui;

public class UnknownCommand extends Command {

    @Override
    public String execute(TaskList taskList, Storage storage, Ui ui) throws DukeException {
        throw new InputException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}
