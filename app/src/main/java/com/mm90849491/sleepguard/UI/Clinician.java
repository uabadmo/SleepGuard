package com.mm90849491.sleepguard.UI;

import com.mm90849491.sleepguard.Objects.Person;

import java.io.Serializable;

/** Clinician class,
 *    Used for further authorisation sequence, hopefully.
 *  @version 0.2.0
 *  @author M.Meng
 */
final public class Clinician extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    final private String licenseNumber;

    public Clinician(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    @Override
    public String toString() {
        return new StringBuffer("").append(super.toString())
                .append(":").append( this.licenseNumber )
                .append("MM")
                .toString();
    }
}
