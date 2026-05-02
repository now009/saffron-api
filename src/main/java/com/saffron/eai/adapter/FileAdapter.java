package com.saffron.eai.adapter;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.saffron.eai.common.EaiAdapterException;
import com.saffron.eai.common.EaiMessage;
import com.saffron.eai.common.EaiResponse;
import com.saffron.eai.domain.EaiAdapterConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

@Slf4j
@Component("fileAdapter")
public class FileAdapter extends AbstractEaiAdapter {

    @Override
    public EaiResponse send(EaiMessage message) throws EaiAdapterException {
        EaiAdapterConfig config = message.getEndpointConfig();
        long startMs = System.currentTimeMillis();

        Session sftpSession = null;
        ChannelSftp channel = null;

        try {
            sftpSession = createSftpSession(config);
            channel = (ChannelSftp) sftpSession.openChannel("sftp");
            channel.connect();

            String encoding = config.getFileEncoding() != null ? config.getFileEncoding() : "UTF-8";
            Charset charset = Charset.forName(encoding);
            byte[] fileContent = message.getPayload().getBytes(charset);
            String fileName = message.getInterfaceId() + "_" + System.currentTimeMillis() + ".dat";

            channel.put(new ByteArrayInputStream(fileContent), config.getRemotePath() + "/" + fileName);

            EaiResponse response = EaiResponse.success(fileName, System.currentTimeMillis() - startMs);
            saveHistory(message, response, "SUCCESS");
            logTransaction(message, response);
            return response;

        } catch (Exception e) {
            EaiResponse errResp = EaiResponse.fail(e.getMessage(), System.currentTimeMillis() - startMs);
            saveHistory(message, errResp, "FAIL");
            throw new EaiAdapterException("SFTP 전송 실패: " + e.getMessage(), e);
        } finally {
            if (channel != null && channel.isConnected()) channel.disconnect();
            if (sftpSession != null && sftpSession.isConnected()) sftpSession.disconnect();
        }
    }

    @Override
    public List<EaiMessage> receive(String interfaceId) throws EaiAdapterException {
        // TODO: SFTP Pull - remotePath 스캔 → 파일 다운로드 → donePath로 이동
        return List.of();
    }

    @Override
    public HealthStatus checkHealth() {
        return HealthStatus.UNKNOWN;
    }

    @Override
    public AdapterType getType() { return AdapterType.FILE; }

    private Session createSftpSession(EaiAdapterConfig config) throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(config.getRemoteUser(), config.getRemoteHost(),
                config.getRemotePort() != null ? config.getRemotePort() : 22);
        session.setPassword(config.getRemotePassword());
        Properties props = new Properties();
        props.put("StrictHostKeyChecking", "no");
        session.setConfig(props);
        session.connect(10000);
        return session;
    }
}
