package com.saffron.eai.adapter;

import com.saffron.eai.domain.EaiAdapterConfig;

public interface TokenProvider {

    String getToken(EaiAdapterConfig config);
}
