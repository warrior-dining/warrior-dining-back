package warriordiningback.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.DuplicateJobException;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class JobConfig extends DefaultBatchConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    // jdbcTemplate.execute("CALL temp.UpdateReservationStatus()");

    @Bean
    public Job testJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws DuplicateJobException {
        return new JobBuilder("testJob", jobRepository)
                .start(testStep(jobRepository, transactionManager))
                .build();
    }

    public Step testStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        Step step = new StepBuilder("testStep", jobRepository)
                .tasklet(testTasklet(), transactionManager)
                .build();
        return step;
    }

    public Tasklet testTasklet() {
        return ((contribution, chunkContext) -> {
            log.info("***** batch! *****");
            // 프로시저 호출 ("Call DB명.프로시저명()");
            jdbcTemplate.execute("CALL temp.UpdateReservationStatus()");

            return RepeatStatus.FINISHED;
        });
    }

}
