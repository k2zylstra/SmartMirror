package smartMirror;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class TimeLabel extends JLayeredPane
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageIcon circleBackground;
	JLabel lblBackground;
	JLabel lblMainText;
	JLabel lblSubText;
	Timer timeTimer;
	TimerTask ttTimeTimer;
	BufferedImage bBackground;
	int degree;

	public TimeLabel(String input)
	{
		super();
		timeTimer = new Timer();
		ttTimeTimer = new TimerTask()
		{
			@Override
			public void run()
			{
				updateTime();
			}
		};

		timeTimer.schedule(ttTimeTimer, 1000, 1000);
		try
		{
			bBackground = ImageIO.read(getClass().getResourceAsStream("/resources/TimeBackground250Vert.png"));
		}
		catch (IOException e)
		{
			String errMsgInfo = "The File For the Background Image was not found";
			JOptionPane.showMessageDialog(null, errMsgInfo, "Error", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}

		SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
		String minutes = minuteFormat.format(Calendar.getInstance().getTime());
		int min = Integer.parseInt(minutes);
		degree = min * 6;
		
		this.setSize(bBackground.getWidth(), bBackground.getHeight());
		addMainText(input);
	}

	public TimeLabel(String input, String subInput)
	{
		this(input);
		addSubText(subInput);
	}

	public TimeLabel(String input, String subInput, ImageIcon background)
	{
		this(input, subInput);
		addCustomBackground(background);
	}

	private void updateTime()
	{
		Calendar forTime = Calendar.getInstance();
		SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");
		String time = timeFormat.format(forTime.getTime());

		if (time.matches("00"))
		{
			Calendar todaysDate = Calendar.getInstance();
			int month = todaysDate.get(Calendar.MONTH) + 1;
			String dateFormatted = month + "/" + todaysDate.get(Calendar.DATE) + "/" + todaysDate.get(Calendar.YEAR);

			lblSubText.setText(dateFormatted);
		}
		if(!(time.equals(lblMainText.getText())))
		{
			SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
			String minutes = minuteFormat.format(Calendar.getInstance().getTime());
			int min = Integer.parseInt(minutes);
			degree = min * 6;
			
			repaint();
		}
		lblMainText.setText(time);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		int halfx = bBackground.getWidth()/2;
		int halfy = bBackground.getHeight()/2;
		
		AffineTransform clockRotate = AffineTransform.getRotateInstance(0, 0);
		clockRotate.rotate(Math.toRadians(degree), halfx, halfy);
		
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(bBackground, clockRotate, null);
	}

	private void addMainText(String text)
	{
		lblMainText = new JLabel();
		Font timeFont = new Font(Font.SANS_SERIF, 0, 60);

		int width = lblMainText.getFontMetrics(timeFont).stringWidth(text);
		int height = lblMainText.getFontMetrics(timeFont).getHeight();

		int xLoc = (bBackground.getWidth() - width) / 2;
		int yLoc = (bBackground.getHeight() - height) / 2;

		lblMainText.setFont(timeFont);
		lblMainText.setLocation(xLoc, yLoc);
		lblMainText.setSize(width, height);
		lblMainText.setText(text);
		lblMainText.setBackground(Color.BLACK);
		lblMainText.setForeground(Color.WHITE);

		this.add(lblMainText, 1, 0);
	}

	private void addSubText(String text)
	{
		lblSubText = new JLabel();
		Font timeFont = new Font(Font.SANS_SERIF, 0, 20);

		int width = lblSubText.getFontMetrics(timeFont).stringWidth(text);
		int height = lblSubText.getFontMetrics(timeFont).getHeight();

		int xLoc = ((lblMainText.getWidth() - width) / 2) + lblMainText.getX();
		int yLoc = lblMainText.getHeight() + lblMainText.getY();

		lblSubText.setFont(timeFont);
		lblSubText.setLocation(xLoc, yLoc);
		lblSubText.setSize(width, height);
		lblSubText.setText(text);
		lblSubText.setBackground(Color.BLACK);
		lblSubText.setForeground(Color.WHITE);

		this.add(lblSubText, 1, 0);
	}

	private void addCustomBackground(ImageIcon Background)
	{
		// TODO make this put in a custom background
	}
}
