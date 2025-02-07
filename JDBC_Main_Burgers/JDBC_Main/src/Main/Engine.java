package Main;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Locale;

public class Engine {
	// Atributo de cada clase
	private D_InventoryManager invMan; // ¿ALOMEJOR HAY QUE PASARLE ALGO POR PARAMETRO?
	private D_UpdateProduct updtPdct;
	private Integer userOption;
	private D_SupplierManagment suppMan;
	/*private D_AddProduct addPdct;*/
	
	public Engine() {
		invMan = new D_InventoryManager();
		updtPdct = new D_UpdateProduct();
		//addPdct = new D_AddProduct();
		suppMan = new D_SupplierManagment();
		userOption = -1;
	}

	public void run() {
		Scanner scn = new Scanner(System.in);
		
		while (this.userOption < 0 || this.userOption >= 7) {
			showMenu();
			// Try catch para asegurarnos que el usuario inserta un numero
			try {
				this.userOption = scn.nextInt();
				scn.nextLine(); // Consumir el salto de línea pendiente
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("ERROR, input has to be a number from 0 to 7, please TRY AGAIN");
				scn.nextLine(); // Consumir entrada incorrecta para evitar bucle infinito
				pause();
			}

			// SALE DEL BUCLE SI EL NUMERO ESTA EN ENTERE EL 0 Y LAS OPCIONES DE INVENTARIO
				switch (this.userOption) {
				case 1: // EN CASO DE IR AL INVENTARIO,EL USUARIO DECIDE QUE HACER AQUI
					this.userOption = -1; // RESTART VARIABLE
					invenotryLogic(); // METODO QUE HACE ELEGIR AL USUARIO ENTRE OTRO MENU Y LE LLEVA A LA CLASE INVENTORY MANAGER QUE SE ENCARGA DE TODO
					break;
				case 2: // ADD
					this.userOption = -1;
					//this.addLogic();
					break;
				case 3:
					this.userOption = -1;
					updateLogic();
					break;
				case 5:
					this.userOption = -1;
					supplierManagmentLogic();
					break;
				default:
					System.out.println("ERROR, input has to be a number from 0 to 7, please TRY AGAIN");
				}
		}
	}

	public void invenotryLogic() {
		Scanner scn = new Scanner(System.in);
		
		while (this.userOption < 0 || this.userOption < 3) {
			inventoryMenu();
			// TRY CATCH PARA ASEGURARNOS QUE EL USUARIO METE UN NUMERO
			try {
				this.userOption = scn.nextInt();
				scn.nextLine(); // ASUMIR EL SALTO DE LINEA
			} catch (Exception e) {
				System.out.println("ERROR, input has to be a number from 0 to 3, please TRY AGAIN");
				scn.nextLine(); // CONSUMIR ENTRADA PARA EVITAR UN SALTO DE LINEA
				pause();
			}

			switch (this.userOption) {
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
			case 0: // EXIT
				this.userOption = -1; // RESET VARIABLE
				System.out.println("Going to the menu...");
				return; // ALOMEJOR CAMBIAR ESTO XD?
			default:
				System.out.println("ERROR, input has to be a number from 0 to 3, please TRY AGAIN");
			}

			this.userOption = -1; // RESET VARIABLE
		}

	}
	
	/*public void addLogic() {
		boolean encontrado = true;
		System.out.println("\n- - - - - - - - ADD - - - - - - - - ");
		this.invMan.getAll(); // PONER DISPONIBLE PRODUCTS
		this.pause(); //PONER DISPONIBLE PROVEEDORES
		this.addPdct.getProveedores();
		this.pause();
		while ( encontrado == true ) {
			System.out.println("Please insert the NAME of the product you want to add: ");
			String name = scn.next();
			System.out.println("Please insert the Category of the product you want to add (fruta, fruto_seco, verdura): ");
			String category = scn.next();
			System.out.println("Please insert the Id of the supplier you want to add the product: ");
			int id = scn.nextInt();
			//SI LA CATEGORIA NO ES NINGUNA DE LAS SIGUIENTES TENDRAS QUE VOLVER A METER LOS DATOS
			if(category.equalsIgnoreCase("fruto_seco") || category.equalsIgnoreCase("verdura") || category.equalsIgnoreCase("fruta")) {
				//SI SE HA PODIDO AÑADIR EL PRODUCTO ENCONTRDAO PASARA A VALER FALSE, POR LO QUE SALDRA DEL BUCLE
				encontrado = this.addPdct.Add(name, category.toLowerCase(), id);
			}else {
				System.out.println(category + " is not a category");
			}
		}
	}*/
	
