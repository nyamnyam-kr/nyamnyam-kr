package kr.nyamnyam.pattern.proxy;

import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("page")
@Data
@Lazy // 필요할 때만 불러와야하기 때문에
public class Pagination {
    private int totalCount, startPage, endPage,
            pageNum, blockCount, prevBlock, nextBlock, blockNum, startRow, endRow, pageCount,
            PAGE_SIZE = 10, BLOCK_SIZE = 5;

    private boolean existsPrev, existsNext;

    public Pagination() {
    }

    public Pagination(int pageNum, int count) {
        this.pageNum = pageNum;
        this.totalCount = count;

        // 페이지당 항목 수와 총 항목 수를 기반으로 페이지 수 계산
        this.pageCount = count / PAGE_SIZE;

        this.blockCount = pageCount / BLOCK_SIZE;

        // 현재 페이지 번호와 페이지당 항목 수를 기반으로 시작 및 종료 행 계산
        this.startRow = (pageNum - 1) * PAGE_SIZE;
        this.endRow = Math.min(startRow + PAGE_SIZE - 1, count - 1);

        // 현재 페이지 번호를 기반으로 블록 번호 계산
        this.blockNum = (pageNum - 1) / BLOCK_SIZE;

        // 현재 페이지 번호를 기반으로 시작 및 종료 페이지 계산
        this.startPage = blockNum * BLOCK_SIZE + 1;
        this.endPage = Math.min(startPage + BLOCK_SIZE - 1, pageCount);

        this.existsPrev = blockNum > 0;
        this.existsNext = blockNum < blockCount - 1;

        // 블록의 이전 및 다음 블록 번호 계산
        this.prevBlock = startPage - BLOCK_SIZE;
        this.nextBlock = startPage + BLOCK_SIZE;
    }



}
