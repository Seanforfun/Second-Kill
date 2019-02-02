package io.seanforfun.seckill.entity.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/1 14:02
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Component
@Scope("prototype")
@Slf4j
@Getter
@Setter
public class PaginationBean {
    private Integer currentPageNum;
    private Integer numPerPage;
    private Integer totalPageNum;

    /**
     * Get total number of pages.
     * @param totalNum total number of object.
     * @param numPerPage    Number of object in single page.
     */
    public void calculationMaxPage(Integer totalNum,
                                   Integer numPerPage) {
        Integer totelPage = totalNum/numPerPage;
        totelPage = (totalNum%numPerPage == 0) ? totelPage:totelPage+1;
        this.setTotalPageNum(totelPage);
    }
}
