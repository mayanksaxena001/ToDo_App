package com.repository;

import java.util.List;

import com.util.TodoItem;

public interface TodoRepository {

	public List<TodoItem> getTodoItemsList();
	
	public int addTodoItem(TodoItem todoItem);
	
	public int updateTodoItem(TodoItem todoItem);
	
	public int deleteTodoItem(Integer id);
}
