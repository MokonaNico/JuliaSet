package JuliaSet;

public class Complex {

    double real;
    double imaginary;

    public Complex(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex Add(Complex other){
        return new Complex(real + other.real, imaginary + other.imaginary);
    }

    public Complex Mult(Complex other){
        return new Complex(real * other.real - imaginary * other.imaginary,
                                imaginary * other.real + real * other.imaginary);
    }

    public double Mod(){
        return Math.sqrt(real*real + imaginary*imaginary);
    }
}
