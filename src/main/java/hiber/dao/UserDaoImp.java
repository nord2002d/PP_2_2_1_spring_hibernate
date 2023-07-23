package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUser(String model, int series) {
      String hql1 = "from Car c where c.model =: model and c.series =: series";
      TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery(hql1, Car.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      Car car = query.getSingleResult();
      String hql2 = "from User u where u.car =: car";
      TypedQuery<User> query1 = sessionFactory.getCurrentSession().createQuery(hql2, User.class);
      query1.setParameter("car", car);
      return query1.getSingleResult();
   }

}
