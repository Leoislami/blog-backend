package ch.hftm.repository;

import java.util.ArrayList;
import java.util.List;

import ch.hftm.entity.Entry;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class EntryRepository implements PanacheRepository<Entry> {

    public List<Entry> entrys = new ArrayList<>();
    
    public List<Entry> getEntrys() {
        return entrys;
    }

    public void addEntry(Entry entry){
        entrys.add(entry);
    }
}

