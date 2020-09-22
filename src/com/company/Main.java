package com.company;

import com.company.employee.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        //create session factory

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Employee.class)
                .buildSessionFactory();

        Employee employee = new Employee("firstName", "lastName", "copany");

        createNewItem(employee, factory);

        System.out.println(retrieveObject(4, factory));

        deleteEmployees(7, factory);



        for (Employee emp : queryEmployees("company",factory)) {
            System.out.println(emp);
        }


        factory.close();
    }



    public static void createNewItem(Employee employee, SessionFactory factory) {
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit();
        } finally {

        }
    }

    public static Employee retrieveObject(int id, SessionFactory factory) {
        Employee employee;
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            employee = session.get(Employee.class, id);
            session.getTransaction().commit();
        } finally {

        }

        return employee;
    }

    public static List<Employee> queryEmployees(String companyName, SessionFactory factory){
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List<Employee> employees = session.createQuery("from Employee e where e.company='company'").getResultList();
        session.getTransaction().commit();
        return employees;
    }

    public static void deleteEmployees(int id, SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
//        Employee employee = session.get(Employee.class, id);
//        session.delete(employee);
//        session.getTransaction().commit();
//        System.out.println("Employee number " + id + " deleted : " + employee );
        session.createQuery("delete from Employee where id=" + id + "").executeUpdate();
        session.getTransaction().commit();
    }


}