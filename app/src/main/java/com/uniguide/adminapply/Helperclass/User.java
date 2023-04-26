package com.uniguide.adminapply.Helperclass;

public class User {
    private String fullName;
    private String dateOfBirth;
    private String countryOfBirth;
    private String placeOfBirth;
    private String countryOfResidence;
    private String province;
    private String email;
    private String country;
    private String contact;
    private String school;
    private String schoolLocation;
    private String schoolCourse;
    private String schoolGPA;
    private String college;
    private String collegeLocation;
    private String collegeCourse;
    private String collegeGPA;
    private String uni;
    private String uniLocation;
    private String uniCourse;
    private String uniGPA;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullName, String dateOfBirth, String countryOfBirth, String placeOfBirth, String countryOfResidence, String province, String email, String country, String contact, String school, String schoolLocation, String schoolCourse, String schoolGPA, String college, String collegeLocation, String collegeCourse, String collegeGPA, String uni, String uniLocation, String uniCourse, String uniGPA) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.countryOfBirth = countryOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.countryOfResidence = countryOfResidence;
        this.province = province;
        this.email = email;
        this.country = country;
        this.contact = contact;
        this.school = school;
        this.schoolLocation = schoolLocation;
        this.schoolCourse = schoolCourse;
        this.schoolGPA = schoolGPA;
        this.college = college;
        this.collegeLocation = collegeLocation;
        this.collegeCourse = collegeCourse;
        this.collegeGPA = collegeGPA;
        this.uni = uni;
        this.uniLocation = uniLocation;
        this.uniCourse = uniCourse;
        this.uniGPA = uniGPA;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public String getProvince() {
        return province;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public String getContact() {
        return contact;
    }

    public String getSchool() {
        return school;
    }

    public String getSchoolLocation() {
        return schoolLocation;
    }

    public String getSchoolCourse() {
        return schoolCourse;
    }

    public String getSchoolGPA() {
        return schoolGPA;
    }

    public String getCollege() {
        return college;
    }

    public String getCollegeLocation() {
        return collegeLocation;
    }

    public String getCollegeCourse() {
        return collegeCourse;
    }

    public String getCollegeGPA() {
        return collegeGPA;
    }

    public String getUni() {
        return uni;
    }

    public String getUniLocation() {
        return uniLocation;
    }

    public String getUniCourse() {
        return uniCourse;
    }

    public String getUniGPA() {
        return uniGPA;
    }

}
