package com.repository.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.repository.TodoRepository;
import com.util.TodoItem;
import com.util.sqlParameterSourcefactory;

@Component
public class TodoRepositoryImpl implements TodoRepository{

	private static final String SELECT_ALL_TODO_ITEMS = "select id,text,created_date,due_date from todo_items";
	private static final String ADD_NEW_TODO_ITEM = "Insert into todo_items(id,text,created_date,due_date) values(:id,:text,:createdDate,:dueDate)";
	private static final String UPDATE_TODO_ITEM = "UPDATE todo_items SET"
			+ " text              = COALESCE  (:text,text),"
			+ " created_date              = COALESCE (:createdDate,created_date),"
			+ " due_date              = COALESCE (:dueDate,due_date) " + " WHERE id = :id";;
	private static final String DELETE_TODO_ITEM = "DELETE FROM todo_items WHERE id = :id";
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public List<TodoItem> getTodoItemsList() {
		System.out.println("Named jdbc template : "+namedParameterJdbcTemplate );
		return namedParameterJdbcTemplate.query(SELECT_ALL_TODO_ITEMS, rowMapper);
	}

	@Override
	public int addTodoItem(TodoItem todoItem) {
		return namedParameterJdbcTemplate.update(ADD_NEW_TODO_ITEM, sqlParameterSourcefactory.fromTodoItem(todoItem));
	}

	@Override
	public int updateTodoItem(TodoItem todoItem) {
		return namedParameterJdbcTemplate.update(UPDATE_TODO_ITEM, sqlParameterSourcefactory.fromTodoItem(todoItem));
	}

	@Override
	public int deleteTodoItem(Integer id) {
		return namedParameterJdbcTemplate.update(DELETE_TODO_ITEM, Collections.singletonMap("id", id));
	}
	
	private RowMapper<TodoItem> rowMapper = (rs, rowNumber) -> {
		TodoItem todoItem=new TodoItem();
		todoItem.setId(rs.getInt(1));
		todoItem.setText(rs.getString(2));
		todoItem.setCreatedDate(rs.getTimestamp(3));
		todoItem.setDueDate(rs.getTimestamp(4));
		return todoItem;
	};

}
