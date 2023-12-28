public class UserDataModel {
    String firstName;
    String lastName;
    String location;

    UserDataModel() {
        firstName="Default";
        lastName = "Default";
        location="Default";
    }

    UserDataModel(String firsString, Stirng lastName, Stirng location) {
        this.firstName = firsString;
        this.lastName = lastName;
        this.location = location;
    }
}
