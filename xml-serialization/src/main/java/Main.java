/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

/**
 *
 * @author ema
 */
public class Main {
    public static void main(String[] args) {
        // Students
        Student myStudent = new Student("Jane", "Doe", 42);
        Student myStudent2 = new Student("Pippo", "Pippi", 42);

        // trying some non-XMLable object
        String str = "";
        Object obj = new Object();

        // Car example
        Car myCar = new Car("Ford", "Mustang", 1969, 50000);
        Car myCar2 = new Car("Fiat", "Punto", 2010, 30000);

        // Serialize all the objects
        Object[] arr = { myStudent, str, obj, myCar, myStudent2, myCar2 };
        XMLSerializer.serialize(arr, "test.xml");
    }
}
