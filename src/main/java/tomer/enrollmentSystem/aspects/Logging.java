package tomer.enrollmentSystem.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

/**
 * Created by Tomer on 14/06/2020.
 */
@Aspect
@Configuration
public class Logging {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


    @Before("execution(* tomer.enrollmentSystem.PupilService*.*(..))")
    public void beforePupilService(JoinPoint joinPoint){
        //Advice
        logger.info("Before execution by user of {}", joinPoint);
    }

    @Before("execution(* tomer.enrollmentSystem.SchoolService*.*(..))")
    public void beforeSchoolService(JoinPoint joinPoint){
        //Advice
        logger.info("Before execution by user of {}", joinPoint);
    }

    @Before("execution(* tomer.enrollmentSystem.Friends*.*(..))")
    public void beforeFriends(JoinPoint joinPoint){
        //Advice
        logger.info("Before execution by user of {}", joinPoint);
    }
}
