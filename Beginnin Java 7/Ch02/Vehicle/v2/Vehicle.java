class Vehicle
{
   private String make;
   private String model;
   private int year;
   Vehicle(String make, String model, int year)
   {
      this.make = make;
      this.model = model;
      this.year = year;
   }
   String getMake()
   {
      return make;
   }
   String getModel()
   {
      return model;
   }
   int getYear()
   {
      return year;
   }
}
class Car extends Vehicle
{
   private int numWheels;
   Car(String make, String model, int year, int numWheels)
   {
      super(make, model, year);
      this.numWheels = numWheels;
   }
   public static void main(String[] args)
   {
      Car car = new Car("Toyota", "Camry", 2011, 4);
      System.out.println("Make = "+car.getMake());
      System.out.println("Model = "+car.getModel());
      System.out.println("Year = "+car.getYear());
      System.out.println("Number of wheels = "+car.numWheels);
      System.out.println();
      car = new Car("Aptera Motors", "Aptera 2e/2h", 2012, 3);
      System.out.println("Make = "+car.getMake());
      System.out.println("Model = "+car.getModel());
      System.out.println("Year = "+car.getYear());
      System.out.println("Number of wheels = "+car.numWheels);
   }
}
class Truck extends Vehicle
{
   private boolean isExtendedCab;
   Truck(String make, String model, int year, boolean isExtendedCab)
   {
      super(make, model, year);
      this.isExtendedCab = isExtendedCab;
   }
   public static void main(String[] args)
   {
      Truck truck = new Truck("Chevrolet", "Silverado", 2011, true);
      System.out.println("Make = "+truck.getMake());
      System.out.println("Model = "+truck.getModel());
      System.out.println("Year = "+truck.getYear());
      System.out.println("Extended cab = "+truck.isExtendedCab);
   }
}
