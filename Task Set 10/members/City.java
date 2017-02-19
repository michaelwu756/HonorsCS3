package members;

import engine.*;
import java.awt.*;
import java.awt.geom.*;

public class City extends DrawnObject
{
	private static final int PRIORITY = 5;
	private static final int SIZE = 20;
	
	private Ellipse2D shape;
	private boolean destroyed;
	public City(double x, double y)
	{
		super(x,y,PRIORITY);
		shape = new Ellipse2D.Double(x-SIZE/2,y-SIZE/2,SIZE,SIZE);
		destroyed = false;
	}
	public void destroy()
	{
		destroyed=true;
	}
	public boolean done()
	{
		return destroyed;
	}
	public void paint(Graphics g)
	{
		Graphics2D tempG = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);
		tempG.setComposite(ac);
		tempG.setColor(Color.BLUE);
		tempG.fill(shape);
		tempG.setColor(Color.RED);
	}
}