package com.example.springboot3todoapplication.services;

import com.example.springboot3todoapplication.models.TodoItem;
import com.example.springboot3todoapplication.repositories.TodoItemRepository;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoItemServiceTest {

    @Autowired
    private TodoItemService service;

    @Autowired
    private TodoItemRepository repository;

    @AfterEach
    void deleteAllItems() {
        repository.deleteAll();
    }


    @Test
    @DisplayName("find a given TodoItem by its Id")
    void findATodoItemById() {
        // find a specific TodoItem by its id
        TodoItem item = new TodoItem();
        item.setDescription("todo item1");
        item.setIsComplete(false);

        item = service.save(item);

        Optional<TodoItem> testItem = service.getById(item.getId());
        assertEquals(testItem.isPresent(), true);
        assertEquals(testItem.get().getId(), item.getId());
    }

    @Test
    void getAllTodoItems() {

        TodoItem item1 = new TodoItem();
        item1.setDescription("todo item1");
        item1.setIsComplete(false);

        item1 = service.save(item1);

        TodoItem item2 = new TodoItem();
        item2.setDescription("todo item1");
        item2.setIsComplete(false);

        item2 = service.save(item2);

        Iterable<TodoItem> items = service.getAll();
        List<TodoItem> list = new ArrayList<>();
        items.iterator().forEachRemaining(list::add);
        assertNotEquals(list.size(), 0);
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getId(), item1.getId());
        assertEquals(list.get(1).getId(), item2.getId());
    }

    @Test
    void savingAValidTodoItemSucceeds() {
        TodoItem item = new TodoItem();
        item.setDescription("todo item1");
        item.setIsComplete(false);

        item = service.save(item);
        assertNotEquals(item.getId(), null);
    }

    @Test
    void savingAnInvalidTodoItemFails() {

        TodoItem item = new TodoItem();
        Exception exception = assertThrows(Exception.class, () -> service.save(item));
        assertEquals("Could not commit JPA transaction", exception.getMessage());
    }

    @Test
    void deletingAValidTodoItemSucceeds() {
        TodoItem item = new TodoItem();
        item.setDescription("todo item1");
        item.setIsComplete(false);

        item = service.save(item);
        service.delete(item);

        Iterable<TodoItem> items = service.getAll();
        List<TodoItem> list = new ArrayList<>();
        items.iterator().forEachRemaining(list::add);
        assertEquals(list.size(), 0);
    }
}