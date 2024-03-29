package propensi.proyek.siRelawan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.proyek.siRelawan.model.ProgramModel;

public interface ProgramDb extends JpaRepository<ProgramModel, String> {

}
