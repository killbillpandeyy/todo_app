package com.example.todo.resources;

import com.example.todo.core.Task;
import com.example.todo.db.TaskDAO;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class TaskResource {
    private final TaskDAO taskDAO;

    public TaskResource(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @POST
    @UnitOfWork
    public Task createTask(Task task) {
        return taskDAO.create(task);
    }

    @GET
    @UnitOfWork
    public List<Task> getAllTasks() {
        return taskDAO.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Response getTask(@PathParam("id") Long id) {
        Optional<Task> task = taskDAO.findById(id);
        if (task.isPresent()) {
            return Response.ok(task.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @UnitOfWork
    public Response updateTask(@PathParam("id") Long id, Task updatedTask) {
        Optional<Task> task = taskDAO.findById(id);
        if (task.isPresent()) {
            Task existingTask = task.get();
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setStartDate(updatedTask.getStartDate());
            existingTask.setTargetDate(updatedTask.getTargetDate());
            taskDAO.create(existingTask);
            return Response.ok(existingTask).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public Response deleteTask(@PathParam("id") Long id) {
        Optional<Task> task = taskDAO.findById(id);
        if (task.isPresent()) {
            taskDAO.delete(task.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
