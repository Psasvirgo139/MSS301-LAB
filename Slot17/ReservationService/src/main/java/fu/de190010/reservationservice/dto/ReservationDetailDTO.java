package fu.de190010.reservationservice.dto;

public class ReservationDetailDTO extends ReservationDTO {
    private RoomDTO room;

    public ReservationDetailDTO() {
        super();
    }

    public RoomDTO getRoom() { return room; }
    public void setRoom(RoomDTO room) { this.room = room; }
}
