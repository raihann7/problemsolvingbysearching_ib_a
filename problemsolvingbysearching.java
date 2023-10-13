import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Location {
    String name;
    int x;
    int y;

    public Location(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}

class GeneticAlgorithm {
    private List<Location> locations;
    private int vehicleCapacity;
    private int maxGenerations;

    public GeneticAlgorithm(List<Location> locations, int vehicleCapacity, int maxGenerations) {
        this.locations = locations;
        this.vehicleCapacity = vehicleCapacity;
        this.maxGenerations = maxGenerations;
    }

    // Fungsi untuk menghitung jarak antara dua lokasi
    private double distance(Location location1, Location location2) {
        int dx = location1.x - location2.x;
        int dy = location1.y - location2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // Inisialisasi populasi awal
    private List<Location> generateRandomSolution() {
        List<Location> randomSolution = new ArrayList<>(locations);
        Collections.shuffle(randomSolution);
        return randomSolution;
    }

    // Evaluasi solusi berdasarkan jarak dan kapasitas
    private double evaluateSolution(List<Location> solution) {
        double totalDistance = 0;
        int totalCapacity = 0;
        Location currentLocation = locations.get(0);

        for (Location location : solution) {
            double distanceToLocation = distance(currentLocation, location);
            totalDistance += distanceToLocation;
            currentLocation = location;
            totalCapacity++;
        }

        if (totalCapacity > vehicleCapacity) {
            totalDistance = Double.MAX_VALUE;
        }

        return totalDistance;
    }

    // Algoritma Genetika
    public List<Location> geneticAlgorithm() {
        List<Location> bestSolution = null;
        double bestDistance = Double.MAX_VALUE;

        for (int generation = 0; generation < maxGenerations; generation++) {
            List<Location> currentSolution = generateRandomSolution();
            double currentDistance = evaluateSolution(currentSolution);

            if (currentDistance < bestDistance) {
                bestSolution = new ArrayList<>(currentSolution);
                bestDistance = currentDistance;
            }
        }

        return bestSolution;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Location> locations = new ArrayList<>();
        locations.add(new Location("Depot", 0, 0));
        locations.add(new Location("Pelanggan 1", 1, 3));
        locations.add(new Location("Pelanggan 2", 4, 5));
        locations.add(new Location("Pelanggan 3", 6, 2));
        locations.add(new Location("Pelanggan 4", 8, 8));

        int vehicleCapacity = 10;  // Kapasitas kendaraan
        int maxGenerations = 100;  // Jumlah generasi

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(locations, vehicleCapacity, maxGenerations);
        List<Location> bestRoute = geneticAlgorithm.geneticAlgorithm();

        System.out.println("Rute Terbaik:");
        for (Location location : bestRoute) {
            System.out.println(location.name);
        }
    }
}
