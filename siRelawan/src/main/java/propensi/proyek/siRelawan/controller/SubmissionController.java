package propensi.proyek.siRelawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.proyek.siRelawan.model.Submission;
import propensi.proyek.siRelawan.service.SubmissionService;

import java.util.List;

@Controller
@RequestMapping("/submission")
public class SubmissionController {

    @Autowired
    SubmissionService submissionService;

    @GetMapping({"", "/"})
    public String showSubmissionList(Model model) {
        List<Submission> submissions = submissionService.getAllSubmission();
        model.addAttribute("submissions", submissions);
        return "submission";
    }
}
