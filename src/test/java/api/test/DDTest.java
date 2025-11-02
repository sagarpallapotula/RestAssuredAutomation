package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.utilities.DataProviders;
import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class DDTest {
	
	
	@Test(priority = 1, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testPostUser(String UserID,String UserName,String FirstName,String LastName,String Email,String Password,String Phone) {
		User userPayload=new User();
		userPayload.setId(Integer.parseInt(UserID));
		userPayload.setUsername(UserName);
		userPayload.setFirstName(FirstName);
		userPayload.setLastName(LastName);
		userPayload.setEmail(Email);
		userPayload.setPassword(Password);
		userPayload.setPhone(Phone);
		Response res= UserEndPoints.createUser(userPayload);
		res.then().log().all();
		Assert.assertEquals(res.getStatusCode(),200);
	}
	
	@Test(priority = 2,dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUser(String UserName) {
		Response res= UserEndPoints.deleteUser(UserName);
		Assert.assertEquals(res.getStatusCode(),200);
	}


	
}
