package cn.mimukeji.common.bean;

/**
 * 分页辅助类
 *
 */
public class Page {
  /** 总记录数 */
  int total = 1;
  /** 页面size */
  int pageSize = 20;
  /** 最大页面size，用于不分页 */
  int maxPageSize = 1000000000;
  /** 当前页 */
  int pageNo = 1;
  /** 下一页 */
  int nextPage;
  /** 上一页 */
  int previousPage;
  /** 总页数 */
  int totalPages = 1;
  /** 是否有下一页 */
  boolean hasNext;
  /** 是否有上一页 */
  boolean hasPrevious;
  /** 起始行数(起始序号) */
  int pageStartRow;
  /** 结尾行数 (结尾序号) */
  int pageEndRow;
  
  /** 重构 */
  public Page() {
  }
  /**
   * 重构
   * @param totalRowsAmount 总页数
   * @param currentPage 当前页
   * @param pageSize size
   */
  public Page(int totalRowsAmount, int currentPage, int pageSize) {
    this.total = totalRowsAmount;
    this.pageNo = currentPage;
    this.pageSize = pageSize;
    this.totalPages = (int) Math.ceil(totalRowsAmount * 1.0 / pageSize);
    // 删除时可能导致当前页大于总页数
    if (currentPage > totalPages) {
      this.pageNo = this.totalPages;
    }
    if (this.pageNo > 1) {
      this.previousPage = this.pageNo - 1;
      this.hasPrevious = true;
    }
    if (this.pageNo < this.totalPages) {
      this.nextPage = this.pageNo + 1;
      this.hasNext = true;
    }
    this.pageEndRow = this.pageNo * pageSize;
    if (pageEndRow > totalPages) {
      pageEndRow = totalPages;
    }
    
    // 最小总页数为1
    if (this.totalPages == 0) {
      this.totalPages = 1;
    }
    // 最小当前页为1
    if (this.pageNo <= 0) {
      this.pageNo = 1;
    }
    
    this.pageStartRow = (this.pageNo - 1) * pageSize;
  }
  /**
   * @return totalRowsAmount
   */
  public int getTotalRowsAmount() {
    return total;
  }
  /**
   * @return pageSize
   */
  public int getPageSize() {
    return pageSize;
  }
  /**
   * @return currentPage
   */
  public int getCurrentPage() {
    return pageNo;
  }
  /**
   * @return nextPage
   */
  public int getNextPage() {
    return nextPage;
  }
  /**
   * @return previousPage
   */
  public int getPreviousPage() {
    return previousPage;
  }
  /**
   * @return totalPages
   */
  public int getTotalPages() {
    return totalPages;
  }
  /**
   * @return hasNext
   */
  public boolean isHasNext() {
    return hasNext;
  }
  /**
   * @return hasPrevious
   */
  public boolean isHasPrevious() {
    return hasPrevious;
  }
  /**
   * @return pageStartRow
   */
  public int getPageStartRow() {
    return pageStartRow;
  }
  /**
   * @return pageEndRow
   */
  public int getPageEndRow() {
    return pageEndRow;
  }
  /**
   * @param totalRowsAmount set totalRowsAmount
   */
  public void setTotalRowsAmount(int totalRowsAmount) {
    this.total = totalRowsAmount;
  }
  /**
   * @param pageSize set pageSize
   */
  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }
  /**
   * @param currentPage set currentPage
   */
  public void setCurrentPage(int currentPage) {
    this.pageNo = currentPage;
  }
  /**
   * @param nextPage set nextPage
   */
  public void setNextPage(int nextPage) {
    this.nextPage = nextPage;
  }
  /**
   * @param previousPage set previousPage
   */
  public void setPreviousPage(int previousPage) {
    this.previousPage = previousPage;
  }
  /**
   * @param totalPages set totalPages
   */
  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }
  /**
   * @param hasNext set hasNext
   */
  public void setHasNext(boolean hasNext) {
    this.hasNext = hasNext;
  }
  /**
   * @param hasPrevious set hasPrevious
   */
  public void setHasPrevious(boolean hasPrevious) {
    this.hasPrevious = hasPrevious;
  }
  /**
   * @param pageStartRow set pageStartRow
   */
  public void setPageStartRow(int pageStartRow) {
    this.pageStartRow = pageStartRow;
  }
  /**
   * @param pageEndRow set pageEndRow
   */
  public void setPageEndRow(int pageEndRow) {
    this.pageEndRow = pageEndRow;
  }
  
  public int getMaxPageSize() {
	return maxPageSize;
}
public void setMaxPageSize(int maxPageSize) {
	this.maxPageSize = maxPageSize;
}
@Override
  public String toString() {
    return "Page [totalRowsAmount=" + total + ", pageSize=" + pageSize + ", currentPage="
        + pageNo + ", nextPage=" + nextPage + ", previousPage=" + previousPage
        + ", totalPages=" + totalPages + ", hasNext=" + hasNext + ", hasPrevious=" + hasPrevious
        + ", pageStartRow=" + pageStartRow + ", pageEndRow=" + pageEndRow + "]";
  }
  
  public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}
public int getPageNo() {
	return pageNo;
}
public void setPageNo(int pageNo) {
	this.pageNo = pageNo;
}
public static void main(String[] args) {
    Page page = new Page(13,1,50);
    System.out.println(page.getPageStartRow());
    System.out.println(page.getPageSize());
}
}
