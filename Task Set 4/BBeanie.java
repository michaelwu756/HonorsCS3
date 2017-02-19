import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class BBeanie extends Applet implements Runnable
{
	int[][] blockArray;
	MovingBlock movingBlockArray[]= new MovingBlock[1];
	SecretBlock secretBlockArray[]= new SecretBlock[4];
	BadGuy badguys[] = new BadGuy[2];
	Scenery scene[] = new Scenery[7];
	Burst bursts[] = new Burst[18];
	HeroSprite hero;
	PowerUp powerUp;
	Grid grid;
	int score;
	boolean killed = false;
	Thread anim;
	Image buffer;
	Graphics bufgr;
	Image saved_i;
	int cut = 50;  // cutpoint in background scenery (to scroll)
	Font font;
	boolean intro = true; // init instruction screen
	int FRAME_DELAY = 50;
	public static final int VWIDTH = 600;
	public static final int VHEIGHT = 300;
	public static final int SCROLL = 30;  // Set edge limit for scrolling
	int vleft = 0;	// Pixel coord of left edge of viewable area
					// (used for scrolling)

	public void init()
	{
		addMouseListener(new mseL());
		addKeyListener(new keyL());

		buffer = createImage(VWIDTH,VHEIGHT);
		bufgr = buffer.getGraphics();
		font = new Font("TimesRoman",Font.ITALIC,30);
		Image heros = getImage(getCodeBase(), "Skarner.png");
		Image herou = getImage(getCodeBase(), "SkarnerU.png");
		Image herod = getImage(getCodeBase(), "SkarnerD.png");
		Image blockImage = getImage(getCodeBase(), "Block.png");
		saved_i = getImage(getCodeBase(), "Back.png");
		Image f1 = getImage(getCodeBase(), "Cactus1.png");
		Image f2 = getImage(getCodeBase(), "Cactus2.png");
		Image f3 = getImage(getCodeBase(), "Cactus3.png");
		Image b1 = getImage(getCodeBase(), "Flame1.png");
		Image b2 = getImage(getCodeBase(), "Flame2.png");
		Image burst = getImage(getCodeBase(), "Burst.png");
		Image guy = getImage(getCodeBase(), "Guy.png");
		Image secretBlockImage = getImage(getCodeBase(),"SecretBlock.png");
		Image powerUpIcon = getImage(getCodeBase(), "PowerUp.png");
		MediaTracker t = new MediaTracker(this);
				t.addImage(heros, 0);
				t.addImage(herou, 0);
				t.addImage(herod, 0);
				t.addImage(blockImage, 0);
				t.addImage(saved_i, 0);
				t.addImage(f1, 0);
				t.addImage(f2, 0);
				t.addImage(f3, 0);
				t.addImage(b1, 0);
				t.addImage(b2, 0);
				t.addImage(burst, 0);
				t.addImage(guy, 0);
				t.addImage(secretBlockImage, 0);
				t.addImage(powerUpIcon, 0);
				try
				{
					t.waitForID(0);
				} catch (InterruptedException e)
			{}
		grid = new Grid(this, blockImage);
		// Store images with classes rather than a reference
		// in every object.
		blockArray = new int[grid.MHEIGHT][grid.MWIDTH];
		HeroSprite.setStaticImages(heros,herou,herod);
		Flower.setStaticImages(f1,f2,f3);
		BFly.setStaticImages(b1,b2);
		Burst.setStaticImage(burst);
		BadGuy.setStaticImage(guy);
		MovingBlock.setStaticImage(blockImage);
		SecretBlock.setStaticImage(secretBlockImage);
		PowerUp.setStaticImage(powerUpIcon);
		
		setLevel1();
		score = 0;
		setSize(new Dimension(VWIDTH,VHEIGHT));
	}

	public void start()
	{
		anim = new Thread(this);
		anim.start();
	}

	public void setDead()
	{
		killed = true;
	}

	public void run()
	{
		int i;

		while(true)
		{
			if (!killed)
				hero.updateSprite(movingBlockArray);
			for (i=0; i < badguys.length; i++)
				badguys[i].updateSprite();
			for (i = 0; i < scene.length; i++)
				scene[i].update();

			// Check for collisions (Note: we don't hit scenery)
			Rectangle cb = hero.collisionBox();
			for (i = 0; i < bursts.length; i++)
				if (bursts[i].isActive() && cb.intersects(bursts[i].collisionBox()))
					bursts[i].hit();
			for (i = 0; i < movingBlockArray.length; i++)
				movingBlockArray[i].updateSprite();
			for (i=0; i < badguys.length; i++)
				if (cb.intersects(badguys[i].collisionBox()))
				{setDead();}
			for (i=0; i < secretBlockArray.length; i++)
				if (cb.intersects(secretBlockArray[i].collisionBox()))
				{
					grid.removeBlock(secretBlockArray[i].col(),secretBlockArray[i].row());
					secretBlockArray[i].setFound(false);
				}
			if (cb.intersects(powerUp.collisionBox()))
			{	
				powerUp.hit();
				hero.powerUp();
			}
			checkScrolling();
			repaint();
			try
			{
				Thread.sleep(FRAME_DELAY);
			} catch (InterruptedException e)
				{}
		}
	}

	void checkScrolling()
	{
		// Test if hero is at edge of view window and scroll appropriately
		if (hero.getX() < (vleft+SCROLL))
		{
			vleft = hero.getX()-SCROLL;
			if (vleft < 0)
				vleft = 0;
		}
		if ((hero.getX() + hero.width()) > (vleft+VWIDTH-SCROLL))
		{
			vleft = hero.getX()+hero.width()-VWIDTH+SCROLL;
			if (vleft > (grid.width()-VWIDTH))
				vleft = grid.width()-VWIDTH;
		}
	}

	public void setLevel1()
	{
		// Now place specific blocks (depends on current map size) (col-14)/2,(ln-161)
		blockArray=new int[][]
		{
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
		};
		for(int y=0; y<blockArray.length; y++)
		{
			for(int x=0; x<blockArray[0].length; x++)
			{
				if (blockArray[y][x]==1)
					grid.setBlock(x,y);
			}
		}
		
		movingBlockArray[0]= new MovingBlock(this,27*Grid.CELLSIZE,7*Grid.CELLSIZE,27*Grid.CELLSIZE,38*Grid.CELLSIZE);
		secretBlockArray[0]= new SecretBlock(this, 36,12);
		secretBlockArray[1]= new SecretBlock(this, 13,13);
		secretBlockArray[2]= new SecretBlock(this, 12,13);
		secretBlockArray[3]= new SecretBlock(this, 12,12);
		// Setup foreground scenery
		scene[0] = new Flower(this,60,269,100,0);
		scene[1] = new Flower(this,90,269,100,20);
		scene[2] = new Flower(this,120,269,100,40);
		scene[3] = new Flower(this,650,269,120,30);
		scene[4] = new Flower(this,680,269,120,0);
		scene[5] = new BFly(this,70,120);
		scene[6] = new BFly(this,383,87);
		// Setup up scoring bursts
		bursts[0] = new Burst(this,320,150);
		bursts[1] = new Burst(this,220,150);
		bursts[2] = new Burst(this,500,60);
		bursts[3] = new Burst(this,720,160);
		bursts[4] = new Burst(this,32*Grid.CELLSIZE,12*Grid.CELLSIZE);
		bursts[5] = new Burst(this,13*Grid.CELLSIZE,13*Grid.CELLSIZE);
		bursts[6] = new Burst(this,12*Grid.CELLSIZE,13*Grid.CELLSIZE);
		bursts[7] = new Burst(this,220,151);
		bursts[8] = new Burst(this,500,60);
		bursts[9] = new Burst(this,720,170);
		bursts[10] = new Burst(this,735,140);
		bursts[11] = new Burst(this,750,156);
		bursts[12] = new Burst(this,750,158);
		bursts[13] = new Burst(this,850,257);
		bursts[14] = new Burst(this,950,257);
		bursts[15] = new Burst(this,1050,257);
		bursts[16] = new Burst(this,1150,257);
		bursts[17] = new Burst(this,1250,257);
		// And, the stars of our show...
		hero = new HeroSprite(this,grid,50,249);
		powerUp = new PowerUp(this, 20*Grid.CELLSIZE,3*Grid.CELLSIZE);
		badguys[0] = new BadGuy(this,540,249,520,620);
		badguys[1] = new BadGuy(this,1000,249,1000,1100);
	}

	private boolean lostGame()
	{
		return killed;
	}

	private boolean wonGame()
	{
		return (score == 10*bursts.length);
	}

	public void update(Graphics g)
	{
		paint(bufgr);
		g.drawImage(buffer,0,0,this);
	}

	public void paint(Graphics g)
	{
		int i;

		if (intro)
		{
			// startup screen
			g.setColor(Color.white);
			g.fillRect(0,0,VWIDTH,VHEIGHT);
			g.setColor(Color.black);
			g.drawRect(0,0,VWIDTH-1,VHEIGHT-1);
			g.setFont(font);
			g.drawString("BOUNCING BEANIE!",50,40);
			g.drawString("Use arrow keys to",50,100);
			g.drawString("move left and right,",40,130);
			g.drawString("space bar to jump.",60,160);
			g.drawString("Click applet to start game.",30,250);
			return;
		}
		/*
		 * Begin main paint
		 */
		g.setClip(0, 0, VWIDTH, VHEIGHT);
		cut = vleft>>1; // setting cut to half the main scroll factor
						// gives the parallax effect
		g.drawImage(saved_i, -100-cut, 0, this);
		//g.drawImage(saved_i, 400-cut, 0, this);
		
		for (i = 0; i < bursts.length; i++)
			bursts[i].paint(g);
		grid.paint(g);
		if (!killed)
			hero.drawSprite(g);
		for (i = 0; i < badguys.length; i++)
			badguys[i].drawSprite(g);
		for (i = 0; i < scene.length; i++)
			scene[i].paint(g);
		for (i = 0; i < movingBlockArray.length; i++)
			movingBlockArray[i].drawSprite(g);
		for (i = 0; i < secretBlockArray.length; i++)
			secretBlockArray[i].drawSprite(g);
		powerUp.drawSprite(g);
		
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString("Score:"+score,250,25);
		if (wonGame())
		{
			g.setColor(Color.black);
			g.setFont(font);
			g.drawString("You win!",100,100);
		}
		if (lostGame())
		{
			g.setColor(Color.black);
			g.setFont(font);
			g.drawString("WASTED",100,100);
		}
	}

	public void bumpScore(int p)
	{
		score += p;
	}

	private class mseL extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			requestFocus();
			intro = false;
		}
	}

	private class keyL extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			int key = e.getKeyCode();
			switch (key)
			{
				// Duplicate keys are defined to maintain
				// the original bindings (as well as provide
				// more sensible ones)
				//
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_J: hero.dir = 1;
									break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_K: hero.dir = 2;
									break;
				case KeyEvent.VK_UP:
				case KeyEvent.VK_SPACE:
				case KeyEvent.VK_A: hero.jmp = 1;
									break;
			}
		}
		public void keyReleased(KeyEvent e)
		{
			int key = e.getKeyCode();
			if ((key == KeyEvent.VK_J)||(key == KeyEvent.VK_K)||
					(key == KeyEvent.VK_LEFT)||(key == KeyEvent.VK_RIGHT))
				hero.dir = 0;
		}
	}
}



