package members;

import engine.*;
import java.awt.*;
import java.awt.geom.*;

public class RestartButton extends DrawnObject
{
	private static int HEIGHT = 50;
	private static int WIDTH = 100;
	private static Color FILL = Color.WHITE;
	private static Color OUTLINE = Color.LIGHT_GRAY;
	private RectangularCollisionBox hitBox;
	private boolean pressed;
	private int shift;
	
	public RestartButton(double x, double y)
	{
		super(x,y);
		shift=0;
		pressed=false;
		hitBox = new RectangularCollisionBox((int)x,(int)y,WIDTH,HEIGHT);
	}
	public RectangularCollisionBox hitBox()
	{
		return hitBox;
	}
	public void setPressed(boolean state)
	{
		if (state==true)
		{
			pressed=true;
			shift=5;
		}
		else
		{
			pressed=false;
			shift=0;
		}
	}
	public boolean pressed()
	{
		return pressed;
	}
	public void paint(Graphics g)
	{
		Graphics2D g2d=(Graphics2D)g;
		g2d.setFont(new Font("Arial",Font.PLAIN, 24));
		g2d.setColor(OUTLINE);
		g2d.fill(new Rectangle2D.Double(x-WIDTH/2-shift, y-HEIGHT/2+shift, WIDTH, HEIGHT));
		g2d.setColor(FILL);
		g2d.fill(new Rectangle2D.Double(x-(WIDTH-10)/2-shift, y-(HEIGHT-10)/2+shift, WIDTH-10, HEIGHT-10));
		g2d.setColor(OUTLINE);
		g2d.drawString("NEW", (int)x-30-shift,(int)y+8+shift);
	}
}