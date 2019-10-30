package util;

public enum Alfabeto {
    
ESP("ABCDEFGHIJKLMNÑOPQRSTUVWXYZ ÁÉÍÓÚ.,:0123456789¿?!¡\""),
ENG("ABCDEFGHIJKLMNOPQRSTUVWXYZ.,:0123456789¿?!¡");

private final String alfabeto;

private Alfabeto(String alfabeto){
    this.alfabeto = alfabeto;
}

@Override
public String toString(){
    return alfabeto;
}












}
