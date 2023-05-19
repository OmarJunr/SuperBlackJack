import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener{

   Game game;

   public ActionHandler(Game game) {
      this.game = game;
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      String command = e.getActionCommand();

      game.removeButtons();

      switch(command) {
      case "start":
         game.titleToGame();
         break;
      case "exit":
         System.exit(0);
         break;
      case "1":
         if(game.situation.equals("playerTurn")) {
            game.playerTurn();
         }
         else if(game.situation.equals("playerNatural")) {
            game.dealerOpen();
         }
         else if(game.situation.equals("dealerTurnContinue")) {
            game.dealerTurn();
         }
         else if(game.situation.equals("gameFinished")) {
            game.resetEverything();
         }
         break;
      case "2":
         if(game.situation.equals("playerTurn")) {
            game.dealerOpen();
         }
         else if(game.situation.contentEquals("gameFinished")) {
            game.gameToTitle();
         }
         break;
      }
   }
}