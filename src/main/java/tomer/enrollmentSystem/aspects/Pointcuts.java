package tomer.enrollmentSystem.aspects;

import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Tomer on 21/06/2020.
 */
public class Pointcuts {

    @Pointcut("execution(* tomer.enrollmentSystem.EnrollmentController*.*(..))")
    public void endrollemtControllerPointcut() { }

    @Pointcut("execution(* tomer.enrollmentSystem.EnrollmentController.pupils(..))")
    public void getPupils() { }

    @Pointcut("execution(* tomer.enrollmentSystem.EnrollmentController.schools(..))")
    public void getSchools() { }

    @Pointcut("execution(* tomer.enrollmentSystem.EnrollmentController.homePage(..))")
    public void homePage() { }
}
