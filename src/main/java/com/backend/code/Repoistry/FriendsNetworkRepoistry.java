package com.backend.code.Repoistry;

import java.util.Date;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.backend.code.Objects.ImageModel;
import com.backend.code.Objects.UserDetails;
import com.backend.code.Objects.addComment;
import com.backend.code.Objects.addFriend;
import com.backend.code.Objects.post;
import com.backend.code.Objects.postResult;
import com.backend.code.Objects.userProfile;

@Repository
public class FriendsNetworkRepoistry implements FriendsNetworkInterface {

    NamedParameterJdbcTemplate template;

    public FriendsNetworkRepoistry(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<userProfile> findById(int id) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        return template.query(
                "select * from userdetails left join profile on userdetails.userid=profile.user_id left join ImageModel on profile.picId=ImageModel.picId where userdetails.userid=:id  ",
                param, new UserDetailsRowMapper());
    }

    public void insertUsersDetails(@RequestBody UserDetails user) throws NoSuchAlgorithmException {
        final String sql = "INSERT INTO userdetails( username, password, email, phonenumber, dateofbirth, gender, age)VALUES (:username ,:password , :email, :phonenumber, :dateofbirth, :gender, :age);";
        HashFunction hash=new HashFunction();
        String hashPassword=hash.toHexString(hash.getSHA(user.getPassword()));
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("username", user.getUsername())
                .addValue("password", hashPassword).addValue("email", user.getEmail())
                .addValue("phonenumber", user.getPhonenumber()).addValue("dateofbirth", user.getDateofbirth())
                .addValue("gender", user.getGender()).addValue("age", user.getAge());

        template.update(sql, param, holder);

    }
    public List<userProfile> login(UserDetails user) throws NoSuchAlgorithmException  {
    	HashFunction hash=new HashFunction();
    	String hashPassword=hash.toHexString(hash.getSHA(user.getPassword()));
    	user.setPassword(hashPassword);
    	System.out.println(hashPassword);
    	final String sql = "select userdetails.userid ,userdetails.username ,userdetails.email,userdetails.phonenumber,userdetails.age ,userdetails.gender ,userdetails.dateofbirth,profile.college ,profile.work ,profile.degree ,profile.locality ,profile.school,ImageModel.picId,ImageModel.name,ImageModel.type,ImageModel.picByte from userdetails  left join profile on userdetails.userid=profile.user_id left join on profile.picId=ImageModel.picId where userdetails.email=:email and userdetails.password=:password ";
        SqlParameterSource param = new MapSqlParameterSource()
        		.addValue("email", user.getEmail())
        		.addValue("password",user.getPassword());
    	return template.query(sql,param,new UserDetailsRowMapper());
    }
    public void profile(com.backend.code.Objects.profile userprofile) {

        final String sql = "INSERT INTO profile(user_id, school, college, degree,work, locality)VALUES (:userid, :school ,:college , :degree, :work, :locality);";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("userid", userprofile.getUserId())
                .addValue("school", userprofile.getSchool()).addValue("college", userprofile.getCollege())
                .addValue("degree", userprofile.getDegree()).addValue("work", userprofile.getWork())
                .addValue("locality", userprofile.getLocality());

        template.update(sql, param, holder);
    }

