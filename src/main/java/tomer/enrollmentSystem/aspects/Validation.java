package tomer.enrollmentSystem.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import tomer.enrollmentSystem.LocationValidation;
import tomer.enrollmentSystem.Pupil;
import tomer.enrollmentSystem.School;


/**
 * Created by Tomer on 18/06/2020.
 */
@Aspect
@Configuration
public class Validation {
    private static final int creationFailed = -1;


    @Around("execution(* tomer.enrollmentSystem.PupilService.addPupil(..))")
    public long validatePupilCoordinates(ProceedingJoinPoint proceedingJoinPoint){
        Object[] pupilArgs = proceedingJoinPoint.getArgs();
        try {
            Pupil pupil = (Pupil) pupilArgs[0];
            if (LocationValidation.isLocationValid(pupil.getLat(),pupil.getLon())) {
                Object result = proceedingJoinPoint.proceed();
                return (long) result;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return creationFailed;
    }

    @Around("execution(* tomer.enrollmentSystem.SchoolService.addSchool(..))")
    public long validateSchoolCoordinates(ProceedingJoinPoint proceedingJoinPoint){
        Object[] schoolArgs = proceedingJoinPoint.getArgs();
        try {
            School school = (School) schoolArgs[0];
            if (LocationValidation.isLocationValid(school.getLat(),school.getLon())) {
                Object result = proceedingJoinPoint.proceed();
                return (long) result;
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return creationFailed;
    }

    @Around("execution(* tomer.enrollmentSystem.Pupil.CourseInfo.setgrade(..))")
    public void validateGrades(ProceedingJoinPoint proceedingJoinPoint) {
        Object[] setGradesArgs = proceedingJoinPoint.getArgs();
        long grade = (long) setGradesArgs[0];
        if ((0<=grade) && (grade <= 100))
        {
            try {
                proceedingJoinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}

