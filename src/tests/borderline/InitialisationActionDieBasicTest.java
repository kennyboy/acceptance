package tests.borderline;

import framework.Test;
import framework.cards.Card;
import framework.interfaces.GameState;
import framework.interfaces.MoveMaker;
import javax.naming.OperationNotSupportedException;
import java.util.*;
import framework.Rules;
import framework.cards.*;
/**
 * Testing the basic mechanics of victory point addition and removal.
 * @author Damon (Stacey damon.stacey)
 */
public class InitialisationActionDieBasicTest extends Test {

   @Override
   public String getShortDescription() {
      return "Checking all action dice can be set and recieved accurately..";
   }

   @Override
   public void run(GameState gameState, MoveMaker move)
                                          throws AssertionError,
                                          UnsupportedOperationException,
                                          IllegalArgumentException {

      /*
         public int[] getActionDice ();
         
         public void setActionDice (int[] dice);
      */
   }
}
