package org.campus.repository;

import org.campus.domain.Attendance;
import org.campus.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by amey on 2/8/16.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository repository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testSaveAttendance() {
        //given
        Student indrajeet = new Student("BE", "CS", 1);
        indrajeet.setName("Indrajeet");
        studentRepository.save(indrajeet);

        Attendance attendance = new Attendance("20160726", "BE", "CS");
        attendance.setStudents(new ArrayList<Student>() {{
            add(indrajeet);
        }});

        //when
        repository.save(attendance);

        //then
        Attendance actual = repository.findByCode("20160726-BE-CS");
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getCode()).isEqualTo("20160726-BE-CS");
        assertThat(actual.getStudents().size()).isEqualTo(1);
        assertThat(actual.getStudents().get(0).getName()).isEqualTo("Indrajeet");

        Student student = studentRepository.findByRoll("BE-CS-1");
        assertThat(student.getName()).isEqualTo("Indrajeet");
    }

}