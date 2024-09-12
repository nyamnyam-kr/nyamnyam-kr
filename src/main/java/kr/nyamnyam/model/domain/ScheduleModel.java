package kr.nyamnyam.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.nyamnyam.model.entity.StadiumEntity;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ScheduleModel {

    private String stadiumId;

    private String scheDate;

    private String gubun;

    private String hometeamId;

    private String awayteamId;

    private Integer homeScore;

    private Integer awayScore;

    private StadiumEntity stadium;
}
