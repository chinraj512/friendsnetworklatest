package com.backend.code.Repoistry;

import java.util.Arrays;
import java.util.Date;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import com.backend.code.Objects.ChatMessage;
import com.backend.code.Objects.ChatPage;
import com.backend.code.Objects.IdName;
import com.backend.code.Objects.IdNameStatus;
import com.backend.code.Objects.IdPattern;
import com.backend.code.Controller.ChatController;
import com.backend.code.Entity.ImageModel;
import com.backend.code.Entity.UserDetails;
import com.backend.code.Entity.profile;
import com.backend.code.Objects.addComment;
import com.backend.code.Objects.addFriend;
import com.backend.code.Objects.chatUsers;
import com.backend.code.Objects.messageobj;
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

    public boolean findByEmailId(String email) {
        final String sql = "select userid,username from userdetails where email=:email";
        SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
        if (template.query(sql, param, new IdNameMapper()) != null)
            return true;
        else
            return false;
    }

    public int updatepassword(String password, String email) {
        final String sql = "update userdetails set password=:password where email=:email";
        SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
        KeyHolder holder = new GeneratedKeyHolder();
        return (template.update(sql, param, holder));

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
        String hashPassword = passwordEncoder.encode(user.getPassword());
        KeyHolder holder = new GeneratedKeyHolder();
        System.out.println("gugguguguguiguigugiugui");
        SqlParameterSource param = new MapSqlParameterSource().addValue("username", user.getUsername())
                .addValue("password", hashPassword).addValue("email", user.getEmail())
                .addValue("phonenumber", user.getPhonenumber()).addValue("dateofbirth", user.getDateofbirth())
                .addValue("gender", user.getGender()).addValue("age", user.getAge());
        template.update(sql, param, holder);

    }

    public void profile(profile userprofile) {

        final String sql = "INSERT INTO profile(user_id, school, college, degree,work, locality,picid)VALUES (:userid, :school ,:college , :degree, :work, :locality,:picid);";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("userid", userprofile.getUserDetails().getUserid())
                .addValue("school", userprofile.getSchool()).addValue("college", userprofile.getCollege())
                .addValue("degree", userprofile.getDegree()).addValue("work", userprofile.getWork())
                .addValue("locality", userprofile.getLocality())
                .addValue("picid", userprofile.getImageModel().getPicid());
        template.update(sql, param, holder);
    }

    public List<Integer> saveImage(ImageModel img) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into Image_model(name,type,picbyte) values(:name,:type,:picByte) returning picid";
        System.out.println(img.getPicByte());
        SqlParameterSource param = new MapSqlParameterSource().addValue("picId", img.getPicid())
                .addValue("name", img.getName()).addValue("type", img.getType()).addValue("picByte", img.getPicByte());
        return template.query(sql, param, new NameMapper());
    }

    public List<ImageModel> findImageByName(int imageId) {
        SqlParameterSource param = new MapSqlParameterSource().addValue("picId", imageId);
        return template.query("select * from  image_model where picid=:picId", param, new ImageMapper());
    }

    public void addPost(post p, int userId) {
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:SS");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String IST = df.format(today);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Date in Indian Timezone (IST) : " + IST);
        final String sql = "insert into post(userid,status,location,likecount,commentcount,date,picid) values(:userId,:status,:location,:likeCount,:commentCount,:date,:picId)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId)
                .addValue("picId", p.getPostId()).addValue("status", p.getStatus())
                .addValue("location", p.getLocation()).addValue("likeCount", p.getLikeCount())
                .addValue("commentCount", p.getCommentCount()).addValue("date", timestamp);
        template.update(sql, param, holder);
    }

    public void addLike(com.backend.code.Objects.addLike like, int userId) {
        final String sql1 = "insert into likec(postid,userid) values(:postId,:userId)";
        final String sql2 = "update post set likecount=likeCount+1 where postid=:postId";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("postId", like.postId).addValue("userId",
                userId);
        template.update(sql1, param, holder);
        template.update(sql2, param, holder);
    }

    public void addComment(addComment comment, int userId) {
        final String sql1 = "insert into comment(postid,userid,comment) values(:postId,:userId,:comment)";
        final String sql2 = "update post set commentcount=commentcount+1 where postid=:postId";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("postId", comment.postid)
                .addValue("userId", userId).addValue("comment", comment.comment);
        template.update(sql1, param, holder);
        template.update(sql2, param, holder);
    }

    public void addFriend(addFriend af, int userId) {
        int user1;
        int user2;
        if (af.getUser1() > userId) {
            user1 = userId;
            user2 = af.getUser1();
        } else {
            user2 = userId;
            user1 = af.getUser1();
        }
        final String sql = "insert into friendsrelation(user1,user2,activity,lastuser) values (:user1,:user2,:relation,:lastAction)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("user1", user1).addValue("user2", user2)
                .addValue("relation", af.getRelation()).addValue("lastAction", af.getLastAction());
        template.update(sql, param, holder);
    }

    public void removeFriend(com.backend.code.Objects.addFriend af, int userId) {
        final String sql = "delete from friendsrelation where user1=:userId and user2=:user1 or user1=:user1 and user2=:userId";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("user1", af.getUser1()).addValue("userId",
                userId);
        template.update(sql, param, holder);
    }

    public void removeLike(com.backend.code.Objects.addLike like, int userId) {
        final String sql = "delete from likec where postid=:postId and userid=:userId";
        final String sql2 = "update post set likecount=likecount-1 where postid=:postId";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("postId", like.postId)
                .addValue("userId", userId).addValue("likeCount", like.likeCount);
        template.update(sql, param, holder);
        template.update(sql2, param, holder);
    }

    public void removeComment(com.backend.code.Objects.addComment comment, int userId) {
        final String sql = "delete from comment where postid=:postId and userid=:userId and commentid=:commentId";
        final String sql2 = "update post set commentcount=commentcount-1 where postid=:postId";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource().addValue("postId", comment.postid)
                .addValue("userId", userId).addValue("commentId", comment.commentid);
        template.update(sql, param, holder);
        template.update(sql2, param, holder);
    }

    public List<IdNameStatus> showFriends(int userid) {
    	ChatController obj=new ChatController();
    	obj.loginUsers.add(0);
        final String sql = "select userid,username,im.picbyte,im.picid,(case \r\n" + 
        		"						when exists(select userid from userdetails where userid in(:users)) then true \r\n" + 
        		"						when not exists (select userid from userdetails where userid  in(:users)) then false end)\r\n" + 
        		"						as status, \r\n" + 
        		"from userdetails\r\n" +
        		"left join profile p on ud.userid=p.userid"+
        		"join image_model im on p.picid=im.picid"+
        		"where (userid in (select user1 as user from friendsrelation where user2=:userId \r\n" + 
        		"				  union\r\n" + 
        		"				  select user2 as user from friendsrelation where user1=:userId))";
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userid)
        		.addValue("users", obj.loginUsers);
        return template.query(sql, param, new IdNameStatusMapper());
    }

    public List<IdName> showMembers(int userid) {
        final String sql = "select userid,username from userdetails where userid!=:userid and userid not in (select userid1 as user from friendsrelation where userid2=:userid union select userid2 as user from friendsrelation where userid1=:userid)";
        SqlParameterSource param = new MapSqlParameterSource().addValue("userid", userid);
        return template.query(sql, param, new IdNameMapper());
    }

    public List<IdName> showLike(int userId) {
        final String sql = "select userid,username from userdetails where userid in (select userid from likec where postid=:postId)";
        SqlParameterSource param = new MapSqlParameterSource().addValue("postId", userId);
        return template.query(sql, param, new IdNameMapper());
    }

    public List<com.backend.code.Objects.displayComment> showComment(com.backend.code.Objects.addComment comment,
            int userId) {
        final String sql = "select c.commentid,c.userid,c.comment,username from comment as c join userdetails as u on c.userid=u.userid where postid=:postId";
        SqlParameterSource param = new MapSqlParameterSource().addValue("postId", comment.postid);
        return template.query(sql, param, new commentMapper());
    }

    public List<postResult> showPost(int userId) {
        final String sql = "select p.postid ,u.userid, p.status,p.location,p.commentcount,p.likecount,p.date,u.username,i.name,i.type,i.picbyte,(case when exists(select userid,postid from likec where userid=:userId and postid=p.postid) then true when not exists (select userid,postid from likec where userid=:userId and postid=p.postid) then false end) as liked from post p join userdetails u on p.userid=u.userid join image_model i on p.picid=i.picid where p.userid in (select user1 as user from friendsrelation where user2=:userId union select user2 as user from friendsrelation where user1=:userId) order by p.date desc";
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
        return template.query(sql, param, new postMapper());
    }

    public List<userpass> findpassword(String username) {
        final String sql = "select userid,email,password from userdetails where email=:username";
        SqlParameterSource param = new MapSqlParameterSource().addValue("username", username);
        return template.query(sql, param, new userpassMapper());
    }

    public List<IdName> MemberSearch(String pattern, int userid) {
        final String sql = "select userid,username from userdetails where ((username like '%'||:pattern||'%') or (email like '%'||:pattern||'%') or (phonenumber like '%'||:pattern||'%')) and (userid!=:userid and userid not in (select user1 as user from friendsrelation where user2=:userid union select user2 as user from friendsrelation where user1=:userid)) ";
        SqlParameterSource param = new MapSqlParameterSource().addValue("pattern", pattern).addValue("userid", userid);
        return template.query(sql, param, new IdNameMapper());
    }

    public List<IdName> FriendSearch(String pattern, int userid) {
        final String sql = "select userid,username from userdetails where ((username like '%'||:pattern||'%') or (email like '%'||:pattern||'%') or (phonenumber like '%'||:pattern||'%')) and(not (userid!=:userid and userid not in (select user1 as user from friendsrelation where user2=:userid union select user2 as user from friendsrelation where user1=:userid)) )";
        SqlParameterSource param = new MapSqlParameterSource().addValue("pattern", pattern).addValue("userid", userid);
        return template.query(sql, param, new IdNameMapper());
    }

    public void insertmessages(ChatMessage chatusers) throws SQLException {
        System.out.println("ggtegeegr");
	int user1;
    int user2;
    if (chatusers.getSender() > chatusers.getReceiver()) {
        user1 = chatusers.getReceiver();
        user2 = chatusers.getSender();
    } else {
    	user1 = chatusers.getSender();
        user2 = chatusers.getReceiver();
    }
	final String sql1="update  messagecount set messagecount = messagecount+1 where user1=:user1 and user2 =:user2 returning messagecount\r\n" ;  
	SqlParameterSource param=new MapSqlParameterSource().addValue("user1",user1).addValue("user2", user2);
	KeyHolder holder = new GeneratedKeyHolder();
	int messagecount=template.update(sql1, param,holder);
	if(messagecount==0){
		final String sql2=	"INSERT INTO public.messagecount(\r\n" + 
				"user1, user2, messagecount)\r\n" + 
				"select :user1 ,:user2 , 1\r\n" + 
				"where not exists(select 1 from messagecount where user1=:user1 and user2=:user2) returning messagecount;\r\n"; 
		template.update(sql2,param,holder);		
	}
    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    Date dateobj = new Date();
	List<Integer> messagenum=template.query("select messagecount from messagecount where user1=:user1 and user2=:user2", param,new messagemapper());
	String sql3="Insert into message (user1,user2,messagenum,message,createdtime,sender) values(:user1,:user2,:messagenum,:message,:createdtime,:senderid);";
	SqlParameterSource param1=new MapSqlParameterSource().addValue("user1", user1).addValue("user2", user2).addValue("message", chatusers.getContent()).addValue("messagenum",messagenum.get(0)).addValue("senderid", chatusers.getSender()).addValue("createdtime",dateobj);
	template.update(sql3, param1,holder);
}
public List<messageobj> getmessages(int user1,int user2){
	int temp;
	 if (user1 > user2) {
	     temp=user1;      
		 user1 = user2;
	     user2 = temp;
	    } 
	final String sql="select * from messages where user1=:user1 and user2=:user2";
	SqlParameterSource param=new MapSqlParameterSource().addValue("user1", user1).addValue("user2", user2);
	return template.query(sql, param,new UserMessage());
}
public List<ChatPage> getChatDetails(int realuser)
{
	
	final String sql="select u.username,u.userid,mc.messagecount,m.message,m.messagenum,m.sender,m.createdtime ,p.picid,ig.name,ig.picbyte,ig.type from messagecount mc inner join message m on (mc.user1=m.user1 and mc.user2=m.user2) inner join userdetails u on (mc.user1 !=:realuser and mc.user1=u.userid or mc.user2=u.userid and mc.user2 !=:realuser) left join profile p on u.userid=p.userid left join image_model ig on p.picid=ig.picid  where (mc.user1=:realuser or mc.user2=:realuser) and mc.messagecount=m.messagenum";
	SqlParameterSource param=new MapSqlParameterSource().addValue("realuser", realuser);
	System.out.println(realuser);
	return template.query(sql,param, new ChatPageMapper()); 
}
public List<IdName> getBirthdayPeoples(int userid)
{
	final String sql="SELECT userid,username FROM userdetails WHERE DATE_PART('day', dateofbirth) = date_part('day', CURRENT_DATE) AND DATE_PART('month', dateofbirth) = date_part('month', CURRENT_DATE) and (userid!=:userid and userid in (select user1 as user from friendsrelation where user2=:userid union select user2 as user from friendsrelation where user1=:userid)) ";
	SqlParameterSource param=new MapSqlParameterSource().addValue("userid", userid);
	List<IdName> lis= template.query(sql,param,new  IdNameMapper());
	return lis;
}


@Scheduled(cron = "0 0 12 * * ?")
public void updateAge()
{
	final String sql="update userdetails set age = age + 1 WHERE DATE_PART('day', dateofbirth) = date_part('day', CURRENT_DATE) AND DATE_PART('month', dateofbirth) = date_part('month', CURRENT_DATE) ";
	KeyHolder holder = new GeneratedKeyHolder();
	template.update(sql, null, holder);
}
public List<chatUsers> getIndividualChat(int userId, int friendId) {
    final String sql="select * from message where (user1=:friendId and user2=:userId) or (user1=:userId and user2=:friendId) ";
    SqlParameterSource param=new MapSqlParameterSource().addValue("userId", userId).addValue("friendId", friendId);
    return template.query(sql,param, new chatUsersMapper());
}
}
