package files.test.files.rest.api.thread;

import files.test.files.rest.api.model.FilesInformationDto;
import files.test.files.rest.api.service.FileReadServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.select.Elements;
import java.util.List;
import java.util.Map;

public class MainThread extends Thread{

private String urlAddress;
private List<FilesInformationDto> listDto = null;
private Map<String,FilesInformationDto> mapFiles =null;


    public MainThread(String urlAddress) {
      this.urlAddress = urlAddress;
    }

    public void initialize(String urlAddress){
        this.urlAddress = urlAddress;
     }

   @Override
    public void run() {
        try {
            FileReadServiceImpl service = new FileReadServiceImpl();
          //  service.init(this.urlAddress);

            Document doc = Jsoup.connect(urlAddress.toString()).get();

            Elements allElements = doc.getAllElements();

            Elements files= doc.getElementsByClass("js-navigation-open Link--primary");
;
           this.mapFiles=service.buildMapFiles(urlAddress);
            setMapFiles(mapFiles);
           this.listDto=service.buildFilesInformationDto(mapFiles);

           setListDto(listDto);
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    public Map<String, FilesInformationDto> getMapFiles() {
        return mapFiles;
    }

    public void setMapFiles(Map<String, FilesInformationDto> mapFiles) {
        this.mapFiles = mapFiles;
    }

    public List<FilesInformationDto> getListDto() {
        return listDto;
    }

    public void setListDto(List<FilesInformationDto> listDto) {
        this.listDto = listDto;
    }


}
