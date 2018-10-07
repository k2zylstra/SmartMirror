package smartMirror;

import java.util.EventObject;

public class NewInfoFound extends EventObject
{
	//private String weatherData;
	//public String getWeatherData() {return weatherData; }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NewInfoFound(Object source)
	{
		super(source);
	}
}

interface NewInfoFoundListener
{
	public void handleNewInfoFoundEvent(EventObject e, String str, precipType precip);
}

enum precipType
{
	Rain,
	Snow,
	Sleet,
	Sunny,
	PartlyCloudy,
	Cloudy,
	Lightning
}