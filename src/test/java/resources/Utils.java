package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils 
{
	static RequestSpecification req;
	public RequestSpecification requestSpecification() throws IOException
	{	
		
		if(req==null)
	{	PrintStream log=new PrintStream(new FileOutputStream("logging.txt"));
	/*
		req =new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL")).addQueryParam("key", "qaclick123")
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.JSON).build();
	*/
		req =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.JSON).build();
		return req;
	} 
	return req;	
	//for 1st execution req=null and then in next iterations simply return the
	//req. This will enable log file to retain the content of previous iteration
	// also made the RequestSpecification req--> static so that single instance
	//is used in entire execution. without static, a new instance will be made in
	// each execution
	}
		
	
	//the method getGlobalValue will return any property value, 
	//we just need to provide the name of property in parameter 
	public static String getGlobalValue(String key) throws IOException
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Work\\Shruti_Eclipse\\RestAPI\\src\\test\\java\\resources\\global.properties");
	//	C:\Work\Shruti_Eclipse\RestAPI\src\test\java\resources\global.properties
	//	/RestAPI/src/test/java/resources/global.properties
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	public String getJsonPath(Response response, String key)
	{
	String resp= response.asString();
	JsonPath js=new JsonPath(resp);
	return js.get(key).toString();
	}
	
	
	
	
	
	
	
	
	
}
