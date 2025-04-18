package model.sorter;

import java.util.Comparator;
import model.player.Batter;
import model.player.Pitcher;

public class PlayerSorter implements Sorter{
 
    public PlayerSorter() { };

    /**
     * Use sortOn to decide which sort class to use, and asc to decide which order.
     * @param sortOn batter csv column name
     * @param asc true = asc, false = desc
     * @return Comparator<Batter>
     */
    public Comparator<Batter> getBatterSortType(String sortOn, boolean asc) {
        switch (sortOn) {
            case "Name":
                return asc ? new SortByBatterName() : new SortByBatterNameDesc();
            case "FastallPA":
                return asc ? new SortByFastballPA() : new SortByFastballPADesc();
            case "FastballH":
                return asc ? new SortByFastballH() : new SortByFastballHDesc();
            case "Fastball1B":
                return asc ? new SortByFastball1B() : new SortByFastball1BDesc();
            case "Fastball2B":
                return asc ? new SortByFastball2B() : new SortByFastball2BDesc();
            case "Fastball3B":
                return asc ? new SortByFastball3B() : new SortByFastball3BDesc();
            case "FastballHR":
                return asc ? new SortByFastballHR() : new SortByFastballHRDesc();
            case "BreakingPA":
                return asc ? new SortByBreakingPA() : new SortByBreakingPADesc();
            case "BreakingH":
                return asc ? new SortByBreakingH() : new SortByBreakingHDesc();
            case "Breaking1B":
                return asc ? new SortByBreaking1B() : new SortByBreaking1BDesc();
            case "Breaking2B":
                return asc ? new SortByBreaking2B() : new SortByBreaking2BDesc();
            case "Breaking3B":
                return asc ? new SortByBreaking3B() : new SortByBreaking3BDesc();
            case "BreakingHR":
                return asc ? new SortByBreakingHR() : new SortByBreakingHRDesc();
            case "OffspeedPA":
                return asc ? new SortByOffspeedPA() : new SortByOffspeedPADesc();
            case "OffspeedH":
                return asc ? new SortByOffspeedH() : new SortByOffspeedHDesc();
            case "Offspeed1B":
                return asc ? new SortByOffspeed1B() : new SortByOffspeed1BDesc();
            case "Offspeed2B":
                return asc ? new SortByOffspeed2B() : new SortByOffspeed2BDesc();
            case "Offspeed3B":
                return asc ? new SortByOffspeed3B() : new SortByOffspeed3BDesc();
            case "OffspeedHR":
                return asc ? new SortByOffspeedHR() : new SortByOffspeedHRDesc();
            case "TotalPA":
                return asc ? new SortByTotalPA() : new SortByTotalPADesc();
            case "TotalH":
                return asc ? new SortByTotalH() : new SortByTotalHDesc();
            case "Total1B":
                return asc ? new SortByTotal1B() : new SortByTotal1BDesc();
            case "Total2B":
                return asc ? new SortByTotal2B() : new SortByTotal2BDesc();
            case "Total3B":
                return asc ? new SortByTotal3B() : new SortByTotal3BDesc();        
            case "TotalHR":
                return asc ? new SortByTotalHR() : new SortByTotalHRDesc();
            case "ZoneSwing":
                return asc ? new SortByZoneSwing() : new SortByZoneSwingDesc();
            case "ZoneContact":
                return asc ? new SortByZoneContact() : new SortByZoneContactDesc();
            case "ChaseSwing":
                return asc ? new SortByChaseSwing() : new SortByChaseSwingDesc();
            case "ChaseContact":
                return asc ? new SortByChaseContact() : new SortByChaseContactDesc();
            case "AVG":
                return asc ? new SortByAVG() : new SortByAVGDesc();
            case "OBP":
                return asc ? new SortByOBP() : new SortByOBPDesc();
            case "OPS":
                return asc ? new SortByOPS() : new SortByOPSDesc();                      
            default:
                return ((o1, o2) -> 0);
        }        

    }

    /**
     * Use sortOn to decide which sort class to use, and asc to decide which order.
     * @param sortOn pitcher csv column name
     * @param asc true = asc, false = desc
     * @return Comparator<Pitcher>
     */
    public Comparator<Pitcher> getPitcherSortType(String sortOn, boolean asc) {
        switch (sortOn) {
            case "Name":
                return asc ? new SortByPitcherName() : new SortByPitcherNameDesc();
            case "Rotation":
                return asc ? new SortByRotation() : new SortByRotationDesc();
            case "Strikes":
                return asc ? new SortByStrikes() : new SortByStrikesDesc();
            case "Pitches":
                return asc ? new SortByPitches() : new SortByPitchesDesc();
            case "StrikesRate":
                return asc ? new SortByStrikesRate() : new SortByStrikesRateDesc();
            case "BallsRate":
                return asc ? new SortByBallsRate() : new SortByBallsRateDesc();
            case "4-SeamFastball":
                return asc ? new SortByFourSeam() : new SortByFourSeamDesc();
            case "2-SeamFastball":
                return asc ? new SortByTwoSeam() : new SortByTwoSeamDesc();
            case "Cutter":
                return asc ? new SortByCutter() : new SortByCutterDesc();
            case "Sinker":
                return asc ? new SortBySinker() : new SortBySinkerDesc();
            case "Slider":
                return asc ? new SortBySlider() : new SortBySliderDesc();
            case "Curveball":
                return asc ? new SortByCurveball() : new SortByCurveballDesc();
            case "Knuckle":
                return asc ? new SortByKnuckle() : new SortByKnuckleDesc();
            case "Sweeper":
                return asc ? new SortBySweeper() : new SortBySweeperDesc();
            case "Slurve":
                return asc ? new SortBySlurve() : new SortBySlurveDesc();
            case "Split-Finger":
                return asc ? new SortBySFinger() : new SortBySFingerDesc();
            case "Changeup":
                return asc ? new SortByChangeup() : new SortByChangeupDesc();
            case "Fork":
                return asc ? new SortByFork() : new SortByForkDesc();
            case "Screw":
                return asc ? new SortByScrew() : new SortByScrewDesc();
            default:
                return ((o1, o2) -> 0);
        }        

    }

    /**
     * SortByName asc.
     */
    public static class SortByBatterName implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }
    /**
     * SortByName desc.
     */
    public static class SortByBatterNameDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getName().compareToIgnoreCase(o1.getName());
        }
    } 
    /**
     * SortByFastballPA asc.
     */
    public static class SortByFastballPA implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getFastballPA() - o2.getFastballPA();
        }
    }
    /**
     * SortByFastballPA desc.
     */
    public static class SortByFastballPADesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getFastballPA() - o1.getFastballPA();
        }
    } 
    /**
     * SortByFastballH asc.
     */
    public static class SortByFastballH implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getFastballH() - o2.getFastballH();
        }
    }
    /**
     * SortByFastballH desc.
     */
    public static class SortByFastballHDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getFastballH() - o1.getFastballH();
        }
    } 
    /**
     * SortByFastball1B asc.
     */
    public static class SortByFastball1B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getFastball1B() - o2.getFastball1B();
        }
    }
    /**
     * SortByFastball1B desc.
     */
    public static class SortByFastball1BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getFastball1B() - o1.getFastball1B();
        }
    } 
    /**
     * SortByFastball2B asc.
     */
    public static class SortByFastball2B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getFastball2B() - o2.getFastball2B();
        }
    }
    /**
     * SortByFastball2B desc.
     */
    public static class SortByFastball2BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getFastball2B() - o1.getFastball2B();
        }
    } 
    /**
     * SortByFastball3B asc.
     */
    public static class SortByFastball3B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getFastball3B() - o2.getFastball3B();
        }
    }
    /**
     * SortByFastball3B desc.
     */
    public static class SortByFastball3BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getFastball3B() - o1.getFastball3B();
        }
    }
    /**
     * SortByFastballHR asc.
     */
    public static class SortByFastballHR implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getFastballHR() - o2.getFastballHR();
        }
    }
    /**
     * SortByFastballHR desc.
     */
    public static class SortByFastballHRDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getFastballHR() - o1.getFastballHR();
        }
    }
    /**
     * SortByBreakingPA asc.
     */
    public static class SortByBreakingPA implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getBreakingPA() - o2.getBreakingPA();
        }
    }
    /**
     * SortByBreakingPA desc.
     */
    public static class SortByBreakingPADesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getBreakingPA() - o1.getBreakingPA();
        }
    }
    /**
     * SortByBreakingH asc.
     */
    public static class SortByBreakingH implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getBreakingH() - o2.getBreakingH();
        }
    }
    /**
     * SortByBreakingH desc.
     */
    public static class SortByBreakingHDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getBreakingH() - o1.getBreakingH();
        }
    }
    /**
     * SortByBreaking1B asc.
     */
    public static class SortByBreaking1B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getBreaking1B() - o2.getBreaking1B();
        }
    }
    /**
     * SortByBreaking1B desc.
     */
    public static class SortByBreaking1BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getBreaking1B() - o1.getBreaking1B();
        }
    }
    /**
     * SortByBreaking2B asc.
     */
    public static class SortByBreaking2B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getBreaking2B() - o2.getBreaking2B();
        }
    }
    /**
     * SortByBreaking2B desc.
     */
    public static class SortByBreaking2BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getBreaking2B() - o1.getBreaking2B();
        }
    }
    /**
     * SortByBreaking3B asc.
     */
    public static class SortByBreaking3B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getBreaking3B() - o2.getBreaking3B();
        }
    }
    /**
     * SortByBreaking3B desc.
     */
    public static class SortByBreaking3BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getBreaking3B() - o1.getBreaking3B();
        }
    }
    /**
     * SortByBreakingHR asc.
     */
    public static class SortByBreakingHR implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getBreakingHR() - o2.getBreakingHR();
        }
    }
    /**
     * SortByBreakingHR desc.
     */
    public static class SortByBreakingHRDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getBreakingHR() - o1.getBreakingHR();
        }
    }
    /**
     * SortByOffspeedPA asc.
     */
    public static class SortByOffspeedPA implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getOffspeedPA() - o2.getOffspeedPA();
        }
    }
    /**
     * SortByOffspeedPA desc.
     */
    public static class SortByOffspeedPADesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getOffspeedPA() - o1.getOffspeedPA();
        }
    }
    /**
     * SortByOffspeedH asc.
     */
    public static class SortByOffspeedH implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getOffspeedH() - o2.getOffspeedH();
        }
    }
    /**
     * SortByOffspeedH desc.
     */
    public static class SortByOffspeedHDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getOffspeedH() - o1.getOffspeedH();
        }
    }
    /**
     * SortByOffspeed1B asc.
     */
    public static class SortByOffspeed1B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getOffspeed1B() - o2.getOffspeed1B();
        }
    }
    /**
     * SortByOffspeed1B desc.
     */
    public static class SortByOffspeed1BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getOffspeed1B() - o1.getOffspeed1B();
        }
    }
    /**
     * SortByOffspeed2B asc.
     */
    public static class SortByOffspeed2B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getOffspeed2B() - o2.getOffspeed2B();
        }
    }
    /**
     * SortByOffspeed2B desc.
     */
    public static class SortByOffspeed2BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getOffspeed2B() - o1.getOffspeed2B();
        }
    }
    /**
     * SortByOffspeed3B asc.
     */
    public static class SortByOffspeed3B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getOffspeed3B() - o2.getOffspeed3B();
        }
    }
    /**
     * SortByOffspeed3B desc.
     */
    public static class SortByOffspeed3BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getOffspeed3B() - o1.getOffspeed3B();
        }
    }
    /**
     * SortByOffspeedHR asc.
     */
    public static class SortByOffspeedHR implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getOffspeedHR() - o2.getOffspeedHR();
        }
    }
    /**
     * SortByOffspeedHR desc.
     */
    public static class SortByOffspeedHRDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getOffspeedHR() - o1.getOffspeedHR();
        }
    }
    /**
     * SortByTotalPA asc.
     */
    public static class SortByTotalPA implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getTotalPA() - o2.getTotalPA();
        }
    }
    /**
     * SortByTotalPA desc.
     */
    public static class SortByTotalPADesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getTotalPA() - o1.getTotalPA();
        }
    }
    /**
     * SortByTotalH asc.
     */
    public static class SortByTotalH implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getTotalH() - o2.getTotalH();
        }
    }
    /**
     * SortByTotalH desc.
     */
    public static class SortByTotalHDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getTotalH() - o1.getTotalH();
        }
    }
    /**
     * SortByTotal1B asc.
     */
    public static class SortByTotal1B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getTotal1B() - o2.getTotal1B();
        }
    }
    /**
     * SortByTotal1B desc.
     */
    public static class SortByTotal1BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getTotal1B() - o1.getTotal1B();
        }
    }
    /**
     * SortByTotal2B asc.
     */
    public static class SortByTotal2B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getTotal2B() - o2.getTotal2B();
        }
    }
    /**
     * SortByTotal2B desc.
     */
    public static class SortByTotal2BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getTotal2B() - o1.getTotal2B();
        }
    }
    /**
     * SortByTotal3B asc.
     */
    public static class SortByTotal3B implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getTotal3B() - o2.getTotal3B();
        }
    }
    /**
     * SortByTotal3B desc.
     */
    public static class SortByTotal3BDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getTotal3B() - o1.getTotal3B();
        }
    }
    /**
     * SortByTotalHR asc.
     */
    public static class SortByTotalHR implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o1.getTotalHR() - o2.getTotalHR();
        }
    }
    /**
     * SortByTotalHR desc.
     */
    public static class SortByTotalHRDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return o2.getTotalHR() - o1.getTotalHR();
        }
    }
    /**
     * SortByZoneSwing asc.
     */
    public static class SortByZoneSwing implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o1.getZoneSwing(), o2.getZoneSwing());
        }
    }
    /**
     * SortByZoneSwing desc.
     */
    public static class SortByZoneSwingDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o2.getZoneSwing(), o1.getZoneSwing());
        }
    }
    /**
     * SortByZoneContact asc.
     */
    public static class SortByZoneContact implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o1.getZoneContact(), o2.getZoneContact());
        }
    }
    /**
     * SortByZoneContact desc.
     */
    public static class SortByZoneContactDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o2.getZoneContact(), o1.getZoneContact());
        }
    }
    /**
     * SortByChaseSwing asc.
     */
    public static class SortByChaseSwing implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o1.getChaseSwing(), o2.getChaseSwing());
        }
    }
    /**
     * SortByChaseSwing desc.
     */
    public static class SortByChaseSwingDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o2.getChaseSwing(), o1.getChaseSwing());
        }
    }
    /**
     * SortByChaseSwing asc.
     */
    public static class SortByChaseContact implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o1.getChaseContact(), o2.getChaseContact());
        }
    }
    /**
     * SortByChaseSwing desc.
     */
    public static class SortByChaseContactDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o2.getChaseContact(), o1.getChaseContact());
        }
    }
    /**
     * SortByAVG asc.
     */
    public static class SortByAVG implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o1.getAVG(), o2.getAVG());
        }
    }
    /**
     * SortByAVG desc.
     */
    public static class SortByAVGDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o2.getAVG(), o1.getAVG());
        }
    }
    /**
     * SortByOBP asc.
     */
    public static class SortByOBP implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o1.getOBP(), o2.getOBP());
        }
    }
    /**
     * SortByOBP desc.
     */
    public static class SortByOBPDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o2.getOBP(), o1.getOBP());
        }
    }
    /**
     * SortByOPS asc.
     */
    public static class SortByOPS implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o1.getOPS(), o2.getOPS());
        }
    }
    /**
     * SortByOPS desc.
     */
    public static class SortByOPSDesc implements Comparator<Batter> {
        @Override
        public int compare(Batter o1, Batter o2) {
            return Double.compare(o2.getOPS(), o1.getOPS());
        }
    }

    /**
     * SortByPitcherName asc.
     */
    public static class SortByPitcherName implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }
    /**
     * SortByPitcherName desc.
     */
    public static class SortByPitcherNameDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return o2.getName().compareToIgnoreCase(o1.getName());
        }
    } 
    /**
     * SortByRotation asc.
     */
    public static class SortByRotation implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return o1.getRotation() - o2.getRotation();
        }
    }
    /**
     * SortByRotation desc.
     */
    public static class SortByRotationDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return o2.getRotation() - o1.getRotation();
        }
    }
    /**
     * SortByStrikes asc.
     */
    public static class SortByStrikes implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return o1.getStrikes() - o2.getStrikes();
        }
    }
    /**
     * SortByStrikes desc.
     */
    public static class SortByStrikesDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return o2.getStrikes() - o1.getStrikes();
        }
    }
    /**
     * SortByPitches asc.
     */
    public static class SortByPitches implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return o1.getPitches() - o2.getPitches();
        }
    }
    /**
     * SortByPitches desc.
     */
    public static class SortByPitchesDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return o2.getPitches() - o1.getPitches();
        }
    }
    /**
     * SortByStrikesRate asc.
     */
    public static class SortByStrikesRate implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getStrikesRate(), o2.getStrikesRate());
        }
    }
    /**
     * SortByStrikesRate desc.
     */
    public static class SortByStrikesRateDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getStrikesRate(), o1.getStrikesRate());
        }
    }
    /**
     * SortByBallsRate asc.
     */
    public static class SortByBallsRate implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getBallsRate(), o2.getBallsRate());
        }
    }
    /**
     * SortByBallsRate desc.
     */
    public static class SortByBallsRateDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getBallsRate(), o1.getBallsRate());
        }
    }
    /**
     * SortByFourSeam asc.
     */
    public static class SortByFourSeam implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getFourSeam(), o2.getFourSeam());
        }
    }
    /**
     * SortByFourSeam desc.
     */
    public static class SortByFourSeamDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getFourSeam(), o1.getFourSeam());
        }
    }
    /**
     * SortByTwoSeam asc.
     */
    public static class SortByTwoSeam implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getTwoSeam(), o2.getTwoSeam());
        }
    }
    /**
     * SortByTwoSeam desc.
     */
    public static class SortByTwoSeamDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getTwoSeam(), o1.getTwoSeam());
        }
    }
    /**
     * SortByCutter asc.
     */
    public static class SortByCutter implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getCutter(), o2.getCutter());
        }
    }
    /**
     * SortByCutter desc.
     */
    public static class SortByCutterDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getCutter(), o1.getCutter());
        }
    }
    /**
     * SortBySinker asc.
     */
    public static class SortBySinker implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getSinker(), o2.getSinker());
        }
    }
    /**
     * SortBySinker desc.
     */
    public static class SortBySinkerDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getSinker(), o1.getSinker());
        }
    }
    /**
     * SortBySlider asc.
     */
    public static class SortBySlider implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getSlider(), o2.getSlider());
        }
    }
    /**
     * SortBySlider desc.
     */
    public static class SortBySliderDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getSlider(), o1.getSlider());
        }
    }
    /**
     * SortByCurveball asc.
     */
    public static class SortByCurveball implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getCurve(), o2.getCurve());
        }
    }
    /**
     * SortByCurveball desc.
     */
    public static class SortByCurveballDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getCurve(), o1.getCurve());
        }
    }
    /**
     * SortByKnuckle asc.
     */
    public static class SortByKnuckle implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getKnuckle(), o2.getKnuckle());
        }
    }
    /**
     * SortByKnuckle desc.
     */
    public static class SortByKnuckleDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getKnuckle(), o1.getKnuckle());
        }
    }
    /**
     * SortBySweeper asc.
     */
    public static class SortBySweeper implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getSweeper(), o2.getSweeper());
        }
    }
    /**
     * SortBySweeper desc.
     */
    public static class SortBySweeperDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getSweeper(), o1.getSweeper());
        }
    }
    /**
     * SortBySlurve asc.
     */
    public static class SortBySlurve implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getSlurve(), o2.getSlurve());
        }
    }
    /**
     * SortBySlurve desc.
     */
    public static class SortBySlurveDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getSlurve(), o1.getSlurve());
        }
    }
    /**
     * SortBySFinger asc.
     */
    public static class SortBySFinger implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getSplitFinger(), o2.getSplitFinger());
        }
    }
    /**
     * SortBySFinger desc.
     */
    public static class SortBySFingerDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getSplitFinger(), o1.getSplitFinger());
        }
    }
    /**
     * SortByChangeup asc.
     */
    public static class SortByChangeup implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getChangeup(), o2.getChangeup());
        }
    }
    /**
     * SortByChangeup desc.
     */
    public static class SortByChangeupDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getChangeup(), o1.getChangeup());
        }
    }
    /**
     * SortByFork asc.
     */
    public static class SortByFork implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getFork(), o2.getFork());
        }
    }
    /**
     * SortByFork desc.
     */
    public static class SortByForkDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getFork(), o1.getFork());
        }
    }
    /**
     * SortByScrew asc.
     */
    public static class SortByScrew implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o1.getScrew(), o2.getScrew());
        }
    }
    /**
     * SortByScrew desc.
     */
    public static class SortByScrewDesc implements Comparator<Pitcher> {
        @Override
        public int compare(Pitcher o1, Pitcher o2) {
            return Double.compare(o2.getScrew(), o1.getScrew());
        }
    }

}
