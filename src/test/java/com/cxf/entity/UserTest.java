package com.cxf.entity;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class UserTest {

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

    /**
     * 添加
     */
    @Test
    public void userTest(){
//        保存操作
        Session session = factory.openSession();
//        开启事务
        session.beginTransaction();
        User user = new User();
        user.setName("jaychou");
        user.setAge(18);
        session.save(user);
//        提交事务
        session.getTransaction().commit();
        session.close();
    }


    /**
     * 更新
     */
    @Test
    public void updateUser(){
        //        保存操作
        Session session = factory.openSession();
//        开启事务
        session.beginTransaction();

        User user = session.get(User.class, 1L);
        user.setName("日天");
        session.update(user);
//        提交事务
        session.getTransaction().commit();
        session.close();

    }

    /**
     * 删除
     */
    @Test
    public void deleteUser(){
        //        保存操作
        Session session = factory.openSession();
//        开启事务
        Transaction transaction = session.beginTransaction();

//        User user = session.get(User.class, 1L);
        User user = new User();
        user.setId(2L);
        session.delete(user);
        transaction.commit();
        session.close();

    }

    /**
     * 查询所有
     */
    @Test
    public void findUser(){
        Session session = factory.openSession();
//        查询所有
        String hql = "from User";
        Query query = session.createQuery(hql);
//        获取结果
        List<User> list = query.list();
        for (User user : list) {
            System.out.println(user.getId());
        }
        session.close();

    }


    /**
     * 根据条件查询或者模糊查询
     */
    @Test
    public void findUserByCondition(){
        Session session = factory.openSession();
//        查询所有
        String hql = "from User where name like :n and age = :a";
//        :变量  下面也必须一样，可以随便取名字
        Query query = session.createQuery(hql);

        String parm = "jaychou";

        //设置参数
        query.setParameter("n","%"+parm+"%");
        query.setParameter("a",18);

//        获取结果
        List<User> list = query.list();
        for (User user : list) {
            System.out.println(user);
        }
        session.close();

    }

    /**
     * 根据条件查询后并排序
     */
    @Test
    public void findUserByOrder(){
        Session session = factory.openSession();
//        查询所有
        String hql = "from User where name = :n and age = :a order by age";
//        :变量  下面也必须一样，可以随便取名字
        Query query = session.createQuery(hql);

        //设置参数
        query.setParameter("n","%"+"jay"+"%");
        query.setParameter("a",18);

//        获取结果
        List<User> list = query.list();
        for (User user : list) {
            System.out.println(user);
        }
        session.close();

    }


    /**
     * 分页查询
     */
    @Test
    public void findUserByPage(){
        Session session = factory.openSession();
//        查询所有
        String hql = "from User";
//        :变量  下面也必须一样，可以随便取名字
        Query query = session.createQuery(hql);

        //设置分页
//        设置开始位置
        query.setFirstResult(0);
//        设置每页显示条数
        query.setMaxResults(3);

//        获取结果
        List<User> list = query.list();
        for (User user : list) {
            System.out.println(user);
        }
        session.close();

    }

    /**
     * Criteria方式，另一种方式呈现执行hql语句
     */
    @Test
    public void findByCri(){

        Session session = factory.openSession();

//        Criteria criteria = session.createCriteria(User.class);
//        criteria.add(Restrictions.eq("name","jaychou"));
//        List<User> list = criteria.list();
//        for (User user : list) {
//            System.out.println(user);
//        }

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        //from User
        Root<User> root = query.from(User.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get("name"),"jaychou"));
        Query<User> userQuery = session.createQuery(query);
        List<User> list = userQuery.list();
        for (User user : list) {
            System.out.println(user);
        }

        session.close();

    }


    /**
     * 使用SQL语句
     */
    @Test
    public void findBySql(){
        Session session = factory.openSession();
        NativeQuery nativeQuery = session.createNativeQuery("select * from `hibernate-demo`.t_user")
                .addScalar("u_id",LongType.INSTANCE)
                .addScalar("u_name",StringType.INSTANCE);
        List<Object[]> list = nativeQuery.list();
        for (Object[] objects : list) {
            Long id = (Long) objects[0];
            System.out.println(id);
        }

        session.close();

    }

}