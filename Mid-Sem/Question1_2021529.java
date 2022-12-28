import java.util.Scanner;

class Vector {
    private final double x_coord, y_coord, z_coord;

    public Vector(double x, double y, double z) {
        this.x_coord = x;
        this.y_coord = y;
        this.z_coord = z;
    }

    public double getX_coord() {
        return this.x_coord;
    }

    public double getY_coord() {
        return this.y_coord;
    }

    public double getZ_coord() {
        return this.z_coord;
    }

    @Override
    public String toString() {
        return (
            this.x_coord + "i + " +
            this.y_coord + "j + " +
            this.z_coord + "k"
        );
    }

    public static Vector add(Vector v1, Vector v2) {
        return new Vector(
            v1.x_coord + v2.x_coord,
            v1.y_coord + v2.y_coord,
            v1.z_coord + v2.z_coord
        );
    }

    public static double dot(Vector v1, Vector v2) {
        return (
            v1.x_coord * v2.x_coord +
            v1.y_coord * v2.y_coord +
            v1.z_coord * v2.z_coord
        );
    }

    public static Vector cross(Vector v1, Vector v2) {
        return new Vector(
            v1.y_coord*v2.z_coord - v1.z_coord*v2.y_coord,
            v1.z_coord*v2.x_coord - v1.x_coord*v2.z_coord,
            v1.x_coord*v2.y_coord - v1.y_coord*v2.x_coord
        );
    }
}


public class Question1_2021529 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter x-coordinate of first Vector: ");
        double ax = input.nextDouble();
        System.out.print("Enter y-coordinate of first Vector: ");
        double ay = input.nextDouble();
        System.out.print("Enter z-coordinate of first Vector: ");
        double az = input.nextDouble();
        System.out.print("Enter x-coordinate of second Vector: ");
        double bx = input.nextDouble();
        System.out.print("Enter y-coordinate of second Vector: ");
        double by = input.nextDouble();
        System.out.print("Enter z-coordinate of second Vector: ");
        double bz = input.nextDouble();

        Vector a = new Vector(ax, ay, az);
        Vector b = new Vector(bx, by, bz);

        System.out.println("The dot product is: " + Vector.dot(a, b));
        System.out.println("The sum of two vectors is: " + Vector.add(a, b));
        System.out.println("The cross product of two vectors is: " + Vector.cross(a, b));

        input.close();
    }
}