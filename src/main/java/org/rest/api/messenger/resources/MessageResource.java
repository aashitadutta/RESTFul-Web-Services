package org.rest.api.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.rest.api.messenger.model.Message;
import org.rest.api.messenger.resources.beans.MessageFilterBean;
import org.rest.api.messenger.service.MessageService;

@Path("/messages") //Path level annotation
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	
	//Get request based on query parameters
	//@GET
	//Bean params has various query params 
	public List<Message> getMessagesByBeans(@BeanParam MessageFilterBean filterBean) {
		if((filterBean.getYear()) > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if((filterBean.getStart()) >= 0 && (filterBean.getSize()) >= 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	//Simple Get request
	@GET
	public List<Message> getMessages() {
		return messageService.getAllMessages();
	}
	
	//Simple Post
//	@POST
//	public Message addMessage(Message message) {
//		return messageService.addMessage(message);
//	}
	
	//Post method to conrol response
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
		Message newMessage = messageService.addMessage(message);
		//To send status code - 201 for response
//		return Response.status(Status.CREATED)
//		.entity(newMessage)
//		.build();
		
		//To post response with location headers as URI
		//To avoid hard coding URL, use @Context annotation to get URI info
		String newId = String.valueOf(newMessage.getId());
		
//converting uri to string and add to this new Id and convert whole string again to URI
		//uriInfo.getAbsolutePath().toString() + newId
		
		//adding new Id to existing uri and changing back to URI - Better approach
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				.entity(message)
				.build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	@GET
	@Path("/{messageId}") //method level annotation - variable
	//@Produces(MediaType.APPLICATION_XML) //for xml response
	public Message getMessage(@PathParam("messageId") long messageId, 
			@Context UriInfo uriInfo) { 
		//argument mapped in path is sent in this method
		
		Message message = messageService.getMessage(messageId);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");

		return message;
	}
	
	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build();
		return uri.toString();
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build();
		return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(Long.toString(message.getId()))
				.build()
				.toString();
		return uri;
	}
	
	//Subresource for CommentsClass
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
	
}
