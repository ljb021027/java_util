package com.MyUtils.j8;

import java.util.Objects;

/**
 * @author liujiabei
 * @since 2019/1/10
 */
public class People {

    private String name;
    private String age;
    private int count;

    public People(){

    }
    public People(String name, String age, int count) {
        this.name = name;
        this.age = age;
        this.count = count;
    }

    public String getUni(){
        return name+age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        People people = (People) o;
        return name.equals(people.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
