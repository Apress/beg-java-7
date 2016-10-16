package ca.tutortutor.tv;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface TempVerter
{
   @WebMethod double c2f(double degrees);
   @WebMethod double f2c(double degrees);
}
