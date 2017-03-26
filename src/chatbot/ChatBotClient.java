

package chatbot;

import java.util.Scanner;


public class ChatBotClient {

    
    public static void main(String[] args) {
        
        String entrada="FalloEntrada",salida="FalloSalida";
        
        System.out.println("Bienvenid@ a Chat el Bot, escribe a continuación lo que quieras.");
        System.out.println("");
        
        Scanner sc= new Scanner(System.in);
        entrada=sc.next().toLowerCase();
        
        if(entrada.equals("hola")){
            salida=entrada;
            System.out.println(salida);
        }
        else if (entrada.equals("ahorcado")){
        	System.out.println("BEEP BOOP, INICIANDO EL JUEGO DEL AHORCADO ---- ALPHA VERSION"); //Cambiar cuando el juego este testeado... ^_^
        	chatbot.games.Ahorcado juegoAhorcado = new chatbot.games.Ahorcado();
        	juegoAhorcado.startGame();
        }
        else{
            salida="Empezare saludando yo";
            System.out.println(salida+". Hola,mi nombre es Chat Bot");
        }
        
    }
    
}
