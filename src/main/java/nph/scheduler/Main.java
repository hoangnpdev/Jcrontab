package nph.scheduler;

import nph.scheduler.trigger.CronTrigger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Main {
    public static void main(String[] args) {
        SchedulerFactory schedFact = new StdSchedulerFactory();
        try {
            // Scheduler
            Scheduler sched = schedFact.getScheduler();
            // read from application.cron file
            CronTrigger.appendJobFromFile(sched);

            // running scheduler
            sched.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
