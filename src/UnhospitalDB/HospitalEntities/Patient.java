package UnhospitalDB.HospitalEntities;

import java.util.List;

public class Patient {

    //Attributes
    private int id;
    private String name;
    private String familyName;
    private String city;
    private String province;
    private String postalCode;
    private String phoneNumber;
    public enum Gender{
        Male, Female, Non_Binary
    }
    private Gender gender;
    private String dateOfBirth;
    private List<Visit> visits; //(One patient can have many visits)


    //Constructors
    public Patient() {}
    public Patient(String name, String familyName, String city, String province, String postalCode,
                   String phoneNumber, Gender gender, String dateOfBirth) {
        this.name = name;
        this.familyName = familyName;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }


    //ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Family Name
    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    //City
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    //Province
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province;
    }

    //Postal Code
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    //Phone Number
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //Gender
    public Gender getGender() {
        return gender;
    }
    public String getGenderAsString() {
        return gender.name();
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public void setGenderFromString(String genderString) {
        this.gender = Gender.valueOf(genderString);
    }

    //Date of birth
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    //Visits
    public List<Visit> getVisits() {
        return visits;
    }
    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
    public void addVisit(Visit visit) {
        visits.add(visit);
    }


    //Print Patient Info
    public void printInfo(Patient patient){
        System.out.println("Patient Information:");
        System.out.println("ID: " + patient.getId());
        System.out.println("Name: " + patient.getName());
        System.out.println("Family Name: " + patient.getFamilyName());
        System.out.println("City: " + patient.getCity());
        System.out.println("Province: " + patient.getProvince());
        System.out.println("Postal Code: " + patient.getPostalCode());
        System.out.println("Phone Number: " + patient.getPhoneNumber());
        System.out.println("Gender: " + patient.getGenderAsString());
        System.out.println("Date of Birth: " + patient.getDateOfBirth());
    }

}
