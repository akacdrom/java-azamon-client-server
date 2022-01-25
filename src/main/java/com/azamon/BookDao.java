package com.azamon;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BookDao {
    public void saveBook(Book book){
        Transaction transaction = null;
        try (Session session = SessionFactorySingleton.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            System.out.println(transaction);
            session.save(book);
            transaction.commit();
        }
    }
    public void updateRegisteredUser(Book book){
        Transaction transaction = null;
        try (Session session = SessionFactorySingleton.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.saveOrUpdate(book);
            transaction.commit();
        }
    }
    public Book getRegisteredUserById(long id){
        Transaction transaction = null;
        Book book = null;
        try (Session session = SessionFactorySingleton.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            book = session.get(Book.class, id);
            transaction.commit();
        }
        return book;
    }
    public List<Book> getAllRegisteredUsers(){
        Transaction transaction = null;
        List<Book> books = null;
        try (Session session = SessionFactorySingleton.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            books = session.createQuery("from et_books").list();
            transaction.commit();
        }
        return books;
    }
    public void deleteRegisteredUser(long id){
        Transaction transaction = null;
        Book book = null;
        try (Session session = SessionFactorySingleton.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            book = session.get(Book.class, id);
            session.delete(book);
            transaction.commit();
        }
    }
}
