package tomer.enrollmentSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Runs the application
 */
@SpringBootApplication
//@EnableSwagger2
public class EnrollmentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnrollmentSystemApplication.class, args);
	}
}
