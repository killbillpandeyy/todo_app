package com.example.todo.db;

import com.example.todo.core.Task;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class TaskDAO extends AbstractDAO<Task> {
    public TaskDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Task create(Task task) {
        return persist(task);
    }

    public List<Task> findAll() {
        return list((Query<Task>) namedQuery("com.example.todo.core.Task.findAll"));
    }

    public void delete(Task task) {
        currentSession().delete(task);
    }
}
