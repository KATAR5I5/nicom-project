import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNicomObjects implements Runnable{
    private String fullName, phoneNumberOne, device, model, fullTicketNumber, deliveryDate, fullPrice, price, departmentHasDevice;

    private List<ClientNicom> clientsNicom;
    private List<Device> devices;
    private Map<Device, ClientNicom> mapDevice;
    Path path;

    public CreateNicomObjects() throws IOException {
        this.path = Path.of("full2.xls");
        create();
    }
    public CreateNicomObjects(Path path) throws IOException {
        this.path = path;
        create();
    }

    public List<ClientNicom> getClientsNicom() {
        return clientsNicom;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public Map<Device, ClientNicom> getMapDevice() {
        return mapDevice;
    }

    @Override
    public void run() {
        try {
            create();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void create() throws IOException {
        clientsNicom = new ArrayList<>();
        devices = new ArrayList<>();
        mapDevice = new HashMap<>();
        HSSFWorkbook  myExcelBook = new HSSFWorkbook(Files.newInputStream(path));
//        System.out.println(myExcelBook.getSheetName(0));
        HSSFSheet myExcelSheet = myExcelBook.getSheet(myExcelBook.getSheetName(0));
        //      A B C D I F G H I J  K  L  M  N
//      0 1 2 3 4 5 6 7 8 9 10 11 12 13 14
//      Первая ячейка (0,0) - (rowSTR, columnStart)
//           r - с 8 строки, 6 ячейка
        int rowStart = 7;
        int rowEnd = 60;
        int columnStart = 0;
        System.out.println("Проверь стартовые и конечный ячейки by default: rowStart =" + rowStart + " rowEnd = " + rowEnd);
        for (; rowStart < rowEnd; rowStart++) {
            HSSFRow row = myExcelSheet.getRow(rowStart);
            String cell = row.getCell(columnStart + 7).getStringCellValue();
            if (!cell.equals("")) {
//                Device cell
                departmentHasDevice = String.valueOf(row.getCell(columnStart));
                fullTicketNumber = String.valueOf(row.getCell(columnStart + 7)); //H
                model = String.valueOf(row.getCell(columnStart + 8));
                device = String.valueOf(row.getCell(columnStart + 9));
                fullPrice = String.valueOf(row.getCell(columnStart + 12));
                price = String.valueOf(row.getCell(columnStart + 13));
                deliveryDate = String.valueOf(row.getCell(columnStart + 5));

//                Client Cell
                fullName = String.valueOf(row.getCell(columnStart + 10));
                phoneNumberOne = String.valueOf(row.getCell(columnStart + 11));

//              Create Nicom Object
                Device tempDevice = new Device(model, device, fullTicketNumber, fullPrice, price, deliveryDate, departmentHasDevice);
                ClientNicom tempClientNicom = new ClientNicom(fullName, phoneNumberOne);

                mapDevice.put(tempDevice, tempClientNicom);
                devices.add(tempDevice);
                clientsNicom.add(tempClientNicom);
            }
        } myExcelBook.close();
    }
}