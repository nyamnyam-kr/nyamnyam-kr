package kr.nyamnyam.grpc;

import java.io.FileInputStream;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class GRpcClient {
    public static void main(String[] args) throws Exception {
        String secretKey = "a96bb236202946589043a9c64562f9c5";

        
        CountDownLatch latch = new CountDownLatch(1);
        ManagedChannel channel = NettyChannelBuilder
                .forTarget("clovaspeech-gw.ncloud.com:50051")
                .useTransportSecurity()
                .build();
        NestServiceGrpc.NestServiceStub client = NestServiceGrpc.newStub(channel);
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER),
                "Bearer " +  secretKey );
        client = MetadataUtils.attachHeaders(client, metadata);

        StreamObserver<NestResponse> responseObserver = new StreamObserver<NestResponse>() {
            @Override
            public void onNext(NestResponse response) {
                System.out.println("Received response: " + response.getContents());
            }

            @Override
            public void onError(Throwable t) {
                if(t instanceof StatusRuntimeException) {
                    StatusRuntimeException error = (StatusRuntimeException)t;
                    System.out.println(error.getStatus().getDescription());
                }
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("completed");
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

        java.io.File file = new java.io.File("src/main/resources/audio/KakaoTalk_Audio_20241010_2101_01_573.m4a");
        byte[] buffer = new byte[32000];
        int bytesRead;
        FileInputStream inputStream = new FileInputStream(file);
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
        latch.await();
        channel.shutdown();
    }
}