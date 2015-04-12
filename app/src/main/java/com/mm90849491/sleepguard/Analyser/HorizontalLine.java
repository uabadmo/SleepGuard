package com.mm90849491.sleepguard.Analyser;

/**
 * <\hr>
 * @author Meng Meng
 * 2013.05.24 Alpha
 * A text-based line separator
 * Usage:
 * 		HorizontalLine.draw();
 * Print:
 * ===========================
 */


public class HorizontalLine {
    //------DefaultValue------
    private static int	  LENTH_MAX = 40;
    private static int	  LENGTH = 0;
    private static int	  EXTRA = 2;
    private static String UNIT = "=";
    private static enum NumberBasis {
        BINARY((short) 2), DECIMAL((short) 10);
        private final short degree;

        NumberBasis (short value) {
            this.degree = value;
        }

        public short getDegree() {
            return this.degree;
        }
    }
    //------DefaultValue------

    //------InstanceVar-------
    private int 	lengthMax = LENTH_MAX;
    private int 	length = LENGTH;
    private int 	extra = EXTRA;
    private String 	unit = UNIT;
    //------InstanceVar-------

    //------Constructor-------
    /**
     * Construct and initialise a HorizontalLine object.
     * <ul><li>Maximum length: 40(default)</li>
     *     <li>Length: 0(default)</li>
     *     <li>Extra Length: 2(default)</li>
     *     <li>Pattern unit: "="(default)</li>
     * </ul>
     */
    public HorizontalLine() {
        this.lengthMax = LENTH_MAX;
        this.length = LENGTH;
        this.extra = EXTRA;
        this.unit = UNIT;
        this.valid();
    }

    /**
     * Construct and initialise a HorizontalLine object.
     * <ul><li>Maximum length: 40(default)</li>
     *     <li>Length: 0(default)</li>
     *     <li>Extra Length: ext</li>
     *     <li>Pattern unit: "="(default)</li>
     * </ul>
     * @param ext - Value will be used to set extra length of this object
     */
    public HorizontalLine(int ext) {
        this.lengthMax = LENTH_MAX;
        this.length = LENGTH;
        this.extra = ext;
        this.unit = UNIT;
        this.valid();
    }

    /**
     * Construct and initialise a HorizontalLine object.
     * <ul><li>Maximum length: 40(default)</li>
     *     <li>Length: 0(default)</li>
     *     <li>Extra Length: ext</li>
     *     <li>Pattern unit: uni</li>
     * </ul>
     * @param ext - Value will be used to set extra length of this object
     * @param uni - Value will be used to set pattern unit of this object
     */
    public HorizontalLine(int ext, String uni) {
        this.lengthMax = LENTH_MAX;
        this.length = LENGTH;
        this.extra = ext;
        this.unit = uni;
        this.valid();
    }

    /**
     * Construct and initialise a HorizontalLine object.
     * <ul><li>Maximum length: 40(default)</li>
     *     <li>Length: 		 len</li>
     *     <li>Extra Length: ext</li>
     *     <li>Pattern unit: uni</li>
     * </ul>
     * @param len - Value will be used to set basic length of this object
     * @param ext - Value will be used to set extra length of this object
     * @param uni - Value will be used to set pattern unit of this object
     */
    public HorizontalLine(int len, int ext, String uni) {
        this.lengthMax = LENTH_MAX;
        this.length = len;
        this.extra = ext;
        this.unit = uni;
        this.valid();
    }

    /**
     * Just for copy constructor. Shall not be access directly.
     * <ul><li>Maximum length: 	max</li>
     *     <li>Length: 		 	len</li>
     *     <li>Extra Length: 	ext</li>
     *     <li>Pattern unit: 	uni</li>
     * </ul>
     * @param len - Value will be used to set limit length of this object
     * @param len - Value will be used to set basic length of this object
     * @param ext - Value will be used to set extra length of this object
     * @param uni - Value will be used to set pattern unit of this object
     */
    private HorizontalLine(int max, int len, int ext, String uni) {
        this.lengthMax = max;
        this.length = len;
        this.extra = ext;
        this.unit = uni;
        this.valid();
    }

    /**
     * Construct a depth copy of input object.
     * <ul><li>Maximum length: 	input.max</li>
     *     <li>Length: 		 	input.len</li>
     *     <li>Extra Length: 	input.ext</li>
     *     <li>Pattern unit: 	input.uni</li>
     * </ul>
     * @param newLine - Parameters will be used to initialise the new line.
     */
    public HorizontalLine(HorizontalLine newLine) {
        this(   newLine.getLenMax(),
                newLine.getLength(),
                newLine.getExtra(),
                newLine.getUnit()    );
    }
    //------Constructor-------

