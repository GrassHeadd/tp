package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;

import seedu.address.logic.commands.EditWeddingEventCommand;
import seedu.address.logic.commands.EditWeddingEventCommand.EditWeddingDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

/**
 * Parses input arguments and creates a new EditWeddingEventCommand object.
 */
public class EditWeddingEventCommandParser implements Parser<EditWeddingEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditWeddingEventCommand
     * and returns an EditWeddingEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public EditWeddingEventCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION);

        WeddingId index;
        try {
            index = ParserUtil.parseWeddingId(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditWeddingEventCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_WEDDING_NAME, PREFIX_WEDDING_DATE, PREFIX_WEDDING_LOCATION);

        EditWeddingDescriptor editWeddingDescriptor = new EditWeddingDescriptor();

        if (argMultimap.getValue(PREFIX_WEDDING_NAME).isPresent()) {
            editWeddingDescriptor.setWeddingName(
                    ParserUtil.parseWeddingName(argMultimap.getValue(PREFIX_WEDDING_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_WEDDING_DATE).isPresent()) {
            editWeddingDescriptor.setWeddingDate(
                    ParserUtil.parseWeddingDate(argMultimap.getValue(PREFIX_WEDDING_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_WEDDING_LOCATION).isPresent()) {
            editWeddingDescriptor.setWeddingLocation(
                    ParserUtil.parseWeddingLocation(argMultimap.getValue(PREFIX_WEDDING_LOCATION).get()));
        }

        if (!editWeddingDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditWeddingEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditWeddingEventCommand(index, editWeddingDescriptor);
    }
}
