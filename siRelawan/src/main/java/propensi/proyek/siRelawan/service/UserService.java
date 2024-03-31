package propensi.proyek.siRelawan.service;

import java.util.List;
import java.util.UUID;

import propensi.proyek.siRelawan.dto.request.CreateUserRequestDTO;
import propensi.proyek.siRelawan.dto.response.CreateUserResponseDTO;
import propensi.proyek.siRelawan.model.UserModel;

public interface UserService {
    // String getToken(String username, String fullName);
    CreateUserResponseDTO getUserDetails(UUID id, String token);
    // UUID getUserIdFromToken(String token);
    // String getUsernameFromToken(String token);
    void createUser(CreateUserRequestDTO userRequestDTO);
    boolean authenticateUser(String username, String password);
    String getUsername(String username);

    List<UserModel> getAllUser();
}
