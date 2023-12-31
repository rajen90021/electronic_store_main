package elctric.com.service.implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import elctric.com.dtos.CategoriesDto;
import elctric.com.dtos.ProductDto;
import elctric.com.dtos.pagableResponse;
import elctric.com.entity.Categories;
import elctric.com.entity.Product;
import elctric.com.exception.ResourceNotFoundException;
import elctric.com.exception.filenotfoundexpection;
import elctric.com.repository.CategoriesRepository;
import elctric.com.repository.ProductRepository;
import elctric.com.service.ProductService;

@Service
public class ProductServiceimpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoriesRepository categoriesRepository;
	
	 @Value("${product.profile.image.path:image/product/}")
		private String uploadpath;
//	create 
	@Override
	public ProductDto create(ProductDto dto) {

		String id = UUID.randomUUID().toString();

		dto.setId(id);
		
		dto.setAddeddate(new Date());

		Product product = this.mapper.map(dto, Product.class);

		Product product2 = this.productRepository.save(product);

		return this.mapper.map(product2, ProductDto.class);
	}

//	  update 

	@Override
	public ProductDto update(ProductDto dto, String productid) {

		Product product = null;
		Optional<Product> optional = this.productRepository.findById(productid);

		if (optional.isPresent()) {

			product = optional.get();
		} else {
			throw new ResourceNotFoundException("product id not found " + productid);
		}

		product.setAddeddate(new Date());
		product.setTitle(dto.getTitle());
		product.setDiscription(dto.getDiscription());
		product.setDiscountedprice(dto.getDiscountedprice());
		product.setPrice(dto.getPrice());
		product.setQuantity(dto.getQuantity());
		product.setLive(dto.isLive());
		product.setStock(dto.isStock());
		product.setProductImage(dto.getProductImage());

		Product product3 = this.productRepository.save(product);
		return this.mapper.map(product3, ProductDto.class);
	}

//	delete 
	@Override
	public void delete(String productid) {

		Product product = null;
		Optional<Product> optional = this.productRepository.findById(productid);

		if (optional.isPresent()) {

			product = optional.get();
		} else {
			throw new ResourceNotFoundException("product id not found " + productid);
		}
		
		
		   
	    String fullpath= uploadpath+product.getProductImage();
	    
	    Path path = Paths.get(fullpath);
	    
	    
	    try {
			Files.delete(path);
			
			
		} catch (IOException e) {
			
			
			
			e.printStackTrace();
		}

		this.productRepository.delete(product);
	}

//	get by id
	@Override
	public ProductDto getbyid(String productid) {
		Product product = null;
		Optional<Product> optional = this.productRepository.findById(productid);

		if (optional.isPresent()) {

			product = optional.get();
		} else {
			throw new ResourceNotFoundException("product id not found " + productid);
		}

		return this.mapper.map(product, ProductDto.class);
	}

//	get alll product

	@Override
	public pagableResponse<ProductDto> getallproduct(int pagenumber, int pagesize, String sortby, String dirction) {

		  Sort sort = Sort.by(sortby);
		    if (dirction.equalsIgnoreCase("asc")) {
		        sort = sort.ascending();
		    } else {
		        sort = sort.descending();
		    }

		PageRequest pageRequest = PageRequest.of(pagenumber, pagesize, sort);

		Page<Product> pageofproduct = this.productRepository.findAll(pageRequest);

		List<Product> listofcontent = pageofproduct.getContent();

		List<ProductDto> productotdto = productotdto(listofcontent);

		pagableResponse<ProductDto> Response = new pagableResponse<ProductDto>();

		Response.setContect(productotdto);

		Response.setPagenumber(pageofproduct.getNumber());

		Response.setPagesize(pageofproduct.getSize());

		Response.setTotalelement(pageofproduct.getTotalElements());
		Response.setTotalpage(pageofproduct.getTotalPages());

		Response.setLastpage(pageofproduct.isLast());

		return Response;
	}

