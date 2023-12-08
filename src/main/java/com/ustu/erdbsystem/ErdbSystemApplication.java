package com.ustu.erdbsystem;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelWithPersonDTO;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.store.models.Attribute;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import com.ustu.erdbsystem.ermodels.store.models.enums.AttributeType;
import com.ustu.erdbsystem.ermodels.store.models.enums.Power;
import com.ustu.erdbsystem.ermodels.store.repos.ModelEntityRepo;
import com.ustu.erdbsystem.ermodels.store.repos.ModelRepo;
import com.ustu.erdbsystem.external.excel.TestDataLoaderExcel;
import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.store.models.Group;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Position;
import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.persons.store.models.User;
import com.ustu.erdbsystem.persons.store.models.enums.PersonType;
import com.ustu.erdbsystem.persons.store.repos.GroupRepo;
import com.ustu.erdbsystem.persons.store.repos.PersonRepo;
import com.ustu.erdbsystem.persons.store.repos.PositionRepo;
import com.ustu.erdbsystem.persons.store.repos.StudentRepo;
import com.ustu.erdbsystem.persons.store.repos.TeacherRepo;
import com.ustu.erdbsystem.persons.store.repos.UserRepo;
import com.ustu.erdbsystem.tasks.store.repos.TaskStudentRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class ErdbSystemApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ErdbSystemApplication.class, args);
    }

    @Autowired
    private ModelRepo modelRepo;

    @Autowired
    private ModelEntityRepo modelEntityRepo;

    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelService modelService;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private PositionRepo positionRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private TestDataLoaderExcel testDataLoaderExcel;

    @Autowired
    private TaskStudentRepo taskStudentRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var user = userRepo.save(User.builder()
                .email("123")
                .login("123")
                .password("123")
                .build());

        var person = Person.builder()
                .firstName("ALEX")
                .lastName("FEDOROV")
                .personType(PersonType.STUDENT)
                .user(user)
                .build();
        personRepo.save(person);

        var position = positionRepo.saveAndFlush(Position.builder()
                .title("Академик")
                .build());

        var teacher = teacherRepo.saveAndFlush(Teacher.builder().person(person).position(position).build());
        position.addTeacher(teacher);

        var group = groupRepo.saveAndFlush(Group.builder().title("IST").build());
        var student = studentRepo.saveAndFlush(Student.builder().person(person).group(group).build());

//        System.out.println(teacher);
//
//        System.out.println(teacherRepo.findById(1L));
//        System.out.println(teacherRepo.findByIdWithTasks(1L));

//        taskStudentRepo.findAllTasksWithTeachersAndResultsByStudent(student);


//        System.out.println(personRepo.findById(1L).get().getUser());
//        System.out.println(userRepo.findById(1L).get().getPerson());
//
//        person = Person.builder()
//                .firstName("ALEX123")
//                .lastName("FEDOROV32131231")
//                .personType(PersonType.STUDENT)
//                .build();
//        user.setPerson(person);
//        personRepo.save(person);
//
//        System.out.println(personRepo.findById(1L).get().getUser());
//        System.out.println(personRepo.findById(2L).get().getUser());
//        System.out.println(userRepo.findById(1L).get().getPerson());

//		var model = Model.builder()
//				.title("test")
//				.description("test")
//				.topic("test")
//				.build();
//		person.addModel(model);
//		modelRepo.save(model);
//
//		var modelEntity = ModelEntity.builder()
//				.title("test entity1")
//				.build();
//		var attr1 = Attribute.builder()
//				.title("test attr1")
//				.attributeType(AttributeType.ATTRIBUTE)
//				.build();
//		var attr2 = Attribute.builder()
//				.title("test attr2")
//				.attributeType(AttributeType.ATTRIBUTE)
//				.build();
//		modelEntity.addAttribute(attr1);
//		modelEntity.addAttribute(attr2);
//		model = new Model();
//		model.setId(1L);
//		model.addModelEntity(modelEntity);
//		modelEntityRepo.save(modelEntity);
//
//		var a = modelEntityRepo.findAll();
//		System.out.println(a);
//		System.out.println(a.get(0).getModel());

//		modelEntityRepo.save(modelEntity);
//
//		var modelEntity2 = ModelEntity.builder()
//				.title("test entity2")
//				.build();
//		var attr3 = Attribute.builder()
//				.title("test attr3")
//				.attributeType(AttributeType.ATTRIBUTE)
//				.build();
//		var attr4 = Attribute.builder()
//				.title("test attr4")
//				.attributeType(AttributeType.ATTRIBUTE)
//				.build();
//		modelEntity2.addAttribute(attr3);
//		modelEntity2.addAttribute(attr4);
//		model.addModelEntity(modelEntity2);
//		modelEntityRepo.save(modelEntity2);
//
//		System.out.println("-------------------");

    }
}
