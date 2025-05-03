package com.example.todos.service

import com.example.todos.model.Todo
import com.example.todos.repository.TodoRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TodoService (private val todoRepository: TodoRepository) {

    suspend fun getAllTodos(): List<Todo> = todoRepository.findAll()

    suspend fun getTodoById(id:Long): Todo = todoRepository.findById(id).orElseThrow(){
        ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada con ID: $id")
    }

    suspend fun createTodo(todo: Todo): Todo = todoRepository.save(todo)

    suspend fun updateTodo(id: Long, updatedTodo: Todo): Todo {
        return todoRepository.findById(id).orElseThrow() {
            ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada para modificar con ID: $id")
        }.apply {
                title = updatedTodo.title
                description = updatedTodo.description
                completed = updatedTodo.completed
                todoRepository.save(this)
        }
    }

    suspend fun deleteTodo(id: Long) {
        if(todoRepository.existsById(id)) {
            todoRepository.deleteById(id)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea con ID: $id no fue encontrada para eliminarla")
        }
    }
}