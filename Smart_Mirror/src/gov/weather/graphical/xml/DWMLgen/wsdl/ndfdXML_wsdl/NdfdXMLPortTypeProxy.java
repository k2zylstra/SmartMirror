package gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl;

public class NdfdXMLPortTypeProxy implements gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType {
  private String _endpoint = null;
  private gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType ndfdXMLPortType = null;
  
  public NdfdXMLPortTypeProxy() {
    _initNdfdXMLPortTypeProxy();
  }
  
  public NdfdXMLPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initNdfdXMLPortTypeProxy();
  }
  
  private void _initNdfdXMLPortTypeProxy() {
    try {
      ndfdXMLPortType = (new gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLLocator()).getndfdXMLPort();
      if (ndfdXMLPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ndfdXMLPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ndfdXMLPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ndfdXMLPortType != null)
      ((javax.xml.rpc.Stub)ndfdXMLPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.NdfdXMLPortType getNdfdXMLPortType() {
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType;
  }
  
  public java.lang.String NDFDgen(java.math.BigDecimal latitude, java.math.BigDecimal longitude, java.lang.String product, java.util.Calendar startTime, java.util.Calendar endTime, java.lang.String unit, gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.WeatherParametersType weatherParameters) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.NDFDgen(latitude, longitude, product, startTime, endTime, unit, weatherParameters);
  }
  
  public java.lang.String NDFDgenLatLonList(java.lang.String listLatLon, java.lang.String product, java.util.Calendar startTime, java.util.Calendar endTime, java.lang.String unit, gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.WeatherParametersType weatherParameters) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.NDFDgenLatLonList(listLatLon, product, startTime, endTime, unit, weatherParameters);
  }
  
  public java.lang.String latLonListSubgrid(java.math.BigDecimal lowerLeftLatitude, java.math.BigDecimal lowerLeftLongitude, java.math.BigDecimal upperRightLatitude, java.math.BigDecimal upperRightLongitude, java.math.BigDecimal resolution) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.latLonListSubgrid(lowerLeftLatitude, lowerLeftLongitude, upperRightLatitude, upperRightLongitude, resolution);
  }
  
  public java.lang.String latLonListLine(java.math.BigDecimal endPoint1Lat, java.math.BigDecimal endPoint1Lon, java.math.BigDecimal endPoint2Lat, java.math.BigDecimal endPoint2Lon) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.latLonListLine(endPoint1Lat, endPoint1Lon, endPoint2Lat, endPoint2Lon);
  }
  
  public java.lang.String latLonListZipCode(java.lang.String zipCodeList) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.latLonListZipCode(zipCodeList);
  }
  
  public java.lang.String latLonListCityNames(java.math.BigInteger displayLevel) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.latLonListCityNames(displayLevel);
  }
  
  public java.lang.String latLonListSquare(java.math.BigDecimal centerPointLat, java.math.BigDecimal centerPointLon, java.math.BigDecimal distanceLat, java.math.BigDecimal distanceLon, java.math.BigDecimal resolution) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.latLonListSquare(centerPointLat, centerPointLon, distanceLat, distanceLon, resolution);
  }
  
  public java.lang.String cornerPoints(java.lang.String sector) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.cornerPoints(sector);
  }
  
  public java.lang.String gmlLatLonList(java.lang.String listLatLon, java.util.Calendar requestedTime, java.lang.String featureType, gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.WeatherParametersType weatherParameters) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.gmlLatLonList(listLatLon, requestedTime, featureType, weatherParameters);
  }
  
  public java.lang.String gmlTimeSeries(java.lang.String listLatLon, java.util.Calendar startTime, java.util.Calendar endTime, java.lang.String compType, java.lang.String featureType, java.lang.String propertyName) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.gmlTimeSeries(listLatLon, startTime, endTime, compType, featureType, propertyName);
  }
  
  public java.lang.String NDFDgenByDay(java.math.BigDecimal latitude, java.math.BigDecimal longitude, java.util.Date startDate, java.math.BigInteger numDays, java.lang.String unit, java.lang.String format) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.NDFDgenByDay(latitude, longitude, startDate, numDays, unit, format);
  }
  
  public java.lang.String NDFDgenByDayLatLonList(java.lang.String listLatLon, java.util.Date startDate, java.math.BigInteger numDays, java.lang.String unit, java.lang.String format) throws java.rmi.RemoteException{
    if (ndfdXMLPortType == null)
      _initNdfdXMLPortTypeProxy();
    return ndfdXMLPortType.NDFDgenByDayLatLonList(listLatLon, startDate, numDays, unit, format);
  }
  
  
}