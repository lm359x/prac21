package com.example.prac21;

import com.example.prac21.services.EmailService;
import com.example.prac21.services.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import javax.management.*;
import java.lang.management.ManagementFactory;

@SpringBootApplication
public class Prac21Application {
    @Autowired
    private static EmailService service;

    public static void main(String[] args) {
        SpringApplication.run(Prac21Application.class, args);

        try{
            ObjectName objectName = new ObjectName("com.example.prac21:type=basic,name=schedulerService");
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            server.registerMBean(new SchedulerService(),objectName);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException(e);
        } catch (NotCompliantMBeanException e) {
            throw new RuntimeException(e);
        } catch (InstanceAlreadyExistsException e) {
            throw new RuntimeException(e);
        } catch (MBeanRegistrationException e) {
            throw new RuntimeException(e);
        }
    }

}
