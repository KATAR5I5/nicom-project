public class Position {

    private String status;
    private String dateTo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "Position{" +
                "status='" + status + '\'' +
                ", dateTo='" + dateTo + '\'' +
                '}';
    }
}
