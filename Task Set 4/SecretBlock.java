import java.awt.*;

public class SecretBlock extends ImageSprite
{
	static Image img;
	BBeanie app;
	private boolean found;
	int row,col;
	
	public SecretBlock(BBeanie a, int x, int y)
	{
		super(img, x*Grid.CELLSIZE-2, y*Grid.CELLSIZE-2);
		app = a;
		col=x;
		row=y;
		found=false;
	}
	
	public void setFound(boolean input)
	{
		found=input;
	}
	
	public boolean found()
	{
		return found;
	}
	
	public int row()
	{return row;}
	public int col()
	{return col;}
	
	public static void setStaticImage(Image im)
	{
		img = im;
	}

	public void drawSprite(Graphics gr)
	{
		gr.drawImage(image,(int)x-app.vleft, (int)y, app);
	}

}