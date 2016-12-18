package com.util;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class sqlParameterSourcefactory {

	public static SqlParameterSource fromTodoItem(TodoItem todoItem) {
		TodoItemParameterSource todoItemparamterSource=new TodoItemParameterSource();
		todoItemparamterSource.setId(todoItem.getId());
		todoItemparamterSource.setText(todoItem.getText());
		todoItemparamterSource.setCreatedDate(todoItem.getCreatedDate());
		todoItemparamterSource.setDueDate(todoItem.getDueDate());
		return new BeanPropertySqlParameterSource(todoItemparamterSource);
	}

}
