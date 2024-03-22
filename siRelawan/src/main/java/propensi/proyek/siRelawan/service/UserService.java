package propensi.proyek.siRelawan.service;

import java.util.UUID;

import propensi.proyek.siRelawan.dto.request.CreateUserRequestDTO;
import propensi.proyek.siRelawan.dto.response.CreateUserResponseDTO;

public interface UserService {
    // String getToken(String username, String fullName);
    CreateUserResponseDTO getUserDetails(UUID id, String token);
    // UUID getUserIdFromToken(String token);
    // String getUsernameFromToken(String token);
    void createUser(CreateUserRequestDTO userRequestDTO);
    boolean authenticateUser(String username, String password);
}
