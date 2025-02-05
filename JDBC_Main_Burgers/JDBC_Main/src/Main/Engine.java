package Main;

import java.util.Scanner;

public class Engine {
	// Atributo de cada clase
	private Scanner scn;
	private InventoryManager invMan; // ¿ALOMEJOR HAY QUE PASARLE ALGO POR PARAMETRO?
	private Integer user;

	public Engine() {
		scn = new Scanner(System.in);
		invMan = new InventoryManager();
		user = -1;
	}

	public void run() {

		while (this.user < 0 || this.user >= 5) {
			showMenu();
			// Try catch para asegurarnos que el usuario inserta un numero
			try {
				this.user = scn.nextInt();
				scn.nextLine(); // Consumir el salto de línea pendiente
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("ERROR, input has to be a number from 0 to 5, please TRY AGAIM");
				scn.nextLine(); // Consumir entrada incorrecta para evitar bucle infinito
				pause();
			}

			// SALE DEL BUCLE SI EL NUMERO ESTA EN ENTERE EL 0 Y LAS OPCIONES DE INVENTARIO
			if (this.user < 0 || this.user > 5) {
				System.out.println("WRONG number, input must be between 0 and 5");
				pause();
			} else {
				switch (this.user) {
				case 1: // EN CASO DE IR AL INVENTARIO,EL USUARIO DECIDE QUE HACER AQUI
					this.user = -1; // RESTART VARIABLE
					invenotryLogic();
					break;
				}
			}
		}
	}

	public void invenotryLogic() {
		while (this.user < 0 || this.user < 3) {
			inventoryMenu();
			// TRY CATCH PARA ASEGURARNOS QUE EL USUARIO METE UN NUMERO
			try {
				this.user = this.scn.nextInt();
				this.scn.nextLine(); // ASUMIR EL SALTO DE LINEA
			} catch (Exception e) {
				System.out.println("ERROR, input has to be a number from 0 to 3, please TRY AGAIM");
				this.scn.nextLine(); // CONSUMIR ENTRADA PARA EVITAR UN SALTO DE LINEA
				pause();
			}

			switch (this.user) {
			case 1:
				invMan.getNonExpired();
				pause();
				break;
			case 2:
				invMan.getExpired();
				pause();
				break;
			case 3:
				invMan.getAll();
				pause();
				break;
			case 0:
				this.user = -1; // RESET VARIABLE
				run(); // ALOMEJOR CAMBIAR ESTO XD?
				break;
			}

			this.user = -1; // RESET VARIABLE
		}

	}

	// METODO QUE ENSENYA EL MENU
	public void showMenu() {
		System.out.println("- - - - - - - - WELCOME - - - - - - - - ");
		System.out.println("Please select an option:\n");
		// SI vamos bien de tiempo alomejor añadir empleados?
		System.out.println(
				"1. View inventory\n2. Update product\n3. Place order from supplier\n4. Supplier managment\n5. Statistics\n0. Exit");
	}

	public void inventoryMenu() {
		System.out.println("\n- - - - - - - INVENTORY - - - - - - - ");
		System.out.println("Please select an option:\n");
		System.out.println("1. Non-expired products\n2. Expired productst\n3. List all products\n0. Back to menu"); // PENSAR
	}

	// METODO QUE ESPERA UN ENTER PARA SEGUIR
	public void pause() {
		System.out.println("Please press enter to continue...");
		scn.nextLine();
	}
}
