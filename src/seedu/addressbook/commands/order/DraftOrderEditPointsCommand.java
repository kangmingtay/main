package seedu.addressbook.commands.order;

import seedu.addressbook.commands.Command;
import seedu.addressbook.commands.CommandResult;
import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.member.Member;
import seedu.addressbook.data.member.Points;
import seedu.addressbook.data.member.ReadOnlyMember;

/**
 * Edit the amount of points to redeem from the customer of the draft order.
 * The points to be redeemed will be keyed in and retrieved.
 */
public class DraftOrderEditPointsCommand extends Command {

    public static final String COMMAND_WORD = "draftpoints";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Add the amount of points to be redeemed."
            + "The customer is identified using the index from the last shown menu list. \n\t"
            + "Parameters: POINTS\n\t"
            + "Example: " + COMMAND_WORD + " 50";

    public static final String MESSAGE_SUCCESS = "Points to be redeemed has been added into the draft";

    private final Points toRedeem;

    public DraftOrderEditPointsCommand(int points) {
        this.toRedeem = new Points(points);
    }


    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyMember member = rms.getMemberFromDraftOrder();
            final int points = toRedeem.getPoints();
            final ReadOnlyMember emptyMember = new Member();
            if (member.getName().equals(emptyMember.getName())) {
                throw new IllegalValueException("Member needs to be added first!");
            } else if (member.getPointsValue() < points) {
                throw new IllegalValueException("Member does not have sufficient points to redeem!");
            } else {
                rms.editDraftOrderPoints(points);
                String message = MESSAGE_SUCCESS + "\n" + getDraftOrderAsString();
                return new CommandResult(message);
            }
        } catch (IllegalValueException e) {
            String message = e.getMessage();
            return new CommandResult(message);
        }
    }

}
