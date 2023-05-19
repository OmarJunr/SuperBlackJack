import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class JTextAreaPlus extends JTextArea implements ActionListener{

   int i=0;
   String text;

   Timer timer = new Timer(10, this);

   public JTextAreaPlus() {

   }

   public JTextAreaPlus(String text) {
      super(text);
   }

   public void setTextPlus(String text) {
      this.text = text;
      setText("");
      timer.start();
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      
      char character[] = text.toCharArray();
      int arrayNum = character.length;
      String s = String.valueOf(character[i]);
      append(s);
      i++;

      if(i == arrayNum){
         i = 0;
         timer.stop();
      }
   }
}