package com.clover.pesonaflower.service.impl;

import com.clover.pesonaflower.dto.GaleriDto;
import com.clover.pesonaflower.models.Galeri;
import com.clover.pesonaflower.models.UserEntity;
import com.clover.pesonaflower.repository.GaleriRepository;
import com.clover.pesonaflower.repository.UserRepository;
import com.clover.pesonaflower.security.SecurityUtil;
import com.clover.pesonaflower.service.GaleriService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GaleriServiceImpl implements GaleriService {
    private GaleriRepository galeriRepository;
    private UserRepository userRepository;

    public GaleriServiceImpl(GaleriRepository galeriRepository, UserRepository userRepository) {
        this.galeriRepository = galeriRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<GaleriDto> findAllGaleri() {
        List<Galeri> galeris = galeriRepository.findAll();
        return galeris.stream().map((galeri) -> mapToGalerisDto(galeri)).collect(Collectors.toList());
    }

    private GaleriDto mapToGalerisDto(Galeri galeri) {
        GaleriDto galeriDto = GaleriDto.builder()
                .id(galeri.getId())
                .foto(galeri.getFoto())
                .nama(galeri.getNama())
                .build();
        return galeriDto;
    }

    @Override
    public Galeri saveGaleri(GaleriDto galeriDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Galeri galeri = mapToGaleri(galeriDto);
        return galeriRepository.save(galeri);
    }

    private Galeri mapToGaleri(GaleriDto galeri) {
        Galeri galeriDto = Galeri.builder()
                .id(galeri.getId())
                .foto(galeri.getFoto())
                .nama(galeri.getNama())
                .build();
        return galeriDto;
    }

    @Override
    public GaleriDto findGaleriById(Long galeriId) {
        Galeri galeri = galeriRepository.findById(galeriId).get();
        return mapToGalerisDto(galeri);
    }

    @Override
    public void updateGaleri(GaleriDto galeriDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Galeri galeri = mapToGaleri(galeriDto);
        galeriRepository.save(galeri);
    }

    @Override
    public void delete(long galeriId) {
        galeriRepository.deleteById(galeriId);
    }
}
