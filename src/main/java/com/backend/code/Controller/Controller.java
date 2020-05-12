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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.code.Objects.ImageModel;
import com.backend.code.Objects.UserDetails;
import com.backend.code.Objects.addComment;
import com.backend.code.Objects.addLike;
import com.backend.code.Objects.displayComment;
import com.backend.code.Objects.post;
import com.backend.code.Objects.postResult;
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

	@PostMapping("/Addpost")
	public String addPost(@RequestBody post p) {
		repo.addPost(p);
		return "post is successfully added";
	}

	@PostMapping("/addLike/{userid}")
	public String addLike(@RequestBody addLike like ,@PathVariable("userid") int userId){
		repo.addLike(like,userId);
		return "like added";
	}
	@PostMapping("/removeLike/{userid}")
	public String removeLike(@RequestBody addLike like,@PathVariable("userid") int userId)
	{
		repo.removeLike(like,userId);
		return "like deleted";
	}
	@GetMapping("/showLike/{userid}")
	public List<String> showLike(@RequestBody addLike like,@PathVariable("userid") int userId)
	{
		return repo.showLike(like,userId);
	}
	@PostMapping("/addComment/{userid}")
	public String addComment(@RequestBody addComment comment,@PathVariable("userid") int userId){
		repo.addComment(comment,userId);
		return "comment added";
	}
	@PostMapping("/removeComment/{userid}")
	public String removeComment(@RequestBody addComment comment,@PathVariable("userid") int userId){
		repo.removeComment(comment,userId);
		return "comment deleted";
	}
	@GetMapping("/showComment/{userid}")
	public List<displayComment> showComment(@RequestBody addComment comment,@PathVariable("userid") int userId)
	{
       return repo.showComment(comment,userId);
	}
	@PostMapping("/addFriend/{userid}")
	public String addFriend(@RequestBody com.backend.code.Objects.addFriend Af, @PathVariable("userid") int userId)
	{
		repo.addFriend(Af,userId);
		return "friend request sent";
	}	
	@PostMapping("/removeFriend/{userid}")
	public String removeFriend(@RequestBody com.backend.code.Objects.addFriend Af,@PathVariable("userid") int userId)
	{
		repo.removeFriend(Af,userId);
		return "friend removed";
	}
	@GetMapping("/showFriends/{userid}")
	public List<String> showFriends(@PathVariable("userid") int userId)
	{
		return repo.showFriends(userId);
	}
	
	@GetMapping("/showPost/{userid}")
	public List<postResult> showPost(@PathVariable("userid") int userId)
	{
         return repo.showPost(userId);
	}
	
	@GetMapping("/login")
	public List<UserDetails> login(@RequestBody UserDetails user)
	{
		return repo.login(user);
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
