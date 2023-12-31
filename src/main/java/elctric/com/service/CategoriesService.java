package elctric.com.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import elctric.com.dtos.CategoriesDto;
import elctric.com.dtos.ProductDto;
import elctric.com.dtos.pagableResponse;


@Service
public interface CategoriesService {

	//create
	public CategoriesDto create(CategoriesDto categoriesDto);
	
	
//	update
	
	public CategoriesDto update( CategoriesDto categoriesDto ,String categoryid);
	
	
//	delete
	
	
	public void delete(String Categoryid);
	
//	gell all category
	
	
	public pagableResponse<CategoriesDto> gellallcategory(int pagenumber, int pagesize,String sortby, String dirc);
	
	
//	get by id
	
	public CategoriesDto getbyid(String categoryid);
	
	
//	get by keyword
	
	public List<CategoriesDto> searchcategory(String keyword);
	
	
	
	

	
//	file upload
	
	public String uploadCategoryimage(MultipartFile file, String path) throws IOException;
	
//	serve image
	
	public InputStream serveimage(String path,String imagename)throws FileNotFoundException;
	
}
