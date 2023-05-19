import javax.swing.ImageIcon;

public class Cards {

	ImageIcon front = new ImageIcon();
	ImageIcon spade[] = new ImageIcon[14];
	ImageIcon heart[] = new ImageIcon[14];
	ImageIcon club[] = new ImageIcon[14];
	ImageIcon diamond[] = new ImageIcon[14];

	public Cards() {
		
		front = new ImageIcon(getClass().getClassLoader().getResource("front.png"));

		// SPADE
		for(int num=1; num<14; num++) {
			spade[num]  = new ImageIcon(getClass().getClassLoader().getResource(num + "S.png"));
		}
		
		// HEART
		for(int num=1; num<14; num++) {
			heart[num]  = new ImageIcon(getClass().getClassLoader().getResource(num + "H.png"));
		}
		
		// CLUB
		for(int num=1; num<14; num++) {
			club[num]  = new ImageIcon(getClass().getClassLoader().getResource(num + "C.png"));
		}
		
		// DIAMOND
		for(int num=1; num<14; num++) {
			diamond[num]  = new ImageIcon(getClass().getClassLoader().getResource(num + "D.png"));
		}					
	}
}