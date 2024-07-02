package com.example.todo;
import com.example.todo.db.TaskDAO;
import com.example.todo.core.Task;
import com.example.todo.resources.TaskResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.hibernate.SessionFactory;

public class TodoAppApplication extends Application<TodoAppConfiguration> {
    public static void main(String[] args) throws Exception {
        new TodoAppApplication().run(args);
    }

    private final HibernateBundle<TodoAppConfiguration> hibernateBundle =
            new HibernateBundle<TodoAppConfiguration>(Task.class) {
                @Override
                public DataSourceFactory getDataSourceFactory(TodoAppConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public void initialize(Bootstrap<TodoAppConfiguration> bootstrap) {
        // nothing to do yet
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(TodoAppConfiguration configuration, Environment environment) {
        final SessionFactory sessionFactory = hibernateBundle.getSessionFactory();
        final TaskDAO taskDAO = new TaskDAO(sessionFactory);
        final TaskResource taskImplementation = new TaskResource(taskDAO);
        environment.jersey().register(taskImplementation);
    }
}