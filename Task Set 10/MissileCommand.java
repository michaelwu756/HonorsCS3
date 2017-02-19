import ui.*;

import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.io.*;
import java.applet.*;

public class MissileCommand extends JApplet
{
	private Image backbuffer;
	private Graphics backg;
	public void init()
	{
		GamePanel app = new GamePanel();
		setContentPane(app);
		setVisible(true);
		app.start();
		
		backbuffer = createImage(GamePanel.VWIDTH, GamePanel.VHEIGHT);//new BufferedImage(GamePanel.VWIDTH,GamePanel.VHEIGHT, BufferedImage.TYPE_INT_ARGB_PRE);
		backg = backbuffer.getGraphics();
		
		setSize(app.getPreferredSize());
	}
	public void update(Graphics g)
	{
		paint(backg);
		g.drawImage(backbuffer, 0, 0, this);
	}
	
	public static void main(String args[])
	{
		JFrame frame=new JFrame();
		GamePanel app=new GamePanel();
		frame.getContentPane().add(app);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.start();
	}
}