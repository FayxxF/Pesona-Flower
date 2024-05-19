package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.dto.FaqDto;
import com.clover.pesonaflower.models.Faq;
import com.clover.pesonaflower.service.FaqService;
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

    @Autowired
    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping("/about")
    public String listFaqs (Model model){
        List<FaqDto> faqs = faqService.findAllFaq();
        model.addAttribute("faqs", faqs);
        return "admin/about";
    }

    @GetMapping("/about/create")
    public String createFaqForm(Model model){
        Faq faq = new Faq();
        model.addAttribute("faq", faq);
        return "admin/createFaq";
    }

    @PostMapping("/about/create")
    public String saveFaq(@Valid @ModelAttribute("faq") FaqDto faqDto,
                             BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("faq", faqDto);
            return "admin/createFaq";
        }
        faqService.saveFaq(faqDto);
        return "redirect:/about";
    }

    @GetMapping("/about/{faqId}/edit")
    public String editFaqForm(@PathVariable("faqId")Long faqId, Model model){
        FaqDto faq = faqService.findFaqById(faqId);
        model.addAttribute("faq", faq);
        return "admin/editFaq";
    }

    @PostMapping("/about/{faqId}/edit")
    public String updateFaq(@PathVariable("faqId")Long faqId,
                            @Valid @ModelAttribute("faq") FaqDto faq, BindingResult result){
        if(result.hasErrors()){
            return "admin/editFaq";
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
