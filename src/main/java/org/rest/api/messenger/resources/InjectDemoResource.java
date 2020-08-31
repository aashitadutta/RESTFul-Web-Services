package org.rest.api.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
			                               @HeaderParam("customeHeaderValue") String header,
			                               @CookieParam("namee") String cookie)
	//formParams are also there
	//Matrrix param similar to query param except it uses ; instead of ? 
	//e.g. ;param=value
	{
		return "Matrix Param" + matrixParam + "Header value " + header + "Cookie: " + cookie;
	}
	
	@GET
	@Path("context")
	//Context used to fetch the URI information and set it as response
	//context can be annotated to few special types
	public String getParamsUsingContext(@Context UriInfo uriInfo, //injects uriinfo
			                            @Context HttpHeaders headers) { 
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = headers.getCookies().toString();
		return "path = " + path + "\ncookies = "+ cookies;
	}
	
}
