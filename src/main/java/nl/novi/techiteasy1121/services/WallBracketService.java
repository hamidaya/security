package nl.novi.techiteasy1121.services;

import nl.novi.techiteasy1121.dtos.WallBracketDto;
import nl.novi.techiteasy1121.dtos.WallBracketInputDto;
import nl.novi.techiteasy1121.exceptions.RecordNotFoundException;
import nl.novi.techiteasy1121.models.WallBracket;
import nl.novi.techiteasy1121.repositories.WallBracketRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Zet de annotatie boven de klasse, zodat Spring het herkent en inleest als Service.
@Service
public class WallBracketService {

    // We importeren de repository nu in de service in plaats van in de controller.
    // dit mag met constructor injection of autowire.
    private final WallBracketRepository wallBracketRepository;

    public WallBracketService(WallBracketRepository wallBracketRepository){
        this.wallBracketRepository = wallBracketRepository;
    }

    // Vanuit de repository kunnen we een lijst van WallBrackets krijgen, maar de communicatie container tussen Service en
    // Controller is de Dto. We moeten de WallBrackets dus vertalen naar WallBracketDtos. Dit moet een voor een, omdat
    // de translateToDto() methode geen lijst accepteert als argument, dus gebruiken we een for-loop.
    public List<WallBracketDto> getAllWallBrackets() {
        List<WallBracket> tvList = wallBracketRepository.findAll();
        List<WallBracketDto> tvDtoList = new ArrayList<>();

        for(WallBracket tv : tvList) {
            WallBracketDto dto = transferToDto(tv);
            tvDtoList.add(dto);
        }
        return tvDtoList;
    }

    // Vanuit de repository kunnen we een lijst van WallBrackets met een bepaalde brand krijgen, maar de communicatie
    // container tussen Service en Controller is de Dto. We moeten de WallBrackets dus vertalen naar WallBracketDtos. Dit
    // moet een voor een, omdat de translateToDto() methode geen lijst accepteert als argument, dus gebruiken we een for-loop.
    public List<WallBracketDto> getAllWallBracketsByBrand(String brand) {
        List<WallBracket> tvList = wallBracketRepository.findAllWallBracketsByBrandEqualsIgnoreCase(brand);
        List<WallBracketDto> tvDtoList = new ArrayList<>();

        for(WallBracket tv : tvList) {
            WallBracketDto dto = transferToDto(tv);
            tvDtoList.add(dto);
        }
        return tvDtoList;
    }

    // Deze methode is inhoudelijk hetzelfde als het was in de vorige opdracht. Wat verandert is, is dat we nu checken
    // op optional.isPresent in plaats van optional.isEmpty en we returnen een WallBracketDto in plaats van een WallBracket.
    public WallBracketDto getWallBracketById(Long id) {
        Optional<WallBracket> wallBracketOptional = wallBracketRepository.findById(id);
        if (wallBracketOptional.isPresent()){
            WallBracket tv = wallBracketOptional.get();
            return transferToDto(tv);
        } else {
            throw new RecordNotFoundException("geen wallBracket gevonden");
        }
    }

    // In deze methode moeten we twee keer een vertaal methode toepassen.
    // De eerste keer van dto naar remote controller omdat de parameter een dto is.
    // De tweede keer van wallBracket naar dto, omdat de return waarde een dto is.
    public WallBracketDto addWallBracket(WallBracketInputDto dto) {

        WallBracket tv = transferToWallBracket(dto);
        wallBracketRepository.save(tv);

        return transferToDto(tv);
    }

    // Deze methode is inhoudelijk neit veranderd. Het is alleen verplaatst naar de Service laag.
    public void deleteWallBracket(@RequestBody Long id) {

        wallBracketRepository.deleteById(id);

    }

