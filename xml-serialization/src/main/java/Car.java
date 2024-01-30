
/**
 * Additional test class.
 */
@XMLable
public class Car {

    @XMLfield(type = "String", name = "MARCA")
    private String make;

    @XMLfield(type = "String")
    private String model;

    private int year;

    @XMLfield(type = "int")
    private int price;

    public Car(String make, String model, int year, int price) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
    }
}