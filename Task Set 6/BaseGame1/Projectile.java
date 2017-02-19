import java.awt.*;
import java.awt.geom.*;

public class Projectile extends DrawnObject
{	
	private static int X_AXIS = 0;
	private static int Y_AXIS = 1;
	private static Color FILL=Color.BLUE;
	private static Color OUTLINE=Color.BLACK;
	public static double DIAMETER=10;
	public static double SPEED = 0.3;
	
	private double d,speed;
	private int axis;
	private CircularCollisionBox hitBox;
	public Projectile(double x, double y)
	{
		super(x,y);
		d=DIAMETER;
		speed=SPEED;
		speed*=(int)(Math.random()*2)*2-1;
		axis = (int)(Math.random()*2);
		hitBox=new CircularCollisionBox((int)x,(int)y,(int)d);
	}
	public CircularCollisionBox hitBox()
	{
		return hitBox;
	}
	public void move()
	{
		if (axis == X_AXIS)
		{
			if (x+speed>=Eskiv.VWIDTH-Eskiv.BORDER_WIDTH || x+speed<=Eskiv.BORDER_WIDTH+Eskiv.LEFT_PANEL_WIDTH)
				speed=-speed;
			super.moveX(speed);
			hitBox.updateX((int)x);
		}
		else
		{
			if (y+speed>=Eskiv.VHEIGHT-Eskiv.BORDER_WIDTH || y+speed<=Eskiv.BORDER_WIDTH)
				speed=-speed;
			super.moveY(speed);
			hitBox.updateY((int)y);
		}
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		Ellipse2D shape = new Ellipse2D.Double(x-d/2, y-d/2, d, d);
		g2d.setColor(FILL);
		g2d.fill(shape);
		g2d.setColor(OUTLINE);
		g2d.draw(shape);
	}
}