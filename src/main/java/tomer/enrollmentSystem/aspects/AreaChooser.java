package tomer.enrollmentSystem.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import tomer.enrollmentSystem.EnrollmentController;
import tomer.enrollmentSystem.School;
import tomer.enrollmentSystem.SchoolService;

import java.util.List;

/**
 * Created by Tomer on 18/06/2020.
 */
@Aspect
@Configuration
public class AreaChooser {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Before("execution(* tomer.enrollmentSystem.EnrollmentController.homePage(..))")
    public void focusSchoolLocation(JoinPoint joinPoint){
        List<School> schools = SchoolService.getAllStudents();
        if (!schools.isEmpty())
        {
            School lastSchool = schools.get(schools.size() -1);
            double lat = lastSchool.getLat() ;
            double lon = lastSchool.getLon() ;
            String loc = "center: ["+lon+", "+lat+"],\n";
            EnrollmentController.center = loc;
            logger.info("Focused map center at last school at: "+loc);
        }
    }
}
