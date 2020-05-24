package com.backend.code.Repoistry;

import java.util.Date;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.backend.code.Objects.IdName;
import com.backend.code.Entity.ImageModel;
import com.backend.code.Entity.UserDetails;
import com.backend.code.Entity.profile;
import com.backend.code.Objects.addComment;
import com.backend.code.Objects.addFriend;
import com.backend.code.Entity.post;
import com.backend.code.Objects.postResult;
import com.backend.code.Objects.userProfile;
import com.backend.code.Objects.userpass;

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
                "select * from userdetails left join profile on userdetails.userid=profile.userid left join ImageModel on profile.picId=ImageModel.picId where userdetails.userid=:id  ",
                param, new UserDetailsRowMapper());
    }

    public void insertUsersDetails(@RequestBody UserDetails user) throws NoSuchAlgorithmException {
        final String sql = "INSERT INTO userdetails( username, password, email, phonenumber, dateofbirth, gender, age)VALUES (:username ,:password , :email, :phonenumber, :dateofbirth, :gender, :age);";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashPassword=passwordEncoder.encode(user.getPassword());
        KeyHolder holder = new GeneratedKeyHolder();
        System.out.println("gugguguguguiguigugiugui");
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password", hashPassword).addValue("email", user.getEmail())
                .addValue("phonenumber", user.getPhonenumber()).addValue("dateofbirth", user.getDateofbirth())
                .addValue("gender", user.getGender()).addValue("age", user.getAge());
                   template.update(sql, param, holder);
                
    }
    public void profile(profile userprofile) {

        final String sql = "INSERT INTO profile(user_id, school, college, degree,work, locality,picid)VALUES (:userid, :school ,:college , :degree, :work, :locality,:picid);";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("userid", userprofile.getUserDetails().getUserid())
                .addValue("school", userprofile.getSchool()).addValue("college", userprofile.getCollege())
                .addValue("degree", userprofile.getDegree()).addValue("work", userprofile.getWork())
                .addValue("locality", userprofile.getLocality()).addValue("picid", userprofile.getImageModel().getPicid());

        template.update(sql, param, holder);
    }

    public void saveImage(ImageModel img) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into ImageModel(picid,name,type,picbyte) values(:picId,:name,:type,:picByte)";
        System.out.println(img.getPicByte());
        SqlParameterSource param = new MapSqlParameterSource().addValue("picId", img.getPicid())
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
    .addValue("userId",p.getUserDetails().getUserid())
    .addValue("picId",p.getImageModel().getPicid())
    .addValue("status",p.getStatus())
    .addValue("location",p.getLocation())
    .addValue("likeCount",p.getLikeCount())
    .addValue("commentCount",p.getCommentCount())
    .addValue("date",IST);
    template.update(sql,param,holder);
}
public void addLike(com.backend.code.Objects.addLike like,int userId) {
    final String sql1 ="insert into likec(postid,userid) values(:postId,:userId)";
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
    final String sql1 ="insert into comment(postid,userid,comment) values(:postId,:userId,:comment)";
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
    final String sql="insert into friendsrelation(user1,user2,activity,lastuser) values (:user1,:user2,:relation,:lastAction)";
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
    final String sql="delete from like where postid=:postId and userid=:userId";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("postId",like.postId)
    .addValue("userId",userId);
    template.update(sql,param,holder);
}

public void removeComment(com.backend.code.Objects.addComment comment, int userId) {
    final String sql="delete from comment where postid=:postId and userid=:userId and commentid=:commentId";
    KeyHolder holder = new GeneratedKeyHolder();
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("postId",comment.postid)
    .addValue("userId",userId)
    .addValue("commentId",comment.commentid);
    template.update(sql,param,holder);
}

public List<IdName> showFriends(int userId) {
    final String sql="select username from userdetails where userid in (select user1 as user from friendsrelation where user2=:userId union select user2 as user from friendsrelation where user1=:userId)";
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("userId",userId);
   return template.query(sql,param,new IdNameMapper());
}
public List<IdName> showMembers(int userid){
	final String sql="select userid,username from userdetails where userid!=:userid and userid not in (select userid1 as user from friendsrelation where userid2=:userid union select userid2 as user from friendsrelation where userid1=:userid)";
	 SqlParameterSource param=new MapSqlParameterSource().addValue("userid", userid);
	return template.query(sql,param,new IdNameMapper());
}

public List<IdName> showLike(com.backend.code.Objects.addLike like, int userId) {
    final String sql="select userid,username from userdetails where userid in (select userid from likec where postid=:postId)";
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("postId",like.postId);
    return template.query(sql,param,new IdNameMapper());
}

public List<com.backend.code.Objects.displayComment> showComment(com.backend.code.Objects.addComment comment, int userId) {
    final String sql="select c.commentid,c.userid,c.comment,username from comment as c join userdetails as u on c.userid=u.userid where postid=:postId";
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("postId",comment.postid);
    return template.query(sql,param,new commentMapper());
  }

public List<postResult> showPost(int userId) {
    final String sql="select p.postid ,u.userid, p.status,p.location,p.commentcount,p.likecount,p.date,u.username,i.name,i.type,i.picbyte,(case when exists(select userid,postid from likec where userid=:userid and postid=p.postid) then true when not(exists(select userid,postid from likec where userid=:userid and postid=p.postid) then false end) as liked from post p join userdetails u on p.userid=u.userid join imagemodel i on p.picid=i.picid where p.userid in (select user1 as user from friendsrelation where user2=:userId union select user2 as user from friendsrelation where user1=:userId) orderby p.date unoin select * from imagemodel i join profile p on p.userid=i.userid join post po on po.userid=p.userid where po.userid=p.userid ";
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("userId",userId);
	return template.query(sql,param,new postMapper());
}

public List<userpass> findpassword(String username) {
    final String sql="select userid,email,password from userdetails where email=:username";
    SqlParameterSource param =new MapSqlParameterSource()
    .addValue("username",username);
    return template.query(sql,param,new userpassMapper());
}
}
