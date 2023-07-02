package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.jboss.logging.Logger;

import ch.hftm.blog.control.EntryService;
import ch.hftm.blog.entity.Entry;
import ch.hftm.blog.exception.EntryNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@QuarkusTest
public class EntryServiceTest {
    
    @Inject
    EntryService entryService;

    @Inject
    Logger logger;

    @Test
    @Transactional
    void addEntryTest() {
        // Arrange
        Entry entry = new Entry("Test Title", "Test Content");
        int entriesBefore;
        List<Entry> entries;

        // Act
        logger.info("Starting addEntryTest...");
        entriesBefore = entryService.getEntrys().size();
        entryService.addEntry(entry);
        entries = entryService.getEntrys();

        // Assert
        logger.info("Verifying results of addEntryTest...");
        assertEquals(entriesBefore + 1, entries.size());
        assertEquals(entry.getId(), entries.get(entries.size() - 1).getId());
        logger.info("Finished addEntryTest.");
    }

    @Test
    void getEntryTest() {
        // Arrange
        Entry entry = new Entry("Example Title", "Example Content");
        logger.info("Starting getEntryTest... Entry to be added: " + entry.toString());
        entryService.addEntry(entry);

        // Act
        logger.info("Retrieving the added entry with ID: " + entry.getId());
        Entry retrievedEntry = entryService.getEntry(entry.getId());

        // Assert
        logger.info("Verifying results of getEntryTest...");
        assertEquals(entry.getId(), retrievedEntry.getId());
        assertEquals(entry.getTitle(), retrievedEntry.getTitle());
        assertEquals(entry.getContent(), retrievedEntry.getContent());
        logger.info("Finished getEntryTest.");
    }


    @Test
    @Transactional
    void updateEntryTest() {
        // Arrange
        Entry entry = new Entry("Old Title", "Old Content");
        logger.info("Starting updateEntryTest... Entry to be added and updated: " + entry.toString());
        entryService.addEntry(entry);

        // Act
        entry.setTitle("Updated Title");
        entry.setContent("Updated Content");
        logger.info("Updating the added entry to: " + entry.toString());
        entryService.updateEntry(entry.getId(), entry);
        Entry updatedEntry = entryService.getEntry(entry.getId());

        // Assert
        logger.info("Verifying results of updateEntryTest...");
        assertEquals("Updated Title", updatedEntry.getTitle());
        assertEquals("Updated Content", updatedEntry.getContent());
        logger.info("Finished updateEntryTest.");
    }

    @Test
    @Transactional
    void deleteEntryTest() {
        // Arrange
        Entry entry = new Entry("Entry to Delete", "Content to Delete");
        logger.info("Starting deleteEntryTest... Entry to be added and deleted: " + entry.toString());
        entryService.addEntry(entry);
        Long entryId = entry.getId(); // Make sure to get the ID after saving
        int entriesBefore = entryService.getEntrys().size();

        // Act
        logger.info("Deleting the entry with ID: " + entryId);
        entryService.deleteEntry(entryId);
        Entry deletedEntry = null;
        try {
            deletedEntry = entryService.getEntry(entryId);
        } catch (EntryNotFoundException e) {
            // Handle the exception, if the entry is not found, which is expected
            logger.warn("The deleted entry was not found, as expected: " + e.getMessage());
        }
        List<Entry> entriesAfter = entryService.getEntrys();

        // Assert
        logger.info("Verifying results of deleteEntryTest...");
        assertNull(deletedEntry);
        assertEquals(entriesBefore - 1, entriesAfter.size());
        logger.info("Finished deleteEntryTest.");
    }


    @Test
    @Transactional
    void findEntriesTest() {
        // Arrange
        Entry entry1 = new Entry("First Entry", "First Content");
        Entry entry2 = new Entry("Second Entry", "Second Content");
        logger.info("Starting findEntriesTest... Entries to be added: " + entry1.toString() + ", " + entry2.toString());
        entryService.addEntry(entry1);
        entryService.addEntry(entry2);

        // Act
        List<Entry> entries = entryService.findEntries("Second", 0);
        logger.info("Entries found: " + entries);

        // Assert
        logger.info("Verifying results of findEntriesTest...");
        assertTrue(entries.size() >= 1);
        assertTrue(entries.stream().anyMatch(e -> e.getTitle().equals("Second Entry")));
        logger.info("Finished findEntriesTest.");
    }

    @Test
    @Transactional
    void patchEntryTest() {
        // Arrange
        Entry entry = new Entry("Original Title", "Original Content");
        logger.info("Starting patchEntryTest... Entry to be added and patched: " + entry.toString());
        entryService.addEntry(entry);
        Long entryId = entry.getId(); // Make sure to get the ID after saving
        Entry patchEntry = new Entry();
        patchEntry.setTitle("Patched Title");
        patchEntry.setContent("Patched Content");
        logger.info("Patch details: " + patchEntry.toString());

        // Act
        entryService.patchEntry(entryId, patchEntry);
        Entry patchedEntry = entryService.getEntry(entryId);

        // Assert
        logger.info("Verifying results of patchEntryTest...");
        assertEquals("Patched Title", patchedEntry.getTitle());
        assertEquals("Patched Content", patchedEntry.getContent());
        logger.info("Finished patchEntryTest.");
    }


}

