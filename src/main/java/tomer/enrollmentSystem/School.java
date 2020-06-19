package tomer.enrollmentSystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Class of the School object.
 */
public class School {


    private static long idCounter = 0;
    private double lat;
    private double lon;
    private int minimumGpa;
    private int maxNumberOfPupils;
    private long Id;
    private List<Pupil> studentsEnrolled = new ArrayList<>();

    public School(){}
    public School(double lat, double lon, int minimumGpa, int maxNumberOfPupils)
    {
        this.lat = lat;
        this.lon = lon;
        this.minimumGpa = minimumGpa;
        this.maxNumberOfPupils = maxNumberOfPupils;
        this.setId();
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLon()
    {
        return lon;
    }

    public void setLon(double lon)
    {
        this.lon = lon;
    }

    public int getMinimumGpa()
    {
        return minimumGpa;
    }

    public void setMinimumGpa(int minimumGpa)
    {
        this.minimumGpa = minimumGpa;
    }

    public int getMaxNumberOfPupils()
    {
        return maxNumberOfPupils;
    }

    public void setMaxNumberOfPupils(int maxNumberOfPupils) {
        this.maxNumberOfPupils = maxNumberOfPupils;
    }
    public void setId()
    {
        this.Id = idCounter ;
        idCounter += 1;
    }
    public long getId()
    {
        return Id;
    }

    public boolean isSchoolFull()
    {
        if (studentsEnrolled.size() == maxNumberOfPupils)
        {
            return true;
        }
        return false;
    }
    public List<Pupil> getStudentsEnrolled()
    {
        return studentsEnrolled;
    }

    /**
     * enrolls a student to the current school
     * @param newStudent
     */
    public void enrollStudent(Pupil newStudent)
    {
        newStudent.setSchoolId(Id);
        studentsEnrolled.add(newStudent);
    }

    @Override
    public String toString() {
        String repr;
        repr = "Id: "+Id+" lat: "+lat+" Lon: "+lon+ " min GPA: "+minimumGpa+ " max number of pupils: "+
                maxNumberOfPupils;
        return repr;
    }
}
