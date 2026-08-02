package com.demo.enterprise.service;

import com.demo.enterprise.model.Student;
import com.demo.enterprise.model.StudentForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentService {

    private final List<Student> students = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public StudentService() {
        students.add(new Student(idSequence.getAndIncrement(), "Nguyễn Văn A", "nguyenvana@example.com"));
        students.add(new Student(idSequence.getAndIncrement(), "Trần Thị B", "tranthib@example.com"));
    }

    public List<Student> findAll() {
        return List.copyOf(students);
    }

    // Return Optional<Student>
    public Optional<Student> findOptionById(Long id) {
        return students.stream()
                .filter(student -> student.getId().equals(id))
                .findFirst();
    }

    // Return Student
    public Student findById(Long id) {
        return findOptionById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                           "Student not found with id: " + id));
    }

    public Student save(StudentForm form) {
        Student student = new Student(idSequence.getAndIncrement(), form.getName(), form.getEmail());
        students.add(student);
        return student;
    }
}