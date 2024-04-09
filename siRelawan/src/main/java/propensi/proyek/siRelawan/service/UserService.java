package propensi.proyek.siRelawan.service;

import java.util.List;
import java.util.UUID;

import propensi.proyek.siRelawan.dto.request.CreateUserRequestDTO;
import propensi.proyek.siRelawan.dto.request.UpdateUserRequestDTO;
import propensi.proyek.siRelawan.dto.response.CreateUserResponseDTO;
import propensi.proyek.siRelawan.model.UserModel;

public interface UserService {
    CreateUserResponseDTO getUserDetails(UUID id, String token);

    void createUser(CreateUserRequestDTO userRequestDTO);
    boolean authenticateUser(String username, String password);
    int getUserPoint(String username);
    UserModel getCurrentUser(String username);
    void accumulatePoin(String username, int poin);
    void updateUser(String username, UpdateUserRequestDTO updateUserRequestDTO);
    List<UserModel> getAllUser();
 
}
