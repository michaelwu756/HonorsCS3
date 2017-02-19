package members;

import engine.*;
import ui.*;
import java.awt.*;
import java.awt.image.*;

public class Money extends DrawnObject
{
	private static int LIFETIME=20+GamePanel.MONEY_BASE_LIFE;
	private static int FADE_LIFE=10;
	
	private static Image img;
	private int timesMoved;
	private double dx,dy,theta,speed;
	public Money(double x,double y)
	{
		super(x,y);
		timesMoved=0;
		speed=Math.random()*4+3;
		theta=Math.random()*Math.PI*2;
		dx=speed*Math.cos(theta);
		dy=-speed*Math.sin(theta);
	}
	public static void setStaticImage(Image im)
	{
		img = im;
	}
	public void move()
	{
		super.moveX(dx);
		super.moveY(dy);
		timesMoved++;
	}
	public boolean active()
	{
		return (timesMoved<LIFETIME);
	}
	public int life()
	{
		return timesMoved;
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		if (timesMoved>LIFETIME-FADE_LIFE)
		{
			BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			Graphics tempG = bi.getGraphics();
			tempG.drawImage(img, 0, 0, null);
			float scale[] = {1f,1f,1f,1f};
			scale[3]=(float)(LIFETIME-timesMoved)/(float)FADE_LIFE;
			RescaleOp rop = new RescaleOp(scale,new float[4], null);
			g2d.drawImage(bi, rop, (int)x-img.getWidth(null)/2, (int)y-img.getHeight(null)/2);
		}
		else
			g2d.drawImage(img,(int)x-img.getWidth(null)/2, (int)y-img.getHeight(null)/2,null);
	}
}