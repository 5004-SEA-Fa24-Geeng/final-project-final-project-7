package model.sorter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.player.Batter;
import model.player.Pitcher;

public class PlayerSorterTest {
    
    private PlayerSorter sorter;
    private List<Batter> batters;
    private List<Pitcher> pitchers;
    
    @BeforeEach
    public void setUp() {
        sorter = new PlayerSorter();
        
        // Create a list of test batters with different values for all attributes
        batters = new ArrayList<>();
        
        // Batter 1: "A Player" - Lower values for all numeric fields
        batters.add(new Batter(
            "A Player",           // name (alphabetically first)
            100, 30, 15, 10, 2, 3, // fastball stats (PA, H, 1B, 2B, 3B, HR)
            80, 20, 10, 7, 1, 2,   // breaking stats
            50, 15, 8, 5, 1, 1,    // offspeed stats
            230, 65, 33, 22, 4, 6, // total stats
            0.75, 0.80,           // zone swing, zone contact
            0.30, 0.65,           // chase swing, chase contact
            0.283, 0.350, 0.820   // AVG, OBP, OPS
        ));
        
        // Batter 2: "B Player" - Higher values for all numeric fields
        batters.add(new Batter(
            "B Player",           // name (alphabetically second)
            110, 40, 20, 12, 3, 5, // fastball stats
            90, 30, 15, 9, 2, 4,   // breaking stats
            60, 25, 15, 7, 2, 3,   // offspeed stats
            260, 95, 50, 28, 7, 12, // total stats
            0.85, 0.90,           // zone swing, zone contact
            0.40, 0.75,           // chase swing, chase contact
            0.342, 0.450, 1.100   // AVG, OBP, OPS
        ));

        // Create a list of test pitchers with different values for all attributes
        pitchers = new ArrayList<>();
        
        // Pitcher 1: "A Pitcher" - Lower values for most fields
        pitchers.add(new Pitcher(
            "A Pitcher",         // name (alphabetically first)
            1,                   // rotation (1 = starter)
            700, 1000,           // strikes, pitches - lower
            0.70, 0.30,          // strikes rate, balls rate - lower
            0.50, 0.10, 0.05, 0.05, // fastball types - all lower
            0.15, 0.10, 0.00, 0.00, 0.00, // breaking types - all lower
            0.00, 0.05, 0.00, 0.00 // offspeed types - all lower
        ));
        
        // Pitcher 2: "B Pitcher" - Higher values for most fields
        pitchers.add(new Pitcher(
            "B Pitcher",         // name (alphabetically second)
            2,                   // rotation (2 = reliever) - higher
            800, 1100,           // strikes, pitches - higher
            0.73, 0.27,          // strikes rate, balls rate - higher/lower
            0.60, 0.15, 0.10, 0.10, // fastball types - all higher
            0.20, 0.15, 0.05, 0.05, 0.05, // breaking types - all higher
            0.05, 0.10, 0.05, 0.05 // offspeed types - all higher
        ));
    }
    
    /**
     * Tests sorting by Name (case in switch statement)
     */
    @Test
    public void testSortByBatterName() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Name", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName());
        assertEquals("B Player", sorted.get(1).getName());
        
