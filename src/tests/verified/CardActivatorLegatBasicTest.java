package tests.verified;

import java.util.ArrayList;
import java.util.Collection;

import framework.Test;
import framework.cards.Card;
import framework.interfaces.GameState;
import framework.interfaces.MoveMaker;

/**
 * Testing the basic mechanics of Legat.
 *
 * @author Karla Burnett (karla.burnett)
 */
public class CardActivatorLegatBasicTest extends Test {

    @Override
    public String getShortDescription() {
        return "Checking the basic mechanics of Legat";
    }

    @Override
    public void run(GameState gameState, MoveMaker move)
                                          throws AssertionError,
                                          UnsupportedOperationException,
                                          IllegalArgumentException {
        Card [] ourSide = {Card.NOT_A_CARD,
                                Card.NOT_A_CARD,
                                Card.NOT_A_CARD,
                                Card.NOT_A_CARD,
                                Card.NOT_A_CARD,
                                Card.NOT_A_CARD,
                                Card.NOT_A_CARD};
        
        Card [] opponentSideInitial = {Card.NOT_A_CARD,
                Card.NOT_A_CARD,
                Card.NOT_A_CARD,
                Card.NOT_A_CARD,
                Card.NOT_A_CARD,
                Card.NOT_A_CARD,
                Card.NOT_A_CARD};

        // Place 5 cards on the opponent's side
        gameState.setPlayerCardsOnDiscs(0, ourSide);
        gameState.setPlayerCardsOnDiscs(1, opponentSideInitial);
        
        // Set up the player stats
        gameState.setPlayerVictoryPoints(0, 10);
        gameState.setPlayerVictoryPoints(1, 10);
        gameState.setPlayerSestertii(0, 5);
        gameState.setPlayerSestertii(1, 100);
        
        List<Card> hand = new ArrayList<hand>();
        hand.add(Card.LEGIONARIUS);
        hand.add(Card.AESCULAPINUM);
        hand.add(Card.CONSUL);
        hand.add(Card.ESSEDUM);
        hand.add(Card.MACHINA);
        gameState.setPlayerCards(1, hand);

        // Set up the game state for the test
        gameState.setWhoseTurn(0);
        gameState.setActionDice(new int [] {3, 3, 4});

        Collection<Card> hand = new ArrayList<Card>();
        hand.add(Card.LEGAT);
        gameState.setPlayerHand(0, hand);

        // Place the Legat on disc 3 and activate it
        move.placeCard(Card.LEGAT, 3);
        move.chooseCardToActivate(3).complete();
//        for (Card currentCard : gameState.getPlayerHand(0)) {
//        	System.out.println(currentCard);
//        }


        // Check that player 0 has gained 7 victory points, but player
        // 1's score has not changed
        assert(gameState.getPlayerVictoryPoints(0) == 17);
        assert(gameState.getPlayerVictoryPoints(1) == 10);
        assert(gameState.getPlayerSestertii(0) == 0);
        assert(gameState.getPlayerSestertii(1) == 100);
        
        move.endTurn();
        move.layCard(Card.LEGIONARIUS, Rules.DICE_DISC_1);
        move.layCard(Card.AESCULAPINUM, Rules.DICE_DISC_2);
        move.layCard(Card.CONSUL, Rules.DICE_DISC_4);
        move.layCard(Card.ESSEDUM, Rules.DICE_DISC_6);
        move.layCard(Card.MACHINA, Rules.DICE_DISC_7);
        move.endTurn();
        
        assert(gameState.getPlayerVictoryPoints(0) == 11);
        assert(gameState.getPlayerVictoryPoints(1) == 3);
        assert(gameState.getPlayerSestertii(0) == 0);
        assert(gameState.getPlayerSestertii(1) == 100 - 4 - 5 - 3 - 6 - 4);

        // Activate the Legat again
        move.chooseCardToActivate(3).complete();

        // Check that player 0 has gained 2 victory points, but player
        // 1's score has not changed
        assert(gameState.getPlayerVictoryPoints(0) == 19);
        assert(gameState.getPlayerVictoryPoints(1) == 10);
        assert(gameState.getPlayerSestertii(0) == 0);
        assert(gameState.getPlayerSestertii(1) == 0);
    }
}
