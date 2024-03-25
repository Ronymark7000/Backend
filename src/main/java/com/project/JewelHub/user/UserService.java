package com.project.JewelHub.user;

import com.project.JewelHub.token.ConfirmationToken;
import com.project.JewelHub.token.ConfirmationTokenRepo;
import com.project.JewelHub.token.EmailService;
import com.project.JewelHub.user.dtos.UserDto;
import com.project.JewelHub.user.dtos.UserRegisterDto;
import com.project.JewelHub.user.dtos.UserResDto;
import com.project.JewelHub.util.CustomException;
import com.project.JewelHub.util.CustomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    private final ConfirmationTokenRepo confirmationTokenRepo;

    private final EmailService emailService;

    private static final String NOT_FOUND =  "User not found !!";
    private final String SERVER_ERROR_MESSAGE = "Internal Server Error, Please Check";
    private final int SERVER_ERROR_CODE = 500;

    //get all students
    public List<UserDto> getAllUsers() {
        List<User> users = new ArrayList<>(userRepo.findAll());

        return CustomMapper.mapUserDtos(users);
    }

    public UserResDto getUserByEmailAndPassword (String email, String password) throws CustomException{
        try{
            Optional<User> user= userRepo.findUserByEmailAndPassword(email, password);
            if (user.isPresent()){
                return new UserResDto(user.get());
            }else {
                throw new CustomException(NOT_FOUND, 404);
            }
        } catch (CustomException ex){
            throw new CustomException(ex.getMessage(),ex.getStatus());
        } catch (Exception ex){
            throw new CustomException(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE);
        }
    }

    public UserResDto registerUser(UserRegisterDto userRegisterDto) {
        try {
            Optional<User> userEmail = userRepo.findUserByEmail(userRegisterDto.getEmail());
            if (userEmail.isPresent()){
                throw new CustomException("Email Already Exist! ", 404);
            }
            Optional<User> userContact = userRepo.findUserByContact(userRegisterDto.getContact());
            if (userContact.isPresent()){
                throw new CustomException("Contact Details Already Exist! ", 404);
            }
            User user = new User(userRegisterDto);
            User newUser = userRepo.save(user);

            // Registration Confirmation part //
                ConfirmationToken confirmationToken = new ConfirmationToken(user);
                confirmationTokenRepo.save(confirmationToken);

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(user.getEmail());
                mailMessage.setSubject("Complete Your Registration!");
                mailMessage.setText("To confirm and verify your account, please click the link : "
                        +"http://localhost:5173/api/auth/confirm-account?ctoken="+confirmationToken.getConfirmationToken());
                emailService.sendEmail(mailMessage);

                System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());
            //===========End of Registration confirmation======//
            return new UserResDto(newUser);
        } catch (CustomException ex) {
            throw new CustomException(ex.getMessage(), ex.getStatus());
        } catch (Exception ex) {
            throw new CustomException(SERVER_ERROR_MESSAGE, SERVER_ERROR_CODE);
        }
    }

    public UserDto getUserById(int userId)
    {
        Optional<User> optionalUser = userRepo.findById(userId);

        User user = optionalUser.get();
        return new UserDto(user.getUserId(),user.getFirstname(), user.getLastname(), user.getEmail(), user.getRole(), user.getContact());
    }

    public User createUser(User user) {
        Optional<User> userEmail = userRepo.findUserByEmail(user.getEmail());
        if (userEmail.isPresent()){
            throw new CustomException("Unable to Register",404);
        } else {
            return userRepo.save(user);
        }
    }

    public User updateUser(int id, User updatedUser) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Update properties of the existing user with values from the updated user
            existingUser.setFirstname(updatedUser.getFirstname());
            existingUser.setLastname(updatedUser.getLastname());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setContact(updatedUser.getContact());

            // Save the updated user back to the database
            return userRepo.save(existingUser);
        }
        return updatedUser;
    }

    //------------Currently we wont delete the user--------------//

    public User deleteUser(int userId) {
        Optional<User> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            userRepo.deleteById(userId);
        }
        return null;
    }

}
