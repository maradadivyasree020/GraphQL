package com.example.crud.graphql;

import org.springframework.stereotype.Controller;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;

import com.example.crud.model.Student;
import com.example.crud.repository.StudentRepository;

import java.util.List;

@Controller
public class StudentGraphQLController {

    private final StudentRepository repo;

    public StudentGraphQLController(StudentRepository repo) {
        this.repo = repo;
    }

    @QueryMapping
    public List<Student> allStudents() {
        return repo.findAll();
    }

    @QueryMapping
    public Student studentById(@Argument Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    @MutationMapping
    public Student createStudent(@Argument String name, @Argument String email) {
        return repo.save(new Student(name, email));
    }

    @MutationMapping
    public Student updateStudent(@Argument Long id,
                                 @Argument String name,
                                 @Argument String email) {
        Student student = repo.findById(id)
                              .orElseThrow(() -> new RuntimeException("Student not found with id " + id));
        if (name != null) student.setName(name);
        if (email != null) student.setEmail(email);
        return repo.save(student);
    }

    @MutationMapping
    public Boolean deleteStudent(@Argument Long id) {
        repo.deleteById(id);
        return true;
    }
}
