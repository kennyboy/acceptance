package tests.unverified;
 
import java.util.LinkedList;

import framework.Test;
import framework.cards.Card;
import framework.interfaces.GameState;
import framework.interfaces.MoveMaker;
import framework.interfaces.activators.TelephoneBoxActivator;

/**
 * Testing for a simple scenario with the Telephone Box
 * @author Nicholas Higgins (nicholas.higgins)
 * @author Calvin Tam (calvin.tam)
 *
 */

public class TelephoneBoxTest_B extends Test {
 
	@Override
	public String getShortDescription() {
		return "Tests for a time paradox with the Telephone Box";
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
        gameState.setActionDice(new int [] {3, 4, 5});
 
        Card [] playerField_0 = {	Card.KAT,
                					Card.SCAENICUS,
                					Card.CONSILIARIUS,
                					Card.HARUSPEX,
                					Card.GLADIATOR,
                					Card.ONAGER,
                					Card.TELEPHONEBOX,
        };
        gameState.setPlayerCardsOnDiscs(0, playerField_0);
 
        Card [] playerField_1 = {	Card.KAT,
        							Card.PRAETORIANUS,
        							Card.FORUM,
        							Card.VELITES,
        							Card.SENATOR,
        							Card.CONSUL,
        							Card.TELEPHONEBOX,
        };
        gameState.setPlayerCardsOnDiscs(1, playerField_1);
 
        move.endTurn();
 
        //Player 1
        move.endTurn();
        gameState.setActionDice(new int[] {1,1,6});
 
        //Player 0
        LinkedList<Card> hand = new LinkedList<Card>();
        hand.add(Card.LEGIONARIUS);
        gameState.setPlayerHand(0, hand);
        // Replace the Kat with a Legionarius
        move.placeCard(Card.LEGIONARIUS, 1);
 
        // Activate the Telephone Box and move the Legionarius on Disc 1 back 2 turns
        playerField_0 = gameState.getPlayerCardsOnDiscs(0) ;
        TelephoneBoxActivator t = (TelephoneBoxActivator) move.activateBribeDisc(1);
        t.shouldMoveForwardInTime(false);
        t.setSecondDiceUsed(6); //testing that the time travelling reverts to turn 0 if there have not been sufficient turns to go back
        t.chooseDiceDisc(1);
        t.complete();
 
        // Legionarius has time travelled
        // and replaced the Kat
        // but Kat cannot be replaced unless by another Kat
        // therefore a time paradox has occurred
        assert(gameState.isGameCompleted());
 
	}
}
