package propensi.proyek.siRelawan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.proyek.siRelawan.dto.request.CreateUserRequestDTO;
import propensi.proyek.siRelawan.dto.response.CreateUserResponseDTO;
import propensi.proyek.siRelawan.model.EnumRole;
import propensi.proyek.siRelawan.model.UserModel;
import propensi.proyek.siRelawan.repository.UserDb;

import java.util.Optional;
import java.util.UUID;

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
    

    // @Override
    // public String getToken(String username, String fullName) {
    //     return jwtUtils.generateToken(username, fullName);
    // }

    // @Override
    // public UUID getUserIdFromToken(String token) {
    //     String username = getUsernameFromToken(token);
    //     return userDb.findByUsername(username).getId();
    // }

    // @Override
    // public String getUsernameFromToken(String token) {
    //     return jwtUtils.extractUsername(token);
    // }
}
