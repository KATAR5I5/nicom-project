import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CreateNewExelFile {
    protected Map<Device, ClientNicom> mapDevice;
    protected List<String> actualListDep;
    protected Path path = Path.of("Отчет.xlsx");
    protected XSSFWorkbook myReport = new XSSFWorkbook();
    protected XSSFSheet oneSheet;
    protected XSSFSheet twoSheet;
    protected XSSFSheet threeSheet;
    protected XSSFSheet fourSheet;

    protected XSSFSheet fiveSheet;
    protected XSSFSheet sixSheet;
    protected List<Device> listDevice;

    public CreateNewExelFile(Map<Device, ClientNicom> map, List<String> actualListDepartment) throws IOException {
        this.mapDevice = map;
        this.actualListDep = actualListDepartment;
        createSheets();

    }

    public CreateNewExelFile(Map<Device, ClientNicom> map, List<String> actualListDepartment, Path path) throws IOException {
        this.mapDevice = map;
        this.actualListDep = actualListDepartment;
        this.path = path;
        createSheets();

    }

    protected void createSheets() throws IOException {
        System.out.println("Создаю листы в файле - " + path.getFileName());
//        С ремонтом и уже в отделении
        oneSheet = myReport.createSheet("С ремонтом В ОТДЕЛЕНИЯХ");

//        Забрать с М12 - СТ12 - отделение закрылось
        twoSheet = myReport.createSheet("С ремонтом В НЕСУЩЕСТВУЮЩИХ ОТДЕЛЕНИЯХ");
//        Не доставленные ЕЩЕ или договорились забрать из МАСТЕРСКОЙ
        threeSheet = myReport.createSheet("С ремонтом НЕ В ОТДЕЛЕНИЯХ");
//        Устройства с ДОСТАВКОЙ
        fourSheet = myReport.createSheet("С ремонтом ДОСТАВКА");
//        Устройства на хранении
        fiveSheet = myReport.createSheet("Хранение");
//        Устройства без ремонта
        sixSheet = myReport.createSheet("Без ремонта");

        System.out.println("Сортирую список по отделениям и номерам квитанций");
        Set<Device> mapSetDevice = mapDevice.keySet();
        listDevice = mapSetDevice
                .stream()
                .sorted()
//                .filter(e -> e.getDevice().equals("ноутбук"))
                .collect(Collectors.toList());
//        int setLength = mapSetDevice.size();
        filterAndFillSheet(listDevice, mapDevice, actualListDep);
        myReport.write(Files.newOutputStream(path));
        myReport.close();
    }

    void filterAndFillSheet(List<Device> sortDevice, Map<Device, ClientNicom> map, List<String> actualListDepartment) throws IOException {
//       1 лист С ремонтом В ОТДЕЛЕНИЯХ
        List<Device> listForSheetOne = sortDevice
                .stream()
                .filter(deivice -> {
                    return deivice.getHasRepair() && actualListDepartment.contains(deivice.getDepartment())
                            && deivice.getDepartment().equals(deivice.getDepartmentHasDevice());
                })
                .collect(Collectors.toList());
        fillSheet(listForSheetOne, map, oneSheet);
//  2 лист в файле С ремонтом В НЕСУЩЕСТВУЮЩИХ ОТДЕЛЕНИЯХ
        List<Device> listForSheetTwo = sortDevice
                .stream()
                .filter(e -> {
                    return e.getHasRepair() && !actualListDepartment.contains(e.getDepartment())
                            && e.getDepartment().equals(e.getDepartmentHasDevice());
                })
                .collect(Collectors.toList());
        fillSheet(listForSheetTwo, map, twoSheet);
//        3 лист в файле С ремонтом НЕ В ОТДЕЛЕНИЯХ
        List<Device> listForSheetThree = sortDevice
                .stream()
                .filter(e -> {
                    return e.getHasRepair() && actualListDepartment.contains(e.getDepartment())
                            && !e.getDepartment().equals(e.getDepartmentHasDevice());
                })
                .collect(Collectors.toList());
        fillSheet(listForSheetThree, map, threeSheet);


//    4 лист в файле С ремонтом ДОСТАВКА
        List<Device> listForSheetFour = sortDevice
                .stream()
                .filter(e -> {
                    return e.getHasRepair() && e.getDepartment().equals("Д1");
                })
                .collect(Collectors.toList());
        fillSheet(listForSheetFour, map, fourSheet);
//        5 лист в файле Хранение
        List<Device> listFiveSheetThree = sortDevice
                .stream()
                .filter(e -> {
                    return e.getHasRepair() && actualListDepartment.contains(e.getDepartment())
                            && !e.getDepartment().equals(e.getDepartmentHasDevice());
                })
                .collect(Collectors.toList());
        fillSheet(listForSheetThree, map, threeSheet);

    }

    void fillSheet(List<Device> sortDevice, Map<Device, ClientNicom> map, XSSFSheet sheet) throws IOException {
        System.out.println("Заполняю лист " + sheet.getSheetName());

        int rowNumberWrite = 1;
        for (int i = 0; i < sortDevice.size(); i++) {
            int cellWrite = 0;
            XSSFRow row = sheet.createRow(rowNumberWrite);
//        1 columns - порядковый номер
            XSSFCell num = row.createCell(cellWrite++);
            num.setCellValue(rowNumberWrite);
//        2 columns - номер квитанции
            XSSFCell fullTicket = row.createCell(cellWrite++);
            fullTicket.setCellValue(sortDevice.get(i).getFullTicketNumber());
//        3 columns - модель
            XSSFCell model = row.createCell(cellWrite++);
            model.setCellValue(sortDevice.get(i).getModel());
//        4 columns - устройство
            XSSFCell deviceName = row.createCell(cellWrite++);
            deviceName.setCellValue(sortDevice.get(i).getDevice());
//        5 columns - клиент полное имя
            XSSFCell clientFullName = row.createCell(cellWrite++);
            clientFullName.setCellValue(map.get(sortDevice.get(i)).getFullName());
//        6 columns - Имя клиетна
            XSSFCell clientName = row.createCell(cellWrite++);

            clientName.setCellValue(map.get(sortDevice.get(i)).getSecondName());

//        7 columns - Телефон 1
            XSSFCell clientPhoneOne = row.createCell(cellWrite++);
            Long phoneNum = map.get(sortDevice.get(i)).getPhoneNumberOne();
            if (phoneNum != null) {
                clientPhoneOne.setCellValue(phoneNum);
            } else clientPhoneOne.setCellValue("Нет");

//        8 columns - Телефон 2
            XSSFCell clientPhone2 = row.createCell(cellWrite++);
            Long phoneNum2 = map.get(sortDevice.get(i)).getPhoneNumberTwo();
            if (phoneNum2 != null) {
                clientPhone2.setCellValue(phoneNum2);
            } else clientPhone2.setCellValue("Нет");

//        9 columns - Сумма к оплате
            XSSFCell clientPrice = row.createCell(cellWrite++);
            clientPrice.setCellValue(sortDevice.get(i).getPriceToRepair());

//        10 columns - Адрес отделения
            CurrentDepartmenList cdpl = new CurrentDepartmenList();
//        метро (0) / время работы будни(1) / время работы вых адрес(2) /адрес(3)/ путь до пункта(4)
            List<String> fullAddress = cdpl.listFullAddress(sortDevice.get(i).getDepartment());
            XSSFCell addressDepartment = row.createCell(cellWrite++);

            if (sheet.equals(twoSheet) || sheet.equals(fourSheet)) {
                addressDepartment.setCellValue("Адрес уточните у администратороа");

            } else if (fullAddress == null) {
                fullAddress = cdpl.listFullAddress(sortDevice.get(i).getDepartmentHasDevice());
            } else addressDepartment.setCellValue(fullAddress.get(3));

//        11 columns - СООБЩЕНИЕ
            XSSFCell SMSMassage = row.createCell(cellWrite++);
            String sms = null;
            String name = map.get(sortDevice.get(i)).getSecondName();
            if (name == null) {
                name = "";
            } else name = map.get(sortDevice.get(i)).getSecondName() + ", ";
            if (sheet.equals(oneSheet)) {

                sms = "День добрый! Мы из Nicom-сервиса. " + name + " Ваш аппарат - " + sortDevice.get(i).getDevice() + " - " +
                        "готов. Оплатить при получнии  необходимо - " + sortDevice.get(i).getPriceToRepair() + "руб. Оплата производится - НАЛИЧНЫМИ.  Аппарат в данный момент находится на пункте выдачи по адресу: меторо - " + fullAddress.get(0) + ".  " + fullAddress.get(3) + ". Время работы пункта выдачи в " +
                        "будни - " + fullAddress.get(1) + ". В выходные дни - " + fullAddress.get(2) + ".";
            } else if (sheet.equals(twoSheet)) {
                sms = "День добрый! Мы из Nicom-сервиса. " + name + " Ваш аппарат - " + sortDevice.get(i).getDevice() + " - " +
                        "готов. Оплатить при получнии необходимо - " + sortDevice.get(i).getPriceToRepair() + "руб. Оплата производится - НАЛИЧНЫМИ.  Аппарат в данный момент находится на пункте выдачи.";

            } else if (sheet.equals(threeSheet)) {
                sms = "День добрый! Мы из Nicom-сервиса. " + name + " Ваш аппарат - " + sortDevice.get(i).getDevice() + " - " +
                        "готов. Оплатить при получнии  необходимо - " + sortDevice.get(i).getPriceToRepair() + "руб. Оплата производится - НАЛИЧНЫМИ.  Аппарат в данный момент находится В ПУТИ на пункт выдачи. ОЖИДАЙТЕ ЗВОНКА О ПОСТУПЛЕНИИ УСТРОЙСТВА! " + "Адрес пункта выдачи: меторо - " + fullAddress.get(0) + ". " + fullAddress.get(3) + "." +
                        " Время работы ункта выдачи в " + "будни - " + fullAddress.get(1) + ". В выходные дни - " + fullAddress.get(2) + ".";
            } else if (sheet.equals(fourSheet)) {
                sms = "День добрый! Мы из Nicom-сервиса. " + name + " Ваш аппарат - " + sortDevice.get(i).getDevice() + " " +
                        "готов. Оплатить при получнии  необходимо - " + sortDevice.get(i).getPriceToRepair() + "руб. Оплата производится - НАЛИЧНЫМИ. ОЖИДАЙТЕ ЗВОНКА О СОГЛАСОВАНИИ ДОСТАВКИ! Для уточнения - тел: 8(495) 545-06-08";
            }
            if (sms.length() > 90) { //Length of String for my test
                sheet.setColumnWidth(10, 20000); //Set column width, you'll probably want to tweak the second int
                CellStyle style = myReport.createCellStyle(); //Create new style
                style.setWrapText(true); //Set wordwrap
                SMSMassage.setCellStyle(style); //Apply style to cell
                SMSMassage.setCellValue(sms); //Write header
            }

//        12 columns - СООБЩЕНИЕ - Описание отделения и как добраться
            XSSFCell addressDepartmentWay = row.createCell(cellWrite);
            String SMSMassageTwo = null;
            if (sheet.equals(oneSheet)) {
                SMSMassageTwo = "Как к нам проити: " + fullAddress.get(4);
            } else if (sheet.equals(twoSheet)) {
                SMSMassageTwo = "Адрес уточните у администратороа";
            } else if (sheet.equals(threeSheet)) {
                SMSMassageTwo = "Как к нам проити: " + fullAddress.get(4);
            }
            if (SMSMassageTwo.length() > 90) { //Length of String for my test
                sheet.setColumnWidth(11, 20000); //Set column width, you'll probably want to tweak the second int
                CellStyle style = myReport.createCellStyle(); //Create new style
                style.setWrapText(true); //Set wordwrap
                addressDepartmentWay.setCellStyle(style); //Apply style to cell
                addressDepartmentWay.setCellValue(SMSMassageTwo); //Write header
            }
            rowNumberWrite++;
        }
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}