package hr.abysalto.hiring.mid.components;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements CommandLineRunner {

    @Autowired
    private DatabaseInitializer databaseInitializer;

    @Override
    public void run(String... args) throws Exception {
        if (!databaseInitializer.isDataInitialized()) {
            databaseInitializer.initialize();
            System.out.println("Database initialized successfully!");
        }
    }
}