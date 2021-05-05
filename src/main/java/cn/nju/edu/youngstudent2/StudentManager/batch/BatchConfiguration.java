package cn.nju.edu.youngstudent2.StudentManager.batch;

import java.time.LocalDate;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.nju.edu.youngstudent2.StudentManager.model.Student;
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);
    class RowMapperImpl implements RowMapper<Student> {

        @Override
        public Student mapRow(RowSet arg0) throws Exception {
            String[] cols = arg0.getCurrentRow();
            Student student = new Student();
            student.setId(Integer.parseInt(cols[0]));
            student.setName(cols[1]);
            student.setFaculty(cols[2]);
            student.setSex(cols[3]);
            student.setBirthday(LocalDate.parse(cols[4]));
            return student;
        }
        
    }   

    class JobCompletionNotificationListener extends JobExecutionListenerSupport {
        private final JdbcTemplate jdbcTemplate;

        @Autowired
        public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public void afterJob(JobExecution jobExecution) {
            if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
                log.info("JOB FINISHED");
                jdbcTemplate.query("SELECT id,name FROM students", (rs,row) -> new Student())
                            .forEach(student -> log.info("("+student+") in database."));
            }
        }
    }

    @Bean
    public ItemStreamReader<Student> reader() {
        PoiItemReader<Student> poiReader = new PoiItemReader<Student>();
        poiReader.setResource(new ClassPathResource("./xlsx/测试数据.xlsx"));
        poiReader.setRowMapper(new RowMapperImpl());
        poiReader.setLinesToSkip(1);
        return poiReader;
    }

    @Bean
    public StudentItemProcessor processor() {
        return new StudentItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Student> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Student>()
                    .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                    .sql("INSERT INTO students (id,name,faculty,hometown,birthday,sex) VALUES (:id,:name,:faculty,:hometown,:birthday,:sex)")
                    .dataSource(dataSource).build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step) {
        return jobBuilderFactory.get("importStudentJob").incrementer(new RunIdIncrementer())
                                .listener(listener).flow(step).end().build();
    }

    @Bean
    public Step mystep(JdbcBatchItemWriter<Student> writer) {
        return stepBuilderFactory.get("mystep").<Student,Student>chunk(10)
                        .reader(reader()).processor(processor()).writer(writer).build();
    }
}
