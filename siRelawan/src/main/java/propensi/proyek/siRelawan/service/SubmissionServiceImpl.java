package propensi.proyek.siRelawan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import propensi.proyek.siRelawan.model.Submission;
import propensi.proyek.siRelawan.repository.SubmissionDb;

import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService{

    @Autowired
    SubmissionDb submissionDb;

    @Override
    public List<Submission> getAllSubmission() {
        return submissionDb.findAll();
    }

    @Override
    public List<Submission> sortSubmission() {
        return submissionDb.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

}
