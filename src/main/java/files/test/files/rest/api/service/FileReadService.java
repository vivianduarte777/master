package files.test.files.rest.api.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileReadService {

    public void init();

//    public Resource load(String url);

    public Stream<Path> loadAll(Path urlPath);

}
