package demo.Shift_Managar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import demo.Shift_Managar.model.User;
import demo.Shift_Managar.model.UserRole;
import demo.Shift_Managar.repository.UserRepository;

@SpringBootApplication
public class ShiftManagarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShiftManagarApplication.class, args);
	}

}