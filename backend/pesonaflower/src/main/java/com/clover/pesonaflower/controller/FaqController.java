package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.FaqDto;
import com.clover.pesonaflower.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        return "admin/about-admin";
    }

}
