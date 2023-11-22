import java.util.*;

public class Main {
    public static void main(String[] args) {
        Graf romanyaHaritasi = new Graf();
        romanyaHaritasi.addEdge("Arad", "Zerind", 75);
        romanyaHaritasi.addEdge("Arad", "Sibiu", 140);
        romanyaHaritasi.addEdge("Zerind", "Oradea", 71);
        romanyaHaritasi.addEdge("Sibiu", "Fagaras", 99);
        romanyaHaritasi.addEdge("Sibiu", "Rimnicu Vilcea", 80);
        romanyaHaritasi.addEdge("Oradea", "Sibiu", 151);
        romanyaHaritasi.addEdge("Fagaras", "Bucharest", 211);
        romanyaHaritasi.addEdge("Rimnicu Vilcea", "Pitesti", 97);
        romanyaHaritasi.addEdge("Rimnicu Vilcea", "Craiova", 146);
        romanyaHaritasi.addEdge("Craiova", "Drobeta", 120);
        romanyaHaritasi.addEdge("Drobeta", "Mehadia", 75);
        romanyaHaritasi.addEdge("Pitesti", "Bucharest", 101);
        romanyaHaritasi.addEdge("Craiova", "Pitesti", 138);
        romanyaHaritasi.addEdge("Mehadia", "Lugoj", 70);
        romanyaHaritasi.addEdge("Lugoj", "Timisoara", 111);
        romanyaHaritasi.addEdge("Timisoara", "Arad", 118);

        String hedefSehir = "Bucharest";

        System.out.println("BFS:");
        bfs(romanyaHaritasi, "Arad", hedefSehir);

        System.out.println("\nDFS:");
        dfs(romanyaHaritasi, "Arad", hedefSehir, new HashSet<>());

        System.out.println("\nIDS:");
        ids(romanyaHaritasi, "Arad", hedefSehir);

        System.out.println("\nDLS:");
        dls(romanyaHaritasi, "Arad", hedefSehir, 3);
    }

    private static void bfs(Graf graf, String baslangic, String hedef) {
        Queue<NodeWithDistance> kuyruk = new LinkedList<>();
        Set<String> ziyaretEdilen = new HashSet<>();

        kuyruk.add(new NodeWithDistance(baslangic, 0));
        ziyaretEdilen.add(baslangic);

        while (!kuyruk.isEmpty()) {
            NodeWithDistance mevcut = kuyruk.poll();

            System.out.print(mevcut.dugum + " ");

            if (mevcut.dugum.equals(hedef)) {
                System.out.println("\nHedef bulundu! Toplam Mesafe: " + mevcut.mesafe);
                return;
            }

            for (Map.Entry<String, Integer> komsu : graf.komsulariAl(mevcut.dugum).entrySet()) {
                String komsuSehir = komsu.getKey();
                int komsuMesafe = komsu.getValue();
                if (!ziyaretEdilen.contains(komsuSehir)) {
                    kuyruk.add(new NodeWithDistance(komsuSehir, mevcut.mesafe + komsuMesafe));
                    ziyaretEdilen.add(komsuSehir);
                }
            }
        }

        System.out.println("\nHedef bulunamadı!");
    }


    private static void dfs(Graf graf, String mevcut, String hedef, Set<String> ziyaretEdilen) {
        dfsYardimci(graf, mevcut, hedef, ziyaretEdilen, 0);
    }

    private static void dfsYardimci(Graf graf, String mevcut, String hedef, Set<String> ziyaretEdilen, int mesafe) {
        System.out.print(mevcut + " ");

        if (mevcut.equals(hedef)) {
            System.out.println("\nHedef bulundu! Toplam Mesafe: " + mesafe);
            return;
        }

        ziyaretEdilen.add(mevcut);

        for (Map.Entry<String, Integer> komsu : graf.komsulariAl(mevcut).entrySet()) {
            String komsuSehir = komsu.getKey();
            int komsuMesafe = komsu.getValue();
            if (!ziyaretEdilen.contains(komsuSehir)) {
                dfsYardimci(graf, komsuSehir, hedef, ziyaretEdilen, mesafe + komsuMesafe);
            }
        }
    }


    private static void ids(Graf graf, String baslangic, String hedef) {
        int derinlik = 0;
        while (true) {
            System.out.println("Derinlik: " + derinlik);
            if (dls(graf, baslangic, hedef, derinlik)) {
                System.out.println("Hedef bulundu!");
                return;
            }
            derinlik++;
        }
    }


    private static boolean dls(Graf graf, String mevcut, String hedef, int derinlikSiniri) {
        return dlsYardimci(graf, mevcut, hedef, derinlikSiniri, 0);
    }

    private static boolean dlsYardimci(Graf graf, String mevcut, String hedef, int derinlikSiniri, int mesafe) {
        if (derinlikSiniri < 0) {
            return false;
        }

        System.out.print(mevcut + " ");

        if (mevcut.equals(hedef)) {
            System.out.println("\nHedef bulundu! Toplam Mesafe: " + mesafe);
            return true;
        }

        for (Map.Entry<String, Integer> komsu : graf.komsulariAl(mevcut).entrySet()) {
            String komsuSehir = komsu.getKey();
            int komsuMesafe = komsu.getValue();
            if (dlsYardimci(graf, komsuSehir, hedef, derinlikSiniri - 1, mesafe + komsuMesafe)) return true;
        }

        return false;
    }
}


