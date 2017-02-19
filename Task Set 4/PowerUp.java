import java.awt.*;
public class PowerUp extends ImageSprite
{
	static Image image;
	BBeanie app;
	boolean active = true;

	public PowerUp(BBeanie a, int x, int y)
	{
		super(image, x, y);
		app = a;
	}

	public static void setStaticImage(Image b)
	{
		image = b;
	}

	public boolean isActive()
	{
		return active;
	}
	public void hit()
	{
		active = false;
	}

	public void drawSprite(Graphics gr)
	{
		if (isActive())
			gr.drawImage(image,(int)x-app.vleft, (int)y, app);
	}
}