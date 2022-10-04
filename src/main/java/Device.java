import java.time.LocalDate;
import java.util.Date;

public class Device implements Comparable<Device> {
    private String model;
    private String device;
    DeviceType type;
    Departments departmentType;
    private String department;
    private String departmentHasDevice;

    Date deliveryDate;
    Date completeDate;
    Date extraditeDate;
    private boolean hasRepair;
    boolean isCash;
    boolean isPayment;

    private Double fullpriceToRepair;
    private Double priceToRepair;
    private String fullTicketNumber;
    private Integer ticketNumber;

    public Device(String model, String device, String fullTicketNumber, String fullPrice,
                  String price, String deliveryDate, String departmentHasDevice) {
        this.model = model.trim();
        this.device = device.trim();
        normalizNumberTicket(fullTicketNumber);
        this.fullTicketNumber = fullTicketNumber.trim();
        if (fullPrice.isEmpty()) {
            this.fullpriceToRepair = (double) 0;
        } else {
            try {
                this.fullpriceToRepair = Double.parseDouble(fullPrice);
            } catch (NumberFormatException e) {
                System.out.println("Ячейка <Сумма> содержит НЕ ЧИСЛО ");
                throw new RuntimeException(e);
            }
        }
        if (price.isEmpty()) {
            hasRepair = false;
            this.priceToRepair = (double) 0;
        } else {
            hasRepair = true;
            try {
                this.priceToRepair = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                System.out.println("Ячейка <Сумма доплаты> содержит НЕ ЧИСЛО ");
                throw new RuntimeException(e);
            }
        }
//        this.departmentType = Departments.valueOf(getDepartment().trim());
//        normalizDate(deliveryDate);
        this.departmentHasDevice = departmentHasDevice(departmentHasDevice);
    }

    public boolean getHasRepair() {
        return hasRepair;
    }
    public String getFullTicketNumber() {
        return fullTicketNumber;
    }
    @Override
    public int compareTo(Device o) {
        if (this.department.compareTo(o.getDepartment()) == 0) {
            return ticketNumber.compareTo(o.ticketNumber);
        }
        ;
        return this.department.compareTo(o.getDepartment());
    }
    public String getModel() {
        return model;

    }
    public String getDevice() {
        return device;
    }
    public String getDepartment() {
        return department;
    }
    public Integer getTicketNumber() {
        return ticketNumber;
    }
    public Double getFullpriceToRepair() {
        return fullpriceToRepair;
    }
    public Double getPriceToRepair() {
        return priceToRepair;
    }
    public String getDepartmentHasDevice() {
        return departmentHasDevice;
    }
    String departmentHasDevice(String departmentHasDevice) {
        String[] temp = departmentHasDevice.split(" ");
        if (temp[0].equals("Хранение"))
            return temp[1];
        return temp[0];
    }
    void normalizNumberTicket(String fullTicketNumber) {
        String[] departmentWithTNumber = fullTicketNumber.split("-");
        department = departmentWithTNumber[0].toUpperCase();
        ticketNumber = Integer.parseInt(departmentWithTNumber[1]);
    }
    void normalizDate(String date) {
//        LocalDate d = LocalDate.parse(date);

    }
    @Override
    public String toString() {
        return ", , , , , , , , , , , , , , , , , , , , , , ,\n" +
                "model= " + model + "\n" +
                "device= " + device + "\n" +
                "department= " + department + "\n" +
                "ticketNumber= " + department + "-" + ticketNumber + "\n" +
                "priceToRepair= " + priceToRepair + "\n";
//                "devicetype= " + type + "\n";
    }
}
