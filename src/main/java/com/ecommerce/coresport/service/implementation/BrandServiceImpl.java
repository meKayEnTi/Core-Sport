package com.ecommerce.coresport.service.implementation;

import com.ecommerce.coresport.mapper.BrandMapper;
import com.ecommerce.coresport.model.BrandResponse;
import com.ecommerce.coresport.repository.BrandRepository;
import com.ecommerce.coresport.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandServiceImpl implements BrandService {

    BrandRepository brandRepository;
    BrandMapper brandMapper;

    @Override
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll().stream().map(brandMapper::toBrandResponse).toList();
    }
}
