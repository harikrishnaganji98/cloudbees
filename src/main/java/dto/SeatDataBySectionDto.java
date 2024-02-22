package dto;

import com.cloudbees.enums.SeatStatus;
import com.cloudbees.enums.Section;

public class SeatDataBySectionDto {
    private int seatNumber;
    private SeatStatus seatStatus;
    private Section section;

    public SeatDataBySectionDto(int seatNumber, SeatStatus seatStatus, Section section) {
        this.seatNumber = seatNumber;
        this.seatStatus = seatStatus;
        this.section = section;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatStatus getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}

