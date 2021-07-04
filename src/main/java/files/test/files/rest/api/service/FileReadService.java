package files.test.files.rest.api.service;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import files.test.files.rest.api.model.FilesInformationDto;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface FileReadService {
    public String urlAddress = null;
    public Map<String,FilesInformationDto> mapFiles =null;
    public List<FilesInformationDto> filesDto = null;
    public FilesInformationDto fileInformationDto = null;

    public void init(String urlAdrdress);

    @Async
    private  List<FilesInformationDto> buildFilesInformationDto(Map<String, FilesInformationDto> mapFiles){return filesDto;};

    @Async
    private  Map<String,FilesInformationDto> buildMapFiles(String uriAddress){return mapFiles;};

    @Async
     private String getURL (String urlAddress){
       return urlAddress;
   };

    @Async
     private int returnLines(HttpURLConnection httpConn,String fileName) throws IOException {return 0;};

    @Async
     private int returnBytesCount (HttpURLConnection httpConn) throws IOException{return 0;};

    @Async
     private FilesInformationDto buildInformationDto(String extension,int count, int lines, int bytes){ return fileInformationDto;};
}
