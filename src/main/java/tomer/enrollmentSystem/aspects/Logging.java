package tomer.enrollmentSystem.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import tomer.enrollmentSystem.Pupil;

import java.util.logging.Logger;

/**
 * Created by Tomer on 14/06/2020.
 */
@Aspect
@Configuration
public class Logging {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


    @Before("execution(* tomer.enrollmentSystem.PupilService*.*(..)) || execution(* tomer.enrollmentSystem" +
            ".SchoolService*.*(..)) || execution(* tomer.enrollmentSystem.Friends*.*(..))")
    public void beforePupilService(JoinPoint joinPoint) {
        int counter = 1;
        logger.info(" before {}", joinPoint);
        if (joinPoint.getArgs() != null) {
            for (Object arg : joinPoint.getArgs()) {
                logger.info("Input data for {} , arg number {} is {}", joinPoint, counter, arg);
                counter++;
            }
        }
    }
}

