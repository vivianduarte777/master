package files.test.files.rest.api.controller;

import files.test.files.rest.api.model.FileReadedInformation;
import files.test.files.rest.api.service.FileReadServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

//Rest Application
@RestController
@RequestMapping("/api/read")
public class ReadFileController {
    private final String uriAddress = "https://github.com/vivianduarte777/filesTest/";
    //private final String uriAddress = "github.com/vivianduarte777/files/";
    private final Path fileUrl = null;

    @Autowired
    private FileReadServiceImpl service;

    private List<FileReadedInformation> fileInfo;

   //@GetMapping(path="/api/read")
   @RequestMapping(value="/",method=RequestMethod.GET)
    public String check() {
       String strUrl = null;
        try {
            URI uri = new URI(uriAddress);

           URL url = getURL(uriAddress);
          Object obj = url.getContent();

            strUrl= Paths.get(url.toURI().getPath()).toString();
           ReadableByteChannel rbc = Channels.newChannel(url.openStream());
            if(rbc.isOpen()){
                System.out.println("Open");
             }
           Path path = getPath(url);
           Stream<Path> pathStream = service.loadAll(path);

           pathStream.forEachOrdered(path1->{
              try{
                  readInformation(path1.toFile());
              }catch(IOException err){
                  err.printStackTrace();
              }

          });

        }catch(Exception e){
           e.printStackTrace();

           return e.getMessage();
       }
        if(strUrl !=null) {
            return strUrl;
        }else{
            return "null";
        }


   }

    private void readInformation(File toFile)  throws IOException  {
       long lines = returnLines(toFile);
       int  count = returnBytesCount(toFile);
       String name = returnExtensionName(toFile);

       FileReadedInformation readedInformation = new FileReadedInformation(name,lines,count,count);

        fileInfo.add(readedInformation);


    }

    private long returnLines(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        return reader.lines().count();
    }

    private int returnBytesCount(File f) throws IOException {
        FileInputStream stream = new FileInputStream(f.getAbsoluteFile().getName());
        return stream.readAllBytes().length;
    }

    private String returnExtensionName(File f) throws IOException {
      return f.getAbsoluteFile().getParentFile().getName();
     }

    private URL getURL(String urlAddress) throws  MalformedURLException{
        return new URL(urlAddress);
    }

    private Path getPath(URL url) throws URISyntaxException,  IOException{
        //  FileSystems.newFileSystem(uri, Collections.emptyMap());

        Resource resource = new ClassPathResource(url.getPath());
        URI uri = url.toURI();
        System.out.println(uri);
       // FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap());

        Path path = Paths.get(uri);
        System.out.println(path);
        if (Files.isDirectory(path)) {
            System.out.println("file type is directory");
        } else if (Files.isRegularFile(path)) {
            System.out.println("file type is regular file");
        } else {
            System.out.println("file is neither directory nor regular");
        }
        Files.walk(path).forEach((Path child) -> {
            if (Files.isRegularFile(child)) {
                System.out.print("Regular file: " + child);
            } else {
                System.out.print("Directory: " + child);
            }
        });

     /*   File file = Paths.get(url.toURI().getPath()).toFile();
       System.out.println(file.getPath());
        Path path = Paths.get(url.toURI());
*/
        System.out.println(path.getFileSystem());


        return path;
    }
}
