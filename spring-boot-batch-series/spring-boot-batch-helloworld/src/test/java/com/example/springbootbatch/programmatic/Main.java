package com.example.springbootbatch.programmatic;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.net.URL;

/**
 * main
 *
 * @Author 王洪悦.{javayue@yeah.net}
 * @Create 2017-08-06 下午9:36
 */
public class Main {

    public static void main(String[] args) {
		test1();
	}

	private static void test1() {
		try {
			String[] configLocations = {"classpath*:applicationContext.xml"};
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configLocations);

			JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
			JobRepository jobRepository = applicationContext.getBean(JobRepository.class);
			PlatformTransactionManager transactionManager = applicationContext.getBean(PlatformTransactionManager.class);


			// 创建reader
			FlatFileItemReader<DeviceCommand> flatFileItemReader = new FlatFileItemReader<>();

			flatFileItemReader.setResource(new FileSystemResource("F:\\demoCode\\Macaroon-Spring-Family\\spring-boot-batch-series\\spring-boot-batch-helloworld\\src\\test\\resources\\batch-data.csv"));
//          // 转换数据
			flatFileItemReader.setLineMapper(new DemoLineMapper());

			// 创建processor 对数据进行操作
			DemoProcessor helloItemProcessor = new DemoProcessor();

			// 创建writer
			FlatFileItemWriter<DeviceCommand> flatFileItemWriter = new FlatFileItemWriter<>();
			flatFileItemWriter.setResource(new FileSystemResource("F:\\demoCode\\Macaroon-Spring-Family\\spring-boot-batch-series\\spring-boot-batch-helloworld\\src\\test\\resources\\batch-data.csv"));
			flatFileItemWriter.setLineAggregator(new DemoLineAggregator());

			// 创建Step
			StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository, transactionManager);
			Step step = stepBuilderFactory.get("step")
					.<DeviceCommand, DeviceCommand>chunk(1)
					.reader(flatFileItemReader)       // 读操作
					.processor(helloItemProcessor)    // 处理操作
					.writer(flatFileItemWriter)       // 写操作
					.build();

			// 创建Job
			JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);
			Job job = jobBuilderFactory.get("job")
					.start(step)
					.build();

			// 启动任务
			jobLauncher.run(job, new JobParameters());
		} catch (JobExecutionAlreadyRunningException e) {
			e.printStackTrace();
		} catch (JobRestartException e) {
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			e.printStackTrace();
		}
	}

	private static void test2() {
		// 创建A
	}

}