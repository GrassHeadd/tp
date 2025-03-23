package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingId;
import seedu.address.model.wedding.WeddingTask;

import static java.util.Objects.requireNonNull;

public class UnmarkTaskCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a task as not done for a specified wedding.\n"
            + "Parameters: w/WEDDING_ID i/TASK_INDEX\n"
            + "Example: " + COMMAND_WORD + " w/W1 i/2";

    public static final String MESSAGE_SUCCESS = "Task marked as not done:\n%1$s\nIn Wedding: %2$s";
    public static final String MESSAGE_WEDDING_NOT_FOUND = "Wedding with ID %1$s not found.";
    public static final String MESSAGE_INVALID_TASK_INDEX = "Invalid task index for wedding %1$s.";

    private final WeddingId weddingId;
    private final int taskIndex;

    public UnmarkTaskCommand(WeddingId weddingId, int taskIndex) {
        this.weddingId = weddingId;
        this.taskIndex = taskIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // 1. Find wedding
        Wedding wedding = model.getFilteredWeddingList().stream()
                .filter(w -> w.getWeddingId().equals(weddingId))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_WEDDING_NOT_FOUND, weddingId.value)));

        // 2. Unmark the specified task
        try {
            WeddingTask taskToUnmark = wedding.getTasks().get(taskIndex - 1);
            taskToUnmark.unmark();

            String resultMsg = String.format(MESSAGE_SUCCESS,
                    taskToUnmark,
                    wedding.getWeddingName().fullWeddingName);
            return new CommandResult(resultMsg);

        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_TASK_INDEX, weddingId.value));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnmarkTaskCommand)) {
            return false;
        }
        UnmarkTaskCommand c = (UnmarkTaskCommand) other;
        return weddingId.equals(c.weddingId) && taskIndex == c.taskIndex;
    }
}
