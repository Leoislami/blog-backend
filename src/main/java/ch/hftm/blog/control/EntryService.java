package ch.hftm.blog.control;

import org.jboss.logging.Logger;

import ch.hftm.blog.entity.Entry;
import ch.hftm.blog.exception.EntryNotFoundException;
import ch.hftm.blog.repository.EntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EntryService {
    
    @Inject
    EntryRepository entryRepository;

    @Inject
    Logger logger;

    public List<Entry> getEntrys() {
        var entrys = entryRepository.listAll();
        logger.info("returning " + entrys.size() + " Entrys");
        return entrys; 
    }

    public List<Entry> findEntries(String searchString, int page) {
        var entriesQuery = entryRepository
        .find("title like ?1 or content like ?1", "%" + searchString + "%");
        if(true) {
            entriesQuery = entriesQuery.page(0, 50);
        }
        var entries = entriesQuery.list();
        logger.info("Found" + entries.size() + " entries");
        return entries;
    }

    @Transactional
    public void addEntry(Entry entry) {
        logger.info("Adding Entry " + entry.getTitle());
        entryRepository.persist(entry);
    }

    public Entry getEntry(Long id) {
    Optional<Entry> entry = entryRepository.findByIdOptional(id);
    return entry.orElseThrow(() -> new EntryNotFoundException(id));
    }


    @Transactional
    public void updateEntry(Long id, Entry entry) {
        Entry entryToUpdate = entryRepository.findByIdOptional(id)
                                            .orElseThrow(() -> new EntryNotFoundException(id));
        entryToUpdate.setTitle(entry.getTitle());
        entryToUpdate.setContent(entry.getContent());
        entryToUpdate.setLikes(entry.getLikes());
        logger.info("Updated Entry with ID: " + id);
    }


    @Transactional
    public void patchEntry(Long id, Entry patchEntry) {
        Entry entryToUpdate = entryRepository.findByIdOptional(id)
                                        .orElseThrow(() -> new EntryNotFoundException(id));

        if (patchEntry.getTitle() != null) {
            entryToUpdate.setTitle(patchEntry.getTitle());
    }

        if (patchEntry.getContent() != null) {
            entryToUpdate.setContent(patchEntry.getContent());
    }
    
        if (patchEntry.getLikes() != null) {
            entryToUpdate.setLikes(patchEntry.getLikes());
    }   

        logger.info("Patched Entry with ID: " + id);
}


    @Transactional
    public void deleteEntry(Long id) {
        Entry entryToDelete = entryRepository.findByIdOptional(id)
        .orElseThrow(() -> new EntryNotFoundException(id));
        entryRepository.delete(entryToDelete);
        logger.info("Deleted Entry with ID: " + id);
    }

    
}
