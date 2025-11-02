package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTest2 {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setData() {
		faker=new Faker();
		userPayload=new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		logger=LogManager.getLogger(this.getClass());		
	}
	@Test(priority = 1)
	public void testPostUser() {
		
		logger.info("*******creating the user******");
		Response res= UserEndPoints2.createUser(userPayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
		logger.info("*******user created******");
	}
	
	@Test(priority = 2)
	public void testGetUserbyName() throws InterruptedException {
		logger.info("******Reading the user********");
		Response res= UserEndPoints2.readUser(this.userPayload.getUsername());
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
	
	}
	
	@Test(priority = 3)
	public void testUpdateUser() {
		logger.info("******updating the user********");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response res= UserEndPoints2.updateUser(this.userPayload.getUsername(),userPayload);
		res.then().log().body().statusCode(200);
		Assert.assertEquals(res.getStatusCode(),200);
		logger.info("******User updated********");
	}
	
	@Test(priority = 4)
	public void testDeleteUser() {
		logger.info("******Deleting the user********");
		Response res= UserEndPoints2.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(res.getStatusCode(),200);
		logger.info("******User Deleted********");
	}

}
