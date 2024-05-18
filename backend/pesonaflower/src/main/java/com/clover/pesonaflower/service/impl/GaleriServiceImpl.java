package com.clover.pesonaflower.service.impl;

import com.clover.pesonaflower.dto.GaleriDto;
import com.clover.pesonaflower.models.Galeri;
import com.clover.pesonaflower.repository.GaleriRepository;
import com.clover.pesonaflower.service.GaleriService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GaleriServiceImpl implements GaleriService {
    private GaleriRepository galeriRepository;

    public GaleriServiceImpl(GaleriRepository galeriRepository) {
        this.galeriRepository = galeriRepository;
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
        return null;
    }

    @Override
    public GaleriDto findGaleriById(Long galeriId) {
        return null;
    }

    @Override
    public void updateGaleri(GaleriDto galeri) {

    }

    @Override
    public void delete(long galeriId) {

    }
}