    /**
     * Driver of HorizontalLine
     */
    public static void main(String[] args) {
        System.out.println("Default Value:\n\t" +
                "EXTRA = 2; LENGTH = 0; UNIT = \"=\"; Max = 40" );

        HorizontalLine hr = new HorizontalLine();

        System.out.println("Test result: ");
        System.out.println("Default line(" + hr.valid() + ")");
        hr.draw();
        System.out.println();
        hr.drawln();

        System.out.println("guessLength test: ");
        hr.guessLength(111);
        System.out.println("111" + "(" + hr.getLength() + ")");
        hr.guessLength(0);
        System.out.println("0" + "(" + hr.getLength() + ")");
        hr.guessLength(111);
        System.out.println("3" + "(" + hr.getLength() + ")");
        //Bug case:11 - 11.11 -> -0.1099999999943
        //Fixed by changing the mechanism of countDecimal
        hr.guessLength(11.11);
        System.out.println("11.11" + "(" + hr.getLength() + ")!");
        hr.guessLength(11.1);
        System.out.println("11.1" + "(" + hr.getLength() + ")!");
        hr.guessLength("ABC");
        System.out.println("ABC" + "(" + hr.getLength() + ")");

        hr =  new HorizontalLine(5, "+");
        System.out.println("User-defined new-line(" + hr.valid() + ")");
        hr.getInfo();
        hr.drawln();
        hr.setLength(2);
        hr.getInfo();
        hr.drawln();

        System.out.println("Copy constructor test");
        HorizontalLine hr2 = new HorizontalLine(hr);
        hr2.getInfo();

        hr2.setLength("Validation test".length());
        hr2.drawln();
        System.out.println(hr2.getLength());
        System.out.println("Validation test");
        System.out.println("41: (" + hr2.setLength(41) + ")");
        hr2.getInfo();
        System.out.println("-1: (" + hr2.setLength(-1) + ")");
        hr2.getInfo();
        System.out.println("40: (" + hr2.setLength(40) + ")");
        System.out.println("Max = -20 : (" + hr2.setLenMax(-20) + ")");
        hr2.getInfo();
        hr2.drawln();
        hr2.draw();

        hr = new HorizontalLine(5);
        hr2.setLength(8);
        hr2.setLenMax(40);
        hr2.setUnit("=");
        hr2.setLength(1);
        hr2.print("END");
        System.out.println();
        hr2.setLength(2);
        hr2.print("END");
        System.out.println();
        hr2.setLength(3);
        hr2.print("END");
        System.out.println();
        hr2.guessLength("END");
        hr.draw();
        hr2.draw();
        hr.drawln();
        hr2.setLength(hr2.getLength() + 5 * 2 );
        hr2.println("END");
        hr2.println("E N D");

    }

    //------PublicMethod------
    /**
     * <dl><dt>Display parameters of this object.</dt>
     *     <dd>Call valid() method to validate this object;
     *     print the the validation error code, this code shall always be 0;
     *     print all instance variables' name and value of this object.</dd>
     * </dl>
     */
    public void getInfo() {
        System.out.print("(" + this.valid() + ")");
        System.out.println("EXTRA = " + this.getExtra() +
                "; LENGTH = " + this.getLength() +
                "; UNIT = \"" + this.getUnit() + "\"" +
                "; MAX = " + this.getLenMax());
    }

    /**
     * Retrieve the maximum length value.
     * @return The maximum length of this object can be set by user.
     */
    public int getLenMax() {
        return this.lengthMax;
    }

    /**
     * Retrieve the length value.
     * @return The current value of the length of this object.
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Retrieve the extra length value.
     * @return The current value of the extra length of this object.
     */
    public int getExtra() {
        return this.extra;
    }

    /**
     * Retrieve the pattern unit of the line.
     * @return The current text pattern to form this HorizontalLine.
     */
    public String getUnit() {
        return this.unit;
    }

    /**
     * Set maximum value by input integer value.
     * This value will restrict the length of the line, but extra length can be added.
     * @param max - The value of the maximum length will be set to this object.
     * @return The error code of validation. Normal to receive a none-0 number.
     */
    public int setLenMax(int max) {
        this.lengthMax = max;
        return this.valid();
    }

    /**
     * Set length by input integer value.
     * @param len - The value of the length will be set to this object.
     * @return The error code of validation. Normal to receive a none-0 number.
     */
    public int setLength(int len) {
        this.length = len;
        return this.valid();
    }

