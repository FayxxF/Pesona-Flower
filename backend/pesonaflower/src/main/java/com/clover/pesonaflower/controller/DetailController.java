package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.models.Detail;
import com.clover.pesonaflower.models.UserEntity;
import com.clover.pesonaflower.security.SecurityUtil;
import com.clover.pesonaflower.service.DetailService;
import com.clover.pesonaflower.service.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class DetailController {
    private DetailService detailService;
    private UserService userService;


    @Autowired
    public DetailController(DetailService detailService, UserService userService) {
        this.detailService = detailService;
        this.userService = userService;
    }

    @GetMapping("/detail") //Displaying Detail Page
    public String listDetails(Model model){
        UserEntity user = new UserEntity();
        List<DetailDto> details = detailService.findAllDetail();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("details", details);
        return "detail";
    }

    @GetMapping("/detail/create") //Display Form buat ngisi data nya
    public String createDetailForm(Model model){
        Detail detail = new Detail();
        model.addAttribute("detail", detail);
        return "createDetail";
    }

    @GetMapping("/detail/{detailId}/delete")
    public String deleteDetail(@PathVariable("detailId")long detailId){
        detailService.delete(detailId);
        return "redirect:/detail";
    }

    @PostMapping("/detail/create") //Submit form create data
    public String saveDetail(@Valid @ModelAttribute("detail") DetailDto detailDto,
                             BindingResult result, Model model){

        if (result.hasErrors()){
            model.addAttribute("detail", detailDto);
            return "createDetail";
        }

        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        detailDto.setCreatedBy(user);

        detailService.saveDetail(detailDto);
        return "redirect:/detail";
    }

    @GetMapping("/detail/{detailId}/edit") //Display form buat ngedit datanya
    public String editDetailForm(@PathVariable("detailId")Long detailId, Model model) {
        DetailDto detail = detailService.findDetailById(detailId);
        model.addAttribute("detail", detail);
        return "editDetail";
    }

    @PostMapping("/detail/{detailId}/edit") //Submit hasil editnya
    public String updateDetail(@PathVariable("detailId")Long detailId,
                               @Valid @ModelAttribute("detail") DetailDto detail, BindingResult result){
        if(result.hasErrors()){
            return "editDetail";
        }
        detail.setId(detailId);
        detailService.updateDetail(detail);
        return "redirect:/detail";

    }


}
