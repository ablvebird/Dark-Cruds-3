package UnhospitalDB.HospitalEntities;

public class Employee {

    //Attributes
    private int id;
    private String name;
    private String lastName;
    public enum Position {
        Reception, General_Practice, Traumatology, Internal_Medicine
    }
    private Position position;
    private String loginName;
    private String loginPassword;


    //Constructors
    public Employee() {}
    public Employee(String name, String lastName, Position position, String loginName, String loginPassword) {
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.loginName = loginName;
        this.loginPassword = loginPassword;
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


    //Last Name
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    //Position
    public Position getPosition() {
        return position;
    }
    public String getPositionAsString() {
        return position.name();
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public void setPositionFromString(String positionString) {
        this.position = Position.valueOf(positionString);
    }


    //Login Name
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    //Login Password
    public String getLoginPassword() {
        return loginPassword;
    }
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

}
