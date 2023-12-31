package elctric.com.service.implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import elctric.com.exception.filenotfoundexpection;
import elctric.com.service.FileService;


@Service
public class fileserviceimp  implements FileService{

	@Override
	public String uploadimage(MultipartFile file, String path) throws IOException {
		///path image/user/adarsh.png
		
		   String originalFilename = file.getOriginalFilename();
		   
		   
		   String extract = originalFilename.substring(originalFilename.lastIndexOf("."));
		   
		   
		   
		   String randomid= UUID.randomUUID().toString();
		   
		   
		   String readyimage= randomid+extract;
		   
		   String fullpathwithfilename = path+readyimage;
		   
		   System.out.println(fullpathwithfilename);
		   
		  if(extract.equalsIgnoreCase(".png")|| extract.equalsIgnoreCase(".jpeg")|| extract.equalsIgnoreCase(".jpg")) {
			  
			  
			  
			  File file2= new File(path);
			  
			  
			  if(!file2.exists()) {
				  
				  file2.mkdirs();
				  
			  }
			  else {
				  
				  
				 
				  Files.copy(file.getInputStream() ,Paths.get(fullpathwithfilename));
			  }
			  
		  }
		  else {
			  
			  throw new filenotfoundexpection("file with extension  is not allowed "+extract);
			  
		  }
		   
		   
		
		
		return readyimage;
	}

	@Override
	public InputStream serveimage(String path, String imagename) throws FileNotFoundException {
	
		
		String fullpath= path +File.separator+imagename;
		
		System.out.println(fullpath);
		InputStream inputStream= new FileInputStream(fullpath);
		
		return inputStream;
		
	}

}
