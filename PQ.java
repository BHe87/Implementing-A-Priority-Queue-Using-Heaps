import java.util.*;

public class PQ {

	private Apartment apartment;
	List<Apartment> pq = new ArrayList<>();
	List<Apartment> currentCity = new ArrayList<>();
	Map<String, List> cities = new HashMap<>();
	Map<String, Integer> map = new HashMap<>();

	private class Apartment {
		private String address;
		private String apartmentNumber;
		private String city;
		private int zipCode;
		private int price;
		private int squareFootage;

		//constructor for the apartment object
		private Apartment(String address, String apartmentNumber, String city, int zipCode, int price, int squareFootage) {
			this.address = address;
			this.apartmentNumber = apartmentNumber;
			this.city = city;
			this.zipCode = zipCode;
			this.price = price;
			this.squareFootage = squareFootage;
		}

		public void Apartment() {
		}

		public String getAddress() {
			return address;
		}

		public String getCity() {
			return city;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public int getSquareFootage() {
			return squareFootage;
		}
	}

	private static void heapify(List<Apartment> pq, String priority) {//sorts the array in order of our highest priority in O(log n) runtime
        int n = pq.size();
        for (int k = n/2; k >= 1; k--) {
            sink(pq, k, n, priority);
        }
        while (n > 1) {
            swap(pq, 1, n--);
            sink(pq, 1, n, priority);
        }
    }

    private static void sink(List<Apartment> pq, int k, int n, String priority) {//k is the index of the thing we are trying to sink
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(pq, j, j+1, priority)) {//if left < right
            	j++;//move to right child
            }
			if (!less(pq, k, j, priority)) {//if k >= j we done
            	break;
            } 
           	swap(pq, k, j);
            k = j;
        }
    }

    private static boolean less(List<Apartment> pq, int i, int j, String priority) {//comparing 2 things to maintain heap property
    	if (priority.equals("lowestPrice")) {
    		return pq.get(i-1).getPrice() < pq.get(j-1).getPrice() ? true : false;
    	}
    	if (priority.equals("highestSquareFootage")) {
    		return pq.get(i-1).getSquareFootage() > pq.get(j-1).getSquareFootage() ? true : false;
    	}
    	return false;
    }

    private static void swap(List<Apartment> pq, int k, int j) {//swapping the position of 2 apartments
    	Apartment temp = pq.get(k-1);
		pq.set(k-1, pq.get(j-1));
		pq.set(j-1, temp);
    }

    public void readjustMap() {//after we add/remove from the pq the indices will change so we need to get the new ones to map to.
		map = new HashMap<>();
		for (Apartment apartment : pq) {
			map.put(apartment.getAddress(), pq.indexOf(apartment));
		}
	}

	public void insertApartment(String address, String apartmentNumber, String city, int zipCode, int price, int squareFootage) {
		apartment = new Apartment(address, apartmentNumber, city, zipCode, price, squareFootage);
		pq.add(apartment);
		map.put(address, pq.indexOf(apartment));
		heapify(pq, "lowestPrice");
		readjustMap();
		printApartment(apartment);
		organizeByCity(city, apartment);
	}

	public void organizeByCity(String city, Apartment apartment) {
		if (cities.containsKey(city)) {//if another apartment shares a city with this apartment
			currentCity = cities.get(city);//get the priority queue corresponding to that city
			currentCity.add(apartment);//add this apartment to the priority queue
			heapify(currentCity, "lowestPrice");
		} else {//if the apartment is in a new city
			cities.put(city, new ArrayList<Apartment>());//make a new priority queue for this city
			currentCity = cities.get(city);
			currentCity.add(apartment);//add this apartment to the newly created priority queue
			heapify(currentCity, "lowestPrice");
		}
		
	}
	public Apartment findApartment(String address, String apartmentNumber, int zipCode) {
		int index = map.get(address);//apartment location in the priority queue
		try {//to catch the case when we try to find an apartment that's not there
			apartment = pq.get(index);
			printApartment(apartment);
			return apartment;
		} catch (NullPointerException e) {
			System.out.println("Apartment Not Found");
			e.printStackTrace();
		}
		return null;
	}

	public void removeApartment(String address, String apartmentNumber, int zipCode) {//highest priority is at the start of the array
		int index = map.get(address);
		try {//to catch the case when we try to find an apartment that's not there
			apartment = pq.get(index);
			pq.remove(index);
			heapify(pq, "lowestPrice");
			readjustMap();
			printApartment(apartment);
		} catch (NullPointerException e) {//if we try to remove an apartment that isn't there
			System.out.println("Apartment Not Found");
			e.printStackTrace();
		}
	}

	public Apartment retrieveLowestPrice() {//priority here is lowest price so we sort again in case priority was somwthing different before
		heapify(pq, "lowestPrice");
		apartment = pq.get(0);
		printApartment(apartment);
		return apartment;
	}

	public Apartment retrieveHighestSquareFootage() {//priority here is highest square footage so we sort again in case priority was somwthing different before
		heapify(pq, "highestSquareFootage");
		apartment = pq.get(0);
		printApartment(apartment);
		return apartment;
	}

	public Apartment retrieveLowestPriceByCity(String city) {
		try {
			currentCity = cities.get(city);//finding the priority queue corresponding to the city
			heapify(currentCity, "lowestPrice");
			apartment = currentCity.get(0);//O(1) runtime
			printApartment(apartment);
			return apartment;
		} catch (NullPointerException e) { 
			System.out.println("No Apartment In That City");
			e.printStackTrace();
		}
		return null;
	}

	public Apartment retrieveHighestSquareFootageByCity(String city) {
		try {
			currentCity = cities.get(city);//finding the priority queue corresponding to the city
			heapify(currentCity, "highestSquareFootage");
			apartment = currentCity.get(0);//O(1) runtime
			printApartment(apartment);
			return apartment;
		} catch (NullPointerException e) {
			System.out.println("No Apartment In That City");
			e.printStackTrace();
		}
		return null;
	}

	public void updatePrice(Apartment apartment, int price) {
		apartment.setPrice(price);
		heapify(pq, "lowestPrice");
		readjustMap();
		printApartment(apartment);
	}

	public void printApartment(Apartment apartment) {//printing the apartment for visualization
		System.out.println("\n" + "Apartment: " + "\n" + 
								  "Address: " + apartment.address + "\n" +
								  "Apartment Number: " + apartment.apartmentNumber + "\n" +
								  "City: " + apartment.city + "\n" +
								  "ZipCode: " + apartment.zipCode + "\n" +
								  "Price: " + apartment.price + "\n" +
								  "Square Footage: " + apartment.squareFootage + "\n");
	}

	public void printPQ() {//printing the priority queue for visualization
		System.out.println("-------------------------------");
		System.out.println("Printing Priority Queue");
		System.out.println("-------------------------------");
		for (Apartment apartment: pq) {
			printApartment(apartment);
		}
	}
}