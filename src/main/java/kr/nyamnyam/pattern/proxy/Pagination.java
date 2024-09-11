package kr.nyamnyam.pattern.proxy;

import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("page")
@Data
@Lazy
public class Pagination {
    private int totalCount, startRow, endRow,
            pageCount, pageSize, startPage, endPage,
            rowNum, pageNum, blockNum,
            blockCount, prevBlock, nextBlock;
    public boolean existPrev, existNext;

    public final int BLOCK_SIZE = 5;
    public final int PAGE_SIZE = 10;

    public Pagination() {
    }

    public Pagination(int pageNum, int totalCount) {
        this.pageNum = pageNum;
        this.totalCount = totalCount;
        this.pageCount = (totalCount % PAGE_SIZE == 0) ? totalCount / PAGE_SIZE : totalCount / PAGE_SIZE + 1;
        this.blockCount = (pageCount + BLOCK_SIZE - 1) / BLOCK_SIZE;
        this.startRow = (pageNum - 1) * PAGE_SIZE;
        this.endRow = (pageNum * PAGE_SIZE) - 1;
        this.blockNum = (pageNum - 1) * BLOCK_SIZE;
        this.startPage = (pageNum <= 3) ? 1 : pageNum - 2;
        this.endPage = (endPage >= pageCount - 2) ? pageCount : pageNum + 2;
        this.existPrev = (pageNum <= 1) ? false : true;
        this.existNext = (pageNum == pageCount) ? false : true;

    }
}
