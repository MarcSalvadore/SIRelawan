package propensi.proyek.siRelawan.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.proyek.siRelawan.model.ProgramModel;
import propensi.proyek.siRelawan.repository.ProgramDb;

import java.util.List;

@Service
@Transactional
public class ProgramServiceImpl implements ProgramService{

    @Autowired
    ProgramDb programDb;

    public List<ProgramModel> findAllProgram() {
        return programDb.findAll();
    }
}
