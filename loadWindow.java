package smartMirror;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;


public class loadWindow implements ActionListener
{
	JFrame loadFrame;
	JLayeredPane lpLoadContainer;
	JButton btnClose;
	JButton btnStart;
	JTextArea tbxLatitude;
	JTextArea tbxLongitude;
	
	public loadWindow()
	{
		initializeComponents();
	}
	
	private void initializeComponents()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Font fntBtnStart = new Font(Font.SANS_SERIF, 0, 16);
		
		// add loadFrame
		loadFrame = new JFrame("Load");
		loadFrame.setUndecorated(true);
		loadFrame.setSize(800, 500);
		loadFrame.setLocation((screenSize.width - loadFrame.getWidth()) /2 ,(screenSize.height - loadFrame.getHeight()) / 2);
		loadFrame.setVisible(true);
		
		// add lpLoadContainer
		lpLoadContainer = new JLayeredPane();
		lpLoadContainer.setSize(loadFrame.getWidth(), loadFrame.getHeight());
		lpLoadContainer.setLocation(0, 0);
		loadFrame.add(lpLoadContainer);
		
		// add btnClose
		btnClose = new JButton();
		btnClose.setSize(32, 32);
		btnClose.setLocation(loadFrame.getWidth() - btnClose.getWidth(), 0);
		btnClose.setBackground(Color.WHITE);
		btnClose.addActionListener(this);
		lpLoadContainer.add(btnClose, 1, 0);
		
		// add btnStart
		btnStart = new JButton();
		btnStart.setLocation(0, 0);
		btnStart.setBackground(Color.WHITE);
		btnStart.setText("Start");
		btnStart.setMargin(new Insets(0, 0, 0, 0));
		btnStart.setFont(fntBtnStart);
		btnStart.setSize(50, 32);
		btnStart.addActionListener(this);
		btnStart.setFocusable(false);
		lpLoadContainer.add(btnStart, 1, 0);
		
		// add tbxLatitude
		tbxLatitude = new JTextArea("hey");
		tbxLatitude.setPreferredSize(new Dimension(100, 50));
		tbxLatitude.setLocation(50, 200);
		tbxLatitude.setText("test text");//TODO get this to be visible
		tbxLatitude.setBackground(Color.BLACK);
		lpLoadContainer.add(tbxLatitude, 1, 0);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == btnClose)
		{
			exit();
		}
		if(e.getSource() == btnStart)
		{
			start();
		}
	}
	
	public void exit()
	{
		System.exit(0);
	}
	
	public void start()
	{	
		UIWindow startFrame = new UIWindow();
		BigDecimal lat = new BigDecimal(47.184408);
		BigDecimal lng = new BigDecimal(-122.346984);
		startFrame.Load(lat, lng);
	}
}
