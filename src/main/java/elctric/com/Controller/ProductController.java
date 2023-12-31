package elctric.com.Controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

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
import elctric.com.dtos.Userdto;
import elctric.com.dtos.apiResponse;
import elctric.com.dtos.imageresponse;
import elctric.com.dtos.pagableResponse;
import elctric.com.service.implement.ProductServiceimpl;

@RestController

@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductServiceimpl productServiceimpl;
	
	 @Value("${product.profile.image.path:image/product/}")
		private String uploadpath;
	 
	 
	 
//	 create
	 @PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto){
		
		
		ProductDto productDto = this.productServiceimpl.create(dto);
		
		return new ResponseEntity<ProductDto>(productDto,HttpStatus.CREATED);
		
		
		
		
	}
	
	
//	delete 
	 @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{productid}")
	public ResponseEntity<apiResponse> delete(@PathVariable("productid") String productid){
		  
		this.productServiceimpl.delete(productid);
		
		apiResponse apiResponse= new apiResponse();
		
		
		apiResponse.setMessage("product delete succfully ");
		apiResponse.setStatus(HttpStatus.OK);
		apiResponse.setSucess(true);
		
		return new ResponseEntity<apiResponse>(apiResponse,HttpStatus.OK);
	}
	
	
//	update 
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{productid}")
	public ResponseEntity<ProductDto> update( @RequestBody ProductDto dto,@PathVariable("productid") String productid){
		
		
		  ProductDto update = this.productServiceimpl.update(dto, productid);
		  
		  
		  return new ResponseEntity<ProductDto>(update,HttpStatus.OK);
		  
		  
	}
	
//	get by id 
	@GetMapping("/{productid}")
	public ResponseEntity<ProductDto> getbyid(@PathVariable("productid") String productid){
		ProductDto productDto = this.productServiceimpl.getbyid(productid);
		
		
		return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
		
	}
	
//	search 
	@GetMapping("/search/{keyword}")
	public ResponseEntity<pagableResponse<ProductDto>> search(
			
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			@RequestParam(value = "pagesize" ,defaultValue = "5" ,required = false) int pagesize,
			@RequestParam(value = "sortby",defaultValue = "title" ,required = false) String sortby,
			
			@RequestParam(value = "dirc",defaultValue = "asc",required = false) String dirc,
			  
			@PathVariable("keyword") String keyword
			
			){
		
		
		 pagableResponse<ProductDto> pagableResponse = this.productServiceimpl.searchbytitle(keyword, pagenumber, pagesize, sortby, dirc);
				return new ResponseEntity<pagableResponse<ProductDto>>(pagableResponse,HttpStatus.OK);
		
		
	}
	
//	get all live 
	
	@GetMapping("/live")
	public ResponseEntity<pagableResponse<ProductDto>> live(
			
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			@RequestParam(value = "pagesize" ,defaultValue = "5" ,required = false) int pagesize,
			@RequestParam(value = "sortby",defaultValue = "title" ,required = false) String sortby,
			
			@RequestParam(value = "dirc",defaultValue = "asc",required = false) String dirc
			  
		
			
			){
		  pagableResponse<ProductDto> getallliveproduct = this.productServiceimpl.getallliveproduct(pagenumber, pagesize, sortby, dirc);
		
		
				return  new ResponseEntity<pagableResponse<ProductDto>>(getallliveproduct,HttpStatus.OK);
		
		
		
	}
	
	
//	get alll
	
	@GetMapping
	public ResponseEntity<pagableResponse<ProductDto>> getall(
			
			@RequestParam(value = "pagenumber",defaultValue = "0",required = false) int pagenumber,
			@RequestParam(value = "pagesize" ,defaultValue = "5" ,required = false) int pagesize,
			@RequestParam(value = "sortby",defaultValue = "title" ,required = false) String sortby,
			
			@RequestParam(value = "dirc",defaultValue = "asc",required = false) String dirc
			
			
			){
		  pagableResponse<ProductDto> getallproduct = this.productServiceimpl.getallproduct(pagenumber, pagesize, sortby, dirc);
		        
				return new ResponseEntity<pagableResponse<ProductDto>>(getallproduct,HttpStatus.OK);
		
	}
	
	
	
//	upload iamge 
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/image/{productid}")
	public ResponseEntity<imageresponse> uploadimage(@PathVariable("productid") String productid , @RequestParam("file") MultipartFile file) throws IOException{
		
		
		 String uploadproductimage = this.productServiceimpl.uploadproductimage(file, uploadpath);
		 
		 
		 System.out.println(uploadproductimage);
		 
		 ProductDto productDto = this.productServiceimpl.getbyid(productid);
			
			productDto.setProductImage(uploadproductimage);
			
			ProductDto product = this.productServiceimpl.update(productDto, productid);
		 
		 imageresponse imgResponse= new imageresponse();
		 
		 imgResponse.setMessage("file uploaded sucesfully ");
		 imgResponse.setFilename(uploadproductimage);
		 imgResponse.setSucess(true);
		 imgResponse.setStatus(HttpStatus.OK);
		 
		 return new ResponseEntity<imageresponse>(imgResponse,HttpStatus.OK);
		 
		 
		 
		 
		
	}

	
	@GetMapping("/image/{productid}")
	public void serveimage(@PathVariable("productid") String productid,HttpServletResponse httpServletResponse) throws IOException {
		
		
		ProductDto getbyid = this.productServiceimpl.getbyid(productid);
		
		        String categoriesimage = getbyid.getProductImage();
		        
		        InputStream serveimage = this.productServiceimpl.serveimage(uploadpath, categoriesimage);
		
		    httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
		    
		    StreamUtils.copy(serveimage, httpServletResponse.getOutputStream());
		
	}
	
	

	   
}
