package propensi.proyek.siRelawan.service;

import propensi.proyek.siRelawan.model.Submission;
import java.util.List;

public interface SubmissionService {

    List<Submission> getAllSubmission();

    List<Submission> sortSubmission();
}
