package model;

public class Conversor {
    public double converter(double celsius) {
        System.out.println("Convertendo " + celsius + "°C para Fahrenheit...");
        return (celsius * 9/5) + 32;
    }
    public double converter(double quilometros, String unidade) {
        if (unidade.equalsIgnoreCase("milhas")) {
            System.out.println("Convertendo " + quilometros + " km para milhas...");
            return quilometros * 0.621371;
        }
        return quilometros;
    }
    public String converter(String texto) {
        System.out.println("Convertendo \"" + texto + "\" para letras maiúsculas...");
        return texto.toUpperCase();
    }
}