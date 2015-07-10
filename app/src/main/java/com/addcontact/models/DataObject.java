package com.addcontact.models;

/**
 * Created by Manish on 10/06/2015.
 */
public class DataObject {
    private int Id;
    private String FirstName,MiddleName,LastName,MobileNo,Address,dateTime,Village,Taluko,District,Pincode,SlipNo,CustomerId,Category,InstallmentYear,InstallmentPrice,AuthorisePerson;

    public DataObject(int Id,String FirstName,String MiddleName,String LastName,String MobileNo,String Address,String Village,String Taluko,String District,String Pincode,String SlipNo,
                      String CustomerId,String Category,String InstallmentYear,String InstallmentPrice,String AuthorisePerson,String datetime){
        //mText1 = text1;
        //mText2 = text2;
        this.Id=Id;
        this.FirstName=FirstName;
        this.MiddleName=MiddleName;
        this.LastName=LastName;
        this.MobileNo=MobileNo;
        this.Address=Address;
        this.dateTime=datetime;
        this.Village=Village;
        this.Taluko=Taluko;
        this.District=District;
        this.Pincode=Pincode;
        this.SlipNo=SlipNo;
        this.CustomerId=CustomerId;
        this.Category=Category;
        this.InstallmentYear=InstallmentYear;
        this.InstallmentPrice=InstallmentPrice;
        this.AuthorisePerson=AuthorisePerson;
    }

    public void setId(int Id){
        this.Id=Id;
    }
    public int getId(){
        return Id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String MiddleName) {
        this.MiddleName = MiddleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String MobileNo) {
        this.MobileNo = MobileNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getVillage() {
        return Village;
    }

    public void setVillage(String Village) {
        this.Village = Village;
    }

    public String getTaluko() {
        return Taluko;
    }

    public void setTaluko(String Taluko) {
        this.Taluko = Taluko;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String District) {
        this.District = District;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String Pincode) {
        this.Pincode = Pincode;
    }

    public String getSlipNo() {
        return SlipNo;
    }

    public void setSlipNo(String SlipNo) {
        this.SlipNo = SlipNo;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getInstallmentYear() {
        return InstallmentYear;
    }

    public void setInstallmentYear(String InstallmentYear) {
        this.InstallmentYear = InstallmentYear;
    }

    public String getIInstallmentPrice() {
        return InstallmentPrice;
    }

    public void setInstallmentPrice(String InstallmentPrice) {
        this.InstallmentPrice = InstallmentPrice;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getAuthorisePerson() {
        return AuthorisePerson;
    }

    public void setAuthorisePerson(String AuthorisePerson) {
        this.AuthorisePerson = AuthorisePerson;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
