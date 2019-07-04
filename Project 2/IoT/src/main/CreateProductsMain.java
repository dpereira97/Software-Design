package main;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/**
 * Criador de produtos
 * @author fc47806, fc47878, fc47888
 */
public class CreateProductsMain {
	
	private static final String TAB = "\t\t\t   ";
	
	public static void main(String[] args) throws IOException {
		
		Scanner leitor = new Scanner(System.in);
		
		// Step 1 - Product name
		System.out.println("Introduza o nome do produto: ");
		String nameFile = leitor.nextLine();
		
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(nameFile + ".ajproperties"));
	
		fileWriter.write("src.includes = src/");
		fileWriter.newLine();
		fileWriter.flush();
		
		// Step 2 - Product Language
		chooseProductLanguage(leitor, fileWriter);
		
		// Step 3 - Check kind of user (No disability, blind and deaf)
		chooseUserProperty(leitor, fileWriter);
		
		// Step 4 - Air Quality Feature
		chooseAirProductFeature(leitor, fileWriter);
		
		// Step 5 - Temperature Feature
		chooseTemperatureProductFeature(leitor, fileWriter);
		
		// Step 6 - Movement Sensor Feature
		chooseMovementProductFeature(leitor, fileWriter);
		
		// Step 7 - Wearable Button Feature
		chooseButtonProductFeature(leitor, fileWriter);
		
		// Step 8 - Wearable Activity Feature
		chooseActivityProductFeature(leitor, fileWriter);
		
		System.out.println("O seu produto foi construido");
		
		fileWriter.close();
	}

	private static void chooseProductLanguage(Scanner leitor, 
			  BufferedWriter fileWriter) throws IOException {
		
		System.out.println("Introduza a linguagem pretendida (1 ou 2):");
		System.out.println("1. Portugues");
		System.out.println("2. Ingles");
		int opcao = leitor.nextInt();

		fileWriter.write("src.excludes = ");
		
		if (opcao == 1) {
			
			fileWriter.write("src/i18n/en_US.aj,\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/i18n/Messages_en_US.properties");
		}
		
		else if (opcao == 2) {
			
			fileWriter.write("src/i18n/pt_PT.aj,\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/i18n/Messages_pt_PT.properties");
		}

		fileWriter.flush();
	}

	private static void chooseUserProperty(Scanner leitor, 
		   BufferedWriter fileWriter) throws IOException {
		
		System.out.println("Introduza o tipo de utilizador (1, 2 ou 3):");
		System.out.println("1. Sem deficiencia");
		System.out.println("2. Surdo");
		System.out.println("3. Invisual");
		int opcao = leitor.nextInt();
		
		fileWriter.write(",\\");
		fileWriter.newLine();
		
		switch(opcao) {
			case 1:
				fileWriter.write(TAB + "src/output/aspects/,\\");
				fileWriter.newLine();
				fileWriter.write(TAB + "src/output/speech/,\\");
				fileWriter.newLine();
				fileWriter.write(TAB + "src/output/lights/");
			break;
			case 2:
				fileWriter.write(TAB + "src/output/speech/,\\");
				fileWriter.newLine();
				fileWriter.write(TAB + "src/output/aspects/SpeechOutput.aj");
			break;
			case 3:
				fileWriter.write(TAB + "src/output/lights/,\\");
				fileWriter.newLine();
				fileWriter.write(TAB + "src/output/aspects/LightOutput.aj");
			break;
			default:
				System.out.println("Unknown operation...");
		}
		
		fileWriter.flush();
	}

	private static void chooseAirProductFeature(Scanner leitor, 
				BufferedWriter fileWriter) throws IOException {
		
		System.out.println("Deseja que o produto tenha Sensores de Qualidade do Ar (1 ou 2):");
		System.out.println("1. Sim");
		System.out.println("2. Nao");
		int opcao = leitor.nextInt();
		
		if (opcao == 2) {
			fileWriter.write(",\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/airQualityFeature/,\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/main/aspects/airQualityMain.aj");
			fileWriter.flush();
		}
	}
	
	private static void chooseTemperatureProductFeature(Scanner leitor, 
						BufferedWriter fileWriter) throws IOException {
		
		System.out.println("Deseja que o produto tenha Sensores de Temperatura de Ambiente (1 ou 2):");
		System.out.println("1. Sim");
		System.out.println("2. Nao");
		int opcao = leitor.nextInt();
		
		if (opcao == 2) {
			fileWriter.write(",\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/environmentMonitorFeature/,\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/main/aspects/environmentMain.aj");
			fileWriter.flush();
		}
	}

	private static void chooseMovementProductFeature(Scanner leitor, 
					BufferedWriter fileWriter) throws IOException {
		
		System.out.println("Deseja que o produto tenha Sensores de Movimento (1 ou 2):");
		System.out.println("1. Sim");
		System.out.println("2. Nao");
		int opcao = leitor.nextInt();
		
		if (opcao == 2) {
			fileWriter.write(",\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/movementDetectorFeature/,\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/main/aspects/movementMain.aj");
			fileWriter.flush();
		}
	}
	
	private static void chooseButtonProductFeature(Scanner leitor, 
			BufferedWriter fileWriter) throws IOException {

		System.out.println("Deseja que o produto tenha Dispositivo Vestivel com Botao (1 ou 2):");
		System.out.println("1. Sim");
		System.out.println("2. Nao");
		int opcao = leitor.nextInt();
		
		if (opcao == 2) {
			fileWriter.write(",\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/wearableDeviceFeature/button/,\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/main/aspects/wearableButton.aj");
			fileWriter.flush();
		}
	}

	private static void chooseActivityProductFeature(Scanner leitor, 
			BufferedWriter fileWriter) throws IOException {

		System.out.println("Deseja que o produto tenha Dispositivo Vestivel com Sensor de Ritmo Cardiaco (1 ou 2):");
		System.out.println("1. Sim");
		System.out.println("2. Nao");
		int opcao = leitor.nextInt();
		
		if (opcao == 2) {
			fileWriter.write(",\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/wearableDeviceFeature/activity/,\\");
			fileWriter.newLine();
			fileWriter.write(TAB + "src/main/aspects/wearableActivity.aj");
			fileWriter.flush();
		}
	}
}