    /**
     * Set extra length by input integer value.
     * This length can be greater than maximum length of this object.
     * @param ext - The value of the extra length will be set to this object.
     * @return The error code of validation. Normal to receive a none-0 number.
     */
    public int setExtra(int ext) {
        this.extra = ext;
        return this.valid();
    }

    /**
     * Set pattern unit by input string.
     * @param uni - The text pattern will be set to this object.
     */
    public void setUnit(String uni) {
        this.unit = uni;
    }

    /**
     * Calculate how many decimal place does this input have;
     * set the length of this object to this number;
     * call valid() method to validate the current parameters.
     * <ul><li>The return value is not the length.</li>
     *     <li>Can perform double type number's calculation, but not suggested.</li>
     *     <li>Can be extended to count binary digits of the integer input of decimal form.</li>
     * </ul>
     * @param len The number that its number of digits will be calculated.
     * @return The error code of validation. If not zero, the length shall be set to boundary.
     */
    public int guessLength(double len) {
        this.length = countDecimal(len);
        return this.valid();
    }

    /**
     * Call String.length() method to retrieve the length of the string;
     * the value will be set to the length of this object;
     * call valid() method to validate the current parameters.
     * @param len The input string that its length will be counted.
     * @return The error code of validation. If not zero, the length shall be set to boundary.
     */
    public int guessLength(String len) {
        this.length = len.length();
        return this.valid();
    }

    /**
     * Print the object to screen:
     * <ul><li>The word pattern will be printed many times to give a illustration of horizontal line.</li>
     *     <li>The number of total word patterns will be the sum of the length and extra length of this object.</li>
     *     <li>No newline character will be added to the end of the line.</li>
     */
    public void draw() {
        for (int i = 0;
             i < this.getLength() + this.getExtra();
             i++) {
            System.out.print(this.getUnit());
        }
    }

    /**
     * Print the object to screen:
     * <ul><li>The word pattern will be printed many times to give a illustration of horizontal line.</li>
     *     <li>The number of total word patterns will be the sum of the length and extra length of this object.</li>
     *     <li>A newline character will be added to the end of the line.</li>
     */
    public void drawln() {
        this.draw();
        System.out.println();
    }

    /**
     * <dl><dt>Print a line which has a few words in the centre</dt>
     *     <dd>e.g.: ===WORD===</dd>
     * </dl>
     * <dl><dt>If the string is longer than the line, it will be cut to fit</dt>
     *     <dd>e.g.: ordWORDwor</dd>
     * </dl>
     * No newline character will be added in the end of this line,
     * @param inp The string will be printed in the centre of this line.
     */
    public void print(String inp) {
        inp = inp.replaceAll(" ", this.getUnit());
        int limit = this.getLength() + this.getExtra();
        int start = ( inp.length() - limit ) / 2 ;
        if (start >= 0) {
            System.out.print(inp.substring(start, start + limit));
        } else {
            int stop = inp.length();
            while (start < 0) {
                System.out.print(this.getUnit());
                start++;
                stop++;
            }
            System.out.print(inp);
            while (stop < limit) {
                System.out.print(this.getUnit());
                stop++;
            }
        }
    }

    /**
     * <dl><dt>Print a line which has a few words in the centre</dt>
     *     <dd>e.g.: ===WORD===</dd>
     * </dl>
     * <dl><dt>If the string is longer than the line, it will be cut to fit</dt>
     *     <dd>e.g.: ordWORDwor</dd>
     * </dl>
     * A newline character will be added in the end of this line,
     * @param inp The string will be printed in the centre of this line.
     */
    public void println(String inp) {
        this.print(inp);
        System.out.println();
    }
    //------PublicMethod------

    //-----PrivateMethod------
    private int valid() {
        //Only reduce extra when Max is less than 0
        if (this.lengthMax < 0) {
            this.lengthMax = 0;
            this.length = 0;
            this.extra = 0;
            return 2;
        }
        //Length test
        if (this.length > this.lengthMax) {
            this.length = this.lengthMax;
            return 1;
        }
        if (this.length < 0) {
            this.length = 0;
            return -1;
        }
        //Extra test
        if ( (this.extra + this.length) < 0) {
            this.extra = -this.length;
            return -2;
        }
        return 0;
    }

    private int countDecimal(double target) {
        int    length = 0;
        int    inte = (int) target;
        //double deci = target - (double)inte;
        while ( target != Math.round(target) ) {
            length++;
            target = target * NumberBasis.DECIMAL.getDegree();
        }
        while (inte != 0) {
            length++;
            inte = inte / NumberBasis.DECIMAL.getDegree();
        }
        return length;
    }
    //-----PrivateMethod------

}
