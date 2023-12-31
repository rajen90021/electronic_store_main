package elctric.com.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import elctric.com.dtos.ProductDto;
import elctric.com.dtos.pagableResponse;

public interface ProductService {

	
	
//	create 
	
	
	ProductDto  create(ProductDto dto);
	
//	update 
	
	ProductDto update(ProductDto dto,String productid);
	
//	delete
	
	void delete(String productid);
	
//	get by id
	
	ProductDto getbyid(String productid);
	
	
//	get all product
	
	pagableResponse<ProductDto>  getallproduct(int pagenumber,int pagesize,String sortby ,String dirction );
	
//	search by title
	
	
	pagableResponse<ProductDto> searchbytitle(String keyword,int pagenumber,int pagesize,String sortby ,String dirction );
	
//	  get all live 
	
	pagableResponse<ProductDto> getallliveproduct(int pagenumber, int pagesize, String sortby,
			String dirction);	

//	 create product with category
	
	ProductDto  productwithcategory(ProductDto dto,String categoryid);
	
	
	
//	assign category to product
	
	  ProductDto   assigncategorytoproduct(String categoryid,String productid);
	  
//	  get category product
	  public    pagableResponse<ProductDto> getcategoryproduct(String categoryid ,int pagenumber,int pagesize,String sortby ,String dirction);
	
//	file upload
	
	public String uploadproductimage(MultipartFile file, String path) throws IOException;
	
//	serve image
	
	public InputStream serveimage(String path,String imagename)throws FileNotFoundException;
	
	
//	get stock
	
//	pagableResponse<ProductDto> getstock(int pagenumber, int pagesize, String sortby,
//				String dirction);
}
