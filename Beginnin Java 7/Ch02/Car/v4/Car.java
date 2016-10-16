class Car
{
   String make;
   String model;
   int numDoors;
   Car(String make, String model)
   {
      this(make, model, 4);
   }
   Car(String make, String model, int nDoors)
   {
      this.make = make;
      this.model = model;
      numDoors = nDoors;
   }
   public static void main(String[] args)
   {
      Car myCar = new Car("Toyota", "Camry");
      Car yourCar = new Car("Mazda", "RX-8", 2);
   }
}
