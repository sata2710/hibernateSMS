package com.sms;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.sms.entity.Student;
import com.sms.util.HibernateUtil;

public class App {

    public static void main(String[] args) {
        App app = new App();

        // Create a new student
        Student student = new Student("sachin", "Information Technology");
        app.createStudent(student);

        // Read the created student by ID
        Student readStudent = app.getStudentById(student.getId());
        System.out.println("Before Update: " + readStudent);

        // Update the student's course
        if (readStudent != null) {
            readStudent.setCourse("Computer Science");
            app.updateStudent(readStudent);

            // Fetch the updated student details
            Student updatedStudent = app.getStudentById(student.getId());
            System.out.println("After Update: " + updatedStudent);
        }

        // Delete the student
        if (readStudent != null) {
            app.deleteStudent(readStudent.getId());
        }
    }

    public void createStudent(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Student getStudentById(int id) {
        Student student = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            student = session.get(Student.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    public void updateStudent(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(student);
            transaction.commit();
            System.out.println("Update committed successfully.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Transaction rolled back due to an error.");
            }
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.delete(student);
                System.out.println("Student is deleted");
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
