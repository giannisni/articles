//package com.database.articles;
//
//import com.database.articles.model.User;
//import com.database.articles.repository.UserRepository;
//import com.database.articles.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//class ArticlesApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//	@Mock
//	private UserRepository userRepository;
//
//	@Autowired
//	private UserService userService;
//
//	//Create test for registerNewUser method
//	@Test
//	void testRegisterNewUser() {
//		//Create a new user
//		User user = new User();
//		user.setUsername("user1");
//		user.setPassword("password");
//
//		//Mock the UserRepository
//		when(userRepository.save(any(User.class))).thenReturn(user);
//
//		//Call the registerNewUser method
//		User result = userService.registerNewUser(user);
//
//		//Check if the result is not null
//		assertNotNull(result);
//		//Check if the username is correct
//		assertEquals("user1", result.getUsername());
//		//Check if the password is correct
//		assertEquals("password", result.getPassword());
//		//Check if the save method is called
//		verify(userRepository).save(any(User.class));
//	}
//
//}
