package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

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

    @Test
    @Transactional
    void addEntryTest() {
        // Arrange
        Entry entry = new Entry("Test Title", "Test Content");
        int entriesBefore;
        List<Entry> entries;

        // Act
        entriesBefore = entryService.getEntrys().size();
        entryService.addEntry(entry);
        entries = entryService.getEntrys();

        // Assert
        assertEquals(entriesBefore + 1, entries.size());
        assertEquals(entry.getId(), entries.get(entries.size() - 1).getId());
    }

    @Test
    void getEntryTest() {
        // Arrange
        Entry entry = new Entry("Example Title", "Example Content");
        entryService.addEntry(entry);

        // Act
        Entry retrievedEntry = entryService.getEntry(entry.getId());

        // Assert
        assertEquals(entry.getId(), retrievedEntry.getId());
        assertEquals(entry.getTitle(), retrievedEntry.getTitle());
        assertEquals(entry.getContent(), retrievedEntry.getContent());
    }

    @Test
    @Transactional
    void updateEntryTest() {
        // Arrange
        Entry entry = new Entry("Old Title", "Old Content");
        entryService.addEntry(entry);

        // Act
        entry.setTitle("Updated Title");
        entry.setContent("Updated Content");
        entryService.updateEntry(entry.getId(), entry);
        Entry updatedEntry = entryService.getEntry(entry.getId());

        // Assert
        assertEquals("Updated Title", updatedEntry.getTitle());
        assertEquals("Updated Content", updatedEntry.getContent());
    }

    @Test
    @Transactional
    void deleteEntryTest() {
        // Arrange
        Entry entry = new Entry("Entry to Delete", "Content to Delete");
        entryService.addEntry(entry);
        Long entryId = entry.getId(); // Make sure to get the ID after saving
        int entriesBefore = entryService.getEntrys().size();

        // Act
        entryService.deleteEntry(entryId);
        Entry deletedEntry = null;
        try {
            deletedEntry = entryService.getEntry(entryId);
        } catch (EntryNotFoundException e) {
            // Handle the exception, if the entry is not found, which is expected
        }
        List<Entry> entriesAfter = entryService.getEntrys();

        // Assert
        assertNull(deletedEntry);
        assertEquals(entriesBefore - 1, entriesAfter.size());
    }


    @Test
    @Transactional
    void findEntriesTest() {
        // Arrange
        Entry entry1 = new Entry("First Entry", "First Content");
        Entry entry2 = new Entry("Second Entry", "Second Content");
        entryService.addEntry(entry1);
        entryService.addEntry(entry2);

        // Act
        List<Entry> entries = entryService.findEntries("Second", 0);

        // Assert
        assertTrue(entries.size() >= 1);
        assertTrue(entries.stream().anyMatch(e -> e.getTitle().equals("Second Entry")));
    }
}