        // Descending
        comp = sorter.getBatterSortType("Name", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName());
        assertEquals("A Player", sorted.get(1).getName());
    }
    
    /**
     * Tests sorting by FastallPA (case in switch statement)
     * Note: There's a typo in the code "FastallPA" instead of "FastballPA"
     */
    @Test
    public void testSortByFastballPA() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("FastallPA", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 100 PA
        assertEquals("B Player", sorted.get(1).getName()); // 110 PA
        
        // Descending
        comp = sorter.getBatterSortType("FastallPA", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 110 PA
        assertEquals("A Player", sorted.get(1).getName()); // 100 PA
    }
    
    /**
     * Tests sorting by FastballH (case in switch statement)
     */
    @Test
    public void testSortByFastballH() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("FastballH", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 30 H
        assertEquals("B Player", sorted.get(1).getName()); // 40 H
        
        // Descending
        comp = sorter.getBatterSortType("FastballH", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 40 H
        assertEquals("A Player", sorted.get(1).getName()); // 30 H
    }
    
    /**
     * Tests sorting by Fastball1B (case in switch statement)
     */
    @Test
    public void testSortByFastball1B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Fastball1B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 15 1B
        assertEquals("B Player", sorted.get(1).getName()); // 20 1B
        
        // Descending
        comp = sorter.getBatterSortType("Fastball1B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 20 1B
        assertEquals("A Player", sorted.get(1).getName()); // 15 1B
    }
    
    /**
     * Tests sorting by Fastball2B (case in switch statement)
     */
    @Test
    public void testSortByFastball2B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Fastball2B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 10 2B
        assertEquals("B Player", sorted.get(1).getName()); // 12 2B
        
        // Descending
        comp = sorter.getBatterSortType("Fastball2B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 12 2B
        assertEquals("A Player", sorted.get(1).getName()); // 10 2B
    }
    
    /**
     * Tests sorting by Fastball3B (case in switch statement)
     */
    @Test
    public void testSortByFastball3B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Fastball3B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 2 3B
        assertEquals("B Player", sorted.get(1).getName()); // 3 3B
        
        // Descending
        comp = sorter.getBatterSortType("Fastball3B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 3 3B
        assertEquals("A Player", sorted.get(1).getName()); // 2 3B
    }
    
    /**
     * Tests sorting by FastballHR (case in switch statement)
     */
    @Test
    public void testSortByFastballHR() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("FastballHR", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 3 HR
        assertEquals("B Player", sorted.get(1).getName()); // 5 HR
        
        // Descending
        comp = sorter.getBatterSortType("FastballHR", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 5 HR
        assertEquals("A Player", sorted.get(1).getName()); // 3 HR
    }
    
    /**
     * Tests sorting by BreakingPA (case in switch statement)
     */
    @Test
    public void testSortByBreakingPA() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("BreakingPA", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 80 PA
        assertEquals("B Player", sorted.get(1).getName()); // 90 PA
        
        // Descending
        comp = sorter.getBatterSortType("BreakingPA", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 90 PA
        assertEquals("A Player", sorted.get(1).getName()); // 80 PA
    }
    
    /**
     * Tests sorting by BreakingH (case in switch statement)
     */
    @Test
    public void testSortByBreakingH() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("BreakingH", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 20 H
        assertEquals("B Player", sorted.get(1).getName()); // 30 H
        
        // Descending
        comp = sorter.getBatterSortType("BreakingH", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 30 H
        assertEquals("A Player", sorted.get(1).getName()); // 20 H
    }
    
    /**
     * Tests sorting by Breaking1B (case in switch statement)
     */
    @Test
    public void testSortByBreaking1B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Breaking1B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 10 1B
        assertEquals("B Player", sorted.get(1).getName()); // 15 1B
        
        // Descending
        comp = sorter.getBatterSortType("Breaking1B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 15 1B
        assertEquals("A Player", sorted.get(1).getName()); // 10 1B
    }
    
    /**
     * Tests sorting by Breaking2B (case in switch statement)
     */
    @Test
    public void testSortByBreaking2B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Breaking2B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 7 2B
        assertEquals("B Player", sorted.get(1).getName()); // 9 2B
        
        // Descending
        comp = sorter.getBatterSortType("Breaking2B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 9 2B
        assertEquals("A Player", sorted.get(1).getName()); // 7 2B
    }
    
    /**
     * Tests sorting by Breaking3B (case in switch statement)
     */
    @Test
    public void testSortByBreaking3B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Breaking3B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 1 3B
        assertEquals("B Player", sorted.get(1).getName()); // 2 3B
        
        // Descending
        comp = sorter.getBatterSortType("Breaking3B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 2 3B
        assertEquals("A Player", sorted.get(1).getName()); // 1 3B
    }
    
    /**
     * Tests sorting by BreakingHR (case in switch statement)
     */
    @Test
    public void testSortByBreakingHR() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("BreakingHR", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 2 HR
        assertEquals("B Player", sorted.get(1).getName()); // 4 HR
        
        // Descending
        comp = sorter.getBatterSortType("BreakingHR", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 4 HR
        assertEquals("A Player", sorted.get(1).getName()); // 2 HR
    }
    
    /**
     * Tests sorting by OffspeedPA (case in switch statement)
     */
    @Test
    public void testSortByOffspeedPA() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("OffspeedPA", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 50 PA
        assertEquals("B Player", sorted.get(1).getName()); // 60 PA
        
        // Descending
        comp = sorter.getBatterSortType("OffspeedPA", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 60 PA
        assertEquals("A Player", sorted.get(1).getName()); // 50 PA
    }
    
    /**
     * Tests sorting by OffspeedH (case in switch statement)
     */
    @Test
    public void testSortByOffspeedH() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("OffspeedH", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 15 H
        assertEquals("B Player", sorted.get(1).getName()); // 25 H
        
        // Descending
        comp = sorter.getBatterSortType("OffspeedH", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 25 H
        assertEquals("A Player", sorted.get(1).getName()); // 15 H
    }
    
    /**
     * Tests sorting by Offspeed1B (case in switch statement)
     */
    @Test
    public void testSortByOffspeed1B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Offspeed1B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 8 1B
        assertEquals("B Player", sorted.get(1).getName()); // 15 1B
        
        // Descending
        comp = sorter.getBatterSortType("Offspeed1B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 15 1B
        assertEquals("A Player", sorted.get(1).getName()); // 8 1B
    }
    
    /**
     * Tests sorting by Offspeed2B (case in switch statement)
     */
    @Test
    public void testSortByOffspeed2B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Offspeed2B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 5 2B
        assertEquals("B Player", sorted.get(1).getName()); // 7 2B
        
        // Descending
        comp = sorter.getBatterSortType("Offspeed2B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 7 2B
        assertEquals("A Player", sorted.get(1).getName()); // 5 2B
    }
    
    /**
     * Tests sorting by Offspeed3B (case in switch statement)
     */
    @Test
    public void testSortByOffspeed3B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Offspeed3B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 1 3B
        assertEquals("B Player", sorted.get(1).getName()); // 2 3B
        
        // Descending
        comp = sorter.getBatterSortType("Offspeed3B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 2 3B
        assertEquals("A Player", sorted.get(1).getName()); // 1 3B
    }
    
    /**
     * Tests sorting by OffspeedHR (case in switch statement)
     */
    @Test
    public void testSortByOffspeedHR() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("OffspeedHR", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 1 HR
        assertEquals("B Player", sorted.get(1).getName()); // 3 HR
        
        // Descending
        comp = sorter.getBatterSortType("OffspeedHR", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 3 HR
        assertEquals("A Player", sorted.get(1).getName()); // 1 HR
    }
    
    /**
     * Tests sorting by TotalPA (case in switch statement)
     */
    @Test
    public void testSortByTotalPA() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("TotalPA", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 230 PA
        assertEquals("B Player", sorted.get(1).getName()); // 260 PA
        
        // Descending
        comp = sorter.getBatterSortType("TotalPA", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 260 PA
        assertEquals("A Player", sorted.get(1).getName()); // 230 PA
    }
    
    /**
     * Tests sorting by TotalH (case in switch statement)
     */
    @Test
    public void testSortByTotalH() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("TotalH", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 65 H
        assertEquals("B Player", sorted.get(1).getName()); // 95 H
        
        // Descending
        comp = sorter.getBatterSortType("TotalH", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 95 H
        assertEquals("A Player", sorted.get(1).getName()); // 65 H
    }
    
    /**
     * Tests sorting by Total1B (case in switch statement)
     */
    @Test
    public void testSortByTotal1B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Total1B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 33 1B
        assertEquals("B Player", sorted.get(1).getName()); // 50 1B
        
        // Descending
        comp = sorter.getBatterSortType("Total1B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 50 1B
        assertEquals("A Player", sorted.get(1).getName()); // 33 1B
    }
    
    /**
     * Tests sorting by Total2B (case in switch statement)
     */
    @Test
    public void testSortByTotal2B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Total2B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 22 2B
        assertEquals("B Player", sorted.get(1).getName()); // 28 2B
        
        // Descending
        comp = sorter.getBatterSortType("Total2B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 28 2B
        assertEquals("A Player", sorted.get(1).getName()); // 22 2B
    }
    
    /**
     * Tests sorting by Total3B (case in switch statement)
     */
    @Test
    public void testSortByTotal3B() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("Total3B", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 4 3B
        assertEquals("B Player", sorted.get(1).getName()); // 7 3B
        
        // Descending
        comp = sorter.getBatterSortType("Total3B", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 7 3B
        assertEquals("A Player", sorted.get(1).getName()); // 4 3B
    }
    
    /**
     * Tests sorting by TotalHR (case in switch statement)
     */
    @Test
    public void testSortByTotalHR() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("TotalHR", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 6 HR
        assertEquals("B Player", sorted.get(1).getName()); // 12 HR
        
        // Descending
        comp = sorter.getBatterSortType("TotalHR", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 12 HR
        assertEquals("A Player", sorted.get(1).getName()); // 6 HR
    }
    
    /**
     * Tests sorting by ZoneSwing (case in switch statement)
     */
    @Test
    public void testSortByZoneSwing() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("ZoneSwing", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 0.75
        assertEquals("B Player", sorted.get(1).getName()); // 0.85
        
        // Descending
        comp = sorter.getBatterSortType("ZoneSwing", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 0.85
        assertEquals("A Player", sorted.get(1).getName()); // 0.75
    }
    
    /**
     * Tests sorting by ZoneContact (case in switch statement)
     */
    @Test
    public void testSortByZoneContact() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("ZoneContact", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 0.80
        assertEquals("B Player", sorted.get(1).getName()); // 0.90
        
        // Descending
        comp = sorter.getBatterSortType("ZoneContact", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 0.90
        assertEquals("A Player", sorted.get(1).getName()); // 0.80
    }
    
    /**
     * Tests sorting by ChaseSwing (case in switch statement)
     */
    @Test
    public void testSortByChaseSwing() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("ChaseSwing", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 0.30
        assertEquals("B Player", sorted.get(1).getName()); // 0.40
        
        // Descending
        comp = sorter.getBatterSortType("ChaseSwing", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 0.40
        assertEquals("A Player", sorted.get(1).getName()); // 0.30
    }
    
    /**
     * Tests sorting by ChaseContact (case in switch statement)
     */
    @Test
    public void testSortByChaseContact() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("ChaseContact", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 0.65
        assertEquals("B Player", sorted.get(1).getName()); // 0.75
        
        // Descending
        comp = sorter.getBatterSortType("ChaseContact", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 0.75
        assertEquals("A Player", sorted.get(1).getName()); // 0.65
    }
    
    /**
     * Tests sorting by AVG (case in switch statement)
     */
    @Test
    public void testSortByAVG() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("AVG", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 0.283
        assertEquals("B Player", sorted.get(1).getName()); // 0.342
        
        // Descending
        comp = sorter.getBatterSortType("AVG", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 0.342
        assertEquals("A Player", sorted.get(1).getName()); // 0.283
    }
    
    /**
     * Tests sorting by OBP (case in switch statement)
     */
    @Test
    public void testSortByOBP() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("OBP", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 0.350
        assertEquals("B Player", sorted.get(1).getName()); // 0.450
        
        // Descending
        comp = sorter.getBatterSortType("OBP", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 0.450
        assertEquals("A Player", sorted.get(1).getName()); // 0.350
    }
    
    /**
     * Tests sorting by OPS (case in switch statement)
     */
    @Test
    public void testSortByOPS() {
        // Ascending
        Comparator<Batter> comp = sorter.getBatterSortType("OPS", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("A Player", sorted.get(0).getName()); // 0.820
        assertEquals("B Player", sorted.get(1).getName()); // 1.100
        
        // Descending
        comp = sorter.getBatterSortType("OPS", false);
        sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        assertEquals("B Player", sorted.get(0).getName()); // 1.100
        assertEquals("A Player", sorted.get(1).getName()); // 0.820
    }
    
    /**
     * Tests the default case in the switch statement
     */
    @Test
    public void testBatterDefaultCase() {
        // Test with an invalid column name
        Comparator<Batter> comp = sorter.getBatterSortType("InvalidColumn", true);
        List<Batter> sorted = new ArrayList<>(batters);
        Collections.sort(sorted, comp);
        
        // Original order should be maintained since the default comparator returns 0
        assertEquals("A Player", sorted.get(0).getName());
        assertEquals("B Player", sorted.get(1).getName());
    }
    /**
     * Tests sorting by Name (case in switch statement)
     */
    @Test
    public void testSortByPitcherName() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Name", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName());
        assertEquals("B Pitcher", sorted.get(1).getName());
        
        // Descending
        comp = sorter.getPitcherSortType("Name", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName());
        assertEquals("A Pitcher", sorted.get(1).getName());
    }
    
    /**
     * Tests sorting by Rotation (case in switch statement)
     */
    @Test
    public void testSortByRotation() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Rotation", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // Rotation 1 (starter)
        assertEquals("B Pitcher", sorted.get(1).getName()); // Rotation 2 (reliever)
        
        // Descending
        comp = sorter.getPitcherSortType("Rotation", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // Rotation 2 (reliever)
        assertEquals("A Pitcher", sorted.get(1).getName()); // Rotation 1 (starter)
    }
    
    /**
     * Tests sorting by Strikes (case in switch statement)
     */
    @Test
    public void testSortByStrikes() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Strikes", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 700 strikes
        assertEquals("B Pitcher", sorted.get(1).getName()); // 800 strikes
        
        // Descending
        comp = sorter.getPitcherSortType("Strikes", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 800 strikes
        assertEquals("A Pitcher", sorted.get(1).getName()); // 700 strikes
    }
    
    /**
     * Tests sorting by Pitches (case in switch statement)
     */
    @Test
    public void testSortByPitches() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Pitches", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 1000 pitches
        assertEquals("B Pitcher", sorted.get(1).getName()); // 1100 pitches
        
        // Descending
        comp = sorter.getPitcherSortType("Pitches", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 1100 pitches
        assertEquals("A Pitcher", sorted.get(1).getName()); // 1000 pitches
    }
    
    /**
     * Tests sorting by StrikesRate (case in switch statement)
     */
    @Test
    public void testSortByStrikesRate() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("StrikesRate", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.70 strike rate
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.73 strike rate
        
        // Descending
        comp = sorter.getPitcherSortType("StrikesRate", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.73 strike rate
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.70 strike rate
    }
    
    /**
     * Tests sorting by BallsRate (case in switch statement)
     */
    @Test
    public void testSortByBallsRate() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("BallsRate", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.27 ball rate
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.30 ball rate
        
        // Descending
        comp = sorter.getPitcherSortType("BallsRate", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.30 ball rate
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.27 ball rate
    }
    
    /**
     * Tests sorting by 4-SeamFastball (case in switch statement)
     */
    @Test
    public void testSortByFourSeam() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("4-SeamFastball", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.50 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.60 usage
        
        // Descending
        comp = sorter.getPitcherSortType("4-SeamFastball", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.60 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.50 usage
    }
    
    /**
     * Tests sorting by 2-SeamFastball (case in switch statement)
     */
    @Test
    public void testSortByTwoSeam() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("2-SeamFastball", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.10 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.15 usage
        
        // Descending
        comp = sorter.getPitcherSortType("2-SeamFastball", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.15 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.10 usage
    }
    
    /**
     * Tests sorting by Cutter (case in switch statement)
     */
    @Test
    public void testSortByCutter() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Cutter", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.05 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.10 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Cutter", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.10 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.05 usage
    }
    
    /**
     * Tests sorting by Sinker (case in switch statement)
     */
    @Test
    public void testSortBySinker() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Sinker", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.05 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.10 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Sinker", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.10 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.05 usage
    }
    
    /**
     * Tests sorting by Slider (case in switch statement)
     */
    @Test
    public void testSortBySlider() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Slider", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.15 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.20 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Slider", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.20 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.15 usage
    }
    
    /**
     * Tests sorting by Curveball (case in switch statement)
     */
    @Test
    public void testSortByCurveball() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Curveball", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.10 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.15 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Curveball", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.15 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.10 usage
    }
    
    /**
     * Tests sorting by Knuckle (case in switch statement)
     */
    @Test
    public void testSortByKnuckle() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Knuckle", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.00 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.05 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Knuckle", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.05 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.00 usage
    }
    
    /**
     * Tests sorting by Sweeper (case in switch statement)
     */
    @Test
    public void testSortBySweeper() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Sweeper", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.00 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.05 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Sweeper", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.05 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.00 usage
    }
    
    /**
     * Tests sorting by Slurve (case in switch statement)
     */
    @Test
    public void testSortBySlurve() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Slurve", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.00 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.05 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Slurve", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.05 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.00 usage
    }
    
    /**
     * Tests sorting by Split-Finger (case in switch statement)
     */
    @Test
    public void testSortBySplitFinger() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Split-Finger", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.00 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.05 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Split-Finger", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.05 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.00 usage
    }
    
    /**
     * Tests sorting by Changeup (case in switch statement)
     */
    @Test
    public void testSortByChangeup() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Changeup", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.05 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.10 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Changeup", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.10 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.05 usage
    }
    
    /**
     * Tests sorting by Fork (case in switch statement)
     */
    @Test
    public void testSortByFork() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Fork", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.00 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.05 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Fork", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.05 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.00 usage
    }
    
    /**
     * Tests sorting by Screw (case in switch statement)
     */
    @Test
    public void testSortByScrew() {
        // Ascending
        Comparator<Pitcher> comp = sorter.getPitcherSortType("Screw", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("A Pitcher", sorted.get(0).getName()); // 0.00 usage
        assertEquals("B Pitcher", sorted.get(1).getName()); // 0.05 usage
        
        // Descending
        comp = sorter.getPitcherSortType("Screw", false);
        sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        assertEquals("B Pitcher", sorted.get(0).getName()); // 0.05 usage
        assertEquals("A Pitcher", sorted.get(1).getName()); // 0.00 usage
    }
    
    /**
     * Tests the default case in the switch statement
     */
    @Test
    public void testPitcherDefaultCase() {
        // Test with an invalid column name
        Comparator<Pitcher> comp = sorter.getPitcherSortType("InvalidColumn", true);
        List<Pitcher> sorted = new ArrayList<>(pitchers);
        Collections.sort(sorted, comp);
        
        // Original order should be maintained since the default comparator returns 0
        assertEquals("A Pitcher", sorted.get(0).getName());
        assertEquals("B Pitcher", sorted.get(1).getName());
    }
}