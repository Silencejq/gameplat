package shijiaqi.gameplatform;
import javax.swing.*;

public class framestart {
	public static void start(JFrame a,int b,int c,String d) {
		a.setLocation(320,180);
		a.setTitle(d);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		a.setSize(b, c);
		a.setVisible(true);
	}
	public static void start(JFrame a,int b,int c,String d,int n,int m) {
		a.setLocation(n,m);
		a.setTitle(d);
		a.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		a.setSize(b, c);
		a.setVisible(true);
	}
}
