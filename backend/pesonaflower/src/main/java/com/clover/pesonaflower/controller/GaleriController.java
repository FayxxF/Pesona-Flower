package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.GaleriDto;
import com.clover.pesonaflower.models.Galeri;
import com.clover.pesonaflower.models.UserEntity;
import com.clover.pesonaflower.security.SecurityUtil;
import com.clover.pesonaflower.service.GaleriService;
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
public class GaleriController {
    private GaleriService galeriService;
    private UserService userService;

    @Autowired
    public GaleriController(GaleriService galeriService, UserService userService) {

        this.galeriService = galeriService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String listGaleri(Model model){
        UserEntity user = new UserEntity();
        List<GaleriDto> galeris = galeriService.findAllGaleri();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("galeris", galeris);
        return "home";
    }

    @GetMapping("/home")
    public String listGaleris(Model model){
        UserEntity user = new UserEntity();
        List<GaleriDto> galeris = galeriService.findAllGaleri();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("galeris", galeris);
        return "home";
    }


    @GetMapping("/home/create")
    public String createGaleriForm(Model model){
        Galeri galeri = new Galeri();
        model.addAttribute("galeri", galeri);
        return "createGaleri";
    }

    @PostMapping("/home/create")
    public String saveGaleri(@Valid @ModelAttribute("galeri") GaleriDto galeriDto,
                             BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("galeri", galeriDto);
            return "createGaleri";
        }
        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        galeriDto.setCreatedBy(user);
        galeriService.saveGaleri(galeriDto);
        return "redirect:/home";

    }

    @GetMapping("/home/{galeriId}/edit")
    public String editGaleriForm(@PathVariable("galeriId")Long galeriId, Model model){
        GaleriDto galeri = galeriService.findGaleriById(galeriId);
        model.addAttribute("galeri", galeri);
        return "editGaleri";
    }

    @PostMapping("/home/{galeriId}/edit")
    public String updateGaleri(@PathVariable("galeriId")Long galeriId,
                               @Valid @ModelAttribute("galeri") GaleriDto galeri, BindingResult result){
        if(result.hasErrors()){
            return "editGaleri";
        }
        galeri.setId(galeriId);
        galeriService.updateGaleri(galeri);
        return "redirect:/home";
    }

    @GetMapping("/home/{galeriId}/delete")
    public String deleteGaleri(@PathVariable("galeriId")long galeriId){
        galeriService.delete(galeriId);
        return "redirect:/home";
    }
}