//	search by title

	@Override
	public pagableResponse<ProductDto> searchbytitle(String keyword, int pagenumber, int pagesize, String sortby,
			String dirction) {

		  Sort sort = Sort.by(sortby);
		    if (dirction.equalsIgnoreCase("asc")) {
		        sort = sort.ascending();
		    } else {
		        sort = sort.descending();
		    }

		PageRequest pageRequest = PageRequest.of(pagenumber, pagesize, sort);

		Page<Product> pageofproduct = this.productRepository.findBytitleContaining(keyword,pageRequest);

		List<Product> listofcontent = pageofproduct.getContent();

		List<ProductDto> productotdto = productotdto(listofcontent);

		pagableResponse<ProductDto> Response = new pagableResponse<ProductDto>();

		Response.setContect(productotdto);

		Response.setPagenumber(pageofproduct.getNumber());

		Response.setPagesize(pageofproduct.getSize());

		Response.setTotalelement(pageofproduct.getTotalElements());
		Response.setTotalpage(pageofproduct.getTotalPages());

		Response.setLastpage(pageofproduct.isLast());

		return Response;

	}

//	   get all live product

	@Override
	public pagableResponse<ProductDto> getallliveproduct(int pagenumber, int pagesize, String sortby,
			String dirction) {
			
		

		  Sort sort = Sort.by(sortby);
		    if (dirction.equalsIgnoreCase("asc")) {
		        sort = sort.ascending();
		    } else {
		        sort = sort.descending();
		    }

		PageRequest pageRequest = PageRequest.of(pagenumber, pagesize, sort);

     	Page<Product> pageofproduct = this.productRepository.findByLiveTrue(pageRequest);

		List<Product> listofcontent = pageofproduct.getContent();

		List<ProductDto> productotdto = productotdto(listofcontent);

		pagableResponse<ProductDto> Response = new pagableResponse<ProductDto>();

		Response.setContect(productotdto);

		Response.setPagenumber(pageofproduct.getNumber());

		Response.setPagesize(pageofproduct.getSize());

		Response.setTotalelement(pageofproduct.getTotalElements());
		Response.setTotalpage(pageofproduct.getTotalPages());

		Response.setLastpage(pageofproduct.isLast());

		return Response;

		
		


		
	}

//	get by stock 
//	@Override
//	public pagableResponse<ProductDto> getstock(int pagenumber, int pagesize, String sortby,
//			String dirction) {
//			
//		
//		Sort sort = Sort.by(sortby);
//		if (dirction.equalsIgnoreCase("asc")) {
//			sort.ascending();
//		} else {
//			sort.descending();
//		}
//
//		PageRequest pageRequest = PageRequest.of(pagenumber, pagesize, sort);
//
//     	Page<Product> pageofproduct = this.productRepository.findByIsStock(pageRequest);
//
//		List<Product> listofcontent = pageofproduct.getContent();
//
//		List<ProductDto> productotdto = productotdto(listofcontent);
//
//		pagableResponse<ProductDto> Response = new pagableResponse<ProductDto>();
//
//		Response.setContect(productotdto);
//
//		Response.setPagenumber(pageofproduct.getNumber());
//
//		Response.setPagesize(pageofproduct.getSize());
//
//		Response.setTotalelement(pageofproduct.getTotalElements());
//		Response.setTotalpage(pageofproduct.getTotalPages());
//
//		Response.setLastpage(pageofproduct.isLast());
//
//		return Response;
//
//		
//	}

//	*****************************************************************************
	public List<ProductDto> productotdto(List<Product> products) {

		List<ProductDto> list = new ArrayList<>();

		for (Product product : products) {

			ProductDto productDto = this.mapper.map(product, ProductDto.class);

			list.add(productDto);
		}

		return list;
	}
