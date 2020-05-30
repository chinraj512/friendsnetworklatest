package com.backend.code.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.backend.code.Entity.ConfirmationToken;
import com.backend.code.Entity.User;
import com.backend.code.Repoistry.ConfirmationTokenRepository;
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
    public String confirmUserAccount( @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            return "Successfully registered";
        }
        else
        {
            return "Somethind went wrong with verification link";
        }

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
