package com.backend.code.Controller;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.backend.code.Entity.ConfirmationToken;
import com.backend.code.Entity.ResetPasswordToken;
import com.backend.code.Entity.User;
import com.backend.code.Entity.UserDetails;
import com.backend.code.Objects.tokenPassword;
import com.backend.code.Repoistry.ConfirmationTokenRepository;
import com.backend.code.Repoistry.FriendsNetworkRepoistry;
import com.backend.code.Repoistry.ResetPasswordTokenRepository;
import com.backend.code.Repoistry.UserRepository;
import com.backend.code.service.EmailSenderService;



@RestController
@CrossOrigin
public class UserAccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired 
    private FriendsNetworkRepoistry friend; 
    
    @Autowired
    private ResetPasswordTokenRepository reset;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user)
    {
    	System.out.println(user.getEmail());
        User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
        if(existingUser != null)
        {
            return "this email is already registered";
        }
        else
        {
            userRepository.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("chinrajcn512@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
            +"http://localhost:3001/confirm-account?token="+confirmationToken.getConfirmationToken());

             emailSenderService.sendEmail(mailMessage);

            return "Check the mail "+ user.getEmail()+" for confirmation link ";
        }
    }
    @GetMapping("/confirm-account")
    public String confirmUserAccount( @RequestParam("token")String confirmationToken) throws NoSuchAlgorithmException
    {
    	System.out.println("hvhgh");
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null)
        {
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            UserDetails realUser=new UserDetails();
            user.setEnabled(true);
            userRepository.save(user);
            Date timelimit=token.getCreatedDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(timelimit);
            cal.add(Calendar.MINUTE, 10);
            Date now=Calendar.getInstance().getTime(); 
            Calendar current = Calendar.getInstance();
            current.setTime(now);
            System.out.println(cal+" "+current +" "+cal.before(current)+" "+cal.after(current));
            if(cal.after(current))
            {
            	realUser.setUsername(user.getUsername());
            	 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                 String hashPassword=passwordEncoder.encode(user.getPassword());
                 realUser.setPassword(hashPassword);
            	realUser.setEmail(user.getEmail());
            	realUser.setGender(user.getGender());
            	realUser.setPhonenumber(user.getPhonenumber());
            	realUser.setAge(user.getAge());
            	realUser.setDateofbirth(user.getDateofbirth());
            	friend.insertUsersDetails(realUser);

            	return "Successfully registered";
            }
            else
            {
            	return "link expired";
            }
        }
        else
        {
            return "Something went wrong with verification link";
        }

    }
    @PostMapping("/forgot-password/{email}")
    public String forgotUserPassword(@PathVariable("email") String email) {
        boolean existingUser=friend.findByEmailId(email);
        
        if (existingUser ==true) {
            // Create token
            ResetPasswordToken token=new ResetPasswordToken(email);

            // Save it
            reset.save(token);

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("chinrajcn512@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
              + "http://localhost:3001/confirm-reset?token="+token.getConfirmationToken());

            // Send the email
            emailSenderService.sendEmail(mailMessage);

            return "please check the "+email;

        } else {
            return "This email address does not exist!";
        }
  
    }
    @PostMapping("/confirm-reset")
    public String validateResetToken(@RequestBody tokenPassword tok) throws NoSuchAlgorithmException {
        ResetPasswordToken token = reset.findByConfirmationToken(tok.token);
        if (token != null) {
        	Date timelimit=token.getCreatedDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(timelimit);
            cal.add(Calendar.MINUTE, 10);
            Date now=Calendar.getInstance().getTime(); 
            Calendar current = Calendar.getInstance();
            current.setTime(now);
            System.out.println(cal+" "+current +" "+cal.before(current)+" "+cal.after(current));
            if(cal.after(current))
            {
            	 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                 String hashPassword=passwordEncoder.encode(tok.password);
                 if(friend.updatepassword(hashPassword,token.getEmail())>0)
                	 return "password reseted";
                 else
                	 return "not updated";
            }
            else
            {
            	return"the link is broken";
            }
    }
		return null;
    }
    public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public ConfirmationTokenRepository getConfirmationTokenRepository() {
		return confirmationTokenRepository;
	}

	public void setConfirmationTokenRepository(ConfirmationTokenRepository confirmationTokenRepository) {
		this.confirmationTokenRepository = confirmationTokenRepository;
	}

	public EmailSenderService getEmailSenderService() {
		return emailSenderService;
	}

	public void setEmailSenderService(EmailSenderService emailSenderService) {
		this.emailSenderService = emailSenderService;
	}
    
    
}
