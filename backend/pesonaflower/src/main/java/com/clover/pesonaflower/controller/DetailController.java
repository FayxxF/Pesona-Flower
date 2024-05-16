package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DetailController {
    private DetailService detailService;

    @Autowired
    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping("")
    public String listDetails(Model model){
        List<DetailDto> details = detailService.findAllDetail();
        model.addAttribute("details", details);
        return "detail-admin";
    }
}
