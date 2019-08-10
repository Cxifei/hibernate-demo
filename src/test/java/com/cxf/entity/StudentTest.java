package com.cxf.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class StudentTest {

    private SessionFactory factory;

    @Before
    public void setUp(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void save(){
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

//        Student student = new Student();
//        Teacher teacher = new Teacher();
//        student.setName("lucy");
//        teacher.setName("渣哥");
//
//        session.save(teacher);
//
//        student.setTeacher(teacher);
//        session.save(student);


//        把学生的信息存放到老师中
        Student student = session.get(Student.class, 3L);
        Teacher teacher = new Teacher();
        teacher.setName("渣哥");
        Set<Student> studentSet = new HashSet<>();
        studentSet.add(student);
        teacher.setStudents(studentSet);
        session.save(teacher);


        transaction.commit();

        session.close();

    }

    @Test
    public void find(){
        Session session = factory.openSession();

//        一般用方式一的情况多一点
//        方式一
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
//        Root<Student> root = query.from(Student.class);
//        Join<Object, Object> teacher = root.join("teacher", JoinType.INNER);
//        query.where(criteriaBuilder.like(teacher.get("name"),"%渣%")).distinct(true);
//        Query<Student> studentQuery = session.createQuery(query);
//        List<Student> list = studentQuery.list();
//        for (Student student : list) {
//            System.out.println(student);
//        }


//        方式二
        String hql = "from Student s inner join Teacher t on s.teacher.id = t.id where t.name like :name";
        Query query = session.createQuery(hql);
        query.setParameter("name","%渣%");
        List<Object[]> list = query.list();
        for (Object[] o : list) {
            Student student = (Student) o[0];
            System.out.println(student);
        }


        session.close();

    }

}