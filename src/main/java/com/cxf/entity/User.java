package com.cxf.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户 必须实现Serializable接口，而且该类必须有无参构造
 *
 * @author always_on_the_way
 * @date 2019-06-24
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    /**
     * GeneratedValue 配置生成策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自增
    @Column(name = "u_id")
    private Long id;

    @Column(name = "u_name",length = 20)
    private String name;

    @Column(name = "u_age")
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
