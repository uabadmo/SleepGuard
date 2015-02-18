package com.mm90849491.sleepguard;


import java.io.Serializable;

/** Person class, abstract.
 *    Used for storing some essential contact information of a human being mainly in the 21st.
 *    Contains NumberFormatException, error message needs to be prompted by UI.
 *  @version 0.8.0 methods related to phone number need to do integration testing with UI
 *  @author M.Meng
 */
public abstract class Person implements Serializable {

    /* ------------ begin of constant variables ------------ */
    static private String DEF_FIRST_NAME = "John";
    static private String DEF_LAST_NAME = "Smith";
    static private String DEF_PREFIX = "Mr.";
    /* ------------- end of constant variables ------------- */

    /* ------------ begin of instance variables ------------ */
    private String _firstName;
    private String _lastName;
    private String _middleName;
    private String _prefix;
    private byte[] _phoneNumber;
    private int _phoneExtension;
    private String _emailAddress;
    /* ------------- end of instance variables ------------- */

    /* -------------- begin of getter methods -------------- */
    /** Get _firstName.
     *
     * @return String
     */
    protected String firstName() {
        return this._firstName;
    }

    /** Get _lastName.
     *
     * @return String
     */
    protected String lastName() {
        return this._lastName;
    }

    /** Get _middleName.
     *
     * @return String
     */
    protected String middleName() {
        return this._middleName;
    }

    /** Get _phoneNumber.
     *      Caller is responsible to format it.
     * @return String
     */
    protected String phoneNumber() {
        String buffer = "";
        for(byte digit : this._phoneNumber ) {
            buffer = buffer.concat(String.valueOf( digit ));
        }
        return buffer;
    }

    /** Get _phoneExtension.
     *
     * @return String
     */
    protected String phoneExtension() {
        return String.valueOf( this._phoneExtension );
    }

    /** Get _prefix.
     *
     * @return String
     */
    protected String prefix() {
        return this._prefix;
    }

    /** Get _emailAddress.
     *
     * @return String
     */
    protected String emailAddress() {
        return this._emailAddress;
    }
    /* --------------- end of getter methods --------------- */

    /* -------------- begin of setter methods -------------- */
    /** Set _firstName.
     *
     * @param firstName String.
     */
    protected void firstName(String firstName) {
        this._firstName = firstName;
    }

    /** Set _lastName.
     *
     * @param lastName String.
     */
    protected void lastName(String lastName) {
        this._lastName = lastName;
    }

    /** Set _middleName.
     *
     * @param middleName String.
     */
    protected void middleName(String middleName) {
        this._middleName = middleName;
    }

    /** Set _prefix
     *
     * @param prefix String.
     */
    protected void prefix(String prefix) {
        this._prefix = prefix;
    }

    /** Set _phoneNumber.
     *
     * @param phoneNumber String.
     * @throws NumberFormatException error message needs to be prompted by UI.
     */
    protected void phoneNumber(String phoneNumber) throws NumberFormatException {
        int temp;
        int length = phoneNumber.length();
        this._phoneNumber = new byte[length];
        while(length > 0) {
            temp = phoneNumber.charAt(length - 1) - '0' ;
            if(temp > 9 || temp < 0) {
                throw new NumberFormatException(
                        "Phone number cannot contain non-digit character."
                );
            }
            this._phoneNumber[length - 1] = (byte)temp;
        }
    }

    /** Set _phoneExtension.
     *
     * @param phoneExtension String.
     * @throws NumberFormatException error message needs to be prompted by UI.
     */
    protected void phoneExtension(String phoneExtension) throws NumberFormatException {
        try {
            this._phoneExtension = Integer.parseInt( phoneExtension );
        } catch (NumberFormatException e) {
            throw new NumberFormatException(
                    "Phone extension cannot contain non-digit character."
            );
        }
    }

    /** Set _emailAddress
     *
     * @param emailAddress String.
     * @throws NumberFormatException error message needs to be prompted by UI.
     */
    protected void emailAddress(String emailAddress) throws NumberFormatException {
        if(! emailAddress.contains("@")) {
            throw new NumberFormatException(
                    "e-mail address must contains an @."
            );
        }
        this._emailAddress = emailAddress;
    }
    /* --------------- end of getter methods --------------- */

    /* --------------- begin of constructors --------------- */
    /** Default constructor of Client.
     *    Generate a name for lazy users.
     */
    protected Person() {
        this.firstName( DEF_FIRST_NAME );
        this.lastName( DEF_LAST_NAME );
        this.prefix( DEF_PREFIX );
    }

    /**
     * Constructor of Client with given values of instance variables.
     * @param firstName String.
     * @param lastName String.
     * @param middleName String.
     * @param prefix String.
     * @param phoneNumber String throws NumberFormatException.
     * @param phoneExtension String throws NumberFormatException.
     * @param emailAddress String throws NumberFormatException.
     */
    protected Person(String firstName, String lastName, String middleName, String prefix,
                      String phoneNumber, String phoneExtension, String emailAddress) {
        this.firstName( firstName );
        this.lastName( lastName );
        this.middleName( middleName );
        this.prefix( prefix );
        this.phoneNumber( phoneNumber );
        this.phoneExtension( phoneExtension );
        this.emailAddress( emailAddress );
    }
    /* ---------------- end of constructors ---------------- */

    /* ------------- begin of protected methods ------------ */

    @Override
    public String toString() {
        return new StringBuffer("mm").append( this.firstName() )
                .append(":").append( this.lastName())
                .append(":").append( this.middleName() )
                .append(":").append( this.prefix() )
                .append(":").append( this.phoneNumber() )
                .append(":").append( this.phoneExtension() )
                .append(":").append( this.emailAddress() )
                .toString();
    }
    /* -------------- end of protected methods ------------- */


}
