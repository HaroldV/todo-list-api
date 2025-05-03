package com.example.todos.controller

import com.example.todos.model.Todo
import com.example.todos.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {

    @GetMapping
    suspend fun getAllTodos(): ResponseEntity<List<Todo>> = ResponseEntity.ok(todoService.getAllTodos())

    @GetMapping("/{id}")
    suspend fun getTodoById(@PathVariable id: Long): ResponseEntity<Todo> =
        ResponseEntity.ok(todoService.getTodoById(id))

    @PostMapping
    suspend fun createTodo(@RequestBody todo: Todo): ResponseEntity<Todo> {
        val createdTodo = todoService.createTodo(todo)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo)
    }

    @PostMapping("/{id}")
    suspend fun updatedTodo(@PathVariable id: Long, @RequestBody updatedTodo: Todo) =
        ResponseEntity.ok(todoService.updateTodo(id, updatedTodo))

    @DeleteMapping("/{id}")
    suspend fun deleteTodoById(@PathVariable id: Long): ResponseEntity<Unit> {
        todoService.deleteTodo(id)
        return ResponseEntity.noContent().build()
    }
}