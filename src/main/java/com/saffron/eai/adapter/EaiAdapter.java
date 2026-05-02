// com.saffron.eai.adapter.EaiAdapter.java
// 모든 어댑터가 구현해야 하는 인터페이스입니다.

package com.saffron.eai.adapter;

import com.saffron.eai.common.EaiAdapterException;
import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.EaiResponse;

public interface EaiAdapter {

    /** 메시지 전송 (Push/Pull 모두 사용) */
    EaiResponse send(EaiMessage message) throws EaiAdapterException;

    /** 메시지 수신 - Pull 방식에서 Source에서 데이터 가져오기 */
    java.util.List<EaiMessage> receive(String interfaceId) throws EaiAdapterException;

    /** 연결 상태 확인 */
    HealthStatus checkHealth();

    /** 어댑터 타입 반환 */
    AdapterType getType();

    enum AdapterType { REST, SOAP, DB, FILE }

    enum HealthStatus { UP, DOWN, UNKNOWN }
}