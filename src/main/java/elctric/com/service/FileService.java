package elctric.com.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public interface FileService {

	
	
	
	public String uploadimage(MultipartFile file,String path) throws IOException;
	public InputStream serveimage(String path,String imagename) throws FileNotFoundException;
	
}
