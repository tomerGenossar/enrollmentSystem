package tomer.enrollmentSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The class keeps a list of all the pupils and updates the list.
 */
@Service
public class PupilService {

//    @Autowired
//    PupilRepo pupilRepository;

    private static List<Pupil> pupilsList = new ArrayList<>();

    public List<Pupil> getAllStudents()
    {
        return pupilsList;
    }

    /**
     * adds a pupil to the list and updates the maps at the index page
     * @param curPupil
     * @param isSchoolEmpty - true/false if there is a school in the system. Relevant for the
     *                      representation on the map of the first item .
     * @return pupil id
     */
    public long addPupil(Pupil curPupil, boolean isSchoolEmpty)
    {
        curPupil.setId();
        pupilsList.add(curPupil);
        if (pupilsList.size() == 1 && isSchoolEmpty )
        {
            EnrollmentController.IndexPageLocationData = "{\n" +
                    "\"type\": \"Feature\",\n" +
                    "\"geometry\": {\n" +
                    "\"type\": \"Point\",\n" +
                    "\"coordinates\": ["+String.valueOf(curPupil.getLon())+", "+String.valueOf(curPupil.getLat())
                    +"]\n" +
                    "},\n" +
                    "\"properties\": {\n" +
                    "\"title\": \"Student " + String.valueOf(curPupil.getId()) + " \",\n" +
                    "\"icon\": \"pitch\"\n" +
                    "}\n" +
                    "}";
        }
        else
        {
            EnrollmentController.IndexPageLocationData += ", {\n" +
                    "\"type\": \"Feature\",\n" +
                    "\"geometry\": {\n" +
                    "\"type\": \"Point\",\n" +
                    "\"coordinates\": [" + String.valueOf(curPupil.getLon()) + ", " + String.valueOf(curPupil.getLat())
                    + "]\n" +
                    "},\n" +
                    "\"properties\": {\n" +
                    "\"title\": \"Student " + String.valueOf(curPupil.getId()) + " \",\n" +
                    "\"icon\": \"pitch\"\n" +
                    "}\n" +
                    "}";
        }
        return curPupil.getId();
    }

    public static Pupil getPupil(long pupilId)
    {
        for (Pupil curPupil: pupilsList)
        {
             if (curPupil.getId() == pupilId)
                 return curPupil;
        }
        return null;
    }

    public int getPupilAmount()
    {
        return pupilsList.size();
    }
}

