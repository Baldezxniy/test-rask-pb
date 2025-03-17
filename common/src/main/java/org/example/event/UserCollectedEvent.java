package org.example.event;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class UserCollectedEvent implements Serializable {

    private Long userId;

    private String firstName;

    private String lastName;

    private String middleName;

    private String address;

    private List<String> creditCardNumbers;

    public UserCollectedEvent() {
    }

    public UserCollectedEvent(
            Long userId,
            String firstName,
            String lastName,
            String middleName,
            String address,
            List<String> creditCardNumbers) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.address = address;
        this.creditCardNumbers = creditCardNumbers;
    }

    public static UserCollectedEvent init(Long id) {
        var event = new UserCollectedEvent();
        event.setUserId(id);
        return event;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCreditCardNumbers(List<String> creditCardNumbers) {
        this.creditCardNumbers = creditCardNumbers;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getCreditCardNumbers() {
        return creditCardNumbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCollectedEvent that = (UserCollectedEvent) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return "UserCollectedEvent {" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", address='" + address + '\'' +
                ", creditCardNumbers=" + creditCardNumbers +
                '}';
    }
}
