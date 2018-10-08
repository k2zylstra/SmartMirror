package smartMirror;

import java.math.BigDecimal;

public class Main
{
	public static void main(String[] args)
	{
		BigDecimal lat;
		BigDecimal lng;
		
		String strLat = args[0];
		String strLng = args[1];
		
		//String strLat = "47.184408";
		//String strLng = "-122.346984";
		
		double intLat = Double.parseDouble(strLat);
		double intLng = Double.parseDouble(strLng);
		
		lat = new BigDecimal(intLat);
		lng = new BigDecimal(intLng);
		
		UIWindow window = new UIWindow();
		window.Load(lat, lng);
	}
}
