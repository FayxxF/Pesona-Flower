package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.GaleriDto;
import com.clover.pesonaflower.models.Galeri;
import com.clover.pesonaflower.service.GaleriService;
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
        return "admin/home";
    }


    @GetMapping("/create")
    public String createGaleriForm(Model model){
        Galeri galeri = new Galeri();
        model.addAttribute("galeri", galeri);
        return "admin/createGaleri";
    }

    @PostMapping("/create")
    public String saveGaleri(@Valid @ModelAttribute("galeri") GaleriDto galeriDto,
                             BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("galeri", galeriDto);
            return "admin/createGaleri";
        }
        galeriService.saveGaleri(galeriDto);
        return "redirect:/";

    }

    @GetMapping("/{galeriId}/edit")
    public String editGaleriForm(@PathVariable("galeriId")Long galeriId, Model model){
        GaleriDto galeri = galeriService.findGaleriById(galeriId);
        model.addAttribute("galeri", galeri);
        return "admin/editGaleri";
    }

    @PostMapping("/{galeriId}/edit")
    public String updateGaleri(@PathVariable("galeriId")Long galeriId,
                               @Valid @ModelAttribute("galeri") GaleriDto galeri, BindingResult result){
        if(result.hasErrors()){
            return "admin/editGaleri";
        }
        galeri.setId(galeriId);
        galeriService.updateGaleri(galeri);
        return "redirect:/";
    }

    @GetMapping("/{galeriId}/delete")
    public String deleteGaleri(@PathVariable("galeriId")long galeriId){
        galeriService.delete(galeriId);
        return "redirect:/";
    }
}
