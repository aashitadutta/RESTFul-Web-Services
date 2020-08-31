package org.rest.api.messenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.rest.api.messenger.model.ErrorMessage;

//Mapping error to response to exceptions
@Provider //this is used to register this class to jax rs
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

	//catch exceptions and send it back
	@Override
	public Response toResponse(DataNotFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404, 
				"https://cloud.google.com/endpoints/docs/frameworks/java/exceptions");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}

}
