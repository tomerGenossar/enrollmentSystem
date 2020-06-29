package tomer.enrollmentSystem.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;

/**
 * Created by Tomer on 21/06/2020.
 */
@Aspect
@Configuration
public class exceptions {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


    @AfterThrowing(value = "execution(* tomer.enrollmentSystem.EnrollmentController*.*(..))", throwing
            = "ex")
    private void generalError(JoinPoint joinPoint, Exception ex)
    {
        int counter = 1;
        logger.info("caught exception at {}", joinPoint.toString());
        logger.info("exception type: {}",ex.toString());
        if (joinPoint.getArgs() != null) {
            for (Object arg : joinPoint.getArgs()) {
                logger.info("Input data for {} , arg number {} was {}", joinPoint, counter, arg);
                counter++;
            }
        }
    }


    @Around("tomer.enrollmentSystem.aspects.Pointcuts.homePage() || tomer.enrollmentSystem.aspects" +
            ".Pointcuts.getPupils() || tomer.enrollmentSystem.aspects.Pointcuts.getSchools()")
    private String getMethodsErrors(ProceedingJoinPoint proceedingJoinPoint)  {
        try {
            proceedingJoinPoint.proceed();
            Object result = proceedingJoinPoint.proceed();
            return (String) result;
        } catch (Throwable throwable) {
            logger.info("caught exception at {}", proceedingJoinPoint.toString());
            logger.info("exception type: {}",throwable.toString());
            throwable.printStackTrace();
            return ("Error at proceeding "+ proceedingJoinPoint.toString());
        }
    }
}
