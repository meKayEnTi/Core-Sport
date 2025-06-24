package com.ecommerce.coresport.service.implementation;

import com.ecommerce.coresport.mapper.TypeMapper;
import com.ecommerce.coresport.model.TypeResponse;
import com.ecommerce.coresport.repository.TypeRepository;
import com.ecommerce.coresport.service.TypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeServiceImpl implements TypeService {

    TypeRepository typeRepository;
    TypeMapper typeMapper;

    @Override
    public List<TypeResponse> getAllTypes() {
        return typeRepository.findAll().stream().map(typeMapper::toTypeResponse).toList();
    }
}
