package model;

public class Calculadora {
    public int somar(int a, int b) {
        System.out.println("Somando dois inteiros: " + a + " + " + b);
        return a + b;
    }
    public int somar(int a, int b, int c) {
        System.out.println("Somando três inteiros: " + a + " + " + b + " + " + c);
        return a + b + c;
    }
    public double somar(double a, double b) {
        System.out.println("Somando dois doubles: " + a + " + " + b);
        return a + b;
    }
}
