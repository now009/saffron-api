package com.saffron.eai.controller;

import com.saffron.eai.dto.request.QueryValidationRequest;
import com.saffron.eai.dto.response.EaiApiResponse;
import com.saffron.eai.service.EaiQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eai/query")
@RequiredArgsConstructor
public class EaiQueryController {

    private final EaiQueryService queryService;

    @PostMapping("/validate")
    public ResponseEntity<EaiApiResponse<Void>> validate(@RequestBody @Valid QueryValidationRequest request) {
        return ResponseEntity.ok(queryService.validateQuery(request));
    }
}
