import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class SoundTemplate extends JApplet implements Runnable
{
	int beats=60;
	int notes=25;
	int speed=500;
	JToggleButton button[][]=new JToggleButton[beats][notes];
	JScrollPane gridView, noteView;
	JPanel centerPanel = new HorizontalPanel();
	JPanel contentPanel = new JPanel();
	JPanel gridPanel=new JPanel();
	JPanel bottomPanel = new JPanel();
	JPanel notePanel = new JPanel();
	JButton clearButton = new JButton("Clear");
	JToggleButton illuminatiButton= new JToggleButton();
	JSlider speedSlider = new JSlider(0, 1750, 2000-speed);
	ImageIcon illuminatiEye;
	Container container;
	AudioClip soundClip[]=new AudioClip[notes];
	AudioClip damnSon, skrillex, spooky;
	Thread runner;
	boolean notStopped=true, pause=false;
	int col = 0;
	
	public class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
			clear();
		}
	}
	public class DragMouseListener extends MouseAdapter{
		public void mouseEntered(MouseEvent e)
		{
			if (e.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK)
			{
				if (((JToggleButton)e.getComponent()).isSelected())
					((JToggleButton)e.getComponent()).setSelected(false);
				else((JToggleButton)e.getComponent()).setSelected(true);
			}
		}
		public void mousePressed(MouseEvent e)
		{
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				if (((JToggleButton)e.getComponent()).isSelected())
					((JToggleButton)e.getComponent()).setSelected(false);
				else((JToggleButton)e.getComponent()).setSelected(true);
			}
		}
		public void mouseClicked(MouseEvent e)
		{
			mousePressed(e);
		}

	}
	public class SpeedListener implements ChangeListener{
		public void stateChanged(ChangeEvent e)
		{
			speed=2000-((JSlider)e.getSource()).getValue();
		}
	}
	public class IlluminatiListener implements ActionListener{
		public void actionPerformed(ActionEvent e)
		{
			if (((JToggleButton)e.getSource()).isSelected())
			{
				((JToggleButton)e.getSource()).setIcon(illuminatiEye);
				((JToggleButton)e.getSource()).setPreferredSize(new Dimension((illuminatiEye.getIconWidth()*6)/2,illuminatiEye.getIconHeight()));
				pause=true;
				try
				{
					spooky.play();
					runner.sleep(3000);
					damnSon.play();
					runner.sleep(3000);
					skrillex.loop();
					runner.sleep(1500);
				}
				catch(InterruptedException ex)
				{
				}
				pause=false;
			}
			else
			{
				((JToggleButton)e.getSource()).setIcon(null);
				illuminatiButton.setPreferredSize(new Dimension(100,30));
				spooky.stop();
				damnSon.stop();
				skrillex.stop();
			}
		}
	}
	public class HorizontalPanel extends JPanel implements Scrollable{
	
		HorizontalPanel()
		{
			super();
		}
		
		public Dimension getPreferredScrollableViewportSize()
		{
			return getPreferredSize();
		}
		public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
		{
			return 20;
		}
		public boolean getScrollableTracksViewportHeight()
		{
			return false;
		}
		public boolean getScrollableTracksViewportWidth()
		{
			return true;
		}
		public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
		{
			return 20;
		}
	}
	
	public void init()
	{
		damnSon=getAudioClip(getDocumentBase(), "sounds/damnSonWheredYouFindThis.wav");
		skrillex=getAudioClip(getDocumentBase(), "sounds/skrillex.wav");
		spooky=getAudioClip(getDocumentBase(), "sounds/spooky.wav");
		illuminatiEye=new ImageIcon("images/illuminati.jpeg");
		illuminatiEye.setImage(illuminatiEye.getImage().getScaledInstance(40, 40,Image.SCALE_DEFAULT));
		
		String noteNames[]= {"C", "C#/Db", "D", "D#/Eb", "E", "F", "F#/Gb", "G", "G#/Ab", "A", "A#/Bb", "B"};
		soundClip[24]=getAudioClip(getDocumentBase(),"sounds/AirHorn1.wav");
		soundClip[23]=getAudioClip(getDocumentBase(),"sounds/AirHorn2.wav");
		soundClip[22]=getAudioClip(getDocumentBase(),"sounds/AirHorn3.wav");
		soundClip[21]=getAudioClip(getDocumentBase(),"sounds/AirHorn4.wav");
		soundClip[20]=getAudioClip(getDocumentBase(),"sounds/AirHorn5.wav");
		soundClip[19]=getAudioClip(getDocumentBase(),"sounds/AirHorn6.wav");
		soundClip[18]=getAudioClip(getDocumentBase(),"sounds/AirHorn7.wav");
		soundClip[17]=getAudioClip(getDocumentBase(),"sounds/AirHorn8.wav");
		soundClip[16]=getAudioClip(getDocumentBase(),"sounds/AirHorn9.wav");
		soundClip[15]=getAudioClip(getDocumentBase(),"sounds/AirHorn10.wav");
		soundClip[14]=getAudioClip(getDocumentBase(),"sounds/AirHorn11.wav");
		soundClip[13]=getAudioClip(getDocumentBase(),"sounds/AirHorn12.wav");
		soundClip[12]=getAudioClip(getDocumentBase(),"sounds/AirHorn13.wav");
		soundClip[11]=getAudioClip(getDocumentBase(),"sounds/AirHorn14.wav");
		soundClip[10]=getAudioClip(getDocumentBase(),"sounds/AirHorn15.wav");
		soundClip[9]=getAudioClip(getDocumentBase(),"sounds/AirHorn16.wav");
		soundClip[8]=getAudioClip(getDocumentBase(),"sounds/AirHorn17.wav");
		soundClip[7]=getAudioClip(getDocumentBase(),"sounds/AirHorn18.wav");
		soundClip[6]=getAudioClip(getDocumentBase(),"sounds/AirHorn19.wav");
		soundClip[5]=getAudioClip(getDocumentBase(),"sounds/AirHorn20.wav");
		soundClip[4]=getAudioClip(getDocumentBase(),"sounds/AirHorn21.wav");
		soundClip[3]=getAudioClip(getDocumentBase(),"sounds/AirHorn22.wav");
		soundClip[2]=getAudioClip(getDocumentBase(),"sounds/AirHorn23.wav");
		soundClip[1]=getAudioClip(getDocumentBase(),"sounds/AirHorn24.wav");
		soundClip[0]=getAudioClip(getDocumentBase(),"sounds/AirHorn25.wav");
		
		container=getContentPane();
		gridPanel.setLayout(new GridLayout(notes,beats));
		String buttonText[]={"ON","OFF"};
		for(int y=0;y<notes;y++)
			for(int x=0;x<beats;x++)
			{
				button[x][y]=new JToggleButton();
				button[x][y].addMouseListener(new DragMouseListener());
				button[x][y].setPreferredSize(new Dimension(20,20));
				gridPanel.add(button[x][y]);
			}
		gridView = new JScrollPane(gridPanel);
		gridView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		gridView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		gridView.getHorizontalScrollBar().setUnitIncrement(20);
		
		notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.PAGE_AXIS));
		notePanel.add(Box.createVerticalGlue());
		for (int x=0;x<notes;x++)
		{
			JLabel noteLabel = new JLabel(noteNames[11-((x+11)%12)]);
			noteLabel.setPreferredSize(new Dimension(50, 20));
			notePanel.add(noteLabel);
			notePanel.add(Box.createVerticalGlue());
		}
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(gridView,BorderLayout.CENTER);
		centerPanel.add(notePanel,BorderLayout.EAST);
		
		noteView = new JScrollPane(centerPanel);
		noteView.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		noteView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		noteView.setWheelScrollingEnabled(true);
		noteView.setMaximumSize(new Dimension((int)(gridPanel.getPreferredSize().getWidth()+
			notePanel.getPreferredSize().getWidth()+
			noteView.getVerticalScrollBar().getPreferredSize().getWidth()+6),
			(int)(gridPanel.getPreferredSize().getHeight())));
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(noteView);
		contentPanel.add(Box.createVerticalGlue());
		
		clearButton.addActionListener(new ClearListener());
		speedSlider.addChangeListener(new SpeedListener());
		speedSlider.setPreferredSize(new Dimension(80, 30));
		illuminatiButton.setPreferredSize(new Dimension(100,30));
		illuminatiButton.addActionListener(new IlluminatiListener());
		bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		bottomPanel.add(clearButton);
		bottomPanel.add(new JLabel("Speed"));
		bottomPanel.add(speedSlider);
		bottomPanel.add(illuminatiButton);
		
		setMinimumSize(new Dimension(beats*15, notes*15));
		container.add(contentPanel,BorderLayout.CENTER);
		container.add(bottomPanel, BorderLayout.SOUTH);
		setSize(container.getPreferredSize());
		setVisible(true);
	}
	public void clear()
	{
		for(int y=0;y<notes;y++)
			for(int x=0;x<beats;x++)
				button[x][y].setSelected(false);
	}
	public void start()
	{
		if(runner==null)
		{
			runner=new Thread(this);
			runner.start();
		}
	}
	public void run()
	{
		do
		{
			if (!pause)
				goApp();
		}while(notStopped);

	}

	public void goApp()
	{
		try
		{	
			
			for(int y=0;y<notes;y++)
			{
				soundClip[y].stop();
				if(button[col%beats][y].isSelected())
				{
					soundClip[y].play();
				}
				button[col%beats][y].setBackground(Color.WHITE);
				button[col%beats][y].setOpaque(true);
			}
			col++;
			new Thread().sleep(speed);
			for(int y=0;y<notes;y++)
			{
				button[(col-1)%beats][y].setBackground(null);
			}
		}
		catch(InterruptedException e)
		{
		}
	}
}