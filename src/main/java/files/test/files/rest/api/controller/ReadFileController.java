package files.test.files.rest.api.controller;

import files.test.files.rest.api.model.FilesInformationDto;
import files.test.files.rest.api.service.FileReadServiceImpl;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController

@RequestMapping("/api/read")
public class ReadFileController{
    private final String uriAddress = "https://github.com/vivianduarte777/filesTest/";

    private Map<String,FilesInformationDto> mapFiles=null;
    private List<FilesInformationDto> listDto = null;

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
           return e.getMessage();
       }
   }
  }