    public void saveImage(ImageModel img) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into ImageModel(picid,name,type,picbyte) values(:picId,:name,:type,:picByte)";
        System.out.println(img.getPicByte());
        SqlParameterSource param = new MapSqlParameterSource().addValue("picId", img.getPicId())
                .addValue("name", img.getName()).addValue("type", img.getType()).addValue("picByte", img.getPicByte());
        template.update(sql, param, holder);
    }

    public List<ImageModel> findImageByName(int imageId) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("picId", imageId);
        return template.query("select * from  imageModel where picid=:picId", param, new ImageMapper());
    }

    public void addPost(post p) {
        
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:SS z");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String IST = df.format(today);
        System.out.println("Date in Indian Timezone (IST) : " + IST);

    final String sql = "insert into post(userid,picid,status,location,likecount,commentcount,date) values(:userId,:picId,:status,:location,:likeCount,:commentCount,:date)";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param = new MapSqlParameterSource()
    .addValue("postId",p.getPostId())
    .addValue("userId",p.getUserId())
    .addValue("picId",p.getPicId())
    .addValue("status",p.getStatus())
    .addValue("location",p.getLocation())
    .addValue("likeCount",p.getLikeCount())
    .addValue("commentCount",p.getCommentCount())
    .addValue("date",IST);
    template.update(sql,param,holder);
}
public void addLike(com.backend.code.Objects.addLike like,int userId) {
    final String sql1 ="insert into liketracker(postid,userid) values(:postId,:userId)";
    final String sql2="update post set likecount=:likeCount where postid=:postId";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("postId",like.postId)
    .addValue("userId",userId)
    .addValue("likeCount",like.likeCount);
    template.update(sql1,param,holder);
    template.update(sql2,param,holder);
}
public void addComment(addComment comment, int userId) {
    final String sql1 ="insert into commenttracker(postid,userid,comment) values(:postId,:userId,:comment)";
    final String sql2="update post set commentcount=:commentCount where postid=:postId";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("postId",comment.postid)
    .addValue("userId",userId)
    .addValue("commentCount",comment.commentCount)
    .addValue("comment",comment.comment);
    template.update(sql1,param,holder);
    template.update(sql2,param,holder);
}

public void addFriend(addFriend af, int userId) {
    int user1;
    int user2;
    if(af.getUser1()>userId)
    {
       user1=userId;
       user2=af.getUser1();
    }
    else
    {
       user2=userId;
       user1=af.getUser1();
    }
    final String sql="insert into friendsrelation(user1,user2,relation,lastaction) values (:user1,:user2,:relation,:lastAction)";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("user1",user1)
    .addValue("user2",user2)
    .addValue("relation",af.getRelation())
    .addValue("lastAction",af.getLastAction());
    template.update(sql,param,holder);
}

public void removeFriend(com.backend.code.Objects.addFriend af, int userId){
            final String sql="delete from friendsrelation where user1=:userId and user2=:user1 or user1=:user1 and user2=:userId";
            KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("user1",af.getUser1())
    .addValue("userId",userId);
    template.update(sql,param,holder);
        }

public void removeLike(com.backend.code.Objects.addLike like, int userId) {
    final String sql="delete from liketracker where postid=:postId and userid=:userId";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("postId",like.postId)
    .addValue("userId",userId);
    template.update(sql,param,holder);
}

public void removeComment(com.backend.code.Objects.addComment comment, int userId) {
    final String sql="delete from commenttracker where postid=:postId and userid=:userId and commentid=:commentId";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("postId",comment.postid)
    .addValue("userId",userId)
    .addValue("commentId",comment.commentid);
    template.update(sql,param,holder);
}

public List<String> showFriends(int userId) {
    final String sql="select username from userdetails where userid in (select user1 as user from friendsrelation where user2=:userId union select user2 as user from friendsrelation where user1=:userId)";
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("userId",userId);
   return template.query(sql,param,new NameMapper());
}

public List<String> showLike(com.backend.code.Objects.addLike like, int userId) {
    final String sql="select username from userdetails where userid in (select userid from liketracker where postid=:postId)";
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("postId",like.postId);
    return template.query(sql,param,new NameMapper());
}

public List<com.backend.code.Objects.displayComment> showComment(com.backend.code.Objects.addComment comment, int userId) {
    final String sql="select c.commentid,c.userid,c.comment,username from commenttracker as c join userdetails as u on c.userid=u.userid where postid=0";
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("postId",comment.postid);
    return template.query(sql,param,new commentMapper());
  }

public List<postResult> showPost(int userId) {
    final String sql="select p.postid ,u.userid, p.status,p.location,p.commentcount,p.likecount,p.date,u.username,i.name,i.type,i.picbyte from post p join userdetails u on p.userid=u.userid join imagemodel i on p.picid=i.picid where p.userid in (select user1 as user from friendsrelation where user2=:userId union select user2 as user from friendsrelation where user1=:userId)";
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("userId",userId);
	return template.query(sql,param,new postMapper());
}

}
