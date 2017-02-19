package ui;

import members.*;
import engine.*;
import members.ball.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;
import javazoom.jl.player.advanced.*;
import javazoom.jl.player.*;

public class GamePanel extends JPanel implements KeyListener,MouseListener,FocusListener,Runnable
{
	public static int VHEIGHT=412;
	public static int VWIDTH=636;
	public static int BORDER_WIDTH=10;
	public static int STATUS_BAR_HEIGHT=38;
	private static int FRAME_DELAY = 16;
	private static Color BG_COLOR=new Color(247,247,255);
	private static int TEXT_SCREEN_LIFE=120;
	
	private static Image textScreenImage[];

	private int level;
	private int deaths,deathTime;
	private int textScreen;
	private boolean win;
	
	private PlayerBox player;
	private Grid grid;
	
	private LinkedList<DrawnObject> objectsToDraw;
	private boolean focus;
	private Boolean direction[];
	private Thread anim;
	
	public GamePanel()
	{
		super();
		setPreferredSize(new Dimension(VWIDTH,VHEIGHT+STATUS_BAR_HEIGHT));
		direction= new Boolean[4];
		Arrays.fill(direction,false);
		focus=false;
		
		resetValues();
		
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		addFocusListener(this);
		loopAudio("Theme.mp3", 24000);
		playAudio("Ding.mp3");
	}
	
	public static void setTextScreenImages(Image img[])
	{
		textScreenImage=img;
	}
	
	public void checkCollisions()
	{
		int tokensLeft=0;
		if (deathTime!=-1)
		{
			deathTime++;
		}
		if (deathTime>PlayerBox.FADE_LIFE)
		{
			deathTime=-1;
			deaths++;
		}
		for (DrawnObject x:objectsToDraw)
		{
			if (x instanceof Ball)
			{
				Ball ball = (Ball)x;
				if (ball instanceof TokenBall)
				{
					TokenBall tokenBall = (TokenBall)ball;
					if (!player.isDead()&&tokenBall.alive()&& tokenBall.hitBox().collidesWith(player.hitBox()))
					{
						tokenBall.setDead();
						playAudio("Token.mp3");
					}
					if(tokenBall.alive())
						tokensLeft++;
				}
				else if(!player.isDead() && ball.hitBox().collidesWith(player.hitBox()))
				{
					player.setDead();
					deathTime=0;
					playAudio("Hit.mp3");
					for (DrawnObject y:objectsToDraw)
					{
						if (y instanceof TokenBall)
						{
							TokenBall tb = (TokenBall)y;
							tb.setAlive();
						}
					}
				}
			}
		}
		if (tokensLeft==0 && grid.collidesWithFinish(player.hitBox()))
		{
			level++;
			playAudio("Ding.mp3");
			if (level==2)
				level2();
			if (level==3)
				level3();
			if (level==4)
			{
				level--;
				textScreen=0;
				win=true;
			}
				
		}
	}	
	public void updateProjectiles()
	{
		for (DrawnObject x:objectsToDraw)
		{
			if (x instanceof Ball)
			{
				Ball ball = (Ball)x;
				ball.move();
			}
		}
	}
	public void updatePlayerLocation()
	{
		double speed=1.75;
		double dx=0,dy=0;
		if (direction[0])
			dx-=speed;
		if (direction[1])
			dy-=speed;
		if (direction[2])
			dx+=speed;
		if (direction[3])
			dy+=speed;
		double s = player.hitBox().getWidth()/2;
		if (player.xValue()+dx<BORDER_WIDTH+s || player.xValue()+dx>VWIDTH-BORDER_WIDTH-s 
			|| grid.collidesWithWall(new RectangularCollisionBox(
				(int)(player.xValue()+dx),
				(int)player.yValue(),
				(int)player.getSideLength(),
				(int)player.getSideLength())))
			dx=0;
		if (player.yValue()+dy<BORDER_WIDTH+s || player.yValue()+dy>VHEIGHT-BORDER_WIDTH-s
			|| grid.collidesWithWall(new RectangularCollisionBox(
				(int)player.xValue(),
				(int)(player.yValue()+dy),
				(int)player.getSideLength(),
				(int)player.getSideLength())))
			dy=0;
		if (!player.isDead())
		{
			player.moveX(dx);
			player.moveY(dy);
		}
	}
	
