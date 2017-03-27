package chatbot.games;

import java.util.Arrays;
import java.util.Scanner;

public class Ahorcado {
	
	private static final int intentosMax = 8; //Cambiar, a lo mejor hacerlo funcion del tamaño de la palabra secreta
	private String palabraSecreta;
	private String[] letrasDescubiertas;
	private String[] letrasElegidas;
	private int intentosActuales;
	private int aciertos;
	private boolean seguirJugando;
	private Scanner sc;
	
	public Ahorcado(){
		sc = new Scanner(System.in);
	}
	
	public void startGame(){ //Añadir numero jugadores y tal luego...
		seguirJugando = true;
		while (seguirJugando == true){
			
			newGameSetup();
			
			while(this.intentosActuales < intentosMax && this.aciertos < this.palabraSecreta.length()){
				System.out.println("Debug: " + palabraSecreta.length() + " , " + aciertos);
				next_round();
			}
			
			check_win_conditions();
		}
		sc.close(); //Cerramos el scanner al acabar el programa
	}
	private void next_round(){
		System.out.println("Letras descubiertas: ");
		for (int i = 0; i < palabraSecreta.length(); i++){ // Escribe  - - - x -_  _ _ 
			if (letrasDescubiertas[i] == null){
				System.out.print("-");
			}
			else {
				System.out.print(letrasDescubiertas[i]);
			}
		}
		System.out.print("\n");
		
		
		System.out.print("Que letra eliges? ");
		//Mejorar esto
		String letraElegida = sc.next(); //Comprobar que no tienes ya la letra...
		letrasElegidas[intentosActuales] = letraElegida;
		if (palabraSecreta.contains(letraElegida)) {
			System.out.println("letra encontrada");
			String tmpString;											//String igual que la palabraSecreta para comprobar sin destruir la secreta
			int posicionEnString;
			tmpString = palabraSecreta;
			while (tmpString.contains(letraElegida)){ 					//pon todas las letras iguales de la palabra secreta en la posicion que toca en la array de descubiertas
				posicionEnString = tmpString.indexOf(letraElegida);
				letrasDescubiertas[posicionEnString] = letraElegida;
				tmpString = tmpString.replaceFirst(letraElegida," "); 	//Elimina la letra de la posicion donde la has contado ya de la string temporal
				aciertos++;												 //cada letra encontrada es un acierto
			}
		}
		else {
			System.out.println("letra no encontrada");
			//Dibujar ahorcado en +1... funcion que cuente numero intentos...
		}
		System.out.println("Letras usadas: " + Arrays.toString(letrasElegidas)); //Crear funcion para dibujarlas bien
		this.intentosActuales++;
		System.out.println("Te quedan " + (intentosMax-intentosActuales) + " intentos.");
	}
	
	
	private void newGameSetup(){
		System.out.println("Juego del ahorcado");
		System.out.println("------------------");
		System.out.println("Por favor introduzca la palabra secreta");
		this.palabraSecreta = getInput().toLowerCase();
		this.letrasElegidas = new String[intentosMax];
		this.letrasDescubiertas = new String[palabraSecreta.length()];
		this.intentosActuales = 0;
		this.aciertos = 0;
	}
	
	/*Se ejecuta tanto al ganar como al gastar todos los intentos*/
	private void check_win_conditions(){
		System.out.println("La palabra era: " + palabraSecreta + ".");
		System.out.println("Has adivinado " + aciertos + " letras");
		if (aciertos == palabraSecreta.length()) {
			System.out.println("Felicidades, has ganado!!");
		}
		else {
			System.out.println("Has perdido...");
		}
		System.out.println("/////////////////////////////////////////");
		//PEDIR AQUI SI QUIERES SEGUIR JUGANDO
	}
	
	/*Mejorar esta*/
	private String getInput(){
		String tmpString = "";
		while (tmpString.equals("")){
			try {
				tmpString = sc.next();
			}
			catch (Exception e) {
				System.out.print("\nNo lo he entendido: ");
			}
		}
		return tmpString;
	}
	
	public static void main(String[] args) {
		Ahorcado juego = new Ahorcado();
		juego.startGame();
	}

}
