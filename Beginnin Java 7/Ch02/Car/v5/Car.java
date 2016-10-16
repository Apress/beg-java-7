class Car
{
   String make;
   String model;
   int numDoors;
   static int counter;
   Car(String make, String model)
   {
      this(make, model, 4);
   }
   Car(String make, String model, int numDoors)
   {
      this.make = make;
      this.model = model;
      this.numDoors = numDoors;
      counter++;
   }
   public static void main(String[] args)
   {
      Car myCar = new Car("Toyota", "Camry");
      Car yourCar = new Car("Mazda", "RX-8", 2);
      System.out.println(Car.counter);
   }
}
