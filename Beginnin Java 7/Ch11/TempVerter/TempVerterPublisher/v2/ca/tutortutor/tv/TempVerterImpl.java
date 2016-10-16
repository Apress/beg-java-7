package ca.tutortutor.tv;

import javax.jws.WebService;

@WebService(endpointInterface = "ca.tutortutor.tv.TempVerter")
public class TempVerterImpl implements TempVerter
{
   public double c2f(double degrees)
   {
      return degrees*9.0/5.0+32;
   }
   public double f2c(double degrees)
   {
      return (degrees-32)*5.0/9.0;
   }
}
