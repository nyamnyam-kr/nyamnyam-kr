package kr.nyamnyam.model.repository.Custom;

import kr.nyamnyam.model.domain.Chart.CostModel;
import kr.nyamnyam.model.domain.Chart.TotalModel;

import java.util.List;

public interface ReceiptRepositoryCustom {

    Long findRestaurantId(String name);

    List<TotalModel> totalCountFromName();

    List<CostModel> costList(Long userId);

}
