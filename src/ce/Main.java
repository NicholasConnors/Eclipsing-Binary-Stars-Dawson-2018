package ce;

import java.util.InputMismatchException;
import java.util.Scanner;

import ce.simulation.Config;
import ce.simulation.Simulation;

/**
 * Simulates the motion of a 2-body binary star system.
 * Displays the light curve of the pair when/if they eclipse each other.
 * Different systems can be selected.
 * 
 * 02/04/2018
 * 
 * @author Nicholas Connors
 */

public class Main {
	public static void main(String[] args) {	
		Scanner in = new Scanner(System.in);
		Config[] configs = Config.configurations;
		int selection = 0;
		String yesNo;

		//Display all available presets
		System.out.println("ECLIPSING BINARY COMPREHENSIVE EXAMINATION PROJECT");
		System.out.println("==================================================");
		for(int i = 0; i < configs.length; i++) {
			System.out.println((i + 1) + "\t" + configs[i].name);
		}
		
		while(true) {
			//Input validation loop
			while(true) {
				System.out.print("\nEnter the index of a Binary Star system to simulate: ");
				try {
					selection = in.nextInt();
					
					if(selection > 0 && selection <= configs.length) {
						break;	
					} else {
						System.out.println("ERROR - Invalid index");
					}
				}
				catch(InputMismatchException e) {
					in.nextLine();
					System.out.println("ERROR - Must be a number");
				}	
			}
			
			//Load the selected configuration
			new Simulation(Config.configurations[selection - 1]);
			
			//Input validation loop
			while(true) {
				System.out.print("Continue? Y/N: ");
				yesNo = in.next();
				
				if(yesNo.toLowerCase().equals("y")) {
					System.out.println();
					break;
				} else if(yesNo.toLowerCase().equals("n")) {
					in.close();
					System.exit(0);
				} else {
					System.out.println("ERROR - enter Y or N");
				}
			}
		}
	}
}
