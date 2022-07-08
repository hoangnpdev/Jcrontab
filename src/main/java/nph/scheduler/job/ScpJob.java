package nph.scheduler.job;

import nph.scheduler.util.ScpUtil;
import nph.scheduler.util.SymbolUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ScpJob implements Job {

    private final Logger log = Logger.getLogger(ScpJob.class.getName());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            log.info("start running scpjob");
            JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            String cmd = dataMap.getString("cmd");
            List<String> paramList = Arrays.stream(dataMap.getString("params").split(" "))
                    .map(String::trim)
                    .filter(param -> !param.equals(""))
                    .map(SymbolUtil::getSignValue)
                    .collect(Collectors.toList());
            log.info(String.join(",", paramList));
            cmd = String.format(cmd, paramList.toArray());
            String[] args = cmd.split(" ");
            log.info("Run scp command: " + String.join(",", args));

            ScpUtil.run(args);
        } catch (Exception e) {
            log.info("Error:" + ExceptionUtils.getStackTrace(e));
        }
    }
}
