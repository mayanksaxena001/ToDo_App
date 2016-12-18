package com.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.util.TodoItem;

@Path("/service")
public interface TodoApi {

	@GET
	@Path("/items")
	@Produces(MediaType.APPLICATION_JSON)
	List<TodoItem> getTodoItemsList();

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	Response addTodoItem(TodoItem todoItem);

	@POST
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	Response updateTodoItem(@PathParam("id") Integer id, TodoItem todoItem);

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	Response deleteTodoItem(@PathParam("id") Integer id);

}
