package members;

import engine.*;
import java.awt.*;
import java.awt.geom.*;

public class Turret extends DrawnObject
{
	private static final int PRIORITY = 4;
	private static final int SIZE = 30;
	private static final int AMMO=10;
	
	private int ammo;
	private Ellipse2D shape;
	public Turret(double x, double y)
	{
		super(x,y,PRIORITY);
		ammo=AMMO;
		shape = new Ellipse2D.Double(x-SIZE/2,y-SIZE/2,SIZE,SIZE);
	}
	public void addAmmo()
	{
		addAmmo(1);
	}
	public void addAmmo(int a)
	{
		ammo+=a;
	}
	public boolean shoot()
	{
		if (ammo==0)
			return false;
		ammo--;
		return true;
	}
	public void paint(Graphics g)
	{
		Graphics2D tempG = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);
		tempG.setComposite(ac);
		tempG.setColor(Color.PINK);
		tempG.fill(shape);
		tempG.setColor(Color.BLUE);
		drawCenteredString(String.valueOf(ammo),(int)x,(int)y,tempG);
	}
	public void drawCenteredString(String s, int xi, int yi, Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int xf = xi-(fm.stringWidth(s))/ 2;
		int yf = yi+(fm.getAscent()- fm.getDescent())/2;
		g.drawString(s, xf, yf);
	}
}