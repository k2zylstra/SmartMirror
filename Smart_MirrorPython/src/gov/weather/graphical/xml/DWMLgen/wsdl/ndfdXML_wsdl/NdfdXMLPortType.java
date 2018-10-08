/**
 * NdfdXMLPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl;

public interface NdfdXMLPortType extends java.rmi.Remote {

    /**
     * Returns National Weather Service digital weather forecast data.
     * Supports latitudes and longitudes for the Continental United States,
     * Alaska, Hawaii, Guam, and Puerto Rico only. Allowable values for the
     * input variable "product" are "time-series" and "glance". Allowable
     * values for the input variable "Unit" are "e" for U.S. Standare/English
     * units and "m" for Metric units. For both products, a start and end
     * time (Local) are required.  For the time-series product, the input
     * variable "weatherParameters" has array elements set to "true" to indicate
     * which weather parameters are being requested.  If an array element
     * is set to "false", data for that weather parameter are not to be returned.
     */
    public java.lang.String NDFDgen(java.math.BigDecimal latitude, java.math.BigDecimal longitude, java.lang.String product, java.util.Calendar startTime, java.util.Calendar endTime, java.lang.String unit, gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.WeatherParametersType weatherParameters) throws java.rmi.RemoteException;

    /**
     * Returns National Weather Service digital weather forecast data.
     * Supports latitudes and longitudes for the Continental United States,
     * Alaska, Hawaii, Guam, and Puerto Rico only.  The latitude and longitude
     * are delimited by a comma and multiple pairs are delimited by a space
     * (i.e. 30.00,-77.00 40.00,-90.00). Allowable values for the input variable
     * "product" are "time-series" and "glance". Allowable values for the
     * input variable "Unit" are "e" for U.S. Standare/English units and
     * "m" for Metric units. For both products, a start and end time (Local)
     * are required. For both products, a start and end time (Local) are
     * required.  For the time-series product, the input variable "weatherParameters"
     * has array elements set to "true" to indicate which weather parameters
     * are being requested.  If an array element is set to "false", data
     * for that weather parameter are not to be returned.
     */
    public java.lang.String NDFDgenLatLonList(java.lang.String listLatLon, java.lang.String product, java.util.Calendar startTime, java.util.Calendar endTime, java.lang.String unit, gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.WeatherParametersType weatherParameters) throws java.rmi.RemoteException;

    /**
     * Returns a list of Latitude and longitude pairs for a rectangle
     * defined by its lower left and upper right points.  Provides points
     * in a format suitable for use in calling multi-point functions NDFDgenLatLonList
     * and NDFDgenByDayLatLonList.  Supports latitudes and longitudes for
     * the Continental United States, Alaska, Hawaii, Guam, and Puerto Rico.
     */
    public java.lang.String latLonListSubgrid(java.math.BigDecimal lowerLeftLatitude, java.math.BigDecimal lowerLeftLongitude, java.math.BigDecimal upperRightLatitude, java.math.BigDecimal upperRightLongitude, java.math.BigDecimal resolution) throws java.rmi.RemoteException;

    /**
     * Returns a list of latitude and longitude pairs along a line
     * defined by two points.  Supports latitudes and longitudes for the
     * Continental United States, Alaska, Hawaii, Guam, and Puerto Rico only.
     * Provides points in a format suitable for use in calling multi-point
     * functions NDFDgenLatLonList and NDFDgenByDayLatLonList.
     */
    public java.lang.String latLonListLine(java.math.BigDecimal endPoint1Lat, java.math.BigDecimal endPoint1Lon, java.math.BigDecimal endPoint2Lat, java.math.BigDecimal endPoint2Lon) throws java.rmi.RemoteException;

    /**
     * Returns the latitude and longitude pairs corresponding to a
     * list of one or more zip codes.  Supports zip codes for the Continental
     * United States, Alaska, Hawaii, and Puerto Rico only. Provides points
     * in a format suitable for use in calling multi-point functions NDFDgenLatLonList
     * and NDFDgenByDayLatLonList.
     */
    public java.lang.String latLonListZipCode(java.lang.String zipCodeList) throws java.rmi.RemoteException;

    /**
     * Returns the latitude and longitude pairs corresponding to a
     * predefined list of US cities.  Provides points in a format suitable
     * for use in calling multi-point functions NDFDgenLatLonList and NDFDgenByDayLatLonList.
     * The response also includes a list of city names with the order of
     * the names matching the order of the corresponding point.
     */
    public java.lang.String latLonListCityNames(java.math.BigInteger displayLevel) throws java.rmi.RemoteException;

    /**
     * Returns a list of latitude and longitude pairs in a rectangle
     * defined by a central point and distance from that point in the latitudinal
     * and longitudinal directions.  Supports latitudes and longitudes for
     * the Continental United States, Alaska, Hawaii, Guam, and Puerto Rico
     * only.  Provides points in a format suitable for use in calling multi-point
     * functions NDFDgenLatLonList and NDFDgenByDayLatLonList.
     */
    public java.lang.String latLonListSquare(java.math.BigDecimal centerPointLat, java.math.BigDecimal centerPointLon, java.math.BigDecimal distanceLat, java.math.BigDecimal distanceLon, java.math.BigDecimal resolution) throws java.rmi.RemoteException;

    /**
     * Returns latitude and longitude pairs of the four corners of
     * an NDFD grid.  Provides points in a format suitable for use in calling
     * multi-point functions NDFDgenLatLonList and NDFDgenByDayLatLonList.
     * Supports latitudes and longitudes for the Continental United States,
     * Hawaii, Guam, and Puerto Rico only.  Also provides a minimum resolution
     * for requesting the grid.
     */
    public java.lang.String cornerPoints(java.lang.String sector) throws java.rmi.RemoteException;

    /**
     * Returns National Weather Service digital weather forecast data
     * encoded in GML.  Supports latitudes and longitudes for the Continental
     * United States, Alaska, Hawaii, Guam, and Puerto Rico only.  The latitude
     * and longitude are delimited by a comma and multiple pairs are delimited
     * by a space (i.e. 30.00,-77.00 40.00,-90.00). Allowable values for
     * the input variable "featureType" are "Forecast_Gml2Point", "Forecast_GmlObs",
     * "NdfdMultiPointCoverage", "Ndfd_KmlPoint", and "Forecast_GmlsfPoint".
     * For all feature types a time (UTC) is required to indicate when data
     * is requested.  The input variable "weatherParameters" has array elements
     * set to "true" to indicate which weather parameters are being requested.
     * If an array element is set to "false", data for that weather parameter
     * are not to be returned.
     */
    public java.lang.String gmlLatLonList(java.lang.String listLatLon, java.util.Calendar requestedTime, java.lang.String featureType, gov.weather.graphical.xml.DWMLgen.wsdl.ndfdXML_wsdl.WeatherParametersType weatherParameters) throws java.rmi.RemoteException;

    /**
     * Returns National Weather Service digital weather forecast data
     * encoded in GML.  Supports latitudes and longitudes for the Continental
     * United States, Alaska, Hawaii, Guam, and Puerto Rico only.  The latitude
     * and longitude are delimited by a comma and multiple pairs are delimited
     * by a space (i.e. 30.00,-77.00 40.00,-90.00). Allowable values for
     * the input variable "featureType" are "Forecast_Gml2Point", "Forecast_GmlObs",
     * "NdfdMultiPointCoverage", "Ndfd_KmlPoint", and "Forecast_GmlsfPoint".
     * For all feature types a start and end time (UTC) is required to indicate
     * when data is requested.  a comparison type (IsEqual, Between, GreaterThan,
     * GreaterThan, GreaterThanEqualTo, LessThan, and  LessThanEqualTo).
     * The input variable "propertyName" contains a comma delimited string
     * of NDFD element to indicate which weather parameters are being requested.
     */
    public java.lang.String gmlTimeSeries(java.lang.String listLatLon, java.util.Calendar startTime, java.util.Calendar endTime, java.lang.String compType, java.lang.String featureType, java.lang.String propertyName) throws java.rmi.RemoteException;

    /**
     * Returns National Weather Service digital weather forecast data.
     * Supports latitudes and longitudes for the Continental United States,
     * Hawaii, Guam, and Puerto Rico only.  Allowable values for the input
     * variable "format" are "24 hourly" and "12 hourly".  The input variable
     * "startDate" is a date string representing the first day (Local) of
     * data to be returned. The input variable "numDays" is the integer number
     * of days for which the user wants data. Allowable values for the input
     * variable "Unit" are "e" for U.S. Standare/English units and "m" for
     * Metric units.
     */
    public java.lang.String NDFDgenByDay(java.math.BigDecimal latitude, java.math.BigDecimal longitude, java.util.Date startDate, java.math.BigInteger numDays, java.lang.String unit, java.lang.String format) throws java.rmi.RemoteException;

    /**
     * Returns National Weather Service digital weather forecast data.
     * Supports latitudes and longitudes for the Continental United States,
     * Hawaii, Guam, and Puerto Rico only.  The latitude and longitude are
     * delimited by a comma and multiple pairs are delimited by a space (i.e.
     * 30.00,-77.00 40.00,-90.00). Allowable values for the input variable
     * "format" are "24 hourly" and "12 hourly".  The input variable "startDate"
     * is a date string representing the first day (Local) of data to be
     * returned. The input variable "numDays" is the integer number of days
     * for which the user wants data. Allowable values for the input variable
     * "Unit" are "e" for U.S. Standare/English units and "m" for Metric
     * units.
     */
    public java.lang.String NDFDgenByDayLatLonList(java.lang.String listLatLon, java.util.Date startDate, java.math.BigInteger numDays, java.lang.String unit, java.lang.String format) throws java.rmi.RemoteException;
}
