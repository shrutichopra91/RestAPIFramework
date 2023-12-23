package AstepDefinitions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import static io.restassured.RestAssured.given;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import io.restassured.*;
import io.restassured.builder.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.*;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;


public class StepDefinition extends Utils
{
	RequestSpecification req;
	ResponseSpecification resspec;
	RequestSpecification res;
	Response response;
	TestDataBuild data=new TestDataBuild();
	static String place_id;
	@Given("Add Place Payload with {string}  {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException 
	{
/* spec(requestSpecification()) as we have now extended utils class
 * so we can call requestSpecification() method directly without any object */
		
		res=given().spec(requestSpecification()).body(data.addPlacePayload(name,language,address));
  
	}
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) 
	{
	//APIResources is enum
	//valueOf will make call to constructor
	//here the object 'resourceAPI' gets loaded with value of resource type(add,get or delete)
	APIResources resourceAPI=APIResources.valueOf(resource);
	System.out.println(resourceAPI.getResource());
	
	resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();	

	if(method.equalsIgnoreCase("POST"))
	response=res.when().post(resourceAPI.getResource());
	else if (method.equalsIgnoreCase("GET")) 
	response=res.when().get(resourceAPI.getResource());	
	
	}
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) 
	{
		assertEquals(response.getStatusCode(),200);
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) 
	{
		assertEquals(getJsonPath(response,keyValue),Expectedvalue);
	}
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String name, String resource) throws IOException 
	{
		place_id=getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id",place_id);
	//	APIResources resourceAPI=APIResources.valueOf(resource);
	//	response=res.when().get(resourceAPI.getResource());	
	//  above 2 steps are correct but we have a function with same
	//	job. so we can use it
		user_calls_with_http_request(resource, "GET");
		assertEquals(getJsonPath(response,"name"),name);
  
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException 
	{
	res=given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));	
	}




}
