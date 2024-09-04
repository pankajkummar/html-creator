package com.pankaj.html_creator;

public class MechinalDepartment {
    private String name;
    private String id;
    private String designation;
    private String authorizationNo;
    private String formA;
    private String ime;
    private String dob;
    private String doj;
    private String sop;
    private String vtc;
    private String image;
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAuthorizationNo() {
        return authorizationNo;
    }

    public void setAuthorizationNo(String authorizationNo) {
        this.authorizationNo = authorizationNo;
    }

    public String getFormA() {
        return formA;
    }

    public void setFormA(String formA) {
        this.formA = formA;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getSop() {
        return sop;
    }

    public void setSop(String sop) {
        this.sop = sop;
    }

    public String getVtc() {
        return vtc;
    }

    public void setVtc(String vtc) {
        this.vtc = vtc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MechinalDepartment(String name, String id, String designation, String authorizationNo, String formA, String ime, String dob, String doj, String sop, String vtc, String image, String fileName) {
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.authorizationNo = authorizationNo;
        this.formA = formA;
        this.ime = ime;
        this.dob = dob;
        this.doj = doj;
        this.sop = sop;
        this.vtc = vtc;
        this.image = image;
        this.fileName = fileName;
    }
}
