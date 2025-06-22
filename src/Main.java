public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001", "Toyota", "Camry", 60.0);
        Car car2 = new Car("C002", "Honda", "Accord", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);

        rentalSystem.addCarToDatabase(car1);
        rentalSystem.addCarToDatabase(car2);
        rentalSystem.addCarToDatabase(car3);

        rentalSystem.menu();
    }
}
