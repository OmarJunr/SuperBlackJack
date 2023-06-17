import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class CardGroupPanel extends JPanel {

   // Esta classe estende o JPanel e criará um painel que exibe as imagens de várias cartas,
   // armazenados em uma instância de CardGroup, um ao lado do outro.  
   // Além disso, o total da carta é exibido usando a regra de subtração padrão do Ás.

   CardGroupPanel(CardGroup cardGroup, int left, int top, int width, int height, int gap) {

      int numCards = cardGroup.cards.size();

      setBounds(left, top, 35 + numCards * (width + gap), height);
      setLayout(null);
      setOpaque(false); // for transparent background

      int total = cardGroup.getTotalValue();
      if (total > 21 && cardGroup.getNumAces() > 0)
         total -= 10;

      JLabel playerScoreLbl = new JLabel((total == 21 ? "BJ" : total) + "");
      playerScoreLbl.setForeground(Color.WHITE);
      playerScoreLbl.setFont(new Font("Lucida Grande", Font.BOLD, 20));
      playerScoreLbl.setVerticalAlignment(SwingConstants.CENTER);
      playerScoreLbl.setHorizontalAlignment(SwingConstants.RIGHT);
      playerScoreLbl.setBounds(0, 0, 30, height);
      add(playerScoreLbl);

      for (int i = 0; i < numCards; i++) {
         ImagePanel cardImagePanel = new ImagePanel(cardGroup.cards.get(i).getFileName());
         cardImagePanel.setBounds(35 + i * (width + gap), 0, width, height);
         add(cardImagePanel);
      }
   }
}