package cn.nju.edu.youngstudent2.StudentManager.batch;

import java.time.LocalDate;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import cn.nju.edu.youngstudent2.StudentManager.model.Student;
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

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

    @Bean
    public ItemStreamReader<Student> reader() {
        PoiItemReader<Student> poiReader = new PoiItemReader<Student>();
        poiReader.setResource(new ClassPathResource("./测试数据.xlsx"));
        poiReader.setRowMapper(new RowMapperImpl());
        poiReader.setLinesToSkip(1);
        return poiReader;
    }
}
