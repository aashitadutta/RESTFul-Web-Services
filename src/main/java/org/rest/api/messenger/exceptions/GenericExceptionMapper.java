package org.rest.api.messenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import org.rest.api.messenger.model.ErrorMessage;

public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	//catch exceptions and send it back
	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 500, 
				"https://cloud.google.com/endpoints/docs/frameworks/java/exceptions");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.build();
	}

}
