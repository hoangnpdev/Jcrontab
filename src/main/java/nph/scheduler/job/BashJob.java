package nph.scheduler.job;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.util.logging.Logger;

public class BashJob implements Job {

    private static final Logger log = Logger.getLogger(BashJob.class.getName());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            String cmd = dataMap.getString("cmd");
            String[] ps = cmd.split(" ");
            log.info("Run: " + String.join(",", ps));

            Process process = new ProcessBuilder(ps).start();
            process.waitFor();
        } catch (Exception e) {
            log.info("Error: " + ExceptionUtils.getMessage(e));
        }
    }
}
