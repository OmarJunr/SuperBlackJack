import javax.swing.*;

public class MainFrame extends JFrame {

   //A janela principal exibe a imagem de fundo 
   //(observe que os limites dinâmicos são para que a imagem se estenda bem para um determinado tamanho de janela)

   MainFrame() {
      setTitle("Super Blackjack");
      setIconImage(new ImageIcon(getClass().getClassLoader().getResource("iconeBJ.png")).getImage());
      setSize(910, 710);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLayout(null);
      setResizable(false);
      
      ImagePanel bgImagePanel = new ImagePanel("background.png");
      bgImagePanel.setBounds(0, 0, this.getWidth(), this.getHeight());
      setContentPane(bgImagePanel);
   }
}