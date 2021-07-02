package files.test.files.rest.api.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileReadServiceImpl implements FileReadService{

    //private final Path fileUrl = null;

   @Override
    public void init(){

    }

   /*@Override
    public Resource load(String name) {
        try{
            Path file = fileUrl.resolve(name);
            Resource res = new UrlResource(file.toUri());

            if(res.exists()){
                return res;
            }
            if(!res.exists()){
                throw new RuntimeException("Was not possible read the file!");

            }
        }catch(MalformedURLException e){
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
*/
    @Override
    public Stream<Path> loadAll(Path urlPath) {
        try {
   /*         Path file = urlPath.resolve(urlPath);
            Resource res = new UrlResource(file.toUri());
if(res.isFile()){
    System.out.println("Is file ");

}
           if(res.isOpen()){
               System.out.println("Is directory open");
           }
            System.out.println(" Teste " + Files.list(urlPath).toString());*/
            Files.delete(urlPath);
            return Files.list(urlPath);
        } catch (IOException ex) {
            throw new RuntimeException("Is not possible to load the Files!");
        }
    }


}
