package ch.hftm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

import ch.hftm.control.EntryService;
import ch.hftm.entity.Entry;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class EntryServiceTest {
    
    @Inject
    EntryService entryService;

    @Test
    void listningAndAddingEntrys() {
        // Arrange
        Entry entry = new Entry("Test Title","Test Content");
        int entryBefore;
        List<Entry> entrys;

        // Act
        entryBefore = entryService.getEntrys().size();
        entryService.addEntry(entry);
        entrys = entryService.getEntrys();

        // Assert
        assertEquals(entryBefore + 1, entrys.size());
        assertEquals(entry.getId(), entrys.get(entrys.size()-1).getId());

    }
}
