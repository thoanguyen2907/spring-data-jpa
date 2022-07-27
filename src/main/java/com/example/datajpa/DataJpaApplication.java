package com.example.datajpa;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DataJpaApplication {
	int max = 100;
	int min = 50;
	public static void main(String[] args) {
		SpringApplication.run(DataJpaApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository){
	return args ->  {
		   Faker faker = new Faker();
		    String firstName = faker.name().firstName();
		                String lastName = faker.name().lastName();
		                String email = String.format("%s.%s@amigoscode.edu", firstName, lastName);
		         Student student = new Student(
				                     firstName,
				                     lastName,
				                     email,
				                     faker.number().numberBetween(17, 55));

		    student.addBook(
			                    new Book("Clean Code", LocalDateTime.now().minusDays(4)));


		                student.addBook(
			                    new Book("Think and Grow Rich", LocalDateTime.now()));
	         student.addBook(
	                             new Book("Spring Data JPA", LocalDateTime.now().minusYears(1)));
			 student.addEnronment( new Enrolment( new EnrolmentId(1L, 1L), student, new Course("Computer Science", "IT")));
			 student.addEnronment( new Enrolment(new EnrolmentId(1L, 2L), student,new Course("Finance", "Finance")));

		    //int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
		    //StudentIdCard studentIdCard = new StudentIdCard(random_int ,student);
		    //student.setStudentIdCard(studentIdCard);
		    studentRepository.save(student);
	   studentRepository.findById(1L)
	                       .ifPresent(s -> {
	                           System.out.println("fetch book lazy...");
	                           List<Book> books = student.getBooks();
	                           books.forEach(book -> {
	                               System.out.println(
	                                       s.getFirstName() + " borrowed " + book.getBookName());
	                           });
	                       });

	};
	}
}
