package com.backend.code.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.code.Objects.ImageModel;
import com.backend.code.Objects.UserDetails;
import com.backend.code.Repoistry.FriendsNetworkRepoistry;

@RestController
public class Controller {
	@Autowired

	FriendsNetworkRepoistry repo;
	UserDetails user;

	@GetMapping("/addprofile")
	public String profile(@RequestBody com.backend.code.Objects.profile userprofile) {
		repo.profile(userprofile);
		return "success";
	}

	@GetMapping("/user")
	public List<com.backend.code.Objects.userProfile> findById(@RequestParam(value = "userid") int userid) {
		return repo.findById(userid);
	}

	@PostMapping("/createUser")
	public void insertUsersDetails(@RequestBody UserDetails user) {
		repo.insertUsersDetails(user);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/upload")
	public String uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

		System.out.println("Original Image Byte Size - " + file.getBytes().length + " " + file.getOriginalFilename()
				+ " " + file.getContentType());

		ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
        
        repo.saveImage(img);

        return "String";
	}
	@GetMapping("/get/{imageName}")
	public List<ImageModel> getImage(@PathVariable("imageName") int imageId) throws IOException {
				List<ImageModel> image = repo.findImageByName(imageId);
				return image;
			}
    public static byte[] compressBytes(byte[] data) {
                Deflater deflater = new Deflater();
                deflater.setInput(data);
                deflater.finish();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
                byte[] buffer = new byte[1024];
                while (!deflater.finished()) {
                    int count = deflater.deflate(buffer);
                    outputStream.write(buffer, 0, count);
                }
                try {
                    outputStream.close();
                } catch (IOException e) {
        
                }
             System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
             return outputStream.toByteArray();
    
			}
			public static byte[] decompressBytes(byte[] data) {
						Inflater inflater = new Inflater();
						inflater.setInput(data);
				
						ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
						byte[] buffer = new byte[1024];
			
						try {
							while (!inflater.finished()) {
								int count = inflater.inflate(buffer);
								outputStream.write(buffer, 0, count);
							}
							outputStream.close();
						} catch (IOException ioe) {
						} catch (DataFormatException e) {
						}
						return outputStream.toByteArray();
					}
	

}
