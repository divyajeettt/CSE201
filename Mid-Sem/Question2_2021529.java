class Fraction {
    private final int a, b;

    private int gcd(int a, int b) {
        return ((b == 0) ? a : gcd(b, a % b));
    }

    public Fraction(int a, int b) {
        int g;
        while ((g = gcd(a, b)) != 1) {
            a /= g;
            b /= g;
        }

        if (b < 0) {
            a = -a;
            b = -b;
        }

        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return (this.a + "/" + this.b);
    }

    public boolean isPositive() {
        return (this.a >= 0);
    }

    public double abs() {
        return (this.a / (this.b+0.0f));
    }

    public static Fraction add(Fraction f1, Fraction f2) {
        return new Fraction(f1.a*f2.b + f2.a*f1.b, f1.b*f2.b);
    }

    public static Fraction subtract(Fraction f1, Fraction f2) {
        return new Fraction(f1.a*f2.b - f2.a*f1.b, f1.b*f2.b);
    }

    public static Fraction multiply(Fraction f1, Fraction f2) {
        return new Fraction(f1.a*f2.a, f1.b*f2.b);
    }

    public static Fraction divide(Fraction f1, Fraction f2) {
        return new Fraction(f1.a*f2.b, f1.b*f2.a);
    }
}


abstract class Complex<T> {
    private final T a, b;

    public Complex(T a, T b) {
        this.a = a;
        this.b = b;
    }

    public T getA() {
        return a;
    }

    public T getB() {
        return b;
    }

    public abstract double argument();
    public abstract double magnitude();
    public abstract Complex<T> add(Complex<T> c1, Complex<T> c2);
    public abstract Complex<T> multiply(Complex<T> c1, Complex<T> c2);
}


class ComplexInteger extends Complex<Integer> {
    public ComplexInteger(int a, int b) {
        super(a, b);
    }

    @Override
    public String toString() {
        String sign = (this.getB() >= 0) ? " + " : " - ";
        return (this.getA() + sign + this.getB() + "i");
    }

    @Override
    public ComplexInteger add(Complex<Integer> c1, Complex<Integer> c2) {
        return new ComplexInteger(c1.getA() + c2.getA(), c1.getB() + c2.getB());
    }

    @Override
    public ComplexInteger multiply(Complex<Integer> c1, Complex<Integer> c2) {
        return new ComplexInteger(c1.getA()*c2.getA() - c1.getB()*c2.getB(), c1.getA()*c2.getB() + c1.getB()*c2.getA());
    }

    @Override
    public double argument() {
        return Math.atan(this.getB() / this.getA());
    }

    @Override
    public double magnitude() {
        return Math.sqrt(this.getA()*this.getA() + this.getB()*this.getB());
    }
}


class ComplexFraction extends Complex<Fraction> {
    public ComplexFraction(Fraction a, Fraction b) {
        super(a, b);
    }

    @Override
    public String toString() {
        String sign = (this.getB().isPositive()) ? " + " : " - ";
        return (this.getA() + sign + this.getB() + "i");
    }

    @Override
    public ComplexFraction add(Complex<Fraction> c1, Complex<Fraction> c2) {
        return new ComplexFraction(
            Fraction.add(c1.getA(), c2.getA()),
            Fraction.add(c1.getB(), c2.getB())
        );
    }

    @Override
    public ComplexFraction multiply(Complex<Fraction> c1, Complex<Fraction> c2) {
        return new ComplexFraction(
            Fraction.subtract(Fraction.multiply(c1.getA(), c2.getA()), Fraction.multiply(c1.getB(), c2.getB())),
            Fraction.add(Fraction.multiply(c1.getA(), c2.getB()), Fraction.multiply(c1.getB(), c2.getA()))
        );
    }

    @Override
    public double argument() {
        return Math.atan(Fraction.divide(this.getB(), this.getA()).abs());
    }

    @Override
    public double magnitude() {
        return Math.sqrt(Fraction.add(Fraction.multiply(this.getA(), this.getA()), Fraction.multiply(this.getB(), this.getB())).abs());
    }
}


public class Question2_2021529 {
    public static void main(String[] args) {
        Fraction f1 = new Fraction(2, 3);
        Fraction f2 = new Fraction(1, 4);

        Complex<Integer> c1 = new ComplexInteger(2, 3);
        Complex<Integer> c2 = new ComplexInteger(1, 2);

        Fraction f3 = new Fraction(1, 3);
        Fraction f4 = new Fraction(4, 5);
        Fraction f5 = new Fraction(1, 2);

        Complex<Fraction> c3 = new ComplexFraction(f3, f4);
        Complex<Fraction> c4 = new ComplexFraction(f5, f5);

        System.out.println("Fraction 1: " + f1);
        System.out.println("Fraction 2: " + f2);
        System.out.println("Fraction 1 + Fraction 2: " + Fraction.add(f1, f2));;
        System.out.println("Fraction 1 - Fraction 2: " + Fraction.subtract(f1, f2));;
        System.out.println("Fraction 1 * Fraction 2: " + Fraction.multiply(f1, f2));;
        System.out.println("Fraction 1 / Fraction 2: " + Fraction.divide(f1, f2));;

        System.out.println();

        System.out.println("Complex Integer 1: " + c1);
        System.out.println("Complex Integer 2: " + c2);
        System.out.println("Complex Integer 1 + Complex Integer 2: " + c1.add(c1, c2));
        System.out.println("Complex Integer 1 * Complex Integer 2: " + c1.multiply(c1, c2));
        System.out.println("Argument of Complex Integer 1: " + c1.argument());
        System.out.println("Magnitude of Complex Integer 1: " + c1.magnitude());

        System.out.println();

        System.out.println("Complex Fraction 1: " + c3);
        System.out.println("Complex Fraction 2: " + c4);
        System.out.println("Complex Fraction 1 + Complex Fraction 2: " + c3.add(c3, c4));
        System.out.println("Complex Fraction 1 * Complex Fraction 2: " + c3.multiply(c3, c4));
        System.out.println("Argument of Complex Fraction 1: " + c3.argument());
        System.out.println("Magnitude of Complex Fraction 1: " + c3.magnitude());
    }
}