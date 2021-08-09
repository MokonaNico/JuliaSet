package JuliaSet;

public class Complex {

    double real;
    double imaginary;

    public Complex(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex add(Complex other){
        return new Complex(real + other.real, imaginary + other.imaginary);
    }

    public Complex mult(Complex other){
        double resReal = real * other.real - imaginary * other.imaginary;
        double resImaginary = imaginary * other.real + real * other.imaginary;
        return new Complex(resReal, resImaginary);
    }

    public double mod(){
        return Math.sqrt(real*real + imaginary*imaginary);
    }

    public double squaredMod(){
        return real * real + imaginary * imaginary;
    }

    public Complex square(){
        double resReal = real * real - imaginary * imaginary;
        double resImaginary = 2 * imaginary * real;
        return new Complex(resReal, resImaginary);
    }
}
