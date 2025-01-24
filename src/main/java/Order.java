import java.util.List;

public class Order {
    private String dateOf;
    private String status;
    private String price;
    private String number;
    private String href;
    private List<Position> positions;

    public String getDateOf() {
        return dateOf;
    }

    public void setDateOf(String dateOf) {
        this.dateOf = dateOf;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    @Override
    public String toString() {
        return "Order{" +
                "dateOf='" + dateOf + '\'' +
                ", status='" + status + '\'' +
                ", price='" + price + '\'' +
                ", number='" + number + '\'' +
                ", href='" + href + '\'' +
                ", positions=" + positions +
                '}';
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
