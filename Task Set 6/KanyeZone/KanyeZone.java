import ui.*;
import members.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.applet.*;

public class KanyeZone extends JApplet
{
	private Image backbuffer;
	private Graphics backg;	
	public void init()
	{
		Money.setStaticImage(getImage(getCodeBase(),"Images/Money.png"));
		Kanyes.setStaticImage(getImage(getCodeBase(),"Images/Kanye1.png"),
			getImage(getCodeBase(),"Images/Kanye2.png"));
		Indicator.setStaticImage(getImage(getCodeBase(),"Images/SilhouetteBG.png"),
			null);
		GamePanel app = new GamePanel();
		setContentPane(app);
		setVisible(true);
		app.start();
		
		backbuffer = createImage(GamePanel.VWIDTH, GamePanel.VHEIGHT);//new BufferedImage(GamePanel.VWIDTH,GamePanel.VHEIGHT, BufferedImage.TYPE_INT_ARGB_PRE);
		backg = backbuffer.getGraphics();
		
		setSize(app.getPreferredSize());
	}
	public void update( Graphics g )
	{
		paint(backg);
		g.drawImage(backbuffer, 0, 0, this);
	}
	
	public static void main(String args[])
	{
		try
		{
			Money.setStaticImage(ImageIO.read(ClassLoader.getSystemResource("Images/Money.png")));
			Kanyes.setStaticImage(ImageIO.read(ClassLoader.getSystemResource("Images/Kanye1.png")),
				ImageIO.read(ClassLoader.getSystemResource("Images/Kanye2.png")));
			Indicator.setStaticImage(ImageIO.read(ClassLoader.getSystemResource("Images/SilhouetteBG.png")),
				null);
		}catch (IOException e)
		{
			e.printStackTrace();
		}
		JFrame frame=new JFrame();
		GamePanel app=new GamePanel();
		frame.getContentPane().add(app);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.start();
	}
}