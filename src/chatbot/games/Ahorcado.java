package chatbot.games;

import java.util.Scanner;

public class Ahorcado {
	
	private static final int intentosMax = 8; //Cambiar, a lo mejor hacerlo funcion del tamaño de la palabra secreta
	public static final int tamanyoMaximoPalabra = 12; //Hacer que el usuario la pueda modificar? 
	private String palabraSecreta;
	private String[] letrasDescubiertas;
	private String[] letrasElegidas;
	private int intentosActuales;
	private int aciertos;
	private int realPalabraLength; //Tamanyo de la palabra sin contar espacios vacios ej: hola bola = 8 en vez de 9
	private boolean seguirJugando;
	private boolean adivinado;
	private Scanner sc;
	
	public Ahorcado(){
		sc = new Scanner(System.in);
	}
	
	public void startGame(){ //Añadir jugadores y tal luego...
		seguirJugando = true;
		while (seguirJugando == true){
			
			newGameSetup();
			
			while(this.intentosActuales < intentosMax && this.aciertos < this.realPalabraLength){
				//System.out.println("Debug: " + palabraSecreta.length() + " , " + aciertos);
				nextRound();
			}
			
			checkWinConditions();
			
			System.out.println("Quieres seguir jugando?");
			String respuesta = getWords();
			if (respuesta.equals("n") || respuesta.equals("no") || respuesta.equals("nop") || respuesta.equals("nope") || respuesta.equals("que te den") || respuesta.equals("que te pires")) { 
				seguirJugando = false; 
				System.out.println("Adios...");
			}
		}
		sc.close(); //Cerramos el scanner al acabar el programa
	}
	
	/*Lleva la logica de que puede hacer el usuario cada ronda y como actua el programa en funcion de lo que el usuario introduce*/
	private void nextRound(){
		System.out.println("Letras descubiertas: ");
		for (int i = 0; i < palabraSecreta.length(); i++){ // Escribe  los --- por cada letra/espacio  _ _ 
			if (letrasDescubiertas[i] == null){
				System.out.print("-");
			}
			else {
				System.out.print(letrasDescubiertas[i]);
			}
		}
		System.out.print("\n");
		boolean eleccion = false;
		while (eleccion == false){ //Puesto aqui sobretodo para poder hacer continues
			System.out.println("Que letra eliges? (!adivinar para adivinarla) ");
			String letraElegida = getLetterOrCommand(); 
			
			if (letraElegida.length() > 1) { //No es una letra es un comando
				String palabraElegida = parseCommands(letraElegida); //Si es un comando como !adivinar, comprobar que comando es..
				if (palabraElegida.equals("")) { //No ha usado el comando !adivinar o ha adivinado vacio
					System.out.println("No reconozco la palabra o comando");
				}
				else {
					if (palabraElegida.equals(palabraSecreta)){
						adivinado = true;
						System.out.println("Muy bien, la has adivinado!");
						checkWinConditions(); //no aumento el numero de intentos por eso, pero gana la partida
					}
					else {
						System.out.println("Lo siento, no era la palabra secreta!");
						intentosActuales++;
						System.out.println("Te quedan " + (intentosMax-intentosActuales) + " intentos.");
					}
				}
			}
			else { //Si es una letra...
				if (usedLetter(letraElegida)){ //Si ya elegimos esta letra vuelve parriba
					System.out.println("Esta letra ya la elegiste, elige otra!"); 
					continue;
				}
				letrasElegidas[intentosActuales] = letraElegida;
				if (palabraSecreta.contains(letraElegida)) {
					System.out.println("letra encontrada");
					String tmpString = palabraSecreta;							//String igual que la palabraSecreta para comprobar sin destruir la secreta
					int posicionEnString;
					while (tmpString.contains(letraElegida)){ 					//pon todas las letras iguales de la palabra secreta en la posicion que toca en la array de descubiertas
						posicionEnString = tmpString.indexOf(letraElegida);
						letrasDescubiertas[posicionEnString] = letraElegida;
						tmpString = tmpString.replaceFirst(letraElegida," "); 	//Elimina la letra de la posicion donde la has contado ya de la string temporal
						aciertos++;												 //cada letra encontrada es un acierto
					}
				}
				else {
					System.out.println("letra no encontrada");
					//Hacer funcion que dibuje el dibujo del ahorcado mas lleno con cada intento
				}
				System.out.print("Letras usadas: "); //Crear funcion para dibujarlas bien
				for (int i = 0; i < letrasElegidas.length; i++){
					if (letrasElegidas[i] ==  null){ break; } //Esto es porque la array se llena de izquierda a derecha, si hay un null los demas a la derecha lo seran tambien
					System.out.print(letrasElegidas[i] + " ");
				}
				System.out.print("\n");
				intentosActuales++; //NOTA: aciertes o falles le suma un intento, a lo mejor cambiar a que solo cuente al fallar?
				System.out.println("Te quedan " + (intentosMax-intentosActuales) + " intentos.");
				eleccion = true;
			}
		}
	}
	
	
	/*Reinicializa las variables para una partida nueva*/
	private void newGameSetup(){
		System.out.println("Juego del ahorcado");
		System.out.println("------------------");
		System.out.println("Por favor introduzca la palabra secreta");
		this.palabraSecreta = getWords().trim();
		this.letrasElegidas = new String[intentosMax];
		this.letrasDescubiertas = new String[palabraSecreta.length()];
		this.intentosActuales = 0;
		this.aciertos = 0;
		this.adivinado = false;
		realPalabraLength = palabraSecreta.length();
		for (int i = 0; i < palabraSecreta.length(); i++){
			if (palabraSecreta.charAt(i) == ' ') {
				letrasDescubiertas[i] = " "; //Cada vacio encontrado resta uno al tamanyo de la palabra a encontrar(muy cutre?)
				realPalabraLength--;
			}
		}
	}
	
