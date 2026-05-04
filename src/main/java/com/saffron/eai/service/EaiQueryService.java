package com.saffron.eai.service;

import com.saffron.eai.dto.request.QueryValidationRequest;
import com.saffron.eai.dto.response.EaiApiResponse;

public interface EaiQueryService {

    EaiApiResponse<Void> validateQuery(QueryValidationRequest request);
}
