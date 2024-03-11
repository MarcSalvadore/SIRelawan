package propensi.proyek.siRelawan.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.proyek.siRelawan.model.UserModel;

@Repository
public interface UserDb extends JpaRepository<UserModel, UUID>{
    UserModel findByUsername(String username);
    UserModel findByEmail(String email);
}
