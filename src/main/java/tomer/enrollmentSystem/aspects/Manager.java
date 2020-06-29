package tomer.enrollmentSystem.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import tomer.enrollmentSystem.*;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by Tomer on 19/06/2020.
 */
@Aspect
@Configuration
public class Manager {
    public static boolean firstRun = true;

    private static boolean mostCloseSchoolRegistration = false;

    @Before("tomer.enrollmentSystem.aspects.Pointcuts.homePage()")
    public void manageSystem(){
        if (firstRun)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want to change the map style? Press: 1-dark, " +
                    "2-streets,3-light, or any other key to skip");
            String userInput = scanner.nextLine();
            switch (userInput)
            {
                case "1":
                {
                    EnrollmentController.style= "style: 'mapbox://styles/mapbox/dark-v10',\n";
                    break;
                }
                case "2":
                {
                    EnrollmentController.style= "style: 'mapbox://styles/mapbox/streets-v10',\n";
                    break;
                }
                case "3":
                {
                    EnrollmentController.style= "style: 'mapbox://styles/mapbox/light-v10',\n";
                    break;
                }
            }
            System.out.println("The Chosen style is: " + userInput);
            System.out.println("Choose System location, Press: 1-All Israel, 2-Jerusalem");
            userInput = scanner.nextLine();
            if (Objects.equals(userInput, "2"))
            {
                changeLocationToJLM();
            }
            System.out.println("Do you want to change the registration method to most close school , " +
                    "Press: 1-yes, 2-no");
            if (Objects.equals(userInput, "2"))
            {
                mostCloseSchoolRegistration = true;

            }
            firstRun = false;
            scanner.close();
        }
    }

    @Around("execution(* tomer.enrollmentSystem.EnrollmentController.enrollStudent(..))")
    private void changeEnrollmentMethod(ProceedingJoinPoint proceedingJoinPoint)
    {
        if (mostCloseSchoolRegistration)
        {
            Object[] setGradesArgs = proceedingJoinPoint.getArgs();
            long studentId = (long) setGradesArgs[0];
            Pupil pupil = PupilService.getPupil(studentId);
            double pupilLat = pupil.getLat();
            double pupilLon = pupil.getLon();
            List<School> schools = SchoolService.getAllStudents();
            School closetSchool = null;
            double minDistance=-1;
            for (School school:schools) {
                double schoolLat = school.getLat();
                double schoolLon = school.getLon();
                double distance = EnrollmentController.calculateEnrollmentFormula((long) 1.0,pupilLat,pupilLon,
                        schoolLat,schoolLon);
                if (minDistance == -1)
                {
                    minDistance = distance;
                    closetSchool = school;
                }
                if (minDistance < distance)
                {
                    closetSchool = school;
                }
            }
            if (closetSchool!= null) {
                closetSchool.enrollStudent(pupil);
            }
        }
        else
        {
            try {
                proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private void changeLocationToJLM() {

        LocationValidation.lat1 = LocationValidation.jerusalemLat1;
        LocationValidation.lat2 = LocationValidation.jerusalemLat2;
        LocationValidation.lon1 = LocationValidation.jerusalemLon1;
        LocationValidation.lon2 = LocationValidation.jerusalemLon2;

    }
}
