package files.test.files.rest.api.controller;

import ch.qos.logback.core.rolling.helper.FileNamePattern;
import files.test.files.rest.api.model.FileReadedInformation;
import files.test.files.rest.api.model.FilesInformationDto;
import files.test.files.rest.api.service.FileReadService;
import files.test.files.rest.api.service.FileReadServiceImpl;

import files.test.files.rest.api.thread.MainThread;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
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
public class ReadFileController{
    private final String uriAddress = "https://github.com/vivianduarte777/filesTest/";

    private Map<String,FilesInformationDto> mapFiles=null;
    private List<FilesInformationDto> listDto = null;

    //private MainThread mainThread;


     FileReadServiceImpl service;

   @RequestMapping(value="/",method=RequestMethod.GET)
    public String check() {
         try {
             service =  new FileReadServiceImpl();
             this.mapFiles=service.buildMapFiles(uriAddress);
             this.listDto=service.buildFilesInformationDto(mapFiles);

             if(listDto==null){
                 return "Error to read files";
             }
             String strReturn = "{";
            for(FilesInformationDto dto: listDto) {
                strReturn = strReturn + "File extension: " + dto.getExtension() + ", Counts: " + dto.getCount() + " Lines: "//
                        + dto.getLines() + " Bytes: " + dto.getBytes() +"}";
            }
            return strReturn;
         }catch(Exception e){
           e.printStackTrace();

           return e.getMessage();
       }
    //return "ok";
   }


    }
