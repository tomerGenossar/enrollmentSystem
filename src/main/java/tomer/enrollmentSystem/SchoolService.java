package tomer.enrollmentSystem;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The class keeps a list of all the schools and updates the list
 */
@Service
public class SchoolService {
    private List<School> schoolsList = new ArrayList<>();

    public List<School> getAllStudents()
    {
        return schoolsList;
    }

    /**
     * adds a school to the list and updates the maps at the index page
     * @param curSchool
     * @param isPupilsEmpty - true/false if there is a pupil in the system. Relevant for the
     *                      representation on the map of the first item .
     * @return pupil id
     */
    public long addSchool(School curSchool ,boolean isPupilsEmpty)
    {
        curSchool.setId();
        schoolsList.add(curSchool);
        if (isPupilsEmpty && schoolsList.size() == 1)
        {
            EnrollmentController.IndexPageLocationData = "{\n" +
                    "\"type\": \"Feature\",\n" +
                    "\"geometry\": {\n" +
                    "\"type\": \"Point\",\n" +
                    "\"coordinates\": ["+String.valueOf(curSchool.getLon())+", "+String.valueOf(curSchool.getLat())
                    +"]\n" +
                    "},\n" +
                    "\"properties\": {\n" +
                    "\"title\": \"School " + String.valueOf(curSchool.getId()) + " \",\n" +
                    "\"icon\": \"library\"\n" +
                    "}\n" +
                    "}";
        }
        else
        {
            EnrollmentController.IndexPageLocationData += ", {\n" +
                    "\"type\": \"Feature\",\n" +
                    "\"geometry\": {\n" +
                    "\"type\": \"Point\",\n" +
                    "\"coordinates\": [" + String.valueOf(curSchool.getLon()) + ", " + String.valueOf(curSchool
                    .getLat())
                    + "]\n" +
                    "},\n" +
                    "\"properties\": {\n" +
                    "\"title\": \"School " + String.valueOf(curSchool.getId()) + " \",\n" +
                    "\"icon\": \"library\"\n" +
                    "}\n" +
                    "}";
        }
        return curSchool.getId();
    }

    public School getSchool(Long schoolId)
    {
        for (School curSchool: schoolsList) {
            if (curSchool.getId() == schoolId) {
                return curSchool;
            }
        }
        return null;
    }

    public List<School> getAllSchools()
    {
        return schoolsList;
    }

    public int getSchoolsAmount()
    {
        return schoolsList.size();
    }
}
