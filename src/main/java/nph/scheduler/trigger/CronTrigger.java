package nph.scheduler.trigger;

import nph.scheduler.job.BashJob;
import nph.scheduler.job.ScpJob;
import org.quartz.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CronTrigger {

    private static Logger log = Logger.getLogger(CronTrigger.class.getName());

    public static void appendJobFromFile(Scheduler scheduler) throws FileNotFoundException {
        log.info("appendBashJobFromFile");
        List<String> jobDescriptions = loadJobConfigFromFile();
        jobDescriptions.forEach(jd -> {
            try {
                appendBashAndBuiltInJob(scheduler, jd);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        });

    }

    public static void appendBashAndBuiltInJob(Scheduler scheduler, String jobDescription) throws SchedulerException {
        log.info("append bash and built-in job");
        String[] jds = jobDescription.split("\\|");
        String jobType = jds[0].trim();
        String crontab = jds[1].trim();
        String cmd = jds[2].trim();
        String params = jds.length >= 4 ? jds[3] : "";
        log.info(params);

        org.quartz.CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(cmd, "bash_group")
                .withSchedule(CronScheduleBuilder.cronSchedule(crontab))
                .build();

        Class<? extends Job> job = getJobByJobType(jobType);
        JobDetail bashJobDetail = JobBuilder.newJob(job)
                .withIdentity(cmd, "job_group")
                .usingJobData("cmd", cmd)
                .usingJobData("params", params)
                .build();

        scheduler.scheduleJob(bashJobDetail, trigger);
    }

    public static Class<? extends Job> getJobByJobType(String jobType) {
        switch (jobType) {
            case "scp":
                return ScpJob.class;
            default:
                return BashJob.class;
        }
    }

    public static List<String> loadJobConfigFromFile() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("application.cron"));
        return reader.lines()
                .map(String::trim)
                .filter(line -> !line.startsWith("#") && line.length() > 0)
                .collect(Collectors.toList());
    }
}
