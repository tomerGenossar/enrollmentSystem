package tomer.enrollmentSystem;

/**
 * Created by Tomer on 18/06/2020.
 */
public class LocationValidation {
    public static double jerusalemLat1= 31.7507789;
    public static double jerusalemLat2= 31.835148;
    public static double jerusalemLon1= 35.1381233;
    public static double jerusalemLon2= 35.249504;

    public static double isrLat1= 29.4984081;
    public static double isrLat2= 32.7680281;
    public static double isrLon1= 34.9047226;
    public static double isrLon2= 35.7375019;

    public static double lat1= isrLat1;
    public static double lat2= isrLat2;
    public static double lon1= isrLon1;
    public static double lon2= isrLon2;


    public static boolean isLocationValid(double lat, double lon)
    {
        if ((lat1<=lat && lat <=lat2 ) && ( lon1 <= lon && lon <= lon2))
        {
            return true;
        }
        return false;
    }

}
