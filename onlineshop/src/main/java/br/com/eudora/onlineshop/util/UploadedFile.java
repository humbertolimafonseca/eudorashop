package br.com.eudora.onlineshop.util;

public class UploadedFile {
//	"name": "picture1.jpg",
//    "size": 902604,
//    "url": "http:\/\/example.org\/files\/picture1.jpg",
//    "thumbnailUrl": "http:\/\/example.org\/files\/thumbnail\/picture1.jpg",
//    "deleteUrl": "http:\/\/example.org\/files\/picture1.jpg",
//    "deleteType": "DELETE"
    	
    private String name;
	private long size;
	
	
	public UploadedFile(String name, long size) {
		super();
		this.name = name;
		this.size = size;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getSize() {
		return size;
	}


	public void setSize(long size) {
		this.size = size;
	}


	public String getUrl() {
		return "/onlineshop/resources/produto/thumbnail/"+ getName();
	}




	public String getThumbnailUrl() {
		return "/onlineshop/resources/produto/thumbnail/"+ getName();
	}


	public String getDeleteUrl() {
		return "/onlineshop/resources/produto/"+ getName();
	}




	public String getDeleteType() {
		return "DELETE";
	}


	
	

}
