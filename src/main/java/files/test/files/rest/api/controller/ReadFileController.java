package files.test.files.rest.api.controller;

import files.test.files.rest.api.model.FileInformationModel;
import files.test.files.rest.api.model.FilesInformationDto;
import files.test.files.rest.api.model.UrlModel;
import files.test.files.rest.api.service.FileReadServiceImpl;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class ReadFileController{
    private final String uriAddress = "https://github.com/vivianduarte777/filesTest/";

    private Map<String,FilesInformationDto> mapFiles=null;
    private List<FilesInformationDto> listDto = null;

    FileReadServiceImpl service;

   @GetMapping("/templates")
   public ModelAndView index(Model model){
       model.addAttribute("infidel",new FileInformationModel());
       return new ModelAndView("index");
   }

   @PostMapping("read")
  // public String check() {
   //public String getForm(Model model){
   // return model.getAttribute("fileInformation").toString();
       public String postForm(@ModelAttribute FileInformationModel infidel, Model model) {
         // model.getAttribute("fileInformation");
           FileInformationModel m = new FileInformationModel();
           m.setUrlAddress(infidel.getUrlAddress());
           model.addAttribute("urlAddress", m);
           m.setFileInformation(getFilesInformation());
           model.addAttribute("fileInformation",m.getFileInformation());
return m.getFileInformation();
      }
/*
    @PostMapping("read")
    public String postForm(@ModelAttribute FileInformationModel infidel, Model model) {
      // model.getAttribute("fileInformation");
        FileInformationModel m = new FileInformationModel();
        m.setUrlAddress(infidel.getUrlAddress());
        model.addAttribute("urlAddress", m);
        m.setFileInformation(getFilesInformation());
        model.addAttribute("fileInformation",m.getFileInformation());
        return 'result';
     }*/

    public List<FilesInformationDto> getListDto() {
        return listDto;
    }

    public void setListDto(List<FilesInformationDto> listDto) {
        this.listDto = listDto;
    }

    //Return the String with the Information about the files readed
    private String getFilesInformation(){
        String strReturn = null;
        try {
            service =  new FileReadServiceImpl();
            this.mapFiles=service.buildMapFiles(uriAddress);
            this.listDto=service.buildFilesInformationDto(mapFiles);

            strReturn = "{";
            for(FilesInformationDto dto: listDto) {
                strReturn = strReturn + "File extension: " + dto.getExtension() + ", Counts: " + dto.getCount() + " Lines: "//
                        + dto.getLines() + " Bytes: " + dto.getBytes() +"}";
            }

         }catch(Exception e){
            return e.getMessage();
        }
        return strReturn;

    }
}
