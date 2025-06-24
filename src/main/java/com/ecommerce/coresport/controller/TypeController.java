package com.ecommerce.coresport.controller;

import com.ecommerce.coresport.model.TypeResponse;
import com.ecommerce.coresport.service.TypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/types")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TypeController {

    TypeService typeService;

    @GetMapping
    public ResponseEntity<List<TypeResponse>> getAllTypes() {
        List<TypeResponse> types = typeService.getAllTypes();
        return ResponseEntity.ok().body(types);
    }
}
