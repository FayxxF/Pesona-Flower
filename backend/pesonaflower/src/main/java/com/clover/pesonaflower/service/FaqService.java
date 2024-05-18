package com.clover.pesonaflower.service;

import com.clover.pesonaflower.dto.DetailDto;
import com.clover.pesonaflower.dto.FaqDto;
import com.clover.pesonaflower.models.Detail;
import com.clover.pesonaflower.models.Faq;

import java.util.List;

public interface FaqService {
    List<FaqDto> findAllFaq();
    Faq saveFaq(FaqDto faqDto);
    FaqDto findFaqById(Long faqId);

    void updateFaq(FaqDto faq);

    void delete(long faqId);
}