	public void updateLogic() {
		Scanner scn = new Scanner(System.in);
		
		System.out.println("\n- - - - - - - - UPDATE - - - - - - - - ");
		String nameInput = "";
		
		while (nameInput == ""){
			System.out.println("---DISPONIBLE PRODUCTS---");
			this.invMan.getAll();
			System.out.println("Please insert the NAME of the product you want to update: ");
			nameInput = scn.nextLine();
			int idProduct = updtPdct.setUpdateProduct(nameInput); // METODO QUE RECIBE POR PARAMETRO UN NOMBRE Y SI ESTA EN LA BBDD DEVUELVE EL ID, SINO SE DEVELE -1
			if (idProduct > 0) { 
				// SI ENCONTRAMOS EL ID ESTE SERA MAS GRANDE QUE 9
				String name = updtPdct.getNameByID(idProduct);
				float price = updtPdct.getPriceByID(idProduct);
				float tempPrice = 0; // VALORES QUE EL USUARIO INSERTARA
				float stock = updtPdct.getStockByID(idProduct);
				float tempStock = 0; // VALORES QUE EL USUARIO INSERTARA
				Date date = updtPdct.getDateByID(idProduct);
				Date tempDate = null; // VALORES QUE EL USUARIO INSERTARA
				
				
				// METODO PARA ASEGURAR QUE EL USUARIO METE UN FLOAT COMO PRECIO Y ES MAYOR A 0
				while (tempPrice == 0) {
				    System.out.println("\n" + name.toUpperCase() + " actual PRICE value:");
				    System.out.println("Price - " + price + " € x KG" );
				    System.out.print("Insert the new PRICE: ");
				    
				    if (scn.hasNextFloat()) {  // Verifica si hay un número flotante válido
				        tempPrice = scn.nextFloat();
				        scn.nextLine(); // Consumir el salto de línea para evitar problemas con nextLine()
				        
				        if (tempPrice <= 0) { 
				            System.out.println("Invalid input. Price has to be positive.");
				            tempPrice = 0; // Resetear tempPrice para continuar en el loop
				            pause();
				        }
				    } else {
				        System.out.println("Invalid input. Please enter a valid number.");
				        scn.next(); // Descarta la entrada no válida
				        pause();
				    }
				}
				
				// METODO PARA ASEGURAR QUE EL USUARIO METE UN FLOAT COMO STOCK Y ES MAYOR A 0
				while (tempStock == 0) {
				    System.out.println("\n" + name.toUpperCase() + " actual STOCK value:");
				    System.out.println("Price - " + stock + " KG" );
				    System.out.print("Insert the new STOCK: ");
				    
				    if (scn.hasNextFloat()) {  // Verifica si hay un número flotante válido
				    	tempStock = scn.nextFloat();
				        scn.nextLine(); // Consumir el salto de línea para evitar problemas con nextLine()
				        
				        if (tempStock <= 0) { 
				            System.out.println("Invalid input. STOCK has to be positive.");
				            tempStock = 0; // Resetear tempPrice para continuar en el loop
				            pause();
				        }
				    } else {
				        System.out.println("Invalid input. Please enter a valid number.");
				        scn.next(); // Descarta la entrada no válida
				        pause();
				    }
				}
				
				// PARA DARLE FORMATO A LA FECHA
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Formato de fecha
				dateFormat.setLenient(false);
				
				// METODO PARA ASEGURAR QUE EL USUARIO METE UN DATE VALIDO
				while (tempDate == null) {
				    System.out.println("\n" + name.toUpperCase() + " actual DATE value:");
				    System.out.println("Stock - " + date);
				    System.out.print("Insert the new date (dd/MM/yyyy): ");

				    String input = scn.nextLine();

				    try {
				    	tempDate = new java.sql.Date(dateFormat.parse(input).getTime());
				    } catch (ParseException e) {
				        System.out.println("Invalid input. Please enter a valid date in format dd/MM/yyyy.");
				        pause();
				    }
				}
				
				// UNA VEZ INTODUCIDOS CORRECTAMENTE LOS DATOS LOS UPDATEAMOS FINALMENTE
				updtPdct.getUpdateProduct(idProduct, tempPrice, tempStock, tempDate);// SI EL NOMBRE EXISTE SE PASA POR PARAMETRO A GET Y LO UPDATEA EN LA BBDD, MAYBE UN IF?
				break; 
			}else {
				System.out.println("Product dosen't exist, please try again.");
				nameInput = ""; // SI DEVUELVE FALSE EL METODO, LA VARIABLE SE RESTABLECE PARA QUE SIGA EN EL WHILE
			}
		}
		
		pause();
		run();
	}
	
