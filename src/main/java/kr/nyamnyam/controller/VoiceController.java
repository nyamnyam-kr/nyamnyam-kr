package kr.nyamnyam.controller;


import java.io.FileInputStream;
import javax.sound.sampled.*;
import java.io.IOException;
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
        CountDownLatch latch = new CountDownLatch(1);
        StringBuilder resultText = new StringBuilder();


        ManagedChannel channel = NettyChannelBuilder.forTarget("clovaspeech-gw.ncloud.com:50051")
                .useTransportSecurity()
                .build();
        NestServiceGrpc.NestServiceStub client = NestServiceGrpc.newStub(channel);

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
//                if (jsonObject.has("text")) {
//                    String text = jsonObject.getString("text");
//
//                    // text가 비어있지 않은 경우에만 결과에 추가
//                    if (text != null && !text.trim().isEmpty()) {
//                        resultText.append(text).append(" ");
//                    }
//                }
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
                System.err.println("Error: " + t.getMessage());
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

        // 파일에서 음성을 읽어오는 코드
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/audio/음성-241101_164933.wav")) {
            byte[] buffer = new byte[32000];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                requestObserver.onNext(NestRequest.newBuilder()
                        .setType(RequestType.DATA)
                        .setData(NestData.newBuilder()
                                .setChunk(ByteString.copyFrom(buffer, 0, bytesRead))
                                .setExtraContents("{ \"seqId\": 0, \"epFlag\": false}")
                                .build())
                        .build());
            }
            requestObserver.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing audio file.");
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request interrupted.");
        } finally {
            channel.shutdown();
        }

        String responseText = resultText.toString().trim(); // 공백 제거
        return responseText.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No transcription found.") : ResponseEntity.ok(responseText);
    }

}