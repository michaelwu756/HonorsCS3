package members;

import engine.*;
import ui.*;
import java.awt.*;
import java.awt.image.*;

public class Kanyes extends DrawnObject
{	
	private static int FRAME_DELAY=14;
	public static double DIAMETER=60;
	public static double SPEED = 5.0;
	
	private static Image img;
	private static Image img2;
	
	private double d,dx,dy,angle;
	private CircularCollisionBox hitBox;
	private int timesDrawn;
	public Kanyes(double x, double y, double theta)
	{
		super(x,y);
		d=DIAMETER;
		angle=theta%(2*Math.PI);
		dx=SPEED*Math.cos(angle);
		dy=SPEED*Math.sin(angle);
		timesDrawn=0;
		hitBox=new CircularCollisionBox((int)x,(int)y,(int)d);
	}
	public static void setStaticImage(Image im, Image im2)
	{
		img = im;
		img2 = im2;
	}
	public Kanyes(Indicator nextLoc)
	{
		this(nextLoc.xValue(),nextLoc.yValue(),nextLoc.getTheta());
	}
	public CircularCollisionBox hitBox()
	{
		return hitBox;
	}
	public void move()
	{
		if (x+dx>=GamePanel.VWIDTH-GamePanel.BORDER_WIDTH-DIAMETER/2 || x+dx<=GamePanel.BORDER_WIDTH+GamePanel.LEFT_PANEL_WIDTH+DIAMETER/2)
		{
			dx=-dx;
			angle= (Math.PI-angle)%(2*Math.PI);
		}
		super.moveX(dx);
		hitBox.updateX((int)x);
		
		if (y+dy>=GamePanel.VHEIGHT-GamePanel.BORDER_WIDTH-DIAMETER/2 || y+dy<=GamePanel.BORDER_WIDTH+DIAMETER/2)
		{
			dy=-dy;
			angle = 2*Math.PI-angle;
		}
		super.moveY(dy);
		hitBox.updateY((int)y);
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		if ((timesDrawn/FRAME_DELAY)%2==0)
			g2d.drawImage(img, (int)x-img.getWidth(null)/2, (int)y-img.getHeight(null)/2, null);
		else g2d.drawImage(img2, (int)x-img2.getWidth(null)/2, (int)y-img2.getHeight(null)/2, null);
		timesDrawn++;
	}
}