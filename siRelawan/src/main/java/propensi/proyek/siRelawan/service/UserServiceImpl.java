package propensi.proyek.siRelawan.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.proyek.siRelawan.dto.request.CreateUserRequestDTO;
import propensi.proyek.siRelawan.dto.request.UpdateUserRequestDTO;
import propensi.proyek.siRelawan.dto.response.CreateUserResponseDTO;
import propensi.proyek.siRelawan.model.EnumRole;
import propensi.proyek.siRelawan.model.UserModel;
import propensi.proyek.siRelawan.repository.UserDb;

@Service
public class UserServiceImpl implements UserService {
    // @Autowired
    // JwtUtils jwtUtils;

    @Autowired
    UserDb userDb;
    
    // @Autowired
    // PasswordEncoder passwordEncoder;
    
    @Override
    public CreateUserResponseDTO getUserDetails(UUID id, String token) {
        // Get user from repository
        Optional<UserModel> optionalUser = userDb.findById(id);
        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            return new CreateUserResponseDTO(user.getId(), user.getUsername(), user.getFullName(), null, token, token, null);
        }
        return null;
    }

    @Override
    public void createUser(CreateUserRequestDTO userRequestDTO) {
        UserModel newUser = new UserModel();
        newUser.setUsername(userRequestDTO.getUsername());
        newUser.setFullName(userRequestDTO.getFullName());
        newUser.setEmail(userRequestDTO.getEmail());
        newUser.setPassword(userRequestDTO.getPassword());
        newUser.setNomorWA(userRequestDTO.getNomorWA());
        // Set default role to RELAWAN
        newUser.setRole(EnumRole.RELAWAN);

        userDb.save(newUser);
    }
    
    @Override
    public void accumulatePoin(String username, int poin) {
        UserModel user = userDb.findByUsername(username);
        int newPoin = user.getPoin() + poin;
        user.setPoin(newPoin);
        userDb.save(user);
    }

    @Override
    public void updateUser(String username, UpdateUserRequestDTO updateUserRequestDTO) {
         // Retrieve the user from the database using the username
         UserModel user = userDb.findByUsername(username);

         // Update the user with the new data
         user.setFullName(updateUserRequestDTO.getFullName());
         user.setPassword(updateUserRequestDTO.getPassword());
         user.setEmail(updateUserRequestDTO.getEmail());
         user.setNomorWA(updateUserRequestDTO.getNomorWA());
         user.setNIK(updateUserRequestDTO.getNik());
         user.setNPWP(updateUserRequestDTO.getNpwp());
         user.setNoRekening(updateUserRequestDTO.getNoRekening());
 
         // Save the updated user back to the database
         userDb.save(user);
    }
    
    @Override
    public boolean authenticateUser(String username, String password) {
        UserModel user = userDb.findByUsername(username);
        if (user != null) {
            // Use equals method to compare passwords
            if (user.getPassword().equals(password)) { 
                return true;
            }
        }
        return false;
    }

    @Override
    public int getUserPoint(String username) {
        UserModel user = userDb.findByUsername(username);
        return user.getPoin();
    }

    @Override
    public UserModel getCurrentUser(String username) {
        UserModel user = userDb.findByUsername(username);
        return user;
    }

    @Override
    public List<UserModel> getAllUser() {
        return userDb.findAll();
    }
}
