package com.api.impl;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.api.TodoApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repository.TodoRepository;
import com.util.TodoItem;

@Component
@Qualifier("todoService")
public class TodoService implements TodoApi {

	private static final AtomicInteger counter = new AtomicInteger();
	
	@Autowired
	TodoRepository todoRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public List<TodoItem> getTodoItemsList() {
		return todoRepository.getTodoItemsList();
	}

	@Override
	public Response addTodoItem(TodoItem todoItem) {
		int id=nextValue();
		todoItem.setId(id);
		int value=todoRepository.addTodoItem(todoItem);
		String json="",errors="";
		boolean error=false;
		if(value>0){
			try {
				json = objectMapper.writeValueAsString(todoItem);
			} catch (JsonProcessingException e) {
				errors=e.getMessage();
				error=true;
			}
			 if(!error){
				 return Response.ok().type(MediaType.APPLICATION_JSON).entity(json).build();
			 }
		}else{
			errors="Unable to add todo item";
		}
		return Response.status(Status.NO_CONTENT).type(MediaType.APPLICATION_JSON).entity(errors).build();
	}

	@Override
	public Response updateTodoItem(Integer id, TodoItem todoItem) {
		todoItem.setId(id);
		int value=todoRepository.updateTodoItem(todoItem);
		String errors="Unable to add todo item";
		if(value>0){
			return Response.ok().type(MediaType.APPLICATION_JSON).entity("Successfully updated").build();
		}
		return Response.status(Status.NO_CONTENT).type(MediaType.APPLICATION_JSON).entity(errors).build();
	}

	@Override
	public Response deleteTodoItem(Integer id) {
		int value=todoRepository.deleteTodoItem(id);
		String errors="Unable to add todo item";
		if(value>0){
			return Response.ok().type(MediaType.APPLICATION_JSON).entity("Successfully deleted").build();
		}
		return Response.status(Status.NO_CONTENT).type(MediaType.APPLICATION_JSON).entity(errors).build();
	}
	
	public static int nextValue() {
	    return counter.getAndIncrement();
	  }

}
