package typeinfo.toys;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import static  mUtils.Utils.* ;

interface Rotate{

}

abstract class Shape {
    void draw() {
        System.out.println(this + ".draw()");
    }

    abstract public String toString();

}

class Circle extends Shape {
    public String toString() {
        return "Circle";
    }
}

class Square extends Shape {
    public String toString() {
        return "Square";
    }
}

class Triangle extends Shape {
    public String toString() {
        return "Triangle";
    }
}

class Rhomboid extends  Shape{

    public String toString(){
        return "Rhomboid or Rhombus" ;
    }
}

public class Shapes {

    public static void main(String[] args) {
//        List<Shape> shapeList = Arrays.asList(
//                new Circle(), new Square(), new Triangle()
//        );
//        for (Shape shape : shapeList)
//            shape.draw();
        Rhomboid rhomboid = new Rhomboid() ;
        Shape shape = (Shape)rhomboid ;
        Class clazz = null ;
        Object obj = null ;
//        try {
//            clazz = Class.forName("");
//        }catch (ClassNotFoundException e){
//            e.printStackTrace();
//        }
//        try {
//            if (clazz != null) {
//               obj = clazz.newInstance() ;
//            }
//        } catch ( IllegalAccessException | InstantiationException e) {
//            e.printStackTrace();
//        }
//        Rhomboid r = (Rhomboid)shape ;
//        if (obj instanceof Rhomboid){
//            System.out.print("");
//        }

        //Exercise 5
        Rhomboid r3 = new Rhomboid() ;
        rotate(r3) ;
        Circle c3 = new Circle() ;
        rotate(c3);
    }
    public static void rotate(Shape shape) {
        if (!(shape instanceof Circle)){
            print("rotate") ;
        }
    }
}