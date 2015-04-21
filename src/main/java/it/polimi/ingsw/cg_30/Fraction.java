package it.polimi.ingsw.cg_30;

public class Fraction {
	private int numerator;
    private int denominator;
    /**
     * Constructs a fraction with the specified numerator and denominator
     * @param numerator the numerator of the fraction
     * @param denominator the denominator of the fraction
     */
    public Fraction(int numerator, int denominator){
          this.numerator=numerator;
          this.denominator=denominator;
}
    /**
     * Constructs a fraction with the specified numerator and a denominator of 1
     * @param numerator the numerator of the fraction
     */
    public Fraction(int numerator){
          this(numerator,1);
}
    /**
     * @return the numerator
*/
    public int getNumerator() {
          return numerator;
}
    /**
     * @param numerator the numerator to set
*/
    public void setNumerator(int numerator) {
          this.numerator = numerator;
	
    }
    /**
     * @return the denominator
    */
    public int getDenominator() {
          return denominator;
    }
    /**
     * @param denominator the denominator to set
    */
    public void setDenominator(int denominator) {
          this.denominator = denominator;
    }
    /**
     * Add this fraction to the specified fraction and returns it as a new fraction
        (not simplified).
     * It does not modify this fraction.
     * @param f the fraction to be added to this fraction
     * @return a new fraction equivalent to this fraction plus the parameter
     */
    public Fraction add(Fraction f)
    {
    int num= (this.numerator * f.denominator) + (this.denominator *
        f.numerator);
    int den= this.denominator * f.denominator;
    Fraction sum=null;
    sum.setNumerator(num);
    sum.setDenominator(den);
    //Fraction sum=new Fraction(num, den);
    return sum;
    }
    @Override
    public String toString(){
    return this.numerator+"/"+this.denominator;
    }
	
}
