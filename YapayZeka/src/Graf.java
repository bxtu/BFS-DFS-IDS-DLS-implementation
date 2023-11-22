import java.util.HashMap;
import java.util.Map;

class Graf {
    private Map<String, Map<String, Integer>> komsulukListesi;

    public Graf() {
        this.komsulukListesi = new HashMap<>();
    }

    public void addEdge(String kaynak, String hedef, int mesafe) {
        komsulukListesi
                .computeIfAbsent(kaynak, k -> new HashMap<>())
                .put(hedef, mesafe);

        komsulukListesi
                .computeIfAbsent(hedef, k -> new HashMap<>())
                .put(kaynak, mesafe);
    }

    public Map<String, Integer> komsulariAl(String dugum) {
        return komsulukListesi.getOrDefault(dugum, new HashMap<>());
    }
}
