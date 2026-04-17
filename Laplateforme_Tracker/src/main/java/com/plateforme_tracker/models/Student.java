package com.plateforme_tracker.models;

public class Student {
    //empty constructor
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private double grade;

    //evrything but id as its managed by DB
    public Student (String firstName, String lastName, int age, double grade)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.grade = grade;
    }

    public int getId() { return id;}
    public void setId(int id) {this.id = id;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}

    public String getLastName(){return lastName;}
    public void setLatsName(String lastName){this.lastName = lastName;}

    public int getAge(){return age;}
    public void setAge(int age) {this.age = age;}

    public double getGrade() {return grade;}
    public void setGrade(double grade) {this.grade = grade;}
}
