package com.example.bradperkins.project3.DataUtilites;

import java.io.Serializable;

/**
 * Created by bradperkins on 12/8/15.
 */
public class Contact implements Serializable {

    private static final long serialVersionUID = 517116325584636891L;

    public String contactName;
    public String contactPhone;
    public String contactEmail;

    public Contact(String _contactName, String _contactPhone, String _contactEmail) {

        contactName = _contactName;
        contactPhone = _contactPhone;
        contactEmail = _contactEmail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }


}

