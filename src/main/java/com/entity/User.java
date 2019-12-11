package com.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
@Data  //@Data包含了所有的get、set、和toString方法，为避免循环引用报错，可以再重写一下toString方法
@NoArgsConstructor  //相当于加入无参构造函数
public class User {

    @Id
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
