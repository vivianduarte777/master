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

import javax.net.ssl.HttpsURLConnection;
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

            getAllFiles(files,url);

            return files.toString();

        }catch(Exception e){
           e.printStackTrace();

           return e.getMessage();
       }
    //return "ok";
   }

    private void getAllFiles(Elements elements,URL url) {
        for (Element e : elements) {
            String name = e.attr("title");
            int lgt = name.length();
            int indexOf = name.indexOf(".");
            String extension = name.substring(indexOf, lgt);
            System.out.println(extension);
           try {
                String urFileStr = uriAddress + "blob/main/" + name;
                try {
                    URI uriFile = new URI(urFileStr);
                    URL urlFile = getURL(uriAddress);
                    HttpURLConnection httpConn = (HttpURLConnection) urlFile.openConnection();
                    int responseCode = httpConn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        int lines = returnLines(httpConn);
                        int buffers = returnBytesCount(httpConn);
                        String strName = name + " lines = "+lines + " buffers "+ buffers;
                        System.out.println("strName " + strName);
                        filesNameExtension.put(strName, extension);
                      }
                    httpConn.disconnect();
                } catch (Exception errFile) {
                    System.out.println(errFile.getMessage());
                }


                 } catch (Exception errFiles) {
               System.out.println(errFiles.getMessage());

           }

        }
    }

    private int returnLines(HttpURLConnection httpConn) throws IOException {
        InputStream inputstream = httpConn.getInputStream();
       int dataInputStream= inputstream.read();
       System.out.println("lines "+dataInputStream);
        return dataInputStream;
    }

        private int returnBytesCount (HttpURLConnection httpConn) throws IOException {
            InputStream inputstream = httpConn.getInputStream();
            int dataInputStream = 0;
            dataInputStream= inputstream.read();
            if (dataInputStream != -1) {
                System.out.println(dataInputStream);
               dataInputStream = inputstream.readAllBytes().length + dataInputStream;
                System.out.println("readByteCounts " + dataInputStream);
                return dataInputStream;
           }
            return 0;
         }

        private String returnExtensionName (String f) throws IOException {
            return f;
        }

        private URL getURL (String urlAddress) throws MalformedURLException {
            return new URL(urlAddress);
        }

    }
