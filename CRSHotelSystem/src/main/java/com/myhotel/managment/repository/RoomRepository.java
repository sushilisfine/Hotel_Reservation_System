package com.myhotel.managment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhotel.managment.domain.Hotel;
import com.myhotel.managment.domain.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

	Room findByRoomCodeAndHotel(Integer roomCode, Hotel hotel);

	List<Room> findAllByHotel(Hotel hotel);

	Room findByRoomCode(Integer roomCode);
}