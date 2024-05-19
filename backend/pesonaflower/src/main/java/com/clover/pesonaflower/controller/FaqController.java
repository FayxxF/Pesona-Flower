package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.dto.FaqDto;
import com.clover.pesonaflower.models.Faq;
import com.clover.pesonaflower.models.UserEntity;
import com.clover.pesonaflower.security.SecurityUtil;
import com.clover.pesonaflower.service.FaqService;
import com.clover.pesonaflower.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FaqController {
    private FaqService faqService;
    private UserService userService;

    @Autowired
    public FaqController(FaqService faqService, UserService userService) {
        this.faqService = faqService;
        this.userService = userService;
    }

    @GetMapping("/about")
    public String listFaqs (Model model){
        UserEntity user = new UserEntity();
        List<FaqDto> faqs = faqService.findAllFaq();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("faqs", faqs);
        return "about";
    }

    @GetMapping("/about/create")
    public String createFaqForm(Model model){
        Faq faq = new Faq();
        model.addAttribute("faq", faq);
        return "createFaq";
    }

    @PostMapping("/about/create")
    public String saveFaq(@Valid @ModelAttribute("faq") FaqDto faqDto,
                             BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("faq", faqDto);
            return "createFaq";
        }
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        faqDto.setCreatedBy(user);
        faqService.saveFaq(faqDto);
        return "redirect:/about";
    }

    @GetMapping("/about/{faqId}/edit")
    public String editFaqForm(@PathVariable("faqId")Long faqId, Model model){
        FaqDto faq = faqService.findFaqById(faqId);
        model.addAttribute("faq", faq);
        return "editFaq";
    }

    @PostMapping("/about/{faqId}/edit")
    public String updateFaq(@PathVariable("faqId")Long faqId,
                            @Valid @ModelAttribute("faq") FaqDto faq, BindingResult result){
        if(result.hasErrors()){
            return "editFaq";
        }

        faq.setId(faqId);
        faqService.updateFaq(faq);
        return "redirect:/about";
    }

    @GetMapping("/about/{faqId}/delete")
    public String deleteFaq(@PathVariable("faqId")Long faqId){
        faqService.delete(faqId);
        return "redirect:/about";
    }


}
