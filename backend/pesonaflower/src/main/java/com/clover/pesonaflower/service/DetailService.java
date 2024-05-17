package com.clover.pesonaflower.service;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.models.Detail;

import java.util.List;

public interface DetailService {
    List<DetailDto> findAllDetail();
    Detail  saveDetail(DetailDto detailDto);
    DetailDto findDetailById(Long serviceId);

    void updateDetail(DetailDto detail);

    void delete(long detailId);
}
