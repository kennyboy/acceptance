package tests.unverified;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import framework.Test;
import framework.Rules;
import framework.cards.Card;
import framework.interfaces.GameState;
import framework.interfaces.MoveMaker;
import framework.interfaces.activators.ArchitectusActivator;

/**
 *
 * Test the basic functionality of Architectus
 *
 * @author Junjie CHEN
 *
 */

public class CardActivatorArchitectusBasicTest extends Test {

    @Override
    public String getShortDescription() {
        return "Test the basic functionality of Architectus";
    }

    @Override
    public void run(GameState gameState, MoveMaker move) throws AssertionError,
            UnsupportedOperationException, IllegalArgumentException {

        gameState.setWhoseTurn(0);

        Collection<Card> hand = new ArrayList<Card>();
        hand.add(Card.ARCHITECTUS);
        hand.add(Card.TURRIS);
        hand.add(Card.MACHINA);
        hand.add(Card.FORUM);
        hand.add(Card.LEGAT);
        hand.add(Card.ONAGER);

        gameState.setPlayerHand(0, hand);

        //no cards on the field
        Card[] field = new Card[Rules.NUM_DICE_DISCS];
        for(int i = 0; i < field.length; i++) {
            field[i] = Card.NOT_A_CARD;
        }

        //player has enough Sestertiis to lay cards
        gameState.setPlayerSestertii(0, 30);

        gameState.setActionDice(new int[] {1,4,5});

        //===================== test1 ======================
        move.placeCard(Card.ARCHITECTUS, Rules.DICE_DISC_1);

        //the correct amount of money should be deducted
        //game removed from hand and get placed on the field
        assert(gameState.getPlayerSestertii(0) == 27);
        assert(gameState.getPlayerHand(0).size() == 5);
        assert(!gameState.getPlayerHand(0).contains(Card.ARCHITECTUS));

        field = gameState.getPlayerCardsOnDiscs(0);
        assert(field[0] == Card.ARCHITECTUS);

        //if you lay a building card now, it should still cost you money
        move.placeCard(Card.FORUM, Rules.DICE_DISC_3);
        assert(gameState.getPlayerSestertii(0) == 22);
        assert(gameState.getPlayerHand(0).size() == 4);
        assert(!gameState.getPlayerHand(0).contains(Card.FORUM));

        field = gameState.getPlayerCardsOnDiscs(0);
        assert(field[2] == Card.FORUM);

        //activate Architectus
        ArchitectusActivator activator = (ArchitectusActivator) move.chooseCardToActivate(Rules.DICE_DISC_1);
        // The activation takes no parameters and can now be completed
        activator.complete();

        //should be free to lay card now
        move.placeCard(Card.MACHINA, Rules.DICE_DISC_2);

        //the money should not change
        assert(gameState.getPlayerSestertii(0) == 22);
        assert(gameState.getPlayerHand(0).size() == 3);
        assert(!gameState.getPlayerHand(0).contains(Card.MACHINA));

        field = gameState.getPlayerCardsOnDiscs(0);
        assert(field[1] == Card.MACHINA);

        //covered the forum
        move.placeCard(Card.TURRIS, Rules.DICE_DISC_3);
        assert(gameState.getPlayerSestertii(0) == 22);
        assert(gameState.getPlayerHand(0).size() == 2);
        assert(!gameState.getPlayerHand(0).contains(Card.TURRIS));

        field = gameState.getPlayerCardsOnDiscs(0);
        assert(field[2] == Card.TURRIS);

        //forum should be in the discard pile
        List<Card> discard = gameState.getDiscard();
        assert(discard.contains(Card.FORUM));

        //laying character card should still cost you money
        move.placeCard(Card.LEGAT, Rules.DICE_DISC_5);
        assert(gameState.getPlayerSestertii(0) == 17);
        assert(gameState.getPlayerHand(0).size() == 1);
        assert(!gameState.getPlayerHand(0).contains(Card.LEGAT));

        field = gameState.getPlayerCardsOnDiscs(0);
        assert(field[4] == Card.LEGAT);

        //Activation is not ended when something other than placing a building occurs
        move.placeCard(Card.ONAGER, Rules.DICE_DISC_6);
        assert(gameState.getPlayerSestertii(0) == 17);
        assert(gameState.getPlayerHand(0).isEmpty());
        assert(!gameState.getPlayerHand(0).contains(Card.ONAGER));

        field = gameState.getPlayerCardsOnDiscs(0);
        assert(field[5] == Card.ONAGER);
    }
}
