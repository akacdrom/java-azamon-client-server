package com.azamon;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

public class PersonDao {
    public void saveRegisteredPerson(Person person){
        Transaction transaction = null;
        try (Session session = SessionFactorySingleton.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            System.out.println(transaction);
            session.save(person);
            transaction.commit();
        }
    }

    public Admin getAdminByEmail(String email) {
        Criteria crit = SessionFactorySingleton.getSessionFactory().openSession().createCriteria(Admin.class);
        crit.add(Restrictions.eq("email", email)); //assuming Employee entity has "email" field
        if(!crit.list().isEmpty() && crit.list() != null ){
            return (Admin) crit.list().get(0);
        }
        return null;
    }
    public User getUserByEmail(String email) {
        Criteria crit = SessionFactorySingleton.getSessionFactory().openSession().createCriteria(User.class);
        crit.add(Restrictions.eq("email", email)); //assuming Employee entity has "email" field
        if(!crit.list().isEmpty() && crit.list() != null){
            return (User) crit.list().get(0);
        }
        return null;
    }
    public Employee getEmployeeByEmail(String email) {
        Criteria crit = SessionFactorySingleton.getSessionFactory().openSession().createCriteria(Employee.class);
        crit.add(Restrictions.eq("email", email)); //assuming Employee entity has "email" field
        if(!crit.list().isEmpty() && crit.list() != null){
            return (Employee) crit.list().get(0);
        }
        return null;
    }
}
