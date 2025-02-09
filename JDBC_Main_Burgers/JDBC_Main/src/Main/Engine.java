package Main;

import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class Engine {
	// Atributo de cada clase
	private D_InventoryManager invMan; // ¿ALOMEJOR HAY QUE PASARLE ALGO POR PARAMETRO?
	private D_UpdateProduct updtPdct;
	private Integer userOption;
	private D_SupplierManagment suppMan;
	private D_AddProduct addPdct;
	private D_Order order;
	private D_Sell sell;
	private D_Create create;
	private D_Stadistic stats;
	
	public Engine() {
		userOption = -1;
		invMan = new D_InventoryManager();
		updtPdct = new D_UpdateProduct();
		addPdct = new D_AddProduct();
		suppMan = new D_SupplierManagment();
		order = new D_Order();
		sell = new D_Sell();
		stats = new D_Stadistic();
		create = new D_Create();
	}

	public void run() {
		Scanner scn = new Scanner(System.in);
		
		while (this.userOption < 0 || this.userOption > 8) {
			showMenu();
			// Try catch para asegurarnos que el usuario inserta un numero
			 try {
			        this.userOption = scn.nextInt();
			        scn.nextLine(); // Consumir el salto de línea pendiente
			    } catch (Exception e) {
			        System.out.println("ERROR, input has to be a number from 0 to 8, please TRY AGAIN");
			        scn.nextLine(); // Consumir entrada incorrecta para evitar bucle infinito
			        this.userOption = -1; // Asegura que el bucle se repita
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
					this.addLogic();
					break;
				case 3:
					this.userOption = -1;
					updateLogic();
					break;
				case 4:
					this.userOption = -1;
					orderLogic();
					break;
				case 5:
					this.userOption = -1;
					supplierManagmentLogic();
					break;
				case 6: 
					this.userOption = -1;
					stadisticsLogic();
				case 7:
					this.userOption = -1;
					sellLogic();
					break;
				case 8:
					this.userOption = -1;
					createLogic();
				case 0:
					System.out.println("Goodbye!!!!");
					break;
				default:
					System.out.println("ERROR, input has to be a number from 0 to 8, please TRY AGAIN");
				}
		}
	}
	
	public void createLogic() {
		Scanner scn = new Scanner(System.in);
		System.out.println("\n- - - - - - - - CREATE/DELETE - - - - - - - - ");
		System.out.println("Do you want to create or to delete the tables:\n1. Create\n2. Delete\n ");
		int opcion = scn.nextInt();
		create.system32(opcion);
	}
	
	public void orderLogic() {
		Scanner scn = new Scanner(System.in);
		System.out.println("\n- - - - - - - - ORDER - - - - - - - - ");
		boolean realizado = false;
		this.invMan.getAll();
		this.pause();
		while(realizado == false) {
			System.out.println("Please insert the NAME of the product you want to add: ");
			String name = scn.next();
			System.out.println("Please insert the CUANTITY of the product you want to order: ");
			float cantidad = scn.nextFloat();
			realizado = order.setOrder(name, cantidad);
		}
		pause();
	}
	
	
	public void stadisticsLogic() {
		Scanner scn = new Scanner(System.in);
		while(this.userOption < 0 || this.userOption < 6) {
			stadisticsMenu();
			try {
				this.userOption = scn.nextInt();
				scn.nextLine(); // ASUMIR EL SALTO DE LINEA
			} catch (Exception e) {
				System.out.println("ERROR, input has to be a number from 0 to 5, please TRY AGAIN");
				scn.nextLine(); // CONSUMIR ENTRADA PARA EVITAR UN SALTO DE LINEA
				pause();
			}
			
			switch (this.userOption) {
			case 1: 
				stats.expensive();
				pause();
				break;
			case 2:
				stats.cheaper();
				pause();
				break;
			case 3:
				stats.bestSellings();
				pause();
				break;
			case 4:
				stats.totalPrice();
				pause();
				break;
			case 5:
				stats.sellDetails();
				pause();
				break;
			case 0: // EXIT
				this.userOption = -1; // RESET VARIABLE
				run(); // ALOMEJOR CAMBIAR ESTO XD?
				break;
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
	
	public void addLogic() {
		Scanner scn = new Scanner(System.in);
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
		pause();
	}
	
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
				System.out.println("- - - - ACTUAL SUPPLIERS - - - -");
				this.addPdct.getProveedores();
				pause();
				break;
			case 2: 
				System.out.println("- - - - ACTUAL SUPPLIERS - - - -");
				this.addPdct.getProveedores();
				
				this.userOption = -1; // Reset variable
				boolean existSupplier = true; // SE EJECUTA EN EL WHILE, RECOGE UN TRUE O UN FALSE, DEPENDIEDNO EN SI ENCUENTRA EL NOMBRE O NO
				
				// COMPUREBO SI EL NOMBRE EXISTE EN LA BASE DE DATOS GG
				while (existSupplier == true) { // SE BUSCA HASTA QUE SE ENCUENTE UN USUARIO NO EXISTENTE
					System.out.println("\nPlease insert SUPPLIER COMPANY NAME or TYPE exit to go back to the menu");
					supplierName = scn.nextLine();
					existSupplier = suppMan.getExistSupplierName(supplierName.toLowerCase()); // METODO QUE COMPUREBA SI EL NOMBRE EXISTE, ES UN BOOLEANO
					
					if (supplierName.toLowerCase().equals("exit")) { // SI EL USER PONE EXIT SE SALE
						System.out.println("Exiting to the menu...");
						pause();
						return;
					}else if (existSupplier == false){ // SI EL NOMBRE NO EXISTE EN LA BBDD
						System.out.println("SUCCESSFUL NAME, not found in BBDD, proceding to insert SUPPLIER NAME...");
					}else if(existSupplier == true) { // SI ES TRUE ESTO SIGNIFICA QUE EL NOMBRE EXISTE Y NO SE PUEDE ANIADIR
						System.out.println("NAME already Exist in BBDD, please try again");
					}
				}
				
				// LE PIDO EL NOMBRE DEL OWNER DE LA COMANIA
				while (contactName == "") {
					pause();
					System.out.println("\nPlease insert OWNER CONTACT NAME of supplier: " + supplierName);
					
					contactName = scn.nextLine();
					if (contactName.length() > 4) {
						System.out.println("SUCCESSFUL, proceding to insert OWNER CONTACT NAME, into " + supplierName + "...");
					}else {
						System.out.println("INVALID input, OWNER CONTACT NAME has to be lenght more than 4");
						contactName = ""; // RESETEO VARIABLE PARA QUE VUELVA A ENTRAR EN EL BUCLE
					}
				}
				
				// LE PIDO LA DIRECCION DE LA EMPRESA
				while (directionName == "") {
					pause();
					System.out.println("\nPlease insert ADRESS of supplier: " + supplierName);
					
					directionName = scn.next();
					if (directionName.length() > 4) {
						System.out.println("SUCCESSFUL, proceding to insert ADRESS, into " + supplierName + "...");
					}else {
						System.out.println("INVALID input, CONTACT NAME has to be lenght more than 4");
						directionName = ""; // RESETEO VARIABLE PARA QUE VUELVA A ENTRAR EN EL BUCLE
					}
				}
				
				// LE PIDO EL NUMERO DE TELEFONO DEL OWNER/SUPPLIER
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
						System.out.println("SUCCESSFUL, proceding to insert PHONE, into " + supplierName + "...");
					}else {
						System.out.println("INVALID input, HAS TO HAVE has to be lenght more than 9 NUMBERS");
						phoneName = 0; // RESETEO VARIABLE PARA QUE VUELVA A ENTRAR EN EL BUCLE
					}
				}
				
				suppMan.setAddSupplier(supplierName, contactName, directionName, phoneToString); // LE PASAMOS POR PARAMETRO EL NOMBRE, CONTACTO Y DIRECCION
				pause();
				
				break;
			case 3: // EN CASO DE BORRAR EL SUPPLIER
				System.out.println("- - - - ACTUAL SUPPLIERS - - - -");
				this.addPdct.getProveedores();
				
				this.userOption = -1; // Reset variable
				existSupplier = true; // SE EJECUTA EN EL WHILE, RECOGE UN TRUE O UN FALSE, DEPENDIEDNO EN SI ENCUENTRA EL NOMBRE O NO
				
				// COMPUREBO SI EL NOMBRE EXISTE EN LA BASE DE DATOS GG
				while (existSupplier == true) { // SE BUSCA HASTA QUE SE ENCUENTE UN USUARIO EXISTENTE
					System.out.println("\nPlease insert SUPPLIER COMPANY NAME you want to DELETE or TYPE exit to go back to the menu");
					supplierName = scn.nextLine();
					existSupplier = suppMan.getExistSupplierName(supplierName); // METODO QUE COMPUREBA SI EL NOMBRE EXISTE, ES UN BOOLEANO
					
					if (supplierName.toLowerCase().equals("exit")) { // SI EL USER PONE EXIT SE SALE
						System.out.println("Exiting to the menu...");
						pause();
						break;
					}else if (existSupplier == false){ // SI EL NOMBRE NO EXISTE EN LA BBDD
						System.out.println("ERROR, incorrect SUPPLIER NAME, not found in BBDD, please try again...");
					}else if(existSupplier == true) { // SI ES TRUE ESTO SIGNIFICA QUE EL NOMBRE EXISTE Y NO SE PUEDE ANIADIR
						System.out.println("SUCCESFUL SUPPLIER NAME found in BBDD, proceding to DELETE...");
						break;
					}
				}
				
				// YA QUE TENGO EL NOMBRE NECESITO CONSEGUIR EL ID Y BORRAR LA TABLA, PREGUNTAR AL USUARIO SI ESTA SEGURO DE BORRAR
				String comprobation = "";
				System.out.println("\nYou sure you want to delete SUPPLIER and ALL THE DEPENDENCIES: " + supplierName + " y/n");
				while (comprobation == "") {
					comprobation = scn.nextLine();
					if (!comprobation.toLowerCase().equals("y")|| !comprobation.toLowerCase().equals("y")) {
						System.out.println("Please INSERT y or n");
					}else if(comprobation.toLowerCase().equals("y")) {
						suppMan.setDeleteSupplier(supplierName);
					}else {
						System.out.println("Aborting operation...");
					}
				}
				
				break;
			case 4:
				System.out.println("- - - - ACTUAL SUPPLIERS - - - -");
				this.addPdct.getProveedores();
				
				this.userOption = -1; // Reset variable
				existSupplier = true; // SE EJECUTA EN EL WHILE, RECOGE UN TRUE O UN FALSE, DEPENDIEDNO EN SI ENCUENTRA EL NOMBRE O NO
				
				// COMPUREBO SI EL NOMBRE EXISTE EN LA BASE DE DATOS GG
				while (existSupplier == true) { // SE BUSCA HASTA QUE SE ENCUENTE UN USUARIO EXISTENTE
					System.out.println("\nPlease insert SUPPLIER COMPANY NAME you want to UPDATE or TYPE exit to go back to the menu");
					supplierName = scn.nextLine();
					existSupplier = suppMan.getExistSupplierName(supplierName); // METODO QUE COMPUREBA SI EL NOMBRE EXISTE, ES UN BOOLEANO
					
					if (supplierName.toLowerCase().equals("exit")) { // SI EL USER PONE EXIT SE SALE
						System.out.println("Exiting to the menu...");
						pause();
						break;
					}else if (existSupplier == false){ // SI EL NOMBRE NO EXISTE EN LA BBDD
						System.out.println("ERROR, incorrect SUPPLIER NAME, not found in BBDD, please try again...");
					}else if(existSupplier == true) { // SI ES TRUE ESTO SIGNIFICA QUE EL NOMBRE EXISTE Y NO SE PUEDE ANIADIR
						System.out.println("SUCCESFUL SUPPLIER NAME found in BBDD, proceding to UPDATE...");
						break;
					}
				}
				
				// LE PIDO EL NOMBRE DEL OWNER DE LA COMANIA
				while (contactName == "") {
					pause();
					String acctualName = suppMan.getSupplierContactNameById(supplierName);
					System.out.println("\nPlease UPDATE OWNER CONTACT NAME of supplier: " + supplierName);
					System.out.println("Acctual value: " + acctualName);
					
					contactName = scn.nextLine();
					if (contactName.length() > 4) {
						System.out.println("SUCCESSFUL, proceding to UPDATE SUPPLIER NAME, into " + supplierName + "...");
					}else {
						System.out.println("INVALID input, OWNER CONTACT NAME has to be lenght more than 4");
						contactName = ""; // RESETEO VARIABLE PARA QUE VUELVA A ENTRAR EN EL BUCLE
					}
				}
				
				// LE PIDO LA DIRECCION DE LA EMPRESA
				while (directionName == "") {
					pause();
					String acctualDirrection = suppMan.getSupplierAdressById(supplierName);
					System.out.println("\nPlease UPDATE DIRRECTION of supplier: " + supplierName);
					System.out.println("Acctual value: " + acctualDirrection);
					
					System.out.println("\nPlease insert ADRESS of supplier: " + supplierName);
					
					directionName = scn.next();
					if (directionName.length() > 4) {
						System.out.println("SUCCESSFUL, proceding to insert ADRESS, into " + supplierName + "...");
					}else {
						System.out.println("INVALID input, CONTACT NAME has to be lenght more than 4");
						directionName = ""; // RESETEO VARIABLE PARA QUE VUELVA A ENTRAR EN EL BUCLE
					}
				}
				
				// LE PIDO EL NUMERO DE TELEFONO DEL OWNER/SUPPLIER
				while (phoneName == 0) {
					String acctualPhone = suppMan.getSupplierPhoneById(supplierName);
					System.out.println("\nPlease PHONE of supplier: " + supplierName);
					System.out.println("Acctual value: " + acctualPhone);
					
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
						System.out.println("SUCCESSFUL, proceding to insert PHONE, into " + supplierName + "...");
					}else {
						System.out.println("INVALID input, HAS TO HAVE has to be lenght more than 9 NUMBERS");
						phoneName = 0; // RESETEO VARIABLE PARA QUE VUELVA A ENTRAR EN EL BUCLE
					}
				}
				
				suppMan.setUpdateSuppliers(supplierName, contactName, directionName, phoneToString);
				pause();
				break;
			case 0:
				this.userOption = -1;
				System.out.println("Going to the menu...");
				pause();
				return;
			default:
				System.out.println("ERROR, input HAS to be a number from 0 to 3, please TRY AGAIN");
			}
			
			this.userOption = -1; // RESET VARIABLE
		}
	}
	
	public void sellLogic() {
		Scanner scn = new Scanner(System.in);
		scn.useLocale(Locale.US); // Asegura que use el punto como separador decimal
		HashMap<String, Float> engineCart  = new HashMap<String, Float>();

		String category = "";
		while (userOption < 0 || userOption > 3) {
			System.out.println("- - - - - - - - SELL - - - - - - - -");
			System.out.println("Disponible products: ");
			System.out.println("Please select a category which one you want to buy (0...4):\n1. Fruit\n2. Vegetable\n3. Dried fruit\n4. Cart\n0. Exit");
			
			try {
				userOption = scn.nextInt();
			} catch (Exception e) {
				System.out.println("ERROR input category. Has to be a number has to be from 0 to 4, please try again.");
			}
			
			switch (userOption){
			case 1:
				category = "fruta";
				break;
			case 2:
				category = "verdura";
				break;
			case 3:
				category = "fruto_seco";
				break;
			case 4:
				// MOSTRAR EL CARRITO ANTES DE COMPRAR
				this.userOption = -1; // RESETEAMOS LA VARIABLE
				System.out.println("- - - CART - - -");
				for (Entry<String, Float> entry : engineCart.entrySet()) {
		            System.out.println(entry.getKey().toUpperCase() + " x " + entry.getValue() + " € KG");
		        }
				System.out.println("WEIGHING the products...");
				pause();
				sell.purchaseBuy();
				System.out.println("Going back to the menu...");
				pause();
				return;
			case 0:
				this.userOption = -1; // RESETEAMOS LA VARIABLE
				System.out.println("Going back to the menu...");
				pause();
				return;
		}
		
		System.out.println("- - - DISPONIBLE PRODUCTS - - -"); // PRINTEAMOS LOS PRODUCTOS DISPONIBLES DE LOS QUE EL USUARIO DESEEA COMPRAR
		this.sell.getCategory(category);
		
		boolean existProduct = false; // SE INICIALIZA EN FALSE PARA QUE ENTRE EN EL WHILE, DENTRO DE ESTE SI EL PRODUCTO NO EXITSTE SE PONE TRUE
		String productName = "";
		
		// COMPUREBO SI EL NOMBRE del PRODUCTO EXISTE EN LA BASE DE DATOS GG
		while (existProduct == false) { // SE BUSCA HASTA QUE ENCUENTRE UN NOMBRE EXISTENTE
			System.out.println("\nPlease insert the PRODUCT NAME which you want to buy or type EXIT to go back to the menu.");
			productName = scn.next();
			existProduct = sell.getExistProductName(productName.toLowerCase()); // METODO QUE COMPUREBA SI EL NOMBRE EXISTE, ES UN BOOLEANO
			
			if (productName.toLowerCase().equals("exit")) { // SI EL USER PONE EXIT SE SALE
				System.out.println("Exiting to the menu...");
				pause();
				return;
			}else if (productName.toLowerCase().equals("cart")){ // SI DESEA IR AL CARRITO
				System.out.println("Redirecting to the CART...");
				pause();
			}else if (existProduct == false){ // SI EL NOMBRE NO EXISTE EN LA BBDD
				System.out.println("ERROR PRODUCT NAME, not found in BBDD, please try again.");
			}else if(existProduct == true) { // SI ES TRUE ESTO SIGNIFICA QUE EL NOMBRE EXISTE Y NO SE PUEDE ANIADIR
				System.out.println("SUCCESFUL product FOUND.");
			}
		}
		// MAYBE TODO EN UN IF QUE NO SEA IGUAL A UN !CART
		int id_producto = sell.getIdProductByName(productName);
		float price = sell.getPriceById(id_producto);
		float stock = sell.getStockById(id_producto);
		float userBuyed = 0; // LO QUE COMPRA EL USUARIO, SIEMPRE TENDRA MAS VALOR QUE CERO SI ESTE TIENE UN VALOR MENOS QUE EL STOCK
		
			while (userBuyed == 0) {
				System.out.println(""); // Salto de linea
				System.out.println("Product SELECTED "+ productName);
				System.out.println("Price: " + price + " € x KG");
				System.out.println("Acctual stock: " + stock + " KG");
				
				System.out.println("Please introduce the quantity you want to buy");
				try {
					userBuyed = scn.nextFloat();
				} catch (Exception e) {
					System.out.println("ERROR input has to be a number, please introduce a number form 0.01 to" + stock);
				}
				
				// SI ESTE ES MENOR O IGUAL AL NUMERO DE STOCK SERA CORRECTO
				if (userBuyed < stock) { 
					userOption = -1; // REST VARIABLE
					// LOS AÑADIMOS AL AL HASH MAP DE D_SELL;
					// METODO QUE SE ENCARGA DE ELIMINAR EL STOCJ DE LOS ALIMENTOS UNA VEZ COMPRADOS
					sell.setCart(id_producto, userBuyed, (price * userBuyed)); // LE PASO EL ID DEL PRODUCTO EL STOCK QUE HA COMPRADO Y EL SUBTOTAL PARA FACILITARME EL CALCULO EN SELL JEJE GOD
					engineCart.put(productName, (price * userBuyed)); // EL CARRITO
					System.out.println("SUCCESFUL added to CART " + userBuyed + " KG of " + productName + "s whit PRICE: " + (price * userBuyed) + " €");
					break;
				}else { // SI ES MAYOR AL STOCK SE LE AVISA Y SE RESETA LA VARIABLE PARA QUE VUELVA A ENTRAR EN EL BUCLE WHILE
					userOption = -1; // REST VARIABLE
					userBuyed = 0;
					System.out.println("ERROR choosed stock has to be LOWER than " + stock);
					break;
				}
			}
		}
	}

	// METODO QUE ENSENYA EL MENU
	public void showMenu() {
		System.out.println("- - - - - - - - WELCOME - - - - - - - - ");
		System.out.println("Please select an option:\n");
		// SI vamos bien de tiempo alomejor añadir empleados?
		System.out.println(
				"1. View inventory\n2. Add Product\n3. Update product\n4. Place order from supplier\n5. Supplier managment\n6. Statistics\n7. Sell\n8. Create/Delete\n0. Exit");
	}

	public void inventoryMenu() {
		System.out.println("\n- - - - - - - INVENTORY - - - - - - - ");
		System.out.println("Please select an option:\n");
		System.out.println("1. Non-expired products\n2. Expired products\n3. List all products\n0. Back to menu");
	}
	
	public void supplierManagmentMenu() {
		System.out.println("\n- - - - SUPPLIER MANAGMENT - - - - ");
		System.out.println("Please select an option:\n");
		System.out.println("1. List all suppliers\n2. Add new supplier\n3. Delete supplier\n4. Update supplier\n0. Back to menu");
	}
	
	public void stadisticsMenu() {
		System.out.println("\n- - - - - - - STADISTICS - - - - - - - ");
		System.out.println("Please select an option:\n");
		System.out.println("1. More expensive \n2. Cheaper\n3. Best selling\n4. Total inventory\n5. Sale details\n0. Back to menu");
	}

	// METODO QUE ESPERA UN ENTER PARA SEGUIR
	public void pause() {
		Scanner scn = new Scanner(System.in);
		System.out.println("Please press enter to continue...");
		System.out.println(""); // SALTO DE LINEA
		scn.nextLine();
	}
}
