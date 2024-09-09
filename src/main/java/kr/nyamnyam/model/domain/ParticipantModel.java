package kr.nyamnyam.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantModel {

    private String id;        // 참가자 ID
    private String nickname;  // 참가자 닉네임

}
