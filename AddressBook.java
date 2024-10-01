/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package orangehouse.addressbook;

/**
 *
 * @author oscar
 */

   import java.io.*;
   import java.util.*;

public class AddressBook { //Clase AddresBook
    private HashMap<String, String> contacts = new HashMap<>(); //Atributo tipo HashMap<string,string>

    // Método para cargar los contactos desde el archivo
    public void load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    contacts.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    // Método para guardar los contactos en el archivo
    public void save(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    // Método para listar los contactos
    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
    // Método para buscar un contacto por nombre
    public void searchByName(String name) {
        boolean found = false;
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(name)) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No se encontraron contactos con el nombre: " + name);
        }
    }
    // Método para crear un nuevo contacto
    public void create(String number, String name) {
        contacts.put(number, name);
    }

    // Método para eliminar un contacto
    public void delete(String number) {
        contacts.remove(number);
    }

    // Método para el menú interactivo
    public void interactiveMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("Menu:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Buscar contacto por nombre");
            System.out.println("5. Salir");
            System.out.print("Ingrese su eleccion: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consumir la nueva línea pendiente

                switch (choice) {
                case 1:
                    list();
                    break;
                case 2:
                        System.out.print("Ingrese el numero de telefono (10 digitos): ");
                        String number = scanner.nextLine();

                        // Validación de 10 dígitos
                        if (!number.matches("\\d{10}")) {
                            throw new IllegalArgumentException("El numero de telefono debe contener exactamente 10 digitos.");
                        }

                        System.out.print("Ingrese el nombre: ");
                        String name = scanner.nextLine();
                        create(number, name);
                        break;
                case 3:
                    System.out.print("Ingrese el numero de telefono a eliminar: ");
                    number = scanner.nextLine();
                    delete(number);
                    break;
                case 4:
                    System.out.print("Ingrese el nombre a buscar: ");
                    String searchName = scanner.nextLine();
                    searchByName(searchName);
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Eleccion invalida. Intente nuevamente.");
            }
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese una opcion dentro del Menu.");
                scanner.nextLine(); // Limpiar el buffer de entrada   

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 5);
    }


    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        addressBook.load("contacts.txt"); // Carga los contactos al iniciar
        addressBook.interactiveMenu();
        addressBook.save("contacts.txt"); // Guarda los contactos al salir
    }
}

