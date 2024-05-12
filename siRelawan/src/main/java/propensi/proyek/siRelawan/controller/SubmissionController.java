package propensi.proyek.siRelawan.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import propensi.proyek.siRelawan.dto.SubmissionDTO;
import propensi.proyek.siRelawan.model.Submission;
import propensi.proyek.siRelawan.service.SubmissionService;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/submission")
public class SubmissionController {

    @Autowired
    SubmissionService submissionService;

    @GetMapping({"", "/"})
    public String sortSubmission(Model model) {
        List<Submission> submissions = submissionService.getAllSubmission();
        model.addAttribute("submissions", submissions);
        return "submission";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        SubmissionDTO submissionDTO = new SubmissionDTO();
        model.addAttribute("submissionDto", submissionDTO);
        return "createSubmission";
    }

    @PostMapping("/create")
    public String createSubmissionForm(@Valid @ModelAttribute SubmissionDTO submissionDTO,
                                       BindingResult result) {

        if (submissionDTO.getImageFile().isEmpty()) {
            result.addError(new FieldError("submissionDto", "imageFile",
                    "Required image file"));
        }

        if (result.hasErrors()) {
            return "createSubmission";
        }

        // Save image file
        MultipartFile image = submissionDTO.getImageFile();
        Date tanggalBayar = new Date();
        String storageNameFile = tanggalBayar.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "resources/static/img/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageNameFile),
                    StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Submission submission = new Submission();
        submission.setInvoice(submissionDTO.getInvoice());
        submission.setDescription(submissionDTO.getDescription());
        submission.setJumlah(submissionDTO.getJumlah());
        submission.setImageFileName(storageNameFile);
        submission.setTanggalBayar(tanggalBayar);
        submission.setStatus(false);


        return "redirect:/submission";
    }
}
