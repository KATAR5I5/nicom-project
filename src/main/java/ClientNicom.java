import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientNicom {
    private String fullName,firstName,secondName,thirdName;
    private Long phoneNumberOne,phoneNumberTwo;
    private String companyName;
    private Sex sex;

    public String getFullName() {
        return fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Long getPhoneNumberOne() {
        return phoneNumberOne;
    }

    public Long getPhoneNumberTwo() {
        return phoneNumberTwo;
    }

    public ClientNicom(String fullName, String phoneNumberOne) {
        this.fullName = fullName;
        if (!phoneNumberOne.isEmpty())
            normalizePhoneNumber(phoneNumberOne);
        if (!fullName.isEmpty())
            normalizeName(fullName);
    }

    void normalizeName(String name) {
        String[] normalizeName = name.split(" ");
        firstName = null;
        secondName = null;
        thirdName = null;
        for (int i = 0; i < normalizeName.length; i++) {
            if (normalizeName[i].length() > 3) {
                if (i == 0) firstName = normalizeName[0];
                else if (i == 1) {
                    secondName = normalizeName[1];
                } else if (i == 2) {
                    thirdName = normalizeName[2];
                }
            }
        }  //        System.out.println(firstName + secondName + thirdName);
    }

    void normalizePhoneNumber(String number) {
        Pattern patternPhoneNumber = Pattern.compile("(7|8|\\+7)\\-(\\d{3})\\-(\\d{3})\\-(\\d{2})\\-(\\d{2})");
        Matcher matcher = patternPhoneNumber.matcher(number);
        List<Long> numbers = new ArrayList<>();
        while (matcher.find()){
           numbers.add(Long.parseLong(matcher.group().replaceAll("\\-","")));
        }
        if(numbers.size()>0) {
            phoneNumberOne =numbers.get(0);
        }
        if(numbers.size()>1){
            phoneNumberTwo = numbers.get(1);
        }
    }

    @Override
    public String toString() {
        return "--------------------------------------\n" +
                "fullName= " + fullName + "\n" +
                "firstName= " + firstName + "\n" +
                "secondName= " + secondName + "\n" +
                "thirdName= " + thirdName + "\n" +
                "phoneNumberOne= " + phoneNumberOne + "\n" +
                "phoneNumberTwo= " + phoneNumberTwo + "\n";

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientNicom that = (ClientNicom) o;
        if (Objects.equals(phoneNumberOne, that.phoneNumberOne) || Objects.equals(phoneNumberOne, that.phoneNumberTwo) ||
                Objects.equals(phoneNumberTwo, that.phoneNumberOne) || Objects.equals(phoneNumberTwo, that.phoneNumberTwo)) {
            return true;
        } else {
            return fullName.equals(that.getFullName());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumberOne);
    }
}
