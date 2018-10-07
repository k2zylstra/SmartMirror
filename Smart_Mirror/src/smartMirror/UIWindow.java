package smartMirror;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;

import javax.swing.*;

import java.util.Calendar;
import java.util.EventObject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UIWindow implements NewInfoFoundListener
{
	private BigDecimal latitude;
	private BigDecimal longitude;
	private JFrame frame;
	private JLabel lblWeatherTitle;
	private JLabel lblWeatherImg;
	private JLayeredPane jlpBack;
	private JPanel pnlBackground;
	private JTextArea txtWeather;
	private JTextArea txtNotes;
	private LoadingIcon liLoading;
	private TimeLabel lblTime;

	public UIWindow()
	{
		// Creates a the main frame for the data to be displayed
		createFrame();

		// adds all components to the screen and then starts that class that
		// retrieves the data
		addComponents();
		frame.pack();
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public void Load(BigDecimal lat, BigDecimal lng)
	{
		latitude = lat;
		longitude = lng;
		TextGrabber txtData = new TextGrabber();
		txtData.addEventListener(this);
		txtData.grabText(latitude, longitude);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private void createFrame()
	{
		// creates a graphics device that sets the screen to be in full screen
		// mode covering everything else on screen
		GraphicsDevice vc;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		vc = ge.getDefaultScreenDevice();
		frame = new JFrame("Test");
		frame.setVisible(true);

		vc.setFullScreenWindow(frame);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private void addComponents()
	{
		Font fntSerif34 = new Font(Font.SERIF, 0, 34);
		Font fntSerifBold34 = new Font(Font.SERIF, 0, 38);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// add layered pane
		jlpBack = new JLayeredPane();
		jlpBack.setLocation(0, 0);
		jlpBack.setSize(screenSize.width, screenSize.height);
		frame.setContentPane(jlpBack);

		// Makes Cursor Invisible
		changeCursorImage();

		// add background
		pnlBackground = new JPanel();
		pnlBackground.setSize(screenSize.width, screenSize.height);
		pnlBackground.setLocation(0, 0);
		pnlBackground.setBackground(Color.BLACK);
		jlpBack.add(pnlBackground, 0, 0);

		// add time test
		lblTime = new TimeLabel(getTime(), getDate());
		lblTime.setLocation(10, 10);
		jlpBack.add(lblTime, 1, 0);

		// add loading icon
		liLoading = new LoadingIcon();
		liLoading.setLocation(0, lblTime.getHeight() + 10);
		jlpBack.add(liLoading, 1, 0);
		liLoading.start();

		// add Weather title
		lblWeatherTitle = new JLabel();
		lblWeatherTitle.setForeground(Color.WHITE);
		lblWeatherTitle.setFont(fntSerifBold34);
		lblWeatherTitle.setVisible(false); // Made them visible after they finish
												// loading (Looks Cleaner)
		jlpBack.add(lblWeatherTitle, 1, 0);

		// add weather
		txtWeather = new JTextArea();
		txtWeather.setLineWrap(true);
		txtWeather.setWrapStyleWord(true);
		txtWeather.setBackground(Color.BLACK);
		txtWeather.setForeground(Color.WHITE);
		txtWeather.setEditable(false);
		txtWeather.setFont(fntSerif34);
		txtWeather.setVisible(false); // Made them visible after they finish
										// loading (Looks Cleaner)
		jlpBack.add(txtWeather, 1, 0);

		// add empty label that will eventually have the weather icon in it
		lblWeatherImg = new JLabel();
		lblWeatherImg.setVisible(false);
		jlpBack.add(lblWeatherImg, 1, 0);

		// add notes
		txtNotes = new JTextArea();
		txtNotes.setLocation(10, 110);
		txtNotes.setSize(100, 50);
		// frame.add(txtNotes);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private String getTime()
	{
		Calendar forTime = Calendar.getInstance();
		SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm");
		String time = timeFormat.format(forTime.getTime());

		return time;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private String getDate()
	{
		Calendar todaysDate = Calendar.getInstance();
		int month = todaysDate.get(Calendar.MONTH) + 1;
		String dateFormatted = month + "/" + todaysDate.get(Calendar.DATE) + "/" + todaysDate.get(Calendar.YEAR);

		return dateFormatted;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private void changeCursorImage()
	{
		// creates a blank image and then sets that image to be the cursor image
		BufferedImage cursorBlankImage = new BufferedImage(17, 17, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorBlankImage, new Point(0, 0),
				"Blank Cursor");
		jlpBack.getRootPane().setCursor(blankCursor);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private void setLblWeatherTitleSize()
	{
		String formattedLocation = getGeoLocation(latitude, longitude);
		Font lblFont = lblWeatherTitle.getFont();

		int width = lblWeatherTitle.getFontMetrics(lblFont).stringWidth(formattedLocation);
		int height = lblWeatherTitle.getFontMetrics(lblFont).getHeight();
		int xLoc = 10;
		int yLoc = lblTime.getHeight() + lblTime.getY() + 50;

		lblWeatherTitle.setSize(width, height);
		lblWeatherTitle.setLocation(xLoc, yLoc);
		lblWeatherTitle.setText("<HTML><U>" + formattedLocation + "</U></HTML>");
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private String getGeoLocation(BigDecimal xLat, BigDecimal yLng)
	{
		String apiKey = "AIzaSyDVCb9J15u4QH5saVzyVVss_EAznNmsNKs";
		String data = "";
		try
		{
			URL apiUrl = new URL(
					"https://maps.googleapis.com/maps/api/geocode/json?latlng=" + xLat + "," + yLng + "&key=" + apiKey);
			URLConnection apiConnect = apiUrl.openConnection();
			InputStreamReader geoInstrr = new InputStreamReader(apiConnect.getInputStream());
			BufferedReader returnedJSON = new BufferedReader(geoInstrr);
			String dataLine;

			while ((dataLine = returnedJSON.readLine()) != null)
			{
				data += dataLine;
			}
			System.out.println(data);

			String place = getJsonParsed(data);
			return place;
		}
		catch (IOException e)
		{
			
			e.printStackTrace();
		}

		return null;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private String getJsonParsed(String jsonString)
	{
		JSONParser parser = new JSONParser();

		try
		{
			JSONObject obj = (JSONObject) parser.parse(jsonString);
			JSONArray arr = (JSONArray) parser.parse(obj.get("results").toString());
			JSONObject obj2 = (JSONObject) parser.parse(arr.get(2).toString());
			System.out.println(obj2.get("formatted_address"));
			return obj2.get("formatted_address").toString();

			// [formatted_address, types, geometry, address_components,
			// place_id]

		}
		catch (org.json.simple.parser.ParseException e)
		{
			System.out.println(e.getStackTrace());
		}

		return null;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private void setTxtWeatherSizeLoc(String txt)
	{
		Font txtFont = txtWeather.getFont();

		int width = lblWeatherTitle.getWidth();
		int oneLineHeight = txtWeather.getFontMetrics(txtFont).getHeight();
		int height = oneLineHeight * 5;
		int xLoc = 10;
		int yLoc = lblWeatherTitle.getHeight() + lblWeatherTitle.getY() + 30;

		txtWeather.setSize(width, height);
		txtWeather.setLocation(xLoc, yLoc);
		txtWeather.setText(txt);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private void determineIcon(precipType precip)
	{
		ImageIcon icoWeatherImg = null;
		if (precip != null)
		{
			switch (precip)
			{
			case Rain:
				icoWeatherImg = new ImageIcon(getClass().getResource("/resources/Rain-50Edit.png"));
				break;
			case Snow:
				icoWeatherImg = new ImageIcon(getClass().getResource("/resources/Snow-50Edit.png"));
				break;
			case Sleet:
				icoWeatherImg = new ImageIcon(getClass().getResource("/resources/Sleet-50Edit.png"));
				break;
			case PartlyCloudy:
				icoWeatherImg = new ImageIcon(getClass().getResource("/resources/PartlyCloudyDayEdit-50.png"));
				break;
			case Sunny:
				icoWeatherImg = new ImageIcon(getClass().getResource("/resources/Sun-50Edit.png"));
				break;
			case Cloudy:
				icoWeatherImg = new ImageIcon(getClass().getResource("/resources/Cloud-50Edit.png"));
				break;
			case Lightning:
				icoWeatherImg = new ImageIcon(getClass().getResource("/resources/Storm-50Edit.png"));
				break;
			}

			int xLoc = lblWeatherTitle.getWidth() - icoWeatherImg.getIconWidth();
			int yLoc = txtWeather.getY() + (txtWeather.getHeight() / 3);

			lblWeatherImg.setIcon(icoWeatherImg);
			lblWeatherImg.setLocation(xLoc, yLoc);
			lblWeatherImg.setSize(icoWeatherImg.getIconWidth(), icoWeatherImg.getIconHeight());
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	public void handleNewInfoFoundEvent(EventObject e, String str, precipType precip)
	{
		setLblWeatherTitleSize();
		setTxtWeatherSizeLoc(str);
		determineIcon(precip);
		liLoading.stop();
		txtWeather.setVisible(true);
		lblWeatherTitle.setVisible(true);
		lblWeatherImg.setVisible(true);
	}
}
