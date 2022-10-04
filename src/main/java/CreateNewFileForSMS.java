import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class CreateNewFileForSMS extends CreateNewExelFile {
    public CreateNewFileForSMS(Map<Device, ClientNicom> map, List<String> actualListDepartment, Path path) throws IOException {
        super(map, actualListDepartment, path);
    }

    @Override
    void fillSheet(List<Device> sortDevice, Map<Device, ClientNicom> map, XSSFSheet sheet) throws IOException {
        System.out.println("Заполняю лист " + sheet.getSheetName());
        int rowNumberWrite = 1;
        for (int i = 0; i < sortDevice.size(); i++) {
            int cellWrite = 0;
            XSSFRow row = sheet.createRow(rowNumberWrite);
//        1 columns - порядковый номер
            XSSFCell num = row.createCell(cellWrite++);
            num.setCellValue(rowNumberWrite);
//         columns - Телефон 1
            XSSFCell clientPhoneOne = row.createCell(cellWrite++);
            Long phoneNum = map.get(sortDevice.get(i)).getPhoneNumberOne();
            if (phoneNum != null) {
                clientPhoneOne.setCellValue(phoneNum);
            } else clientPhoneOne.setCellValue("Нет");

//         columns - Телефон 2
            XSSFCell clientPhone2 = row.createCell(cellWrite++);
            Long phoneNum2 = map.get(sortDevice.get(i)).getPhoneNumberTwo();
            if (phoneNum2 != null) {
                clientPhone2.setCellValue(phoneNum2);
            } else clientPhone2.setCellValue("Нет");

//         columns - Адрес отделения
            CurrentDepartmenList cdpl = new CurrentDepartmenList();
//        метро (0) / время работы будни(1) / время работы вых адрес(2) /адрес(3)/ путь до пункта(4)
            List<String> fullAddress = cdpl.listFullAddress(sortDevice.get(i).getDepartment());

//         columns - СООБЩЕНИЕ
            XSSFCell SMSMassage = row.createCell(cellWrite++);
            String sms = null;
            String name = map.get(sortDevice.get(i)).getSecondName() + ", ";
            if (name.equals("null, ")) {
                name = "";
            }
            if (sheet.equals(oneSheet)) {
                sms = "День добрый! Мы из Nicom-сервиса. " + name + " Ваш аппарат - " + sortDevice.get(i).getDevice() + " - " + "готов. Оплатить при получнии  необходимо - " + sortDevice.get(i).getPriceToRepair() + "руб. " +
                        "Оплата производится - НАЛИЧНЫМИ.  Аппарат в данный момент находится на пункте выдачи по адресу: " +
                        "меторо - " + fullAddress.get(0) + ".  " + fullAddress.get(3) + ". Время работы пункта выдачи в " + "будни - " + fullAddress.get(1) + ". В выходные " +
                        "дни - " + fullAddress.get(2) + ".";
            } else if (sheet.equals(twoSheet)) {
                sms = "День добрый! Мы из Nicom-сервиса. " + name + " Ваш аппарат - " + sortDevice.get(i).getDevice() + " - " +
                        "готов. Оплатить при получнии необходимо - " + sortDevice.get(i).getPriceToRepair() + "руб. Оплата производится - НАЛИЧНЫМИ.  Аппарат в данный момент находится на пункте выдачи.";

            } else if (sheet.equals(threeSheet)) {
                sms = "День добрый! Мы из Nicom-сервиса. " + name + " Ваш аппарат - " + sortDevice.get(i).getDevice() + " - готов. Оплатить при получнии  необходимо - " +
                        +sortDevice.get(i).getPriceToRepair() + "руб. Оплата производится - НАЛИЧНЫМИ.  Аппарат в данный момент находится В ПУТИ на пункт выдачи. " +
                        "ОЖИДАЙТЕ ЗВОНКА О ПОСТУПЛЕНИИ УСТРОЙСТВА! " + "адрес пункта выдачи: меторо - " + fullAddress.get(0) + ". " +
                        fullAddress.get(3) + ". Время работы ункта выдачи в " + "будни - " + fullAddress.get(1) + ". В выходные дни - " + fullAddress.get(2) + ".";
            } else if (sheet.equals(fourSheet)) {
                sms = "День добрый! Мы из Nicom-сервиса. " + name + " Ваш аппарат - " + sortDevice.get(i).getDevice() + " " +
                        "готов. Оплатить при получнии  необходимо - " + sortDevice.get(i).getPriceToRepair() + "руб. Оплата производится - НАЛИЧНЫМИ. ОЖИДАЙТЕ ЗВОНКА О СОГЛАСОВАНИИ ДОСТАВКИ! Для уточнения - тел: 8(495) 545-06-08";
            }

            if (sms.length() > 90) { //Length of String for my test
                sheet.setColumnWidth(3, 20000); //Set column width, you'll probably want to tweak the second int
                CellStyle style = myReport.createCellStyle(); //Create new style
                style.setWrapText(true); //Set wordwrap
                SMSMassage.setCellStyle(style); //Apply style to cell
                SMSMassage.setCellValue(sms); //Write header
            }


//         columns - СООБЩЕНИЕ - Описание отделения и как добраться
            XSSFCell addressDepartmentWay = row.createCell(cellWrite);
            String SMSMassageTwo = null;
            if (sheet.equals(oneSheet)) {
                SMSMassageTwo = "Как к нам проити: " + fullAddress.get(4);
            } else if (sheet.equals(twoSheet)) {
                SMSMassageTwo = "";
            } else if (sheet.equals(threeSheet)) {
                SMSMassageTwo = "Как к нам проити: " + fullAddress.get(4);
            }
            if (SMSMassageTwo.length() > 90) { //Length of String for my test
                sheet.setColumnWidth(4, 20000); //Set column width, you'll probably want to tweak the second int
                CellStyle style = myReport.createCellStyle(); //Create new style
                style.setWrapText(true); //Set wordwrap
                addressDepartmentWay.setCellStyle(style); //Apply style to cell
                addressDepartmentWay.setCellValue(SMSMassageTwo); //Write header
            }

            rowNumberWrite++;
        }
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

    }
}
