import java.util.*;
import java.io.*;
import java.text.*;
//comments should say WHY you're doing something instead of just saying WHAT you're doing
public class AptTracker {

	public static void main(String[] args) {
		PQ priorityQueue = new PQ();
		String address;
		String apartmentNumber;
		String city;
		int zipCode;
		int price;
		int squareFootage;
		File file;
		Scanner fileReader;
		Scanner reader = new Scanner(System.in);

		try {//reading in the text file to fill the priority queue with data for testing purposes
			fileReader = new Scanner(new File("apartments.txt"));
			fileReader.nextLine();
			while (fileReader.hasNext()) {
				String[] arr = fileReader.nextLine().split(":");
				address = arr[0];
				apartmentNumber = arr[1];
				city = arr[2];
				zipCode = Integer.parseInt(arr[3]);
				price = Integer.parseInt(arr[4]);
				squareFootage = Integer.parseInt(arr[5]);
				priorityQueue.insertApartment(address, apartmentNumber, city, zipCode, price, squareFootage);
			}
		} catch (IOException e) {
			System.out.println("Error Reading File");
		}
		//priorityQueue.printPQ();

		System.out.println("Please Select An Option: " + "\n" + 
						   "(1) Add an Apartment" + "\n" + 
						   "(2) Update an Apartment" + "\n" + 
						   "(3) Remove a Specific Apartment from Consideration" + "\n" +
						   "(4) Retrieve the Lowest Price Apartment" + "\n" + 
						   "(5) Retrieve the Highest Square Footage Apartment" + "\n" + 
						   "(6) Retrieve the Lowest Price Apartment by City" + "\n" +
						   "(7) Retrieve the Highest Square Footage Apartment by City");
		String response = reader.next();

		if (response.equals("1")) {
			System.out.println("Add an Apartment");
			System.out.println("Street Address?");
			reader.nextLine();
			address = reader.nextLine();

			System.out.println("\n" + "Apartment Number?");
			apartmentNumber = reader.nextLine();

			System.out.println("\n" + "City?");
			city = reader.nextLine();

			System.out.println("\n" + "ZipCode?");
			zipCode = reader.nextInt();

			System.out.println("\n" + "Price?");
			price = reader.nextInt();

			System.out.println("\n" + "Square Footage?");
			squareFootage = reader.nextInt();

			priorityQueue.insertApartment(address, apartmentNumber, city, zipCode, price, squareFootage);
		
		} else if (response.equals("2")) {
			System.out.println("Update an Apartment");
			System.out.println("Street Address?");
			reader.nextLine();
			address = reader.nextLine();

			System.out.println("\n" + "Apartment Number?");
			apartmentNumber = reader.nextLine();

			System.out.println("\n" + "ZipCode?");
			zipCode = reader.nextInt();

			priorityQueue.findApartment(address, apartmentNumber, zipCode);
			System.out.println("Would You Like to Update The Price of This Apartment? Type Y for Yes");
			if (reader.next().equalsIgnoreCase("Y")) {//check end line character
				System.out.println("New Price?");
				price = reader.nextInt();
				//finding the apartment with the given information and upating its price
				priorityQueue.updatePrice(priorityQueue.findApartment(address, apartmentNumber, zipCode), price);
			}

		} else if (response.equals("3")) {
			System.out.println("Remove an Apartment");
			System.out.println("Street Address?");
			reader.nextLine();
			address = reader.nextLine();

			System.out.println("\n" + "Apartment Number?");
			apartmentNumber = reader.nextLine();
			
			System.out.println("\n" + "ZipCode?");
			zipCode = reader.nextInt();
			
			priorityQueue.removeApartment(address, apartmentNumber, zipCode);

		} else if (response.equals("4")) {
			System.out.println("Retrive Lowest Price Apartment");
			priorityQueue.retrieveLowestPrice();

		} else if (response.equals("5")) {
			System.out.println("Retrieve Highest Square Footage Apartment");
			priorityQueue.retrieveHighestSquareFootage();

		} else if (response.equals("6")) {
			System.out.println("Retrieve Lowest Price Apartment By City");
			System.out.println("City?");
			reader.nextLine();
			city = reader.nextLine();
			priorityQueue.retrieveLowestPriceByCity(city);

		} else if (response.equals("7")) {
			System.out.println("Retrieve Highest Square Footage Apartment By City");
			System.out.println("City?");
			reader.nextLine();
			city = reader.nextLine();
			priorityQueue.retrieveHighestSquareFootageByCity(city);
		}
		//priorityQueue.printPQ();
	}
}