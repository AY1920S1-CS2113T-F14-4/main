package spinbox.entities.items.tasks;

import spinbox.exceptions.CorruptedDataException;

public abstract class NonSchedulable extends Task {

    /**
     * Constructor to initialize default values of any instances of children of Task.
     *
     * @param taskName the name of task.
     */
    public NonSchedulable(String taskName) {
        super(taskName);
    }

    public NonSchedulable() {
        super();
    }

    @Override
    public void fromStoredString(String fromStorage) throws CorruptedDataException {
        try {
            String[] arguments = fromStorage.split(DELIMITER_FILTER);
            int done = Integer.parseInt(arguments[1]);
            String taskName = arguments[2];
            this.updateDone(done == 1);
            this.setName(taskName);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new CorruptedDataException();
        }
    }

    public boolean isSchedulable() {
        return false;
    }
}
