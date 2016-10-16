import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MimetypesFileTypeMap;

import javax.annotation.Resource;

import javax.xml.ws.BindingType;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Provider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceProvider;

import javax.xml.ws.handler.MessageContext;

import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.http.HTTPException;

@WebServiceProvider
@ServiceMode(value = javax.xml.ws.Service.Mode.MESSAGE)
@BindingType(value = HTTPBinding.HTTP_BINDING)
class ImagePublisher implements Provider<DataSource>
{
   @Resource
   private WebServiceContext wsContext;
   @Override
   public DataSource invoke(DataSource request)
   {
      if (wsContext == null)
         throw new RuntimeException("dependency injection failed on wsContext");
      MessageContext msgContext = wsContext.getMessageContext();
      switch ((String) msgContext.get(MessageContext.HTTP_REQUEST_METHOD))
      {
         case "GET"   : return doGet();
         default      : throw new HTTPException(405);
      }
   }
   private DataSource doGet()
   {
      FileDataSource fds = new FileDataSource("balstone.jpg");
      MimetypesFileTypeMap mtftm = new MimetypesFileTypeMap();
      mtftm.addMimeTypes("image/jpeg jpg");
      fds.setFileTypeMap(mtftm);
      System.out.println(fds.getContentType());
      return fds;
   }
   public static void main(String[] args)
   {
      Endpoint.publish("http://localhost:9902/Image", new ImagePublisher());
   }
}