//	**********************************************************************************

	@Override
	public String uploadproductimage(MultipartFile file, String path) throws IOException {
		System.out.println("1");
		
		  String originalFilename = file.getOriginalFilename();
		  
		  String random = UUID.randomUUID().toString();
		  
		       String extract = originalFilename.substring(originalFilename.lastIndexOf("."));
		       
		       
		          //.png    //534ggg4
		       
//		       image/category/fgrgrg.png
		       String fullimagename=random+extract;
		         
		         String fullpath= path+fullimagename;
		       if(extract.equalsIgnoreCase(".png")||extract.equalsIgnoreCase(".jpg")||extract.equalsIgnoreCase(".jpeg")) {
		    	   
		    	   
		    		System.out.println("2");
		    	   
			         File file2 = new  File(path);
			         
			         if(!file2.exists()) {
			        	 
			        	 file2.mkdirs();
			         }else {
			        	   
			        	Files.copy(file.getInputStream(), Path.of(fullpath));   
			        	 
			         }
			        
			     	System.out.println("3");
		       }else {
		    	   throw new filenotfoundexpection(" file is not supported with extension "+extract);
		       }
		   	System.out.println("4");
		       return fullimagename;
		        
	}

	@Override
	public InputStream serveimage(String path, String imagename) throws FileNotFoundException {
	    String fullpath= path +File.separator+imagename;
		
		
			InputStream inputStream= new FileInputStream(fullpath);
			
			return inputStream;
	}

	@Override
	public ProductDto productwithcategory(ProductDto dto, String categoryid) {
		
		
		 Categories categories= null;
		   Optional<Categories> findById = this.categoriesRepository.findById(categoryid);
		
		if(findById.isPresent()) {
			
		 categories = findById.get();
		}else {
			throw new ResourceNotFoundException("categorires id not found ");
		}
		String id = UUID.randomUUID().toString();

		dto.setId(id);
		
		dto.setAddeddate(new Date());
		

		Product product = this.mapper.map(dto, Product.class);

		  product.setCategories(categories);
		Product product2 = this.productRepository.save(product);

		return this.mapper.map(product2, ProductDto.class); 
		  
		
		
	}

	@Override
	public ProductDto assigncategorytoproduct(String categoryid,String productid) {
		
//		   get category id
		
		 Categories categories= null;
		   Optional<Categories> findById = this.categoriesRepository.findById(categoryid);
		
		if(findById.isPresent()) {
			
		 categories = findById.get();
		}else {
			throw new ResourceNotFoundException("categorires id not found ");
		}
		
//		get product id 
		
		Product product = null;
		Optional<Product> optional = this.productRepository.findById(productid);

		if (optional.isPresent()) {

			product = optional.get();
		} else {
			throw new ResourceNotFoundException("product id not found " + productid);
		}
		   product.setCategories(categories);
		   Product save = this.productRepository.save(product);
		   ProductDto productDto = this.mapper.map(save, ProductDto.class);		
		return productDto;
	}

	@Override
	public  pagableResponse<ProductDto> getcategoryproduct(String categoryid,int pagenumber,int pagesize,String sortby ,String dirction) {
		
		
		
		 Categories categories= null;
		   Optional<Categories> findById = this.categoriesRepository.findById(categoryid);
		
		if(findById.isPresent()) {
			
		 categories = findById.get();
		}else {
			throw new ResourceNotFoundException("categorires id not found ");
		}
		     
		 Sort sort = Sort.by(sortby);
		    if (dirction.equalsIgnoreCase("asc")) {
		        sort = sort.ascending();
		    } else {
		        sort = sort.descending();
		    }
		
		     PageRequest pageRequest = PageRequest.of(pagenumber, pagesize,sort);
		      
		   Page<Product> findByCategories = this.productRepository.findByCategories(categories, pageRequest);
	
		   
		   
		   
		   List<Product> listofcontent = findByCategories.getContent();

			List<ProductDto> productotdto = productotdto(listofcontent);

			pagableResponse<ProductDto> Response = new pagableResponse<ProductDto>();

			Response.setContect(productotdto);

			Response.setPagenumber(findByCategories.getNumber());

			Response.setPagesize(findByCategories.getSize());

			Response.setTotalelement(findByCategories.getTotalElements());
			Response.setTotalpage(findByCategories.getTotalPages());

			Response.setLastpage(findByCategories.isLast());

			return Response;
		   
		
		
		
	}

}
