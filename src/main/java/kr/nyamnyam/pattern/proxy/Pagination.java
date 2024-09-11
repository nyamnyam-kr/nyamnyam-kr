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
    public boolean existPrev, existNext;

    public Pagination() {}

    public Pagination(int totalCount, int pageNum) {
        this.totalCount = totalCount;
        this.pageNum = pageNum;

        this.pageCount = (totalCount + PAGE_SIZE - 1) / PAGE_SIZE;

        this.startPage = Math.max(1, pageNum - 2);
        this.endPage = Math.min(pageCount, pageNum + 2);

        this.existPrev = startPage > 1;
        this.existNext = endPage < pageCount;

        this.startRow = (pageNum - 1) * PAGE_SIZE;
        this.endRow = Math.min(pageNum * PAGE_SIZE, totalCount);
    }
}
