package model;

public class CustomerSearchDTO {
    private int customer_id;
    private String first_name;
    private String last_name;
    private String address;
    private String email;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CustomerSearchDTO(int customer_id) {
        this.customer_id = customer_id;
    }

    public boolean equals(Object o) {
        if (o instanceof CustomerSearchDTO) {
            CustomerSearchDTO f = (CustomerSearchDTO) o;
            return customer_id == f.customer_id;
        }
        return false;
    }

    public CustomerSearchDTO(){

    }
}
