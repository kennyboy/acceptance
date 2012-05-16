package tests.unverified;
 
 
import framework.Rules;
import framework.Test;
import framework.cards.Card;
import framework.interfaces.GameState;
import framework.interfaces.MoveMaker;
import framework.interfaces.activators.ForumActivator;
import framework.interfaces.activators.TelephoneBoxActivator;
 
/**
 * Testing for a simple scenario with the Telephone Box
 * @author Nicholas Higgins (nicholas.higgins)
 * @author Calvin Tam (calvin.tam)
 *
 */
public class TelephoneBoxTest_A extends Test {
 
	@Override
	public String getShortDescription() {
		return "Tests for a simple scenario with the Telephone Box";
	}
 
	@Override
	public void run(GameState gameState, MoveMaker move) throws AssertionError,
			UnsupportedOperationException, IllegalArgumentException {
 
		// Set up the player stats
        gameState.setPlayerVictoryPoints(0, 10);
        gameState.setPlayerVictoryPoints(1, 10);
        gameState.setPlayerSestertii(0, 10);
        gameState.setPlayerSestertii(1, 10);
 
        // Set up the game state for the test
        gameState.setWhoseTurn(0);
        gameState.setActionDice(new int [] {1, 1, 2});
 
        Card [] playerField_0 = {	Card.FORUM,
                					Card.BASILICA,
                					Card.FORUM,
                					Card.GLADIATOR,
                					Card.GLADIATOR,
                					Card.ONAGER,
                					Card.TELEPHONEBOX,
        };
        gameState.setPlayerCardsOnDiscs(0, playerField_0);
 
        Card [] playerField_1 = {	Card.NOT_A_CARD,
        							Card.BASILICA,
        							Card.FORUM,
        							Card.NOT_A_CARD,
        							Card.NOT_A_CARD,
        							Card.NOT_A_CARD,
        							Card.TELEPHONEBOX,
        };
        gameState.setPlayerCardsOnDiscs(1, playerField_1);
 
 
        // Now activate the Forum on Disc 1
        // and give it a 2 so the player earns 2 points from the Forum
        // and the Basilica will give 2 more points
        // as there is one Forum activated and the other not activated
        // Player 0 has 2 + 1 = 3
        ForumActivator f = (ForumActivator) move.chooseCardToActivate(1);
        f.chooseActionDice(1);
        f.complete();
 
        // Test that Player 0 has actually gained 3 VP
        assert(gameState.getPlayerVictoryPoints(0) == 10 + 3);
 
        move.endTurn();
 
        // Player 1's turn
        // Player 1 has 4 blank spaces
 
        //************CHANGE TO 4 FROM 3 **************//
        assert(gameState.getPlayerVictoryPoints(1) == 10 - 4);
        move.endTurn();
 
        // Player 0's turn
        // Replace Forum on Disc_1 with a Centurio
        Card[] playerField_0_updated = {
        		Card.CENTURIO,
        		Card.BASILICA,
				Card.FORUM,
				Card.GLADIATOR,
				Card.GLADIATOR,
				Card.ONAGER,
				Card.TELEPHONEBOX,
        };
        gameState.setPlayerCardsOnDiscs(0, playerField_0_updated);
        move.endTurn();
 
        // Player 1's turn
        // do nothing
        move.endTurn();
 
        // Player 0's turn
        // Activate the Telephone Box and move the Forum on Disc 1 back 4 turns
        gameState.setActionDice(new int[] {1,1,4});
        playerField_0 = gameState.getPlayerCardsOnDiscs(0) ;
        assert(playerField_0[0] == Card.CENTURIO);
        TelephoneBoxActivator t = (TelephoneBoxActivator) move.chooseCardToActivate(Rules.BRIBE_DISC);
        t.shouldMoveForwardInTime(false);
        t.setSecondDiceUsed(4);
        t.chooseDiceDisc(1);
        t.complete();
 
 
        // Timetravel has occurred
        // Forum on Disc 1 should have been replaced by Centurio
        // but activating the Centurio does not cause a time paradox
        assert(!gameState.isGameCompleted());
        move.endTurn();
	}
}
