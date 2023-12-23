package AstepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
@Before("@DeletePlace")
public void beforeScenario() throws IOException
{
	//execute this case only when place_id is null
	
	
	StepDefinition m=new StepDefinition();
	if(StepDefinition.place_id==null)
	{
	m.add_place_payload_with("Shruti", "Tamil", "Asia");
	m.user_calls_with_http_request("AddPlaceAPI", "POST");
	m.verify_place_id_created_maps_to_using("Shruti", "getPlaceAPI");
	}
}
}
