package org.rest.api.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.rest.api.messenger.model.Comment;
import org.rest.api.messenger.service.CommentService;

//Path level annotation is optional for sub resource
@Path("/")
//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {
	@GET
	public String test() {
		return "new sub resource!";
	}
	private CommentService commentService = new CommentService();
	
//	@GET
//	@Path("/{CommentId}") //subresource and accessing parent resource
//	public String test2(@PathParam("messageId") long messageId ,@PathParam("CommentId") long commentId) {
//		return "Method to return comment Id: " + commentId +
//				" for message: "+ messageId;
//	}
	
//	@GET
//	public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
//		return commentService.getAllComments(messageId);
//	}
//	
//	@POST
//	public Comment addComment(@PathParam("messageId") long messageId, Comment comment) {
//		return commentService.addComment(messageId, comment);
//	}
//	
//	@PUT
//	@Path("/{commentId}")
//	public Comment updateComment(@PathParam("messageId") long messageId ,
//			@PathParam("CommentId") long commentId, Comment comment) {
//		comment.setId(commentId);
//		return commentService.updateComment(messageId, comment);
//	}
//	
//	@DELETE
//	@Path("/{commentId}")
//	public void deleteComment(@PathParam("messageId") long messageId ,
//			@PathParam("CommentId") long commentId) {
//		commentService.removeComment(messageId, commentId);
//	}
//	
//	@GET
//	@Path("/{commentId}")
//	public Comment getComment(@PathParam("messageId") long messageId ,
//			@PathParam("CommentId") long commentId) { 
//		return commentService.getComment(messageId, commentId);
//		
//	}
	
	
}
