package propensi.proyek.siRelawan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.proyek.siRelawan.model.ProgramModel;
import propensi.proyek.siRelawan.service.ProgramService;

import java.util.List;

@Controller
@RequestMapping("/statistik")
public class StatistikController {

    @Autowired
    private ProgramService programService;

    @GetMapping
    public String statistikPage(Model model) {
        List<ProgramModel> listProgram = programService.findAllProgram();

        int notStartedCount = 0;
        int inProgressCount = 0;
        int completedCount = 0;

        for (ProgramModel program : listProgram) {
            switch (program.getStatus()) {
                case NOT_STARTED:
                    notStartedCount++;
                    break;
                case IN_PROGRESS:
                    inProgressCount++;
                    break;
                case COMPLETED:
                    completedCount++;
                    break;
            }
        }
        model.addAttribute("notStartedCount", notStartedCount);
        model.addAttribute("inProgressCount", inProgressCount);
        model.addAttribute("completedCount", completedCount);
        return "statistik";
    }
}
