package smartMirror;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LoadingIcon extends JLabel
{
	private static final long serialVersionUID = 7110185079964484817L;
	
	private ImageIcon imgLoading0;
	private ImageIcon imgLoading1;
	private ImageIcon imgLoading2;
	private ImageIcon imgLoading3;
	private ImageIcon imgLoading4;
	
	private TimerTask ttChangeLoadIcon;
	private Timer loadTimer;

	int counter = 0;
	
	public LoadingIcon()
	{
		imgLoading0 = new ImageIcon(getClass().getResource("/resources/Loading0.png"));
		imgLoading1 = new ImageIcon(getClass().getResource("/resources/Loading1.png"));
		imgLoading2 = new ImageIcon(getClass().getResource("/resources/Loading2.png"));
		imgLoading3 = new ImageIcon(getClass().getResource("/resources/Loading3.png"));
		imgLoading4 = new ImageIcon(getClass().getResource("/resources/Loading4.png"));
		
		this.setSize(500, 250);
		
		loadTimer = new Timer();
		ttChangeLoadIcon = new TimerTask()
				{
					@Override
					public void run()
					{
						changeImg();
					}
				};
	}
	
	public void start()
	{
		loadTimer.schedule(ttChangeLoadIcon, 500, 500);
	}
	
	public void stop()
	{
		loadTimer.cancel();
		this.setVisible(false);
	}
	
	private void changeImg()
	{
		
		switch(counter % 5)
		{
		case 0:
			this.setIcon(imgLoading0);
			break;
		case 1:
			this.setIcon(imgLoading1);
			break;
		case 2:
			this.setIcon(imgLoading2);
			break;
		case 3:
			this.setIcon(imgLoading3);
			break;
		case 4:
			this.setIcon(imgLoading4);
			break;
		}
		
		counter ++;
	}
}
