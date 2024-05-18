package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.GaleriDto;
import com.clover.pesonaflower.service.GaleriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class GaleriController {
    private GaleriService galeriService;

    @Autowired
    public GaleriController(GaleriService galeriService) {
        this.galeriService = galeriService;
    }

    @GetMapping("/")
    public String listGaleris(Model model){
        List<GaleriDto> galeris = galeriService.findAllGaleri();
        model.addAttribute("galeris", galeris);
        return "admin/home-admin";
    }
}
