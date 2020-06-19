package tomer.enrollmentSystem;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.ArrayList;
import java.util.List;

/**
 * Pupil object class
 */
public class Pupil {
//    @Id
//    @GeneratedValue
    private static long idCounter = 0;
    private double Lat;
    private double Lon;
    private long schoolId = -1;
    private List<CourseInfo> Grades = new ArrayList<>();
    private long id;

    /**
     * Inner class for the courses info ( pair of course name and grade)
     */
    public static class CourseInfo
    {
        private String courseName;
        private int grade;
        public CourseInfo(){};
        public CourseInfo(String courseName, int grade)
        {
            this.courseName = courseName;
            this.grade = grade;
        }

        public String getcourseName()
        {
            return courseName;
        }

        public void setcourseName(String courseName)
        {
            this.courseName = courseName;
        }

        public int getgrade() {
            return grade;
        }

        public void setgrade(int grade)
        {
            this.grade = grade;
        }
    }

    public Pupil()
    {
        Lat = 0.0;
        Lon = 0.0;
    }
    public Pupil(double Lat, double lon, List<CourseInfo> courses )
    {
        this.Lat = Lat;
        Lon = lon;
        Grades = courses;
//        setId();
    }

    public void setGrades(List<CourseInfo> grades)
    {
        Grades = grades;
    }

    public void setId()
    {
        this.id = idCounter ;
        idCounter+=1;
    }
//    public void setid(Long id)
//    {
//        this.id = id;
//    }
    public Long getid(Long id)
    {
        return id;
    }

    public long getId()
    {
        return id;
    }

    public long getSchoolId()
    {
        return schoolId;
    }

    public void setSchoolId(long schoolId)
    {
        this.schoolId = schoolId;
    }
    public float getGpa()
    {
        float sum = 0;
        int counter = 0;
        for (CourseInfo course:Grades ) {
            counter++;
            sum += course.getgrade();
        }
        return sum/counter;
    }

    @Override
    public String toString() {
        String repr;
        repr = "Id: "+id+" lat: "+Lat+" Lon: "+Lon;
        return repr;
    }

    @JsonProperty("Lat")
    public double getLat()
    {
        return Lat;
    }
    @JsonProperty("Lon")
    public double getLon()
    {
        return Lon;
    }
    @JsonProperty("Lat")
    public void setLat(double lat)
    {
        this.Lat = lat;
    }
    @JsonProperty("Lon")
    public void setLon(double lon)
    {
        this.Lon = lon;
    }
    @JsonProperty("Grades")
    public List<CourseInfo> getGrades()
    {
        return Grades;
    }
}
