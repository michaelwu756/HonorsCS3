import java.awt.*;
import java.awt.geom.*;

public class Target extends DrawnObject
{
	private static Color FILL=Color.GRAY;
	private static Color OUTLINE=Color.BLACK;
	private double s;
	private RectangularCollisionBox hitBox;
	public Target(double x, double y, double sideLength)
	{
		super(x,y);
		s=sideLength;
		hitBox=new RectangularCollisionBox((int)x,(int)y,(int)sideLength, (int)sideLength);
	}
	
	public RectangularCollisionBox hitBox()
	{
		return hitBox;
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
	
	public void newRandomLocation()
	{
		int edge=Eskiv.BORDER_WIDTH+(int)(s/2);
		x= Math.random()*(Eskiv.VWIDTH-2*edge-Eskiv.LEFT_PANEL_WIDTH)+edge+Eskiv.LEFT_PANEL_WIDTH; 
		y= Math.random()*(Eskiv.VHEIGHT-2*edge)+edge;
		hitBox.updateX((int)x);
		hitBox.updateY((int)y);
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		Rectangle2D shape = new Rectangle2D.Double(x-s/2, y-s/2, s, s);
		g2d.setColor(FILL);
		g2d.fill(shape);
		g2d.setColor(OUTLINE);
		g2d.draw(shape);
	}
}