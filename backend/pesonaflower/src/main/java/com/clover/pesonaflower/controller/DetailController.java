package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.models.Detail;
import com.clover.pesonaflower.service.DetailService;
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
public class DetailController {
    private DetailService detailService;

    @Autowired
    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }

    @GetMapping("/detail") //Displaying Detail Page
    public String listDetails(Model model){
        List<DetailDto> details = detailService.findAllDetail();
        model.addAttribute("details", details);
        return "admin/detail-admin";
    }

    @GetMapping("/detail/create") //Display Form buat ngisi data nya
    public String createDetailForm(Model model){
        Detail detail = new Detail();
        model.addAttribute("detail", detail);
        return "admin/createDetail";
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
            return "admin/createDetail";
        }
        detailService.saveDetail(detailDto);
        return "redirect:/detail";
    }

    @GetMapping("/detail/{detailId}/edit") //Display form buat ngedit datanya
    public String editDetailForm(@PathVariable("detailId")Long detailId, Model model) {
        DetailDto detail = detailService.findDetailById(detailId);
        model.addAttribute("detail", detail);
        return "admin/editDetail";
    }

    @PostMapping("/detail/{detailId}/edit") //Submit hasil editnya
    public String updateDetail(@PathVariable("detailId")Long detailId,
                               @Valid @ModelAttribute("detail") DetailDto detail, BindingResult result){
        if(result.hasErrors()){
            return "admin/editDetail";
        }
        detail.setId(detailId);
        detailService.updateDetail(detail);
        return "redirect:/detail";

    }


}
