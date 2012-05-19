package tests.unverified;

import framework.Test;
import framework.cards.Card;
import framework.interfaces.GameState;
import framework.interfaces.MoveMaker;
import framework.interfaces.activators.ArchitectusActivator;
import framework.interfaces.activators.ScaenicusActivator;

import java.util.ArrayList;
import java.util.Collection;


/**
 *
 * Test basic activation of Scaenicus on a Architectus.
 *
 * @author Daniel Morton
 *
 */

public class CardActivatorScaenicusCopyArchitectusTest extends Test {
    @Override
    public String getShortDescription() {
        return "Testing Scaenicus can copy Architectus";
    }

    @Override
    public void run(GameState gameState, MoveMaker move) throws AssertionError, UnsupportedOperationException, IllegalArgumentException {
        gameState.setWhoseTurn(0);
        gameState.setPlayerVictoryPoints(0, 10);
        gameState.setPlayerVictoryPoints(1, 15);
        gameState.setPlayerSestertii(0,10);
        gameState.setPlayerSestertii(1,10);
        Collection<Card> handOfCards = new ArrayList<Card>();
        handOfCards.add(Card.PRAETORIANUS);
        handOfCards.add(Card.SICARIUS);
        handOfCards.add(Card.CONSILIARIUS);
        handOfCards.add(Card.LEGAT);
        gameState.setPlayerHand(0,handOfCards);

        //Test that the current pool of points is correct
        assert(gameState.getPoolVictoryPoints() == 11);

        //Place cards on player discs
        Card[] cardsOnCurrentPlayerDisc = {Card.ARCHITECTUS,
                Card.SCAENICUS,
                Card.NOT_A_CARD,
                Card.NOT_A_CARD,
                Card.NOT_A_CARD,
                Card.NOT_A_CARD,
                Card.NOT_A_CARD};
        gameState.setPlayerCardsOnDiscs(0, cardsOnCurrentPlayerDisc);


        //Set the action dice to activate card
        gameState.setActionDice(new int[] {2,3,4});

        //Activate the scaenicus to mimic Architectus
        ScaenicusActivator scaenicusActivator = (ScaenicusActivator)move.chooseCardToActivate(2);
        ArchitectusActivator architectusActivator = (ArchitectusActivator)(scaenicusActivator.getScaenicusMimicTarget(1));
        architectusActivator.layCard(Card.PRAETORIANUS, 1);
        architectusActivator.layCard(Card.SICARIUS, 2);
        architectusActivator.layCard(Card.CONSILIARIUS, 3);
        architectusActivator.layCard(Card.LEGAT, 4);
        architectusActivator.complete();
        scaenicusActivator.complete();

        //Test that scaenicus has laid the cards
        assert(gameState.getPlayerCardsOnDiscs(0)[0]==Card.PRAETORIANUS);
        assert(gameState.getPlayerCardsOnDiscs(0)[1]==Card.SICARIUS);
        assert(gameState.getPlayerCardsOnDiscs(0)[2]==Card.CONSILIARIUS);
        assert(gameState.getPlayerCardsOnDiscs(0)[3]==Card.LEGAT);

        //Test that it didn't charge any Sestertii
        assert(gameState.getPlayerSestertii(0)==10);
        
        //Test that it is no longer in hand
        Collection<Card> playerHand = gameState.getPlayerHand(0);
        assert (!playerHand.contains(Card.PRAETORIANUS));
        assert (!playerHand.contains(Card.SICARIUS));
        assert (!playerHand.contains(Card.CONSILIARIUS));
        assert (!playerHand.contains(Card.LEGAT));
        
        assert (gameState.getActionDice().length == 2);
        assert (!gameState.isGameCompleted());
    }
}
