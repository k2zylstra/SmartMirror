package smartMirror;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLLocator;
import gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType;
import gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.WeatherParametersType;;

public class TextGrabber
{
	private BigDecimal xCoordLat;
	private BigDecimal yCoordLon;
	
	private String rawData;
	private String rawDataBackup;
	private precipType precip;

	private int counter = 0;
	private List<Integer> apparentTemp;
	private List<Integer> maximumTemp;
	private List<Integer> minimumTemp;
	private List<Integer> temperature;

	private List<UIWindow> _listeners = new ArrayList<UIWindow>();


	// This sets a timer that runs every five minutes (kicks off the textgrabber
	// class)
	public TextGrabber()
	{
		apparentTemp = new ArrayList<Integer>();
		maximumTemp = new ArrayList<Integer>();
		minimumTemp = new ArrayList<Integer>();
		temperature = new ArrayList<Integer>();

		Timer updateTimer = new Timer();
		TimerTask ttrunDataUpdate = new TimerTask()
		{
			@Override
			public void run()
			{
				grabText(xCoordLat, yCoordLon);
			}
		};

		// Starts timer
		updateTimer.schedule(ttrunDataUpdate, 300000, 300000);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// Creates a method that is able to add the UIWindow as an event listener
	public synchronized void addEventListener(UIWindow listener)
	{
		_listeners.add(listener);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// Creates a method that is able to remove an event listener
	public synchronized void removeEventListener(NewInfoFound listener)
	{
		_listeners.remove(listener);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// to fire the event
	private synchronized void fireEvent(String weatherData)
	{
		// Creates an instance of the event object NewInfoFound
		NewInfoFound foundInfo = new NewInfoFound(this);
		Iterator<UIWindow> i = _listeners.iterator();

		while (i.hasNext())
		{
			((NewInfoFoundListener) i.next()).handleNewInfoFoundEvent(foundInfo, weatherData, precip);
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// TODO create method that determines other three icons
	public void grabText(BigDecimal lat, BigDecimal lon)
	{
		xCoordLat = lat;
		yCoordLon = lon;
		

		counter++;
		System.out.println(counter);

		String dataIn = "";

		// The Parameters to be passed into the weather web service
		
		Calendar c = Calendar.getInstance();
		Calendar cT = Calendar.getInstance();
		cT.add(Calendar.DATE, 1);
		WeatherParametersType weatherParameters = new WeatherParametersType(true, true, true, false, false, false,
				false, true, true, true, true, false, true, false, true, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false, false, true, false, false, false, false, false, false,
				false, false, false, false);
		NdfdXMLLocator weatherLocator = new NdfdXMLLocator();
		NdfdXMLPortType weatherReturn;

		try
		{
			// Gets the XML file as a string and assigns it the variable dataIn
			weatherReturn = weatherLocator.getndfdXMLPort();
			dataIn = (weatherReturn.NDFDgen(lat, lon, "time-series", c, cT, "e", weatherParameters));
			rawData = dataIn;
			logData(dataIn);

			// Creates the XML document from dataIn
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parseBuild = factory.newDocumentBuilder();
			InputSource inXMLInputSource = new InputSource();
			inXMLInputSource.setCharacterStream(new StringReader(dataIn.replaceAll(">\\s.*?<", "><")));
			Document returnedXML = parseBuild.parse(inXMLInputSource);

			// Determines whether or not to fire the event
			if (rawData != rawDataBackup)
			{
				String weatherData = format(returnedXML);

				logData(weatherData);
				fireEvent(weatherData);

				rawDataBackup = rawData;
			}
		}
		catch (ServiceException e)
		{
			String errMsgInfo = "There Was a Fatal Error";
			JOptionPane.showMessageDialog(null, errMsgInfo, "Error", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
		catch (RemoteException e)
		{
			String errMsgInfo = "No Internet was detected. \n please connect to the internet and\n restart the application";
			JOptionPane.showMessageDialog(null, errMsgInfo, "Error", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
		catch (Exception e)
		{
			System.out.println("Error: \n\n");
			e.printStackTrace();
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private void logData(String data) throws IOException
	{
		String directory = System.getProperty("user.dir");
		String location = directory + "/log" + Calendar.getInstance().getTime();
		String dataTemp = data;
		data = "";
		
		File log = new File(location);
		if(log.exists())
		{
			FileReader fr = new FileReader(location);
			BufferedReader br = new BufferedReader(fr);
			
			while((br.readLine()) != null)
			{
				data += br.readLine();
			}
			br.close();
		}
		data += "\n\n" + dataTemp;
		
		FileWriter logWriter = new FileWriter(location);
		BufferedWriter bwLog = new BufferedWriter(logWriter);
		
		bwLog.append(data);
		bwLog.close();
		logWriter.close();
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private String format(Document returnedXML)
	{
		String eol = System.getProperty("line.separator");

		// Sets the temperature global
		NodeList temperatureNodes = returnedXML.getElementsByTagName("temperature");
		setTemperature(temperatureNodes);

		// Sets the precipitation type
		NodeList precipitationNodes = returnedXML.getElementsByTagName("weather-conditions");
		String strPrecipitation = getPrecipitation(precipitationNodes);

		// Sets the wind direction global
		NodeList windDirectionNodes = returnedXML.getElementsByTagName("direction");
		List<Integer> windDirections = getWindDirection(windDirectionNodes);

		// Sets the wind speed global
		NodeList windSpeedNodes = returnedXML.getElementsByTagName("wind-speed");
		List<Integer> windSpeeds = getWindSpeed(windSpeedNodes);

		List<String> strWindDirection = formatWind(windDirections);
		
		if(temperature.isEmpty())
		{
			temperature.add(0);
		}
		if(maximumTemp.isEmpty())
		{
			maximumTemp.add(0);
		}
		if(windSpeeds.isEmpty())
		{
			windSpeeds.add(0);
		}
		if(strWindDirection.isEmpty())
		{
			strWindDirection.add("");
		}
		String strTemperature = "Temperature: " + temperature.get(0) + eol + "  -High of " + maximumTemp.get(0) + eol
				+ "  -Low of " + minimumTemp.get(0);
		String toReturn = strTemperature + eol + "Wind: " + windSpeeds.get(0) + " mph " + strWindDirection.get(0) + "\n"
				+ strPrecipitation;
		
		return toReturn;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private List<String> formatWind(List<Integer> windDirections)
	{
		List<String> strListWindDirection = new ArrayList<String>();

		for (int i = 0; i < windDirections.size(); i++)
		{
			if (windDirections.get(i) > 348.75 || windDirections.get(i) < 11.25)
			{
				strListWindDirection.add("North");
			}
			else if (windDirections.get(i) > 11.25 && windDirections.get(i) < 33.75)
			{
				strListWindDirection.add("North NorthEast");
			}
			else if (windDirections.get(i) > 33.75 && windDirections.get(i) < 56.25)
			{
				strListWindDirection.add("NorthEast");
			}
			else if (windDirections.get(i) > 56.25 && windDirections.get(i) < 78.75)
			{
				strListWindDirection.add("East NorthEast");
			}
			else if (windDirections.get(i) > 78.75 && windDirections.get(i) < 101.25)
			{
				strListWindDirection.add("East");
			}
			else if (windDirections.get(i) > 101.25 && windDirections.get(i) < 123.75)
			{
				strListWindDirection.add("East SouthEast");
			}
			else if (windDirections.get(i) > 123.75 && windDirections.get(i) < 146.25)
			{
				strListWindDirection.add("SouthEast");
			}
			else if (windDirections.get(i) > 146.25 && windDirections.get(i) < 168.75)
			{
				strListWindDirection.add("South SouthEast");
			}
			else if (windDirections.get(i) > 168.75 && windDirections.get(i) < 191.25)
			{
				strListWindDirection.add("South");
			}
			else if (windDirections.get(i) > 191.25 && windDirections.get(i) < 213.75)
			{
				strListWindDirection.add("South SouthWest");
			}
			else if (windDirections.get(i) > 213.75 && windDirections.get(i) < 236.25)
			{
				strListWindDirection.add("SouthWest");
			}
			else if (windDirections.get(i) > 236.25 && windDirections.get(i) < 258.75)
			{
				strListWindDirection.add("West SouthWest");
			}
			else if (windDirections.get(i) > 258.75 && windDirections.get(i) < 281.25)
			{
				strListWindDirection.add("West");
			}
			else if (windDirections.get(i) > 281.25 && windDirections.get(i) < 303.75)
			{
				strListWindDirection.add("West NorthWest");
			}
			else if (windDirections.get(i) > 303.75 && windDirections.get(i) < 326.25)
			{
				strListWindDirection.add("NorthWest");
			}
			else if (windDirections.get(i) > 326.25 && windDirections.get(i) < 348.75)
			{
				strListWindDirection.add("North NorthWest");
			}
		}
		return strListWindDirection;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	// Sets the temperature global and is passed in a list of all the
	// temperature nodes
	private void setTemperature(NodeList toParseTemp)
	{
		// Iterates through all the first level temperature nodes
		for (int i = 0; i < toParseTemp.getLength(); i++)
		{
			Node tempNode = toParseTemp.item(i);

			if (toParseTemp.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				NodeList tempValues = tempNode.getChildNodes();

				// Iterates through all the second level nodes and looks for
				// certain names
				for (int j = 0; j < tempValues.getLength(); j++)
				{
					if (tempValues.item(j).getNodeType() == Node.ELEMENT_NODE)
					{
						// checks to see which of the nodes has the correct node
						// names
						// then it takes the next node containing the values (j
						// + 1)
						if (tempValues.item(j).getTextContent().equals("Apparent Temperature"))
						{
							Node apparT = tempValues.item(j).getNextSibling();
							if (apparT.getNodeName().equals("value"))
							{
								apparentTemp.add(Integer.parseInt(apparT.getTextContent()));
							}
						}
						else if (tempValues.item(j).getTextContent().equals("Daily Maximum Temperature"))
						{
							Node maxT = tempValues.item(j).getNextSibling();
							if (maxT.getNodeName().equals("value"))
							{
								maximumTemp.add(Integer.parseInt(maxT.getTextContent()));
							}
						}
						else if (tempValues.item(j).getTextContent().equals("Daily Minimum Temperature"))
						{
							Node minT = tempValues.item(j).getNextSibling();
							if (minT.getNodeName().equals("value"))
							{
								minimumTemp.add(Integer.parseInt(minT.getTextContent()));
							}
						}
						else if (tempValues.item(j).getTextContent().equals("Temperature"))
						{
							Node temp = tempValues.item(j).getNextSibling();
							if (temp.getNodeName().equals("value"))
							{
								temperature.add(Integer.parseInt(temp.getTextContent()));
							}
						}
					}
				}
			}
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private String getPrecipitation(NodeList toParsePrecip)
	{
		for (int i = 0; i < toParsePrecip.getLength(); i++)
		{
			NodeList weatherConditionsChild = toParsePrecip.item(i).getChildNodes();

			for (int j = 0; j < weatherConditionsChild.getLength(); j++)
			{
				if (weatherConditionsChild.item(j).getNodeName().equals("value"))
				{
					String weatherAttrValue = weatherConditionsChild.item(j).getAttributes()
							.getNamedItem("weather-type").getNodeValue();
					weatherAttrValue = weatherAttrValue.toLowerCase();
					
					if (weatherAttrValue.contains("rain"))
					{
						precip = precipType.Rain;
						return "Rain";
					}
					else if (weatherAttrValue.contains("rain"))
					{
						precip = precipType.Snow;
						return "Sleet";
					}
					else if (weatherAttrValue.contains("sleet"))
					{
						precip = precipType.Sleet;
						return "Sleet";
					}
				}
			}
		}
		return null;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private List<Integer> getWindDirection(NodeList toParseWindDirection)
	{
		List<Integer> directionList = new ArrayList<Integer>();

		for (int i = 0; i < toParseWindDirection.getLength(); i++)
		{
			Node windDirectionNode = toParseWindDirection.item(i);

			if (windDirectionNode.getNodeType() == Node.ELEMENT_NODE)
			{
				NodeList windDirectionValues = windDirectionNode.getChildNodes();

				for (int j = 0; j < windDirectionValues.getLength(); j++)
				{
					if (windDirectionValues.item(j).getNodeType() == Node.ELEMENT_NODE)
					{
						if (windDirectionValues.item(j).getNodeName() == "value")
						{
							directionList.add(Integer.parseInt(windDirectionValues.item(j).getTextContent()));
						}
					}
				}
			}
		}
		return directionList;
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	private List<Integer> getWindSpeed(NodeList toParseWindSpeed)
	{
		List<Integer> speedList = new ArrayList<Integer>();

		for (int i = 0; i < toParseWindSpeed.getLength(); i++)
		{
			Node windSpeedNode = toParseWindSpeed.item(i);

			if (windSpeedNode.getNodeType() == Node.ELEMENT_NODE)
			{
				NodeList windSpeedValues = windSpeedNode.getChildNodes();

				for (int j = 0; j < windSpeedValues.getLength(); j++)
				{
					if (windSpeedValues.item(j).getNodeName() == "value")
					{
						speedList.add(Integer.parseInt(windSpeedValues.item(j).getTextContent()));
					}
				}
			}
		}
		return speedList;
	}
}
