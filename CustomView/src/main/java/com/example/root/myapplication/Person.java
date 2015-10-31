package com.example.root.myapplication;

/**
 * Created by Edward Lin
 * on 5/21/15 11:06 PM.
 */
public class Person {

    private String name = "person" ;

    public Person(){
        name = "constructor initialization" ;
        System.out.println("name fo persion ==>>" + name);
    }

    public Person(String name) {
        this.name = name ;
    }

    public void  getName(){
        System.out.println("getName ==>>" + name);
    }

    public static void main(String[] args) {
        Person person = new Person() ;
        person.getName();
    }


}

abstract class Corporation{
    private  String name ;
    abstract void Personel() ;
    abstract void Administrator() ;

    public void setName(String name ){
        this.name = name ;
    }

}

interface  MobileModule{
    String name = "hello mobile";
    void AndroidDepartment() ;
    void ISODepartment() ;
    void HTML5() ;
    void setName(String name ) ;
}

class hello {
}
