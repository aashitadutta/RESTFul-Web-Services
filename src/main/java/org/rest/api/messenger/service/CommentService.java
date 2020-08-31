package org.rest.api.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.rest.api.messenger.database.DatabaseClass;
import org.rest.api.messenger.model.Comment;
import org.rest.api.messenger.model.ErrorMessage;
import org.rest.api.messenger.model.Message;

public class CommentService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public CommentService(){
		messages.put(1L, new Message(1, "Hello rest", "dutta"));
		messages.put(2L, new Message(2, "Hello api", "dutta"));
	}
	
	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		ErrorMessage errorMessage = new ErrorMessage("Not found", 404, 
				"https://cloud.google.com/endpoints/docs/frameworks/java/exceptions");
		Response response = Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
		Message message = messages.get(messageId);
		if(message == null) {
			throw new WebApplicationException(response);
			//throw new WebApplicationException(Status.NOT_FOUND);
		}
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(commentId);
		if(comment == null) {
			//for status not found exception - web application exception known by jax rs
			//throw new WebApplicationException(Status.NOT_FOUND);
			throw new NotFoundException(response); //it has status of not found and we
			//can send response ass well - short cut
		}
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId() <= 0) {
			return null;
		}
		
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
}
