import ui.*;

import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.applet.*;

public class WorldsHardestGame extends JApplet
{
	private Image backbuffer;
	private Graphics backg;
	public void init()
	{
		Image imgs[]=new Image[4];
		imgs[0]=getImage(getCodeBase(),"Images/Level1.png");
		imgs[1]=getImage(getCodeBase(),"Images/Level2.png");
		imgs[2]=getImage(getCodeBase(),"Images/Level3.png");
		imgs[3]=getImage(getCodeBase(),"Images/Finish.png");
		GamePanel.setTextScreenImages(imgs);
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
		Image imgs[]=new Image[4];
		try
		{
			imgs[0]=ImageIO.read(ClassLoader.getSystemResource("Images/Level1.png"));
			imgs[1]=ImageIO.read(ClassLoader.getSystemResource("Images/Level2.png"));
			imgs[2]=ImageIO.read(ClassLoader.getSystemResource("Images/Level3.png"));
			imgs[3]=ImageIO.read(ClassLoader.getSystemResource("Images/Finish.png"));
		}catch (IOException e)
		{
			e.printStackTrace();
		}
		GamePanel.setTextScreenImages(imgs);
		JFrame frame=new JFrame();
		GamePanel app=new GamePanel();
		frame.getContentPane().add(app);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.start();
	}
}