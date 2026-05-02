package com.saffron.eai.adapter;

import com.saffron.eai.common.EaiAdapterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdapterFactory {

    private final List<EaiAdapter> adapters;

    private Map<EaiAdapter.AdapterType, EaiAdapter> adapterMap;

    @jakarta.annotation.PostConstruct
    public void init() {
        adapterMap = adapters.stream()
                .collect(Collectors.toMap(EaiAdapter::getType, Function.identity()));
    }

    public EaiAdapter getAdapter(String adapterType) {
        EaiAdapter.AdapterType type = EaiAdapter.AdapterType.valueOf(adapterType.toUpperCase());
        return getAdapter(type);
    }

    public EaiAdapter getAdapter(EaiAdapter.AdapterType type) {
        EaiAdapter adapter = adapterMap.get(type);
        if (adapter == null) {
            throw new EaiAdapterException("등록되지 않은 어댑터 타입: " + type);
        }
        return adapter;
    }
}
