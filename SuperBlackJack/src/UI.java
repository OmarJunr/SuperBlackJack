import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UI extends JFrame{

   // Tela Inicial UI
   PaintPanel titlePanel;
   JButton startB,exitB;

   // MESA UI
   JPanel table;
   JPanel dealerPanel;
   JPanel playerPanel;
   JLabel playerCardLabel[] = new JLabel[6];
   JLabel dealerCardLabel[] = new JLabel[6];

   // Message UI
   JTextAreaPlus messageText;
   JPanel scorePanel;
   JLabel playerScore, dealerScore;
   JPanel buttonPanel = new JPanel();
   JButton button[] = new JButton[6];
   Game game;

   int cardWidth = 150;
   int cardHeight = 212;


   public UI(Game game) {
      this.game = game;

      this.setTitle("SUPER BlACKJACK");
      this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("ui_baralho.png")).getImage());
      this.setSize(1200,800);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLayout(null);
      this.getContentPane().setBackground(new Color(0,81,0));

      createTitleScreen();
      createTableUI();
      createOtherUI();
      this.setVisible(true);
   }

   public void createTitleScreen() {
	  // Painel do SUPER BLACK JACK E LOGO
      titlePanel = new PaintPanel(this);
      titlePanel.setBounds(0,0,1200,600);
      titlePanel.setOpaque(false);
      this.add(titlePanel);

      // Botão JOGAR
      startB = new JButton("JOGAR");
      startB.setBounds(500,620,200,50);
      startB.setBorder(null);
      startB.setBackground(null);
      startB.setFocusPainted(false);
      startB.setForeground(Color.white);
      startB.setFont(new Font("Book Antiqua", Font.PLAIN,36));
      startB.setVisible(false);
      startB.addActionListener(game.aHandler);
      startB.setActionCommand("start");
      startB.setContentAreaFilled(false);
      this.add(startB);

      // Botão SAIR
      exitB = new JButton("SAIR");
      exitB.setBounds(500,670,200,50);
      exitB.setBorder(null);
      exitB.setBackground(null);
      exitB.setFocusPainted(false);
      exitB.setForeground(Color.white);
      exitB.setFont(new Font("Book Antiqua", Font.PLAIN,36));
      exitB.setVisible(false);
      exitB.addActionListener(game.aHandler);
      exitB.setActionCommand("exit");
      exitB.setContentAreaFilled(false);
      this.add(exitB);

      titlePanel.timer.start();
   }

   public void createTableUI() {
	  // Mesa 
      table = new JPanel();
      table.setBackground(new Color(0,81,0));
      table.setBounds(50,50,850,600);
      table.setLayout(null);
      table.setVisible(false);

      // Cartas do Dealer
      dealerPanel = new JPanel();
      dealerPanel.setBounds(100,120,cardWidth*5,cardHeight);
      dealerPanel.setBackground(null);
      dealerPanel.setOpaque(false);
      dealerPanel.setLayout(new GridLayout(1,5));
      dealerPanel.setVisible(false);
      this.add(dealerPanel);

      // Cartas do Jogador
      playerPanel = new JPanel();
      playerPanel.setBounds(100,370,cardWidth*5,cardHeight);
      playerPanel.setOpaque(false);
      playerPanel.setLayout(new GridLayout(1,5));
      playerPanel.setVisible(false);
      this.add(playerPanel);

      for(int i = 1; i < 6; i++) {
         playerCardLabel[i] = new JLabel();
         playerCardLabel[i].setVisible(false);
         playerPanel.add(playerCardLabel[i]);
      }
      for(int i = 1; i < 6; i++) {
         dealerCardLabel[i] = new JLabel();
         dealerCardLabel[i].setVisible(false);
         dealerPanel.add(dealerCardLabel[i]);
      }

      dealerScore = new JLabel();
      dealerScore.setBounds(50,10,200,50);
      dealerScore.setForeground(Color.white);
      dealerScore.setFont(new Font("Times New Roman", Font.PLAIN, 42));
      table.add(dealerScore);

      playerScore = new JLabel();
      playerScore.setBounds(50,540,200,50);
      playerScore.setForeground(Color.white);
      playerScore.setFont(new Font("Times New Roman", Font.PLAIN, 42));
      table.add(playerScore);

      this.add(table);
   }

   public void createOtherUI() {
      messageText = new JTextAreaPlus();
      messageText.setBounds(230,680,720,100);
      messageText.setBackground(null);
      messageText.setForeground(Color.white);
      messageText.setFont(new Font("Times New Roman", Font.PLAIN, 40));
      messageText.setEditable(false);
      this.add(messageText);

      buttonPanel = new JPanel();
      buttonPanel.setBounds(920,340,200,300);
      buttonPanel.setBackground(null);
      buttonPanel.setLayout(new GridLayout(6,1));
      this.add(buttonPanel);

      for(int i = 1; i < 6; i++) {
         button[i] = new JButton();
         button[i].setBackground(null);
         button[i].setForeground(Color.white);
         button[i].setFocusPainted(false);
         button[i].setBorder(null);
         button[i].setFont(new Font("Times New Roman", Font.PLAIN, 42));
         button[i].addActionListener(game.aHandler);
         button[i].setActionCommand(""+i);
         button[i].setVisible(false);
         buttonPanel.add(button[i]);
      }
   }
}