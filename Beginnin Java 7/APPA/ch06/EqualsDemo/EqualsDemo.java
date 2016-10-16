import java.util.Objects;

class EqualsDemo
{
   public static void main(String[] args)
   {
      Car[] cars1 = { new Car(4, "Goodyear"), new Car(4, "Goodyear") };
      Car[] cars2 = { new Car(4, "Goodyear"), new Car(4, "Goodyear") };
      Car[] cars3 = { new Car(4, "Michelin"), new Car(4, "Goodyear") };
      Car[] cars4 = { new Car(3, "Goodyear"), new Car(4, "Goodyear") };
      Car[] cars5 = { new Car(4, "Goodyear"), new Car(4, "Goodyear"),
                      new Car(3, "Michelin") };
      System.out.println(Objects.deepEquals(cars1, cars2)); // Output: true
      System.out.println(Objects.deepEquals(cars1, cars3)); // Output: false
      System.out.println(Objects.deepEquals(cars1, cars4)); // Output: false
      System.out.println(Objects.deepEquals(cars1, cars5)); // Output: false
   }
}
class Car
{
   Wheel[] wheels;
   Car(int numWheels, String wheelBrand)
   {
      wheels = new Wheel[numWheels];
      for (int i = 0; i < wheels.length; i++)
         wheels[i] = new Wheel(wheelBrand);
   }
   @Override
   public boolean equals(Object o)
   {
      if (!(o instanceof Car))
         return false;
      Car car = (Car) o;
      if (wheels.length != car.wheels.length)
         return false;
      boolean wheelsEqual = false;
      for (int i = 0; i < wheels.length; i++)
         if (!wheels[i].equals(car.wheels[i]))
            return false;
      return true;
   }
}
class Wheel
{
   String brand;
   Wheel(String brand)
   {
      this.brand = brand;
   }
   @Override
   public boolean equals(Object o)
   {
      if (!(o instanceof Wheel))
         return false;
      Wheel wheel = (Wheel) o;
      return brand.equals(wheel.brand);
   }
}
