package kr.nyamnyam.pattern.proxy;

import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("page")
@Data
@Lazy
public class Pagination {

    // pageNum, rowNum, blockNum, search
    // 0차:row => count, start, end
    // 1차:page => count, size, start, end
    // 2차:block => count, size, prev, next
    // boolean exitssPrev, existsNext

    private int totalCount, startRow, endRow,
            pageCount, pageSize, startPage, endPage, pageNum,
            blockCount, prevBlock, nextBlock, blockNum;
    private final int BLOCK_SIZE = 5, PAGE_SIZE = 10;
    private boolean existPrev, existNext;

    public Pagination() {
    }

    public Pagination(int totalCount, int pageNum) {

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
