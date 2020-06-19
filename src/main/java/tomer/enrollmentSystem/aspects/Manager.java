package tomer.enrollmentSystem.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import tomer.enrollmentSystem.EnrollmentController;

import java.util.Scanner;

/**
 * Created by Tomer on 19/06/2020.
 */
@Aspect
@Configuration
public class Manager {
    public static boolean firstRun = true;


    @Before("execution(* tomer.enrollmentSystem.EnrollmentController.homePage(..))")
    public void focusSchoolLocation(){
        if (firstRun)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Do you want to change the map style? Press: 1-dark, " +
                    "2-streets,3-light, or any other key to skip");
            String styleOption = scanner.nextLine();
            scanner.close();
            switch (styleOption)
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
            System.out.println("The Chosen style is: " + styleOption);
            firstRun = false;
        }
    }
}
