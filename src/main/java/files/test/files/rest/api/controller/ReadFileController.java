package files.test.files.rest.api.controller;

import ch.qos.logback.core.rolling.helper.FileNamePattern;
import files.test.files.rest.api.model.FileReadedInformation;
import files.test.files.rest.api.service.FileReadServiceImpl;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.stream.Stream;

//Rest Application
@RestController
@RequestMapping("/api/read")
public class ReadFileController {
    private final String uriAddress = "https://github.com/vivianduarte777/filesTest/";

    private Map<String,String> filesNameExtension=new HashMap<>();

    @Autowired
    private FileReadServiceImpl service;

    private List<FileReadedInformation> fileInfo;

   @RequestMapping(value="/",method=RequestMethod.GET)
    public String check() {
         try {
            URI uri = new URI(uriAddress);

           URL url = getURL(uriAddress);

            Document doc = Jsoup.connect(uriAddress.toString()).get();

            Elements allElements = doc.getAllElements();

           // System.out.println(allElements.toString());

            Elements files= doc.getElementsByClass("js-navigation-open Link--primary");

            getAllFiles(files);

            return files.toString();

        }catch(Exception e){
           e.printStackTrace();

           return e.getMessage();
       }
    //return "ok";
   }

    private void getAllFiles(Elements elements) {
       for(Element e:elements){
           String name =e.attr("title");
           int lgt =name.length();
           String extension = name.substring(lgt - 3, lgt);
           System.out.println(extension);
           filesNameExtension.put(name,extension);

         }

    }

    private long returnLines(String f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("file:///github.com/vivianduarte777/filesTest/blob/main/aluguel.pdf"));
        return reader.lines().count();
    }

    private int returnBytesCount(String f) throws IOException {
        FileInputStream stream = new FileInputStream(f);
        return stream.readAllBytes().length;
    }

    private String returnExtensionName(String f) throws IOException {
      return f;
     }

    private URL getURL(String urlAddress) throws  MalformedURLException{
        return new URL(urlAddress);
    }

    private Path getPath(URL url) throws URISyntaxException,  IOException{

  Path path = Paths.get(url.toURI()).getRoot();
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