	/*Se ejecuta tanto al ganar(!adivinar correcto, numero de palabras adivinadas = tamanyo palabra) como al gastar todos los intentos*/
	private void checkWinConditions(){
		System.out.println("La palabra era: " + palabraSecreta + ".");
		System.out.println("Has adivinado " + aciertos + " letras");
		if (aciertos == realPalabraLength || adivinado == true) {
			System.out.println("Felicidades, has ganado!!");
		}
		else {
			System.out.println("Has perdido...");
		}
		System.out.println("/////////////////////////////////////////");
	}
	
	/*Devuelve un comando (precedido por !) o una letra, en minusculas los dos*/
	private String getLetterOrCommand(){
		String input;
		boolean correctInput = false;
		while (correctInput == false){
			try{
				input = sc.nextLine();
				input = input.trim(); //cargate espacios en blanco antes/despues palabra
				if (input.equals("")){}  //Entra "" el primer bucle, comprobar porqueeeee
				else if (input.charAt(0) == '!'){ //Es un comando
					return input.toLowerCase();
				}
				else if (input.length() == 1){ 			//Mas ya no es una letra
					char letra = input.toLowerCase().charAt(0);
					if ( (letra >= 65 && letra <= 90) || (letra>=97 && letra <= 122) ) { //Tiene que ser letra
						return input.toLowerCase(); //Podria juntar las dos anteriores pero asi es mas claro creo...
					}
				}
				else{
					System.out.println("Letra o comando no reconocido");
				}
			}
			catch (Exception e){
				System.out.println("Letra o comando no reconocida, " + e.toString());
			}
		}
		return "error";
	}
	
	/*Devuelve cierto si la letra ha sido elegida ya*/
	private boolean usedLetter(String letra){
		for (int i = 0; i < letrasElegidas.length; i++){
			if (letrasElegidas[i] != null){
				if (letrasElegidas[i].equals(letra)){
					return true;
				}
			}
		}
		return false;
	}
	/*Los comandos vienen en lowercase.. por ahora solo comando adivinar*/
	private String parseCommands(String comando){
		if (comando.equals("!adivino") || comando.equals("!adivinar")){
			System.out.print("Que palabra es? ");
			String adivina = getWords();
			return adivina;
		}
		else {
			return "";
		}
	}
	
	/*Mejorar esta, devuelve palabra/palabras en minuscula*/
	private String getWords(){
		String tmpString = "";
			try {
				while (tmpString.equals("")){
				tmpString = sc.nextLine();
				if (tmpString.length() > tamanyoMaximoPalabra ) { 
					System.out.println("Palabra demasiado larga"); 
					tmpString = "";
					}
				}
			}
			catch (Exception e) {
				System.out.print("\nNo lo he entendido: ");
			}
		return tmpString.toLowerCase();
	}
	
	public static void main(String[] args) {
		Ahorcado juego = new Ahorcado();
		juego.startGame();
	}

}
