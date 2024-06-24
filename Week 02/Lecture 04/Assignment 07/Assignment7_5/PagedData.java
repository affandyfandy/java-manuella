import java.util.List;

public class PagedData<T> {
    private List<T> data;
    private int pageSize;
    private int pageNumber;
    private int totalItems;
    private int totalPages;

    public PagedData(List<T> data, int pageSize, int pageNumber) {
        this.data = data;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.totalItems = data.size();
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
    }

    public List<T> getPage() {
        int fromIndex = (pageNumber - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalItems);
        return data.subList(fromIndex, toIndex);
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public String toString() {
        return "Data=" + getPage();
    }
}
