package elctric.com.service.implement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import elctric.com.dtos.CategoriesDto;
import elctric.com.dtos.ProductDto;
import elctric.com.dtos.pagableResponse;
import elctric.com.entity.Categories;
import elctric.com.exception.ResourceNotFoundException;
import elctric.com.exception.filenotfoundexpection;
import elctric.com.repository.CategoriesRepository;
import elctric.com.service.CategoriesService;


@Service
public class CategoriesServiceImp implements CategoriesService {

	
	@Autowired
	 private CategoriesRepository categoriesRepository;
	
	  
	
	@Autowired
	private ModelMapper mapper;
	
	 @Value("${categories.profile.image.path:image/categories/}")
		private String uploadpath;
	
	
//	create
	@Override
	public CategoriesDto create(CategoriesDto categoriesDto) {
		
		String uniqueid= UUID.randomUUID().toString();
		
		categoriesDto.setId(uniqueid);
	    
		Categories categories = mapper.map(categoriesDto, Categories.class);
		
		Categories save = this.categoriesRepository.save(categories);
		     
		
		return this.mapper.map(save, CategoriesDto.class);
	}

	@Override
	
//	update
	public CategoriesDto update(CategoriesDto categoriesDto ,String categoryid) {
		
		System.out.println("3");
		  Categories categories = null;
		  Optional<Categories> findById = this.categoriesRepository.findById(categoryid);
			System.out.println("4");
		  
		  if(findById.isPresent()) {
			   categories = findById.get();
				System.out.println("5");
		  }else {
			  throw new ResourceNotFoundException("categories id is not presen t "+categoryid);
		  }
			System.out.println("6");
		 
		  categories.setDescription(categoriesDto.getDescription());
		  categories.setTitle(categoriesDto.getTitle());
		  categories.setCategoriesimage(categoriesDto.getCategoriesimage());
			System.out.println("7");
		  
		  Categories save = this.categoriesRepository.save(categories);
			System.out.println("8");
		  
		  
		return this.mapper.map(save, CategoriesDto.class);
	}

	@Override
	
//	delete
	
	public void delete(String Categoryid) {
		  Categories categories = null;
		  Optional<Categories> findById = this.categoriesRepository.findById(Categoryid);
		  
		  
		  if(findById.isPresent()) {
			   categories = findById.get();
		  }else {
			  throw new ResourceNotFoundException("categories id is not present "+Categoryid);
		  }
		  
		    String fullpaths= uploadpath+categories.getCategoriesimage();
		    
		    Path path = Paths.get(fullpaths);
		    
		    try {
				Files.delete(path);
				
				
			} catch (IOException e) {
				
				
				e.printStackTrace();
			}
		  
		     
		    
		  
		  this.categoriesRepository.delete(categories);
		
		
		
	}

	
//	get by id
	@Override
	public CategoriesDto getbyid(String categoryid) {
		
		
		 Categories categories = null;
		  Optional<Categories> findById = this.categoriesRepository.findById(categoryid);
		  
		  
		  if(findById.isPresent()) {
			   categories = findById.get();
		  }else {
			  throw new ResourceNotFoundException("categories id is not present  "+categoryid);
		  }
		
		return this.mapper.map(categories, CategoriesDto.class);
	}

	@Override
	public pagableResponse<CategoriesDto> gellallcategory(int pagenumber ,int pagesize,String sortby, String dirc) {
		
		Sort sort = Sort.by(sortby);

		if (dirc.equalsIgnoreCase("dsc")) {
		    sort = sort.descending();
		} else {
		    sort = sort.ascending();
		}

		PageRequest pageable = PageRequest.of(pagenumber, pagesize,sort);
		
		 Page<Categories> pageofCategories = this.categoriesRepository.findAll(pageable);
		    
		     List<Categories> listofCategory = pageofCategories.getContent();
		
		  List<CategoriesDto> categorytoCategoryDto = categorytoCategoryDto(listofCategory);
		  
		       
		   
		  pagableResponse<CategoriesDto> pagableResponse= new pagableResponse<CategoriesDto>();
		  
		  
		  pagableResponse.setContect(categorytoCategoryDto);
		  
		  pagableResponse.setPagenumber(pageofCategories.getNumber());
		  
		  pagableResponse.setPagesize(pageofCategories.getSize());
		  
		  pagableResponse.setTotalelement(pageofCategories.getTotalElements());
		  
		  pagableResponse.setLastpage(pageofCategories.isLast());
  
		  pagableResponse.setTotalpage(pageofCategories.getTotalPages());
		  

		return pagableResponse;
	}

	


	@Override
	public List<CategoriesDto> searchcategory(String keyword) {
		    List<Categories> findBytitleContaining = this.categoriesRepository.findByTitleContaining(keyword);
		    List<CategoriesDto> categorytoCategoryDto = categorytoCategoryDto(findBytitleContaining);
		            
		return categorytoCategoryDto;
	}
	
	
	public List<CategoriesDto> categorytoCategoryDto(List<Categories> categories){
	      
		   List<CategoriesDto> categoriesDtos= new ArrayList<>();
		   
		   for(   Categories categories2 :categories) {
			   
			   categoriesDtos.add(this.mapper.map(categories2, CategoriesDto.class));
		   }
		   
		return categoriesDtos;
		
	}

	
//	upload image
	
	@Override
	public String uploadCategoryimage(MultipartFile file, String path) throws IOException {
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
	
//	serve image

	@Override
	public InputStream serveimage(String path, String imagename) throws FileNotFoundException {
	
		
           String fullpath= path +File.separator+imagename;
		
		
		InputStream inputStream= new FileInputStream(fullpath);
		
		return inputStream;
	}

//	create product with category
	

}