    // Deze methode is inhoudelijk niet veranderd, alleen staat het nu in de Service laag en worden er Dto's en
    // vertaal methodes gebruikt.
    public WallBracketDto updateWallBracket(Long id, WallBracketInputDto newWallBracket) {

        Optional<WallBracket> wallBracketOptional = wallBracketRepository.findById(id);
        if (wallBracketOptional.isPresent()){

            WallBracket wallBracket1 = wallBracketOptional.get();


            wallBracket1.setAmbiLight(newWallBracket.getAmbiLight());
            wallBracket1.setAvailableSize(newWallBracket.getAvailableSize());
            wallBracket1.setAmbiLight(newWallBracket.getAmbiLight());
            wallBracket1.setBluetooth(newWallBracket.getBluetooth());
            wallBracket1.setBrand(newWallBracket.getBrand());
            wallBracket1.setHdr(newWallBracket.getHdr());
            wallBracket1.setName(newWallBracket.getName());
            wallBracket1.setOriginalStock(newWallBracket.getOriginalStock());
            wallBracket1.setPrice(newWallBracket.getPrice());
            wallBracket1.setRefreshRate(newWallBracket.getRefreshRate());
            wallBracket1.setScreenQuality(newWallBracket.getScreenQuality());
            wallBracket1.setScreenType(newWallBracket.getScreenType());
            wallBracket1.setSmartTv(newWallBracket.getSmartTv());
            wallBracket1.setSold(newWallBracket.getSold());
            wallBracket1.setType(newWallBracket.getType());
            wallBracket1.setVoiceControl(newWallBracket.getVoiceControl());
            wallBracket1.setWifi(newWallBracket.getWifi());
            WallBracket returnWallBracket = wallBracketRepository.save(wallBracket1);

            return transferToDto(returnWallBracket);

        } else {

            throw new  RecordNotFoundException("geen wallBracketgevonden");

        }

    }

    // Dit is de vertaal methode van WallBracketInputDto naar WallBracket.
    public WallBracket transferToWallBracket(WallBracketInputDto dto){
        var wallBracket = new WallBracket();

        wallBracket.setType(dto.getType());
        wallBracket.setBrand(dto.getBrand());
        wallBracket.setName(dto.getName());
        wallBracket.setPrice(dto.getPrice());
        wallBracket.setAvailableSize(dto.getAvailableSize());
        wallBracket.setRefreshRate(dto.getRefreshRate());
        wallBracket.setScreenType(dto.getScreenType());
        wallBracket.setScreenQuality(dto.getScreenQuality());
        wallBracket.setSmartTv(dto.getSmartTv());
        wallBracket.setWifi(dto.getWifi());
        wallBracket.setVoiceControl(dto.getVoiceControl());
        wallBracket.setHdr(dto.getHdr());
        wallBracket.setBluetooth(dto.getBluetooth());
        wallBracket.setAmbiLight(dto.getAmbiLight());
        wallBracket.setOriginalStock(dto.getOriginalStock());
        wallBracket.setSold(dto.getSold());

        return wallBracket;
    }

    // Dit is de vertaal methode van WallBracket naar WallBracketDto
    public WallBracketDto transferToDto(WallBracket wallBracket){
        WallBracketDto dto = new WallBracketDto();

        dto.setId(wallBracket.getId());
        dto.setType(wallBracket.getType());
        dto.setBrand(wallBracket.getBrand());
        dto.setName(wallBracket.getName());
        dto.setPrice(wallBracket.getPrice());
        dto.setAvailableSize(wallBracket.getAvailableSize());
        dto.setRefreshRate(wallBracket.getRefreshRate());
        dto.setScreenType(wallBracket.getScreenType());
        dto.setScreenQuality(wallBracket.getScreenQuality());
        dto.setSmartTv(wallBracket.getWifi());
        dto.setWifi(wallBracket.getWifi());
        dto.setVoiceControl(wallBracket.getVoiceControl());
        dto.setHdr(wallBracket.getHdr());
        dto.setBluetooth(wallBracket.getBluetooth());
        dto.setAmbiLight(wallBracket.getAmbiLight());
        dto.setOriginalStock(wallBracket.getOriginalStock());
        dto.setSold(wallBracket.getSold());

        return dto;
    }
}