	public void supplierManagmentLogic() {
		Scanner scn = new Scanner(System.in);
		String supplierName = "";
		String contactName= "";
		String directionName = "";
		int phoneName = 0;
		String phoneToString = "";
		
		while (this.userOption < 0 || this.userOption < 3) {
			supplierManagmentMenu();
			
			try {
				this.userOption = scn.nextInt();
				scn.nextLine();
			}catch (Exception e) {
				// TODO: handle exception
				System.out.println("ERROR, input HAS to be a number from 0 to 3, pleas TRY AGAIN.");
				scn.nextLine();
				pause();
			}
			
			switch(this.userOption) {
			case 1:
				break;
			case 2:

				this.userOption = -1; // Reset variable
				boolean existSupplier = true; // SE EJECUTA EN EL WHILE, RECOGE UN TRUE O UN FALSE, DEPENDIEDNO EN SI ENCUENTRA EL NOMBRE O NO
				
				while (existSupplier == true) { // SE BUSCA HASTA QUE SE ENCUENTE UN USUARIO NO EXISTENTE
					System.out.println("Please insert SUPPLIER COMPANY NAME or TYPE exit to go back to the menu");
					supplierName = scn.nextLine();
					existSupplier = suppMan.getExistSupplierName(supplierName); // METODO QUE COMPUREBA SI EL NOMBRE EXISTE, ES UN BOOLEANO
					
					if (existSupplier == true) { // SI ES TRUE ESTO SIGNIFICA QUE EL NOMBRE EXISTE Y NO SE PUEDE ANIADIR
						scn.nextLine(); // LIMPIAMOS EL BUFFER
						System.out.println("NAME already Exist in BBDD, please try again");
					}else if (existSupplier == false){ // SI EL NOMBRE NO EXISTE EN LA BBDD
						System.out.println("SUCCESFUL NAME, not found in BBDD, proceding to insert SUPPLIER NAME...");
					}else if(supplierName == "exit") {
						System.out.println("Exiting to the menu...");
						pause();
						break;
					}
				}
				
				while (contactName == "") {
					pause();
					System.out.println("\nPlease insert CONTACT NAME of supplier: " + supplierName);
					
					contactName = scn.nextLine();
					if (contactName.length() > 4) {
						System.out.println("SUCCESFUL, proceding to insert CONTACT NAME, into " + supplierName + "...");
					}else {
						System.out.println("INVALID input, CONTACT NAME has to be lenght more than 4");
						contactName = ""; // RESETEO VARIABLE PARA QUE VUELVA A ENTRAR EN EL BUCLE
					}
				}
				
				while (directionName == "") {
					pause();
					System.out.println("\nPlease insert ADRESS of supplier: " + supplierName);
					
					directionName = scn.next();
					if (directionName.length() > 4) {
						System.out.println("SUCCESFUL, proceding to insert ADRESS, into " + supplierName + "...");
					}else {
						System.out.println("INVALID input, CONTACT NAME has to be lenght more than 4");
						directionName = ""; // RESETEO VARIABLE PARA QUE VUELVA A ENTRAR EN EL BUCLE
					}
				}
				
				while (phoneName == 0) {
					pause();
					System.out.println("\nPlease insert PHONE of supplier: " + supplierName);
					
					try {
						scn.nextLine(); // LIMPIAMOS BUFFER
						phoneName = scn.nextInt();
					}catch (Exception e) {
						// TODO: handle exception
						System.out.println("ERROR, PHONE number has to be a number.");
					}
					
					phoneToString = Integer.toString(phoneName);
					
					if (phoneToString.length() == 9) {
						System.out.println("SUCCESFUL, proceding to insert PHONE, into " + supplierName + "...");
					}else {
						System.out.println("INVALID input, HAS TO HAVE has to be lenght more than 9 NUMBERS");
						phoneName = 0; // RESETEO VARIABLE PARA QUE VUELVA A ENTRAR EN EL BUCLE
					}
				}
				
				suppMan.setAddSupplier(supplierName, contactName, directionName, phoneToString); // LE PASAMOS POR PARAMETRO EL NOMBRE, CONTACTO Y DIRECCION
				pause();
				break;
			case 3:
				break;
			case 0:
				this.userOption = -1;
				System.out.println("Going to the menu...");
				pause();
				break;
			default:
				System.out.println("ERROR, input HAS to be a number from 0 to 3, please TRY AGAIN");
			}
			
			this.userOption = -1; // RESET VARIABLE
		}
	}

	// METODO QUE ENSENYA EL MENU
	public void showMenu() {
		System.out.println("- - - - - - - - WELCOME - - - - - - - - ");
		System.out.println("Please select an option:\n");
		// SI vamos bien de tiempo alomejor añadir empleados?
		System.out.println(
				"1. View inventory\n2. Add Product\n3. Update product\n4. Place order from supplier\n5. Supplier managment\n6. Statistics\n7. Sell\n0. Exit");
	}

	public void inventoryMenu() {
		System.out.println("\n- - - - - - - INVENTORY - - - - - - - ");
		System.out.println("Please select an option:\n");
		System.out.println("1. Non-expired products\n2. Expired products\n3. List all products\n0. Back to menu");
	}
	
	public void supplierManagmentMenu() {
		System.out.println("\n- - - - SUPPLIER MANAGMENT - - - - ");
		System.out.println("Please select an option:\n");
		System.out.println("1. List all suppliers\n2. Add new supplier\n3. Delete supplier\n0. Back to menu");
	}

	// METODO QUE ESPERA UN ENTER PARA SEGUIR
	public void pause() {
		Scanner scn = new Scanner(System.in);
		System.out.println("Please press enter to continue...");
		scn.nextLine();
	}
}
