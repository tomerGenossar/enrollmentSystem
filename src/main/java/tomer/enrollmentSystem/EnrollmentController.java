package tomer.enrollmentSystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The main class of the Enrollment system. handle all the post requests
 */
@RestController
//@RequestMapping("/api")
public class EnrollmentController {
    private static String indexPageBeginning ="<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<meta charset='utf-8' />\n" +
            "<title>Enrollment system - schools and pupils locations</title>\n" +
            "<meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />\n" +
            "<script src='https://api.tiles.mapbox.com/mapbox-gl-js/v1.0.0/mapbox-gl.js'></script>\n" +
            "<link href='https://api.tiles.mapbox.com/mapbox-gl-js/v1.0.0/mapbox-gl.css' rel='stylesheet' />\n" +
            "<style>\n" +
            "body { margin:0; padding:0; }\n" +
            "#map { position:absolute; top:0; bottom:0; width:100%; }\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            " \n" +
            "<div id='map'></div>\n" +
            "<script>\n" +
            "mapboxgl.accessToken = 'pk.eyJ1IjoiZGFubnliMTIzIiwiYSI6ImNqMGljZ256dzAwMDAycXBkdWxwbDgzeXYifQ.Ck5P-0NKPVKAZ6SH98gxxw';\n" +
            "var map = new mapboxgl.Map({\n" +
            "container: 'map',\n" +
            "style: 'mapbox://styles/mapbox/light-v10',\n" +
            "center: [34.5, 32],\n" +
            "zoom: 6\n" +
            "});\n" +
            " \n" +
            "map.on('load', function () {\n" +
            " \n" +
            "map.addLayer({\n" +
            "\"id\": \"points\",\n" +
            "\"type\": \"symbol\",\n" +
            "\"source\": {\n" +
            "\"type\": \"geojson\",\n" +
            "\"data\": {\n" +
            "\"type\": \"FeatureCollection\",\n" +
            "\"features\": [";
    static String IndexPageLocationData ="";
    private static String indexPageEnd = "]\n" +
            "}\n" +
            "},\n" +
            "\"layout\": {\n" +
            "\"icon-image\": \"{icon}-15\",\n" +
            "\"text-field\": \"{title}\",\n" +
            "\"text-font\": [\"Open Sans Semibold\", \"Arial Unicode MS Bold\"],\n" +
            "\"text-offset\": [0, 0.6],\n" +
            "\"text-anchor\": \"top\"\n" +
            "}\n" +
            "});\n" +
            "});\n" +
            "</script>\n" +
            " \n" +
            "</body>\n" +
            "</html>";

    @Autowired
    private  PupilService pupilService;
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private Friends friendsList;

    /**
     * The index page. Located in address http://localhost:8080/. Shows all the schools and pupils on a map
     * @return
     */
    @RequestMapping("/")
    public String homePage()
    {
        return indexPageBeginning + IndexPageLocationData + indexPageEnd;
    }


    @RequestMapping("/pupils")
    public List<Pupil> pupils()
    {
        return pupilService.getAllStudents();
    }

    /**
     * Post request for new pupil. needs to be in the following format:
     Endpoint: /pupil
     Payload:
     {
     Lat: Double,
     Lon: Double,
     Grades : [
     {
     courseName : String,
     grade : Integer
     }
     ]
     }
     * @param curPupil
     * @return pupil id
     */
    @RequestMapping(method = RequestMethod.POST, value ="/pupil")
    public Long addPupil(@RequestBody Pupil curPupil)
    {
        if (schoolService.getSchoolsAmount() ==0)
            return pupilService.addPupil(curPupil , true);
        else
        {
            return pupilService.addPupil(curPupil , false);
        }
    }

    @RequestMapping("/schools")
    public List<School> schools()
    {
        return schoolService.getAllStudents();
    }

    /**
     * Post request for new school. needs to be in the following format:
     *
     Endpoint: /school
     Payload:
     {
     Lat: Double,
     Lon: Double,
     minimumGpa: Integer,
     maxNumberOfPupils: Integer
     }
     Return
     * @param curSchool
     */
    @RequestMapping(method = RequestMethod.POST, value ="/school")
    public Long addSchool(@RequestBody School curSchool)
    {
        if (pupilService.getPupilAmount() == 0)
        {
            return schoolService.addSchool(curSchool, true);
        }
        else {
            return schoolService.addSchool(curSchool, false);
        }
    }

    /**
     * Sets FriendShip between 2 pupils. the relation is symmetric
     * @param pupil1Id
     * @param pupil2Id
     */
    @RequestMapping(method = RequestMethod.POST, value ="/setFriendShip/{pupil1Id}/{pupil2Id}")
    public void setFriends(@PathVariable String pupil1Id, @PathVariable String pupil2Id)
    {
        long curId1 = Long.parseLong(pupil1Id);
        long curId2 = Long.parseLong(pupil2Id);
        friendsList.addFriend(curId1, curId2);
        friendsList.addFriend(curId2, curId1);
    }

