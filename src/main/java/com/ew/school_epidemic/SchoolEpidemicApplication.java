package com.ew.school_epidemic;

import com.ew.school_epidemic.utils.StudentData;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootApplication
@EnableScheduling
public class SchoolEpidemicApplication {

    public static void main(String[] args) throws Exception {
//        File file = new File("C:\\Users\\ew\\Desktop\\student.xlsx");
//        FileInputStream input = new FileInputStream(file);
//        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
//        new StudentData().saveStudent(multipartFile);
        SpringApplication.run(SchoolEpidemicApplication.class, args);
    }

}
