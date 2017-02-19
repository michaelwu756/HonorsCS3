package members;

import engine.*;
import java.awt.*;
import java.awt.geom.*;

public class CursorTarget extends DrawnObject

{
	private static final int PRIORITY = 2;
	private static final int SIZE = 6;
	
	private Line2D l1,l2;
	public CursorTarget()
	{
		super(0,0,PRIORITY);
		l1 = new Line2D.Double(0,0,0,0);
		l2 = new Line2D.Double(0,0,0,0);
	}
	public void setPos(int x, int y)
	{
		this.x=x;
		this.y=y;
		l1.setLine(x-SIZE/2,y,x+SIZE/2,y);
		l2.setLine(x,y-SIZE/2,x,y+SIZE/2);
	}
	public void paint(Graphics g)
	{
		Graphics2D tempG = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);
		tempG.setComposite(ac);
		tempG.setColor(Color.GREEN);
		tempG.draw(l1);
		tempG.draw(l2);
	}
}