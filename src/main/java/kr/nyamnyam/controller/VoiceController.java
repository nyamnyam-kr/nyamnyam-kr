package kr.nyamnyam.controller;

import javax.sound.sampled.*;
import java.util.concurrent.CountDownLatch;

import com.google.protobuf.ByteString;
import com.nbp.cdncp.nest.grpc.proto.v1.NestConfig;
import com.nbp.cdncp.nest.grpc.proto.v1.NestData;
import com.nbp.cdncp.nest.grpc.proto.v1.NestRequest;
import com.nbp.cdncp.nest.grpc.proto.v1.NestResponse;
import com.nbp.cdncp.nest.grpc.proto.v1.NestServiceGrpc;
import com.nbp.cdncp.nest.grpc.proto.v1.RequestType;
import io.grpc.ManagedChannel;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voice")
public class VoiceController {

    private final String secretKey = "a96bb236202946589043a9c64562f9c5";
    private ManagedChannel channel;
    private NestServiceGrpc.NestServiceStub client;

    @PostMapping("/recognize")


    public ResponseEntity<String> startRecognition() {
//        printSupportedAudioFormats(); // 지원되는 오디오 형식 알아보기 위해
        CountDownLatch latch = new CountDownLatch(1);
        StringBuilder resultText = new StringBuilder();

        // gRPC 클라이언트 초기화
        channel = NettyChannelBuilder.forTarget("clovaspeech-gw.ncloud.com:50051")
                .useTransportSecurity()
                .build();
        client = NestServiceGrpc.newStub(channel);

        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER),
                "Bearer " + secretKey);
        client = MetadataUtils.attachHeaders(client, metadata);

        StreamObserver<NestResponse> responseObserver = new StreamObserver<NestResponse>() {
            @Override
            public void onNext(NestResponse response) {
                System.out.println("Received response: " + response.getContents());

                String contents = response.getContents();
                JSONObject jsonObject = new JSONObject(contents);

                if (jsonObject.has("transcription")) {
                    JSONObject transcription = jsonObject.getJSONObject("transcription");
                    if (transcription.has("text")) {
                        String text = transcription.getString("text");
                        resultText.append(text).append(" ");
                    }
                }
            }

            @Override
            public void onError(Throwable t) {
                if (t instanceof StatusRuntimeException) {
                    StatusRuntimeException error = (StatusRuntimeException) t;
                    System.out.println(error.getStatus().getDescription());
                }
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed");
                latch.countDown();
            }
        };

        StreamObserver<NestRequest> requestObserver = client.recognize(responseObserver);

        requestObserver.onNext(NestRequest.newBuilder()
                .setType(RequestType.CONFIG)
                .setConfig(NestConfig.newBuilder()
                        .setConfig("{\"transcription\":{\"language\":\"ko\"}}")
                        .build())
                .build());

        // 실시간 음성 데이터 스트리밍
        try {

            AudioFormat format = new AudioFormat(8000, 8, 1, true, false); // 8kHz, 8비트, Mono

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            byte[] buffer = new byte[32000];
            int bytesRead;

            // 특정 키 입력으로 종료할 수 있는 루프를 만듭니다.
            // (예: 예외 발생 시 종료)
            boolean running = true;

            while (running) {
                bytesRead = line.read(buffer, 0, buffer.length);
                if (bytesRead > 0) {
                    requestObserver.onNext(NestRequest.newBuilder()
                            .setType(RequestType.DATA)
                            .setData(NestData.newBuilder()
                                    .setChunk(ByteString.copyFrom(buffer, 0, bytesRead))
                                    .setExtraContents("{ \"seqId\": 0, \"epFlag\": false}")
                                    .build())
                            .build());
                }

                // 종료 조건을 체크합니다. (예: 키 입력)
                // 실제 구현 시 사용자 인터페이스나 추가 로직 필요
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing audio input.");
        } finally {
            requestObserver.onCompleted();
            channel.shutdown();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request interrupted.");
        }

        String responseText = resultText.toString().trim(); // 공백 제거
        return responseText.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No transcription found.") : ResponseEntity.ok(responseText);
    }

    // 지원되는 오디오 형식을 알아보기 위해서
   /* private void printSupportedAudioFormats() {
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        for (Mixer.Info mixerInfo : mixers) {
            Mixer mixer = AudioSystem.getMixer(mixerInfo);
            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            for (Line.Info lineInfo : lineInfos) {
                if (lineInfo instanceof DataLine.Info) {
                    DataLine.Info dataLineInfo = (DataLine.Info) lineInfo;
                    System.out.println("Supported formats for mixer: " + mixerInfo.getName());
                    for (AudioFormat format : dataLineInfo.getFormats()) {
                        System.out.println("Format: " + format);
                    }
                }
            }
        }
    }*/




}