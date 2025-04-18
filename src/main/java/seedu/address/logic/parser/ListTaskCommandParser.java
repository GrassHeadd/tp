package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_ID;

import java.util.stream.Stream;

import seedu.address.logic.commands.ListTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.WeddingId;

/**
 * Parses input arguments and creates a new ListTaskCommand object.
 */
public class ListTaskCommandParser implements Parser<ListTaskCommand> {

    @Override
    public ListTaskCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_WEDDING_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_WEDDING_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(ListTaskCommand.MESSAGE_USAGE);
        }

        WeddingId weddingId = ParserUtil.parseWeddingId(argMultimap.getValue(PREFIX_WEDDING_ID).get());

        return new ListTaskCommand(weddingId);
    }

    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
