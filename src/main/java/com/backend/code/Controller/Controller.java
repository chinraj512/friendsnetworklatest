package com.backend.code.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.code.Objects.ChatPage;
import com.backend.code.Objects.IdName;
import com.backend.code.Objects.IdNameStatus;
import com.backend.code.Objects.IdPattern;
import com.backend.code.Entity.ImageModel;
import com.backend.code.Entity.UserDetails;
import com.backend.code.Entity.profile;
import com.backend.code.Exception.GlobalExceptionHandler;
import com.backend.code.Exception.ResourceNotFoundException;
import com.backend.code.Objects.addComment;
import com.backend.code.Objects.addLike;
import com.backend.code.Objects.chatUsers;
import com.backend.code.Objects.displayComment;
import com.backend.code.Objects.longProfile;
import com.backend.code.Entity.post;
import com.backend.code.Objects.postResult;
import com.backend.code.Objects.shortProfile;
import com.backend.code.Objects.userProfile;
import com.backend.code.Repoistry.FriendsNetworkRepoistry;

@RestController
public class Controller {
	
	@Autowired

	FriendsNetworkRepoistry repo;
	UserDetails user;

	@GetMapping("/addprofile")
	public String profile(@RequestBody profile userprofile) {

		repo.profile(userprofile);
		return "success";
	}

	@GetMapping("/user")
	public ResponseEntity<?> findById(@RequestParam(value = "userid") int userid) throws ResourceNotFoundException {
		List<userProfile> user = repo.findById(userid);
		if (user.size() == 0) {
			throw new ResourceNotFoundException("user not found");
		}
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/createUser")
	public ResponseEntity<?> insertUsersDetails(@Valid @RequestBody UserDetails user) throws NoSuchAlgorithmException {
		String result = "successfully accepted";
		try {
			repo.insertUsersDetails(user);
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.ok().body("user is already registor");
		}
		return ResponseEntity.ok().body(result);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/upload")
	public List<Integer> uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		System.out.println("Original Image Byte Size - " + file.getBytes().length + " " + file.getOriginalFilename()
				+ " " + file.getContentType());
		ImageModel img = new ImageModel();
		img.setName(file.getOriginalFilename());
		img.setType(file.getContentType());
		img.setPicByte(compressBytes(file.getBytes()));
		return repo.saveImage(img);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/get/{imageName}")
	public List<ImageModel> getImage(@PathVariable("imageName") int imageId)
			throws IOException, ResourceNotFoundException {
		List<ImageModel> image = repo.findImageByName(imageId);
		if (image == null) {
			throw new ResourceNotFoundException("Image is not found ");
		}
		byte[] uy = decompressBytes(image.get(0).getPicByte());
		image.get(0).setPicByte(uy);
		return image;
	}
	
	@CrossOrigin(origins = "*")
	@PostMapping("/Addpost/{userid}")
	public ResponseEntity<String> addPost(@RequestBody post p,@PathVariable("userid") int userId) {
		
			System.out.println("rwwgw");
			repo.addPost(p,userId);
	
		return ResponseEntity.ok().body("post added");
	}
    @CrossOrigin(origins = "*")
	@PostMapping("/addLike/{userid}")
	public String addLike(@RequestBody addLike like, @PathVariable("userid") int userId) {
		repo.addLike(like, userId);
		return "like added";
	}
    @CrossOrigin(origins = "*")
	@PostMapping("/removeLike/{userid}")
	public String removeLike(@RequestBody addLike like, @PathVariable("userid") int userId) {
		repo.removeLike(like, userId);
		return "like deleted";
	}
    @CrossOrigin(origins = "*")
	@GetMapping("/showLike/{userid}")
	public List<IdName> showLike(@PathVariable("userid") int userId) {
		System.out.println("gguhkhk");
		return repo.showLike( userId);
	}
    @CrossOrigin(origins = "*")
	@PostMapping("/addComment/{userid}")
	public String addComment(@RequestBody addComment comment, @PathVariable("userid") int userId) {
		repo.addComment(comment, userId);
		return "comment added";
	}

	@PostMapping("/removeComment/{userid}")
	public String removeComment(@RequestBody addComment comment, @PathVariable("userid") int userId) {
		repo.removeComment(comment, userId);
		return "comment deleted";
	}

	@GetMapping("/showComment/{userid}")
	public List<displayComment> showComment(@RequestBody addComment comment, @PathVariable("userid") int userId) {
		return repo.showComment(comment, userId);
	}

	@PostMapping("/addFriend/{userid}")
	public String addFriend(@RequestBody com.backend.code.Objects.addFriend Af, @PathVariable("userid") int userId) {
		System.out.println(Af.getUser1());
		repo.addFriend(Af, userId);
		return "friend request sent";
	}

	@PostMapping("/removeFriend/{userid}")
	public ResponseEntity<?> removeFriend(@RequestBody com.backend.code.Objects.addFriend Af,
			@PathVariable("userid") int userId) {
		System.out.println("ddfhdjj");
		try {
			repo.removeFriend(Af, userId);
		} catch (NullPointerException e) {
			return ResponseEntity.ok().body("no values are supplied");
		}
		return ResponseEntity.ok().body("friend Removed");
	}

	@GetMapping("/showFriends")
	public List<IdNameStatus> showFriends(@RequestParam(value="userid") int userid) {

		return repo.showFriends(userid);
	}

	@GetMapping("/showmembers")
	public List<IdName> showMembers(@RequestParam(value = "userid") int userid) {
		return repo.showMembers(userid);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/showpost/{userid}")
	public List<postResult> showPost(@PathVariable("userid") int userId) {
		System.out.println("inside");
		List<postResult> y=repo.showPost(userId);
		for(int i=0;i<y.size();i++)
		{
			y.get(i).picByte=(decompressBytes(y.get(i).picByte));
		}

		return y;
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

	@PostMapping("/searchformembers")
	public List<IdName> MemberSearch(@RequestBody IdPattern idpatttern)
	{
		String pattern=idpatttern.pattern;
		int userid=idpatttern.userid;
		return repo.MemberSearch(pattern,userid);		
	}
	@PostMapping("/searchforfriends")
	public List<IdName> FriendSearch(@RequestBody IdPattern idpatttern)
	{
		String pattern=idpatttern.pattern;
		int userid=idpatttern.userid;
		return repo.FriendSearch(pattern,userid);		
	}
	@PostMapping("/insertmessages")
	public String InsertMessage(@RequestBody chatUsers chatusers) throws SQLException
	{
		repo.insertmessages(chatusers);
		return "successfully inserted";
	}
	@GetMapping("/getchatusers")
	public List<ChatPage> getmessages(@RequestParam(value="realuser") int realuser)
	{
		System.out.println("jhgjhgj");
		return repo.getChatDetails(realuser);
	}
	@GetMapping("/getbirthdaypeoples")
	public List<IdName> getBirthdayPeoples(@RequestParam(value="userid") int userid)
	{
		return repo.getBirthdayPeoples(userid);
	}
	@GetMapping("/getshortprofile")
	public List<shortProfile> getShortProfile(@RequestParam (value="userid") int userid){
		System.out.println("sdfrf");
		return repo.getShortProfile(userid);
	}
	
	@GetMapping("/getlongprofile")
	public longProfile getLongProfile(@RequestParam(value="userid") int userid){
		return repo.getLongProfile(userid);
	}
}
