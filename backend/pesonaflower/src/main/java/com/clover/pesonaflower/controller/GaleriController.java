package com.clover.pesonaflower.controller;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.dto.GaleriDto;
import com.clover.pesonaflower.models.Detail;
import com.clover.pesonaflower.models.Galeri;
import com.clover.pesonaflower.models.UserEntity;
import com.clover.pesonaflower.security.SecurityUtil;
import com.clover.pesonaflower.service.GaleriService;
import com.clover.pesonaflower.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
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
                             BindingResult result, Model model,
                             @RequestParam("fileFoto")MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()){
            model.addAttribute("galeri", galeriDto);
            return "createGaleri";
        }

        String fotoBunga = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        galeriDto.setFoto(fotoBunga);

        Galeri savedGaleri = galeriService.saveGaleri(galeriDto);
        String uploadDir = "src/main/resources/static/images/galeri/" + savedGaleri.getId();
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
                               @Valid @ModelAttribute("galeri") GaleriDto galeri, BindingResult result, Model model,
                               @RequestParam("newFoto")MultipartFile newFoto) throws IOException{

        if(result.hasErrors()){
            GaleriDto galeriDto = galeriService.findGaleriById(galeriId);
            model.addAttribute("galeri", galeriDto);
            return "editDetail";
        }

        if(!newFoto.isEmpty()){
            GaleriDto existingGaleri = galeriService.findGaleriById(galeriId);
            if(existingGaleri != null && existingGaleri.getFileFoto() != null){
                String oldFotoUrl = existingGaleri.getFoto();
                String oldFotoPath = "src/main/resources/static/images/detail/" + galeriId + "/" + oldFotoUrl;
                Path oldFotoFilePath = Paths.get(oldFotoPath);

                try {
                    Files.deleteIfExists(oldFotoFilePath);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            galeri.setFoto(newFoto.getOriginalFilename());
            String uploadDir = "src/main/resources/static/images/galeri/" + galeriId;
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = newFoto.getInputStream()){
                Path filePath = uploadPath.resolve(galeri.getFoto());
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e){
                throw new IOException("Tidak bisa menyimpan data gambar yang dikirim " + galeri.getFoto());
            }
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
