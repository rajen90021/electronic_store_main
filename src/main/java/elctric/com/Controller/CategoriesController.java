package elctric.com.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import elctric.com.dtos.CategoriesDto;
import elctric.com.dtos.ProductDto;
import elctric.com.dtos.apiResponse;
import elctric.com.dtos.imageresponse;
import elctric.com.dtos.pagableResponse;
import elctric.com.service.implement.CategoriesServiceImp;
import elctric.com.service.implement.ProductServiceimpl;

@RestController
@RequestMapping("/category")
public class CategoriesController {

	@Autowired
	private CategoriesServiceImp  categoriesServiceImp;
	
	@Autowired
	private ProductServiceimpl productServiceimpl;
	
	 @Value("${categories.profile.image.path:image/categories/}")
	private String uploadpath;
	
	 
	 @PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<CategoriesDto> createCategory(@Valid @RequestBody CategoriesDto categoriesDto){
		
		CategoriesDto create = this.categoriesServiceImp.create(categoriesDto);
		
		return new ResponseEntity<CategoriesDto>(create, HttpStatus.CREATED);
		
		
	}
	 
	 @PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{categoryid}")
	public ResponseEntity<CategoriesDto> updateuser(@Valid @PathVariable String categoryid,@RequestBody CategoriesDto categorydto){
		
		   CategoriesDto update = this.categoriesServiceImp.update(categorydto, categoryid);
		   
		   return new ResponseEntity<CategoriesDto>(update,HttpStatus.CREATED);
		
	}

	 
	 @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{Categoryid}")
	public ResponseEntity<apiResponse> delete(@PathVariable("Categoryid") String Categoryid){
		
		this.categoriesServiceImp.delete(Categoryid);
		
		  apiResponse apiResponse = new apiResponse();
		  
		  apiResponse.setMessage("category delete sucessfully");
		  apiResponse.setSucess(true);
		  apiResponse.setStatus(HttpStatus.OK);
		  
		  return new ResponseEntity<apiResponse>(apiResponse,HttpStatus.OK);
		
	}
	
	
//	get by id
	
	@GetMapping("/{categoryid}")
	public ResponseEntity<CategoriesDto> getbyid(@PathVariable("categoryid") String categoryid){
		
		CategoriesDto getbyid = this.categoriesServiceImp.getbyid(categoryid);
		
		return new ResponseEntity<CategoriesDto>(getbyid, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<pagableResponse<CategoriesDto>> getallcategory(
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			@RequestParam(value = "pagesize" ,defaultValue = "5" ,required = false) int pagesize,
			@RequestParam(value = "sortby",defaultValue = "title" ,required = false) String sortby,
			
			@RequestParam(value = "dirc",defaultValue = "asc",required = false) String dirc
			){
		
		
		
		 pagableResponse<CategoriesDto> gellallcategory = this.categoriesServiceImp.gellallcategory(pagenumber,pagesize,sortby,dirc);
		
		
		return new ResponseEntity<pagableResponse<CategoriesDto>>(gellallcategory,HttpStatus.FOUND);
	}
	
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<CategoriesDto>>  search(@PathVariable("keyword") String keyword){
		
		List<CategoriesDto> getbykeyword = this.categoriesServiceImp.searchcategory(keyword);
		
		
		return new ResponseEntity<List<CategoriesDto>>(getbykeyword,HttpStatus.FOUND);
	}
	
	
	@PostMapping("/coverimage/{categoryid}")
	public ResponseEntity<imageresponse> uploadImageCategory(@RequestParam("file") MultipartFile file, @PathVariable("categoryid") String categoryid) {
	    try {
	        String uploadCategoryImageName = this.categoriesServiceImp.uploadCategoryimage(file, uploadpath);
	        System.out.println(uploadCategoryImageName);
	        CategoriesDto categoriesDto = this.categoriesServiceImp.getbyid(categoryid);
	        categoriesDto.setCategoriesimage(uploadCategoryImageName);
	        
	        CategoriesDto update = this.categoriesServiceImp.update(categoriesDto, categoryid);
	        System.out.println("update sucess");
	        imageresponse imageResponse = new imageresponse();
	        imageResponse.setFilename(file.getOriginalFilename());
	        imageResponse.setMessage("File uploaded successfully");
	        imageResponse.setSucess(true);
	        imageResponse.setStatus(HttpStatus.OK);

	        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
	    } catch (IOException e) {
	        // Handle exceptions and return an appropriate error response
	        imageresponse imageResponse = new imageresponse();
	        imageResponse.setSucess(false);
	        imageResponse.setMessage("File upload failed: " + e.getMessage());
	        imageResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        return new ResponseEntity<>(imageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@GetMapping("/coverimage/{categoryid}")
	public void serveimage(@PathVariable("categoryid") String categoryid,HttpServletResponse httpServletResponse) throws IOException {
		
		
		CategoriesDto getbyid = this.categoriesServiceImp.getbyid(categoryid);
		
		        String categoriesimage = getbyid.getCategoriesimage();
		        
		        InputStream serveimage = this.categoriesServiceImp.serveimage(uploadpath, categoriesimage);
		
		    httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		    
		    StreamUtils.copy(serveimage, httpServletResponse.getOutputStream());
		
	}
	
	
	
//	create product with category
	@PostMapping("/{categoryid}/products")
	public ResponseEntity<ProductDto> productwithcategory(@PathVariable("categoryid") String categoryid
			
			,@RequestBody ProductDto dto
			){
		
		
		      ProductDto productwithcategory = this.productServiceimpl.productwithcategory(dto, categoryid);
		       return new ResponseEntity<ProductDto>(productwithcategory,HttpStatus.OK);
		
	}
	
	
//	assign category with product
	
	
	@PutMapping("/{categoryid}/product/{productid}")
	 public ResponseEntity<ProductDto> assigncategorywithproduct(
			 
			 @PathVariable String categoryid,
			 
			 @PathVariable String productid
			 ){
	
		   ProductDto assigncategorytoproduct = this.productServiceimpl.assigncategorytoproduct(categoryid, productid);
		 
		 return new ResponseEntity<ProductDto>(assigncategorytoproduct,HttpStatus.OK);
		 
	 }
	
	@GetMapping("/{categoryid}/product")
	public ResponseEntity<pagableResponse<ProductDto>> getcategoryproduct(@PathVariable("categoryid") String categoryid,
			
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			@RequestParam(value = "pagesize" ,defaultValue = "5" ,required = false) int pagesize,
			@RequestParam(value = "sortby",defaultValue = "title" ,required = false) String sortby,
			
			@RequestParam(value = "dirc",defaultValue = "asc",required = false) String dirc
			
			
			
			
			){
			
		
		   pagableResponse<ProductDto> getcategoryproduct = this.productServiceimpl.getcategoryproduct(categoryid, pagenumber, pagesize, sortby, dirc);
		   
		return new ResponseEntity<pagableResponse<ProductDto>>(getcategoryproduct,HttpStatus.OK);
	
	
		
		
		
	}
}







