package cn.hugo.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    //线程安全 可以放到外面
    public static final SessionFactory sessionFactory;
    static {
        Configuration configuration = new Configuration().configure();
        // 相当于连接池
        sessionFactory = configuration.buildSessionFactory();
    }
    public static Session openSession(){
        // session线程不安全
        Session session = sessionFactory.openSession();
        return session;
    }
}
