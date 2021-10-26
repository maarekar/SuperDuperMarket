package api.users;

import api.DTOZone;
import api.ISuperDuperMarket;

import java.util.*;

public class MarketWithZones {
    private Map<String, ISuperDuperMarket> zones;

    public MarketWithZones(){
        zones = new HashMap<>();
    }

    public synchronized void addZone(String zoneName, ISuperDuperMarket zone){
        zones.put(zoneName, zone);
    }

    public synchronized ISuperDuperMarket getZone(String zone){
        return zones.get(zone);
    }

    public boolean isZoneExists(String zone){
        return zones.containsKey(zone);
    }

    public synchronized List<DTOZone> getAllTheZones(){
        List<DTOZone> zones = new ArrayList<>();

        for (ISuperDuperMarket zone: this.zones.values()){
            zones.add(new DTOZone(zone));
        }

        return zones;
    }
}
