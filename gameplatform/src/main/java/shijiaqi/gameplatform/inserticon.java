package shijiaqi.gameplatform;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class inserticon {
	public ImageIcon insert(String url,JComponent component,double a,double b) {
		ImageIcon icon = new ImageIcon(getClass().getResource(url));
		int width=(int)(component.getWidth()*a);
		int height=(int)(component.getHeight()*b);
		Image pic=icon.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT);
		icon.setImage(pic);
		return icon;
	}
	public ImageIcon insert(String url,int a,int b) {
		ImageIcon icon = new ImageIcon(getClass().getResource(url));
		Image pic=icon.getImage().getScaledInstance(a,b,Image.SCALE_DEFAULT);
		icon.setImage(pic);
		return icon;
	}
}