	public void level1()
	{
		textScreen=0;
		int data[][]={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,3,3,3,1,1,1,1,1,1,1,1,1,1,0,0,2,2,2,1,1},
			{1,1,3,3,3,1,0,0,0,0,0,0,0,0,0,0,1,2,2,2,1,1},
			{1,1,3,3,3,1,0,0,0,0,0,0,0,0,0,0,1,2,2,2,1,1},
			{1,1,3,3,3,1,0,0,0,0,0,0,0,0,0,0,1,2,2,2,1,1},
			{1,1,3,3,3,1,0,0,0,0,0,0,0,0,0,0,1,2,2,2,1,1},
			{1,1,3,3,3,0,0,1,1,1,1,1,1,1,1,1,1,2,2,2,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
		};
		
		player=new PlayerBox(BORDER_WIDTH+14+3*28,BORDER_WIDTH+7*28,20);
		grid=new Grid(data);
		
		objectsToDraw = new LinkedList<DrawnObject>();
		objectsToDraw.add(grid);
		objectsToDraw.add(player);
		objectsToDraw.add(new LinearBall(BORDER_WIDTH+14+15*28,BORDER_WIDTH+14+5*28,grid,-LinearBall.X_AXIS));
		objectsToDraw.add(new LinearBall(BORDER_WIDTH+14+15*28,BORDER_WIDTH+14+7*28,grid,-LinearBall.X_AXIS));
		objectsToDraw.add(new LinearBall(BORDER_WIDTH+14+6*28,BORDER_WIDTH+14+6*28,grid,LinearBall.X_AXIS));
		objectsToDraw.add(new LinearBall(BORDER_WIDTH+14+6*28,BORDER_WIDTH+14+8*28,grid,LinearBall.X_AXIS));
	}
	public void level2()
	{
		textScreen=0;
		int data[][]={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,3,3,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,3,3,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,3,3,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1},
			{1,1,1,1,2,2,2,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1},
			{1,1,1,1,2,2,2,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
		};
		
		player=new PlayerBox(BORDER_WIDTH+11*28,BORDER_WIDTH+14+2*28,20);
		grid=new Grid(data);
		
		objectsToDraw = new LinkedList<DrawnObject>();
		objectsToDraw.add(grid);
		objectsToDraw.add(player);
		double centerX=BORDER_WIDTH+11*28;
		double centerY=BORDER_WIDTH+8*28;
		int interval =20;
		double speed = -Math.PI/95;
		objectsToDraw.add(new CircularBall(centerX,centerY,0,0,speed));
		for (int i=0; i<5; i++)
		{
			objectsToDraw.add(new CircularBall(centerX,centerY,(i+1)*interval,0,speed));
			objectsToDraw.add(new CircularBall(centerX,centerY,(i+1)*interval,Math.PI/2,speed));
			objectsToDraw.add(new CircularBall(centerX,centerY,(i+1)*interval,Math.PI,speed));
			objectsToDraw.add(new CircularBall(centerX,centerY,(i+1)*interval,3*Math.PI/2,speed));
		}
		objectsToDraw.add(new TokenBall(centerX+3*28,centerY));
		objectsToDraw.add(new TokenBall(centerX,centerY-3*28));
		objectsToDraw.add(new TokenBall(centerX,centerY+3*28));
	}
	public void level3()
	{
		textScreen=0;
		int data[][]={
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
			{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
			{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
			{1,1,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,1,1},
			{1,1,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,1,1},
			{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
			{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
			{1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
		};
		player=new PlayerBox(BORDER_WIDTH+14+3*28,BORDER_WIDTH+7*28,20);
		grid=new Grid(data);
		
		objectsToDraw = new LinkedList<DrawnObject>();
		objectsToDraw.add(grid);
		objectsToDraw.add(player);
		int topLeftX=BORDER_WIDTH+14+5*28;
		int topLeftY=BORDER_WIDTH+14+3*28;
		int bottomLeftX=topLeftX+28;
		int bottomLeftY=topLeftY+7*28;
		for (int i=0;i<6;i++)
		{
			objectsToDraw.add(new LinearBall(topLeftX+2*i*28,topLeftY,grid,LinearBall.Y_AXIS,4.25));
			objectsToDraw.add(new LinearBall(bottomLeftX+2*i*28,bottomLeftY,grid,-LinearBall.Y_AXIS,4.25));
		}
		objectsToDraw.add(new TokenBall(topLeftX,topLeftY));
		objectsToDraw.add(new TokenBall(topLeftX,bottomLeftY));
		objectsToDraw.add(new TokenBall(topLeftX+11*28,topLeftY));
		objectsToDraw.add(new TokenBall(topLeftX+11*28,bottomLeftY));
	}
	public void resetValues()
	{
		level=1;
		deaths=0;
		deathTime=-1;
		textScreen=-1;
		win=false;
		level1();
	}
	
	public void start()
	{
		anim = new Thread(this);
		resetValues();
		anim.start();
	}
	public void run()
	{
		Thread thisThread = Thread.currentThread();
		while(anim==thisThread)
		{
			if (focus && textScreen<0 && !win)
			{
				updatePlayerLocation();
				updateProjectiles();
				checkCollisions();
			}
			repaint();
			try
			{
				Thread.sleep(FRAME_DELAY);
			} catch (InterruptedException e)
			{}
		}
	}
	
	public void playAudio(String fileName)
	{
		Thread audioThread = new Thread(new Runnable(){
			public void run()
			{
				try
				{
					InputStream fis = this.getClass().getResourceAsStream("/Sounds/" + fileName);
					Player playMP3 = new Player(fis);
					playMP3.play();
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Failed to play the file.");
				}
			}
		});
		audioThread.start();
	}
	public void loopAudio(String fileName, int finish)
	{
		Thread audioThread = new Thread(new Runnable(){
			class loopListener extends PlaybackListener
			{
				public void playbackFinished(PlaybackEvent e)
				{
					try
					{
						loopAudio(fileName, finish);
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
				public void playbackStarted(PlaybackEvent e){}
			}
			public void run()
			{
				try
				{
					InputStream fis = this.getClass().getResourceAsStream("/Sounds/" + fileName);
					jlap.playMp3(fis,0,finish,new loopListener());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
		audioThread.run();
	}
	
	public void keyReleased(KeyEvent ke)
	{
		for (int i=0; i<4; i++)
		{
			if(ke.getKeyCode()==KeyEvent.VK_LEFT+i)
			{
				direction[i]=false;
			}
		}
	}
	public void keyPressed(KeyEvent ke)
	{
		for (int i=0; i<4; i++)
		{
			if(ke.getKeyCode()==KeyEvent.VK_LEFT+i)
			{
				direction[i]=true;
			}
		}
		if (ke.getKeyCode()==KeyEvent.VK_ENTER)
		{
			start();
		}
	}
	public void keyTyped(KeyEvent ke){}
	
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e)
	{
		requestFocusInWindow();
	}
	
	public void focusGained(FocusEvent e)
	{
		focus=true;
	}
	public void focusLost(FocusEvent e)
	{
		focus=false;
	}
	
	public void paintComponent(Graphics g)
	{
		BufferedImage bi = new BufferedImage(VWIDTH, VHEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D tempG = (Graphics2D)bi.getGraphics();
		if (textScreen<0)
		{
			tempG.setClip(0, 0, VWIDTH, VHEIGHT);
			tempG.setColor(BG_COLOR);
			tempG.setFont(new Font("Arial",Font.BOLD,18));
			tempG.fillRect(0,0,VWIDTH,VHEIGHT);
			for (DrawnObject x:objectsToDraw)
			{
			x.paint(tempG);
			}
		}
		else if (!win)
		{
			tempG.drawImage(textScreenImage[level-1],0,0,null);
			textScreen++;
			if (textScreen>TEXT_SCREEN_LIFE)
				textScreen=-1;
		}
		else
		{
			tempG.drawImage(textScreenImage[level],0,0,null);
		}
		if (!focus)
		{
			tempG.setColor(new Color(0,0,0,150));
			tempG.fillRect(0,0,VWIDTH,VHEIGHT);
			tempG.setColor(Color.WHITE);
			tempG.drawString("Paused", -30+VWIDTH/2,VHEIGHT/2);
		}
		g.setClip(0, 0, VWIDTH, VHEIGHT+STATUS_BAR_HEIGHT);
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, VWIDTH, STATUS_BAR_HEIGHT);
		g.drawImage(bi,0,STATUS_BAR_HEIGHT,null);
		g.setColor(new Color(254,254,254));
		g.setFont(new Font("Arial",Font.BOLD, 18));
		g.drawString(level+"/3", VWIDTH/2-15,28);
		g.drawString("DEATHS: "+deaths, VWIDTH-120, 28);
	}
}