import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class TestPOI {
    public static void main(String[] args) throws IOException, InterruptedException {
//        Создаем МАР устройств и и клиентов из 1с документа
        CreateNicomObjects nicom = new CreateNicomObjects(Path.of("full3.xls"));
        Thread thread1 = new Thread(nicom);
        thread1.start();

//        Создаем актуальный список из файла "Наши отделения"
        CurrentDepartmenList cdpl = new CurrentDepartmenList(Path.of("Dep.xlsx"));
        Thread thread2 = new Thread(cdpl);
        thread2.start();
        thread1.join();
        thread2.join();
        List<String> actualListDep = cdpl.getActualListDepartment();
//            Создаем отчеты для отправки сообщений
        Map<Device, ClientNicom> mapNicom = nicom.getMapDevice();
        CreateNewExelFile newFileExel = new CreateNewExelFile(mapNicom, actualListDep, Path.of("Отчет.xlsx"));
        CreateNewFileForSMS cr = new CreateNewFileForSMS(mapNicom, actualListDep, Path.of("Отпрака SMS.xlsx"));
        System.out.println("\nDone!");
//        newFileExel.fillOneSheet();

//        printMap(nicom.getMapDevice());
//        printDeviceIsRepair(nicom.getMapDevice());
//        printDeviseNotInDepartment(nicom.getMapDevice());
//        printClients(nicom.getMapDevice());
//        printSMStext();

//        CurrentDepartmenList cdp =new CurrentDepartmenList();
//        cdp.openFileAndFillActualList();
//        System.out.println(cdp.getActualListDepartment());
//        cdp.verifyDepartmentsCompleteDevice(nicom.getMapDevice(),cdp.getActualListDepartment());
//        System.out.println(cdp.getMissingListDepartment());
//        System.out.println(cdp.getMissingListDevicesInDepartment());
//        printDeviseNotInDepartment(nicom.getMapDevice());

//        CreateNewExelFile q = new CreateNewExelFile();
//        q.create();

    }


    static void printMap(Map<Device, ClientNicom> map) {
        System.out.println(map);
//        map.keySet()
//                .stream()
//                .map(el -> el.getDepartment())
//                .filter(el -> !el.getDepartment().equals(el.getDepartmentHasDevice()))
//                .filter(el -> el.getPriceToRepair()>5000)
//                .sorted((dev1, dev2) -> (dev1.getDepartment().compareTo(dev2.getDepartment())))

//                .collect(Collectors.toList());
//                .forEach(System.out::println);
    }

    static void printDeviceIsRepair(Map<Device, ClientNicom> map) {
        map.keySet()
                .stream()
                .filter(el -> el.getHasRepair())
                .forEach(System.out::println);
    }

    static void printDeviseNotInDepartment(Map<Device, ClientNicom> map) {
        System.out.println("Данные устройства находятся в отделениях в которых НЕ СДАВАЛИСЬ:");
        map.keySet()
                .stream()
                .filter(el -> !el.getDepartment().equals(el.getDepartmentHasDevice()))
                .forEach(System.out::println);
    }

    static void printClients(Map<Device, ClientNicom> map) {
        map.values()
                .stream()
//                .filter(el -> !(el.getPhoneNumberTwo()==null))
                .forEach(System.out::println);
    }
}