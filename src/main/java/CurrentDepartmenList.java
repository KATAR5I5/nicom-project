import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CurrentDepartmenList implements Runnable{
    private Path path;
    private XSSFWorkbook allNicomDepartmensBook;
    private List<String> actualList = new ArrayList<>();
    private List<Device> missingListDevicesInDepartment;
    private List<String> missingListDepartment;

    public CurrentDepartmenList() throws IOException {
        path = Path.of("Dep.xlsx");
        openFileAndFillActualList();
    }

    public CurrentDepartmenList(Path path) throws IOException {
        this.path = path ;
    }

    @Override
    public void run() {
        try {
            openFileAndFillActualList ();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Device> getMissingListDevicesInDepartment() {
        if (missingListDevicesInDepartment == null) {
            System.out.println("Сначала проведите верификацию отделений");
        }
        return missingListDevicesInDepartment;
    }

    public List<String> getMissingListDepartment() {
        if (missingListDepartment == null) {
            System.out.println("Сначала проведите верификацию отделений");
        }
        return missingListDepartment;
    }

    boolean verifyDepartmentsCompleteDevice(Map<Device, ClientNicom> map, List<String> actualList) {
        boolean result = true;
        Iterator<Device> it = map.keySet().iterator();
        missingListDevicesInDepartment = new ArrayList<>();
        missingListDepartment = new ArrayList<>();
        while (it.hasNext()) {
            Device temp = it.next();
            if (!actualList.contains(temp.getDepartment())) {
                missingListDevicesInDepartment.add(temp);
                missingListDepartment.add(temp.getDepartment());
                result = false;
            }
        }
        String answer = result ? "Все отделения работают." : "В списке присутствуют устройства, отделения которых не РАБОТАЮТ или НЕ СУЩЕСТВУЮТ!";
        System.out.println(answer);
        return result;

//        System.out.println(departmentThisList.contains(list));
    }

    private void openFileAndFillActualList() throws IOException {
//        open the Exel Book
        allNicomDepartmensBook = new XSSFWorkbook(Files.newInputStream(path));
//        get name of sheets
        String sheetOneName = allNicomDepartmensBook.getSheetName(0);
        String sheetTwoName = allNicomDepartmensBook.getSheetName(1);
//        String sheetThree =allNicomDepartmens.getSheetName(2);

        XSSFSheet sheetOne = allNicomDepartmensBook.getSheet(sheetOneName);
        XSSFSheet sheetTwo = allNicomDepartmensBook.getSheet(sheetTwoName);

        fillActualList(sheetOne);
        fillActualList(sheetTwo);
        allNicomDepartmensBook.close();
    }
    private void fillActualList(XSSFSheet sheet) {
        int rowStart = 2;
        int cellStart = 1;
        while (true) {
            XSSFRow row = sheet.getRow(rowStart);
            XSSFCell cell = row.getCell(cellStart);
            if (!cell.getStringCellValue().equals("")) {
                actualList.add(cell.getStringCellValue().toUpperCase());
                rowStart++;
            } else break;
        }
    }

    public List<String> getActualListDepartment() {
        return actualList;
    }

    //    метро / время работы будни / время работы вых адрес /адрес/ путь до пункта
    public List<String> listFullAddress(String department) throws IOException {
        if (actualList.contains(department)) {
            //        open the Exel Book
            allNicomDepartmensBook = new XSSFWorkbook(Files.newInputStream(path));
//        get name of sheets
            String sheetOneName = allNicomDepartmensBook.getSheetName(0);
            String sheetTwoName = allNicomDepartmensBook.getSheetName(1);
//        String sheetThree =allNicomDepartmens.getSheetName(2);
            XSSFSheet sheetOne = allNicomDepartmensBook.getSheet(sheetOneName);
            XSSFSheet sheetTwo = allNicomDepartmensBook.getSheet(sheetTwoName);

            List<String> list = find(sheetOne, department);
            if (list != null) {
                return list;
            } else {
                return find(sheetTwo, department);
            }
        }
        return null;
    }

    //    метро / время работы будни / время работы вых адрес /адрес/ путь до пункта
    private List<String> find(XSSFSheet sheetTemp, String department) {
        int rowStart = 2;
        int cellStart = 1;
        while (true) {
            XSSFRow row = sheetTemp.getRow(rowStart);
            XSSFCell cell = row.getCell(cellStart);
            String cellValue = cell.getStringCellValue();
            if (cellValue.equals(department)) {
                List<String> list = new ArrayList<>();
                String temp = row.getCell(cellStart+1).getStringCellValue();
                String temp2 = temp.substring(0,temp.indexOf("-")-1);
                list.add(temp2);//метро
                list.add(row.getCell(cellStart + 2).getStringCellValue()); //время
                list.add(row.getCell(cellStart + 3).getStringCellValue()); //время
                list.add(row.getCell(cellStart + 4).getStringCellValue()); //адрес
                list.add(row.getCell(cellStart + 8).getStringCellValue()); //путь
                return list;
            } else if (!cellValue.equals("")) {
                rowStart++;
            } else break;
        }
        return null;
    }
}