    /**
     * Enroll a student to school. Pupil will be enrolled to a school that maximizes the following formula:
     * NUMBER OF FRIENDS IN THE SCHOOL * 1 / DISTANCE FROM SCHOOL(KM) Where the distance
     is calculated using Haversine formula. If the student Does not have any friend enrolled in schools he
     will be enrolled to the closet school. in order to enroll a pupil to a specific school, the pupil GPA
     should be greater than schools minimum GPA and the number of enrolled pupils has to be less than the
     schools max number of pupils.
     * @param pupilId - pupil id to enroll
     */
    @RequestMapping(method = RequestMethod.POST, value="/enroll/{pupilId}")
    public void enrollStudent(@PathVariable String pupilId)
    {
        long idConverted = Long.parseLong(pupilId);
        Pupil curPupil = pupilService.getPupil(idConverted);
        float pupilGPA = curPupil.getGpa();
        ArrayList<Long> pupilFriendsList = friendsList.getFriends(idConverted);
        HashMap<Long,Long> friendsNumberInSchool = new HashMap<>(); // hash map of school id and number of
        // friends in the school
        if (pupilFriendsList == null) {
            enrollStudentNoFriends(pupilGPA, curPupil);
            return;
        }
        //find and count all the relevant schools
        for (Long curId : pupilFriendsList) {
            Long schoolId = pupilService.getPupil(curId).getSchoolId();
            School curSchool = schoolService.getSchool(schoolId);
            if ((!curSchool.isSchoolFull()) && (pupilGPA > curSchool.getMinimumGpa())) {
                if (friendsNumberInSchool.get(schoolId) == null) {
                    long val = 1;
                    friendsNumberInSchool.put(schoolId, val);
                } else {
                    long value = friendsNumberInSchool.get(schoolId);
                    value++;
                    friendsNumberInSchool.put(schoolId, value);
                }
            }
        }
        if (!friendsNumberInSchool.isEmpty())
        {
            enrollStudentWithFriends(friendsNumberInSchool,curPupil);
        }
        else
        {
            enrollStudentNoFriends(pupilGPA, curPupil );
        }
    }

    /**
     * Enrolls a student that have friends at the relevant schools.
     * @param friendsNumberInSchool - hash map of school id and number of friends in the school
     * @param curPupil - pupil to enroll
     */
    private void enrollStudentWithFriends(HashMap<Long,Long> friendsNumberInSchool,Pupil curPupil)
    {
        double maxFormulaVal= -1 ;
        School schoolToEnrol = null;
        for (long key : friendsNumberInSchool.keySet())
        {
            School curSchool = schoolService.getSchool(key);
            double enrollmentFormulaVal = calculateEnrollmentFormula(friendsNumberInSchool.get(key),
                    curPupil.getLat(), curPupil.getLon() ,curSchool.getLat(), curSchool.getLon());
            if (enrollmentFormulaVal > maxFormulaVal) {
                maxFormulaVal = friendsNumberInSchool.get(key);
                schoolToEnrol = curSchool;
            }
        }
        schoolToEnrol.enrollStudent(curPupil);
    }

    /**
     * Enrolls a student that do not have friends at the relevant schools.
     * @param pupilGPA
     * @param curPupil
     */
    private void enrollStudentNoFriends(double pupilGPA, Pupil curPupil) {
        long val = 0;
        double minFormulaVal=0;
        boolean isFirstSchool = true;
        School schoolToEnroll = null;
        for (School curSchool: schoolService.getAllSchools())
        {
            if ((!curSchool.isSchoolFull()) && (pupilGPA > curSchool.getMinimumGpa()))
            {
                double enrollmentFormulaVal = calculateEnrollmentFormula(val, curPupil.getLat(),
                        curPupil.getLon() ,curSchool.getLat(), curSchool.getLon());
                if (isFirstSchool)
                {
                    minFormulaVal = enrollmentFormulaVal;
                    schoolToEnroll = curSchool;
                    isFirstSchool = false ;
                }
                else
                {
                    if (enrollmentFormulaVal < minFormulaVal  )
                    {
                        schoolToEnroll = curSchool;
                    }
                }
            }
        }
        if (schoolToEnroll!= null)
        {
            schoolToEnroll.enrollStudent(curPupil);
        }
    }

    /**
     * calculate the following formula:
     * NUMBER OF FRIENDS IN THE SCHOOL * 1 / DISTANCE FROM SCHOOL(KM) Where the distance
     * is calculated using Haversine formula. If friendsNum==0 returns just the distance.
     * based on: https://gist.github.com/vananth22/888ed9a22105670e7a4092bdcf0d72e4
     * @param friendsNum - number of friends in specific school
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     */
    private double calculateEnrollmentFormula(Long friendsNum, Double lat1, Double lon1, Double lat2, Double
            lon2)
    {
        final int R = 6371; // Radius of the earth
        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
        if (friendsNum ==0)
        {
            return distance;
        }
        return friendsNum/distance;
    }

    /**
     * converts value to Radians
     * @param value
     * @return
     */
    private static Double toRad(Double value)
    {
        return value * Math.PI / 180;
    }

}
