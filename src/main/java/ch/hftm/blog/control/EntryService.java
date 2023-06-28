package ch.hftm.blog.control;

import org.jboss.logging.Logger;

import ch.hftm.blog.entity.Entry;
import ch.hftm.blog.repository.EntryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
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

    @Transactional
    public void addEntry(Entry entry) {
        logger.info("Adding Entry " + entry.getTitle());
        entryRepository.persist(entry);
    }

    public Entry getEntry(Long id) {
        Optional<Entry> entry = entryRepository.findByIdOptional(id);
        if (entry.isPresent()) {
            return entry.get();
        } else {
            logger.warn("Entry not found with ID: " + id);
            return null;
        }
    }

    @Transactional
    public void updateEntry(Long id, Entry entry) {
        Optional<Entry> entryToUpdate = entryRepository.findByIdOptional(id);
        if (entryToUpdate.isPresent()) {
            Entry foundEntry = entryToUpdate.get();
            foundEntry.setTitle(entry.getTitle());
            foundEntry.setContent(entry.getContent());
            logger.info("Updated Entry with ID: " + id);
        } else {
            logger.warn("Could not update - Entry not found with ID: " + id);
        }
    }

    @Transactional
    public void deleteEntry(Long id) {
        Optional<Entry> entryToDelete = entryRepository.findByIdOptional(id);
        if (entryToDelete.isPresent()) {
            entryRepository.delete(entryToDelete.get());
            logger.info("Deleted Entry with ID: " + id);
        } else {
            logger.warn("Could not delete - Entry not found with ID: " + id);
        }
    }
}
