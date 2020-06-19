package tomer.enrollmentSystem;

/**
 * Created by Tomer on 18/06/2020.
 */
public class LocationValidation {
    public static double jerusalemLat1= 31.7507789;
    public static double jerusalemLat2= 31.835148;
    public static double jerusalemLon1= 35.1381233;
    public static double jerusalemLon2= 35.249504;


    public static boolean isLocationValid(double lat, double lon)
    {
        if ((jerusalemLat1<=lat && lat <=jerusalemLat2 ) && ( jerusalemLon1 <= lon && lon <= jerusalemLon2))
        {
            return true;
        }
        return false;
    }

}
