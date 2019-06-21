package cn.hugo.test;

import cn.hugo.domain.Customer;
import cn.hugo.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.junit.Test;

import javax.persistence.criteria.From;
import java.util.List;

public class HibernateTest {
    @Test
    public void test() {
        // 获取连接对象 一个项目中一般只需要一个，当需要操作多个数据库的时候可以为每个设置一个sessionFactory
        // 内部维护了Hibernate连接池 和其中二级缓存
        // 也可以使用第三方连接池
        Session session = HibernateUtil.openSession();
        Customer customer = new Customer();
        customer.setName("hugo");
        customer.setPhoneNumber("13610167884");
        customer.setAddress("花语水岸");
        session.save(customer);
        // 必须处理完一件事情的时候进行关闭
        session.close();
        // 可以当程序关闭的时候才关闭
        HibernateUtil.openSession().close();
    }

    @Test
    public void queryTest() {
        // 获取连接对象 一个项目中一般只需要一个，当需要操作多个数据库的时候可以为每个设置一个sessionFactory
        // 内部维护了Hibernate连接池 和其中二级缓存
        // 也可以使用第三方连接池
        Session session = HibernateUtil.openSession();
        Customer customer = session.get(Customer.class, 1L);
        // 必须处理完一件事情的时候进行关闭
        System.out.println(customer);
        session.close();
        // 可以当程序关闭的时候才关闭
        HibernateUtil.openSession().close();
    }

    @Test
    public void updateTest() {
        // 获取连接对象 一个项目中一般只需要一个，当需要操作多个数据库的时候可以为每个设置一个sessionFactory
        // 内部维护了Hibernate连接池 和其中二级缓存
        // 也可以使用第三方连接池
        Session session = HibernateUtil.openSession();
        // 开启事务
        Transaction transaction = session.beginTransaction();
        // 新建对象更新
        Customer customer = new Customer();

        // ---- way 1
        // 这里需要注意的是 如果数据库有数据，
        // 该如果有Primary key的数据则更新 其他没有设置的数据设置为null
        // 这里需要注意

        customer.setCustomId(6L);
        customer.setName("Bella");
        session.update(customer);

        // 事务在配置c3p0之后不会进行自动提交

        // ---- way 2
        /*
        customer = session.get(Customer.class, 2L);
        customer.setName("Bella");
        session.update(customer);
        */
        // 提交事务
        transaction.commit();
        session.close();
        // 可以当程序关闭的时候才关闭
        HibernateUtil.openSession().close();
    }
    // 该方法是对Update的补充，因为saveOrUpdate允许 id为空 update方法不允许id为空
    @Test
    public void saveOrUpdate(){
        Session session =HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = new Customer();
        customer.setCustomId(6L);
        customer.setAddress("new address");
        session.saveOrUpdate(customer);
        transaction.commit();
        session.close();
    }

    @Test
    public void deleteTest(){
        Session session = HibernateUtil.openSession();
        // 开启事务
        Transaction transaction = session.beginTransaction();
        // way 1 不支持级联删除
//        Customer customer =new Customer();
//        customer.setCustomId(1L);
//        session.delete(customer);

        // way 2 支持级联删除
        Customer customer1 = session.get(Customer.class,1L);
        session.delete(customer1);
        transaction.commit();
        session.close();
    }

    @Test
    public void queryAll(){
        Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();

        // HQL查询方法
        Query query = session.createQuery("from cn.hugo.domain.Customer");
        List<Customer> list = query.list();
        for (Customer customer : list){
            System.out.println(customer);
        }

        // 原生SQL查询 过期 这里返回的是数组
//        NativeQuery sqlQuery = session.createSQLQuery("select * form customer");
//        List<Object[]> list1 = sqlQuery.list();

        transaction.commit();
        session.close();


    }

}
