package com.dantefung.springbootbatchtask;

import static org.assertj.core.api.Assertions.assertThat;

import com.dantefung.springbootbatchtask.batch.CapitalizeNamesJobConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringBootBatchTaskApplicationTests.BatchTestConfig.class})
public class SpringBootBatchTaskApplicationTests {


	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void testConvertNamesJob() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchJob();
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
	}

	@Configuration
	@Import(CapitalizeNamesJobConfig.class)
	static class BatchTestConfig {

		@Autowired
		private Job capitalizeNamesJob;

		@Bean
		JobLauncherTestUtils jobLauncherTestUtils() throws NoSuchJobException {
			JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
			jobLauncherTestUtils.setJob(capitalizeNamesJob);

			return jobLauncherTestUtils;
		}
	}
}
