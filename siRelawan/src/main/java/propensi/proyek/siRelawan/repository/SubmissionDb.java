package propensi.proyek.siRelawan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.proyek.siRelawan.model.Submission;

public interface SubmissionDb extends JpaRepository<Submission, Integer> {
}
