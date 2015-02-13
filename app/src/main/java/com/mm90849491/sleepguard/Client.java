package com.mm90849491.sleepguard;

/** Client class, subclass of Person.
 *    Used for password protection.
 *  @version 1.0.0
 *  @author M.Meng
 *  @see com.mm90849491.sleepguard.Person
 */
final public class Client extends Person {

    /* ------------ begin of constant variables ------------ */
    static private boolean DEF_ANONYMOUS = true;
    static private boolean HAS_PASSWORD = true;
    static private boolean HAS_NOT_PASSWORD = false;
    /* ------------- end of constant variables ------------- */

    /* ------------ begin of instance variables ------------ */
    private boolean _anonymous;
    private String _password;
    /* --------------- considerable versions --------------- */
    //private String _username = "mobA";
    //private String _password = "aMob";
    /* ------------- end of instance variables ------------- */

    /* -------------- begin of getter methods -------------- */
    /** Getter of _anonymous.
     *     Must be checked before retrieve password,
     *        since password may point to NULL.
     *  @return :boolean true if has a password,
     *                    false if no password.
     */
    protected boolean isAnonymous() {
        return this._anonymous;
    }

    /** Getter of _password.
     *     This method should only be used for authentication.
     *  @return :String _password
     */
    private String password() {
        return this._password;
    }
    /* --------------- end of getter methods --------------- */

    /* -------------- begin of setter methods -------------- */
    /** Setter of _anonymous.
     *    This method should only be used in _password setter method
     *        to ensure the consistency of these two instance variables.
     *  @param _anonymous :boolean must be Client.HAS_PASSWORD or
     *                                        Client.HAS_NOT_PASSWORD
     */
    private void anonymous(boolean _anonymous) {
        this._anonymous = _anonymous;
    }

    /** Setter of _password.
     *    This method will call anonymous(bool) to set the value of _anonymous.
     *    Empty string string is allowed,
     *    null pointer should not be passed, but it can be handled.
     *  @param _password String leading and trailing space will be cutoff.
     *                      Case-sensitive
     */
    protected void password(String _password) {
        if( _password == null || _password.trim().length() == 0 ) {
            this.anonymous(HAS_NOT_PASSWORD);
        } else {
            this._password = _password.trim();
            this.anonymous(HAS_PASSWORD);
        }
    }
    /* --------------- end of getter methods --------------- */

    /* --------------- begin of constructors --------------- */
    /** Default constructor of Client.
     *    Only set _anonymous to DEF_ANONYMOUS value.
     *    _password will not be initialised
     */
    protected Client() {
        this.anonymous( Client.DEF_ANONYMOUS );
    }
    /* ---------------- end of constructors ---------------- */

    /* ------------- begin of protected methods ------------ */
    /**
     *  Verifying given password.
     *  @param _password String leading and trailing space will be cutoff.
     *                      Case-sensitive
     *  @return boolean true if given string matches password or password is not set.
     */
    protected boolean validate(String _password) {
        return ( this.isAnonymous() == Client.HAS_NOT_PASSWORD ) ||
                ( this.password().equals( _password.trim() ) );
    }
    /* -------------- end of protected methods ------------- */
}
