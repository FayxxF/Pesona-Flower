package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.models.Detail;
import com.clover.pesonaflower.models.UserEntity;
import com.clover.pesonaflower.security.SecurityUtil;
import com.clover.pesonaflower.service.DetailService;
import com.clover.pesonaflower.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
                             BindingResult result, Model model,
                             @RequestParam("fileFoto_bunga") MultipartFile multipartFile)throws IOException {

        if (result.hasErrors()){
            model.addAttribute("detail", detailDto);
            return "createDetail";
        }

        String fotoBunga = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        detailDto.setFoto_bunga(fotoBunga);

        Detail savedDetail = detailService.saveDetail(detailDto);
        String uploadDir = "src/main/resources/static/images/detail/" + savedDetail.getId();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)){ //memeriksa apakah directory sudah ada
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fotoBunga);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException("Tidak bisa menyimpan gambar yang dikirim: " + fotoBunga);
        }

        UserEntity user = new UserEntity();
        String username = SecurityUtil.getSessionUser();
        if(username != null){
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

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
                               @Valid @ModelAttribute("detail") DetailDto detail, BindingResult result, Model model,
                               @RequestParam("newFoto") MultipartFile newFoto) throws IOException {


        if(result.hasErrors()){
            DetailDto detailDto = detailService.findDetailById(detailId);
            model.addAttribute("detail", detailDto);
            return "editDetail";
        }

        if(!newFoto.isEmpty()){
            DetailDto existingDetail = detailService.findDetailById(detailId);
            if(existingDetail != null && existingDetail.getFileFoto_bunga() != null){
                String oldFotoUrl = existingDetail.getFoto_bunga();
                String oldFotoPath = "src/main/resources/static/images/detail/" + detailId + "/" + oldFotoUrl;
                Path oldFotoFilePath = Paths.get(oldFotoPath);

                try {
                    Files.deleteIfExists(oldFotoFilePath);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            detail.setFoto_bunga(newFoto.getOriginalFilename());
            String uploadDir = "src/main/resources/static/images/detail/" + detailId;
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = newFoto.getInputStream()){
                Path filePath = uploadPath.resolve(detail.getFoto_bunga());
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e){
                throw new IOException("Tidak bisa menyimpan data gambar yang dikirim " + detail.getFoto_bunga());
            }
        }


        detail.setId(detailId);
        detailService.updateDetail(detail);
        return "redirect:/detail";

    }

}
