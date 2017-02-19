package members;

import engine.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class PlayerBox extends DrawnObject
{
	private static Color FILL=Color.RED;
	private static Color OUTLINE=Color.BLACK;
	public static int FADE_LIFE=50;	
	private static int OUTLINE_BORDER=3;
	private int deadTime;
	private double initX, initY;
	private double s;
	private boolean dead;
	private RectangularCollisionBox hitBox;
	
	public PlayerBox(double x, double y, double sideLength)
	{
		super(x,y);
		initX=x;
		initY=y;
		s=sideLength;
		dead = false;
		deadTime = -1;
		hitBox=new RectangularCollisionBox((int)x,(int)y,(int)s,(int)s);
	}
	public RectangularCollisionBox hitBox()
	{
		return hitBox;
	}
	public double getSideLength()
	{
		return s;
	}
	public void moveX(double dx)
	{
		super.moveX(dx);
		hitBox.updateX((int)x);
	}
	public void moveY(double dy)
	{
		super.moveY(dy);
		hitBox.updateY((int)y);
	}
	public boolean isDead()
	{
		return dead;	
	}
	public void setDead()
	{
		dead=true;
		deadTime=0;
	}
	public void resetLocation()
	{
		x=initX;
		y=initY;
		hitBox.updateX((int)x);
		hitBox.updateY((int)y);
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		Rectangle2D shape = new Rectangle2D.Double(0, 0, s, s);
		
		BufferedImage bi = new BufferedImage((int)s, (int)s, BufferedImage.TYPE_INT_ARGB);
		Graphics2D tempG = (Graphics2D)bi.getGraphics();
		tempG.setColor(OUTLINE);
		tempG.fill(shape);
		shape.setFrame(OUTLINE_BORDER, OUTLINE_BORDER, (s-2*OUTLINE_BORDER), (s-2*OUTLINE_BORDER));
		tempG.setColor(FILL);
		tempG.fill(shape);
		float scale[] = {1f,1f,1f,1f};
		if (deadTime!=-1)
		{
			scale[3]=(float)(FADE_LIFE-deadTime)/(float)FADE_LIFE;
			deadTime++;
		}
		if (deadTime>FADE_LIFE)
		{
			deadTime=-1;
			dead=false;
			resetLocation();
		}
		RescaleOp rop = new RescaleOp(scale,new float[4], null);
		g2d.drawImage(bi, rop,(int)(x-s/2), (int)(y-s/2));
	}
}