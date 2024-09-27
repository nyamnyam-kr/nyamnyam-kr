package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.domain.Chart.CheckModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;
import kr.nyamnyam.model.entity.ReceiptEntity;

import java.util.List;

public interface ReceiptRepositoryCustom {

    Long findRestaurantId(String name);

    List<TotalModel> totalCountFromName();

}
