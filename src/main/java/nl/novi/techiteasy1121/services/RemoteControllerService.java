package nl.novi.techiteasy1121.services;

import nl.novi.techiteasy1121.dtos.RemoteControllerDto;
import nl.novi.techiteasy1121.dtos.RemoteControllerInputDto;
import nl.novi.techiteasy1121.exceptions.RecordNotFoundException;
import nl.novi.techiteasy1121.models.RemoteController;
import nl.novi.techiteasy1121.repositories.RemoteControllerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Zet de annotatie boven de klasse, zodat Spring het herkent en inleest als Service.
@Service
public class RemoteControllerService {

    // We importeren de repository nu in de service in plaats van in de controller.
    // dit mag met constructor injection of autowire.
    private final RemoteControllerRepository remoteControllerRepository;

    public RemoteControllerService(RemoteControllerRepository remoteControllerRepository){
        this.remoteControllerRepository = remoteControllerRepository;
    }

    // Vanuit de repository kunnen we een lijst van RemoteControllers krijgen, maar de communicatie container tussen Service en
    // Controller is de Dto. We moeten de RemoteControllers dus vertalen naar RemoteControllerDtos. Dit moet een voor een, omdat
    // de translateToDto() methode geen lijst accepteert als argument, dus gebruiken we een for-loop.
    public List<RemoteControllerDto> getAllRemoteControllers() {
        List<RemoteController> tvList = remoteControllerRepository.findAll();
        List<RemoteControllerDto> tvDtoList = new ArrayList<>();

        for(RemoteController tv : tvList) {
            RemoteControllerDto dto = transferToDto(tv);
            tvDtoList.add(dto);
        }
        return tvDtoList;
    }

    // Vanuit de repository kunnen we een lijst van RemoteControllers met een bepaalde brand krijgen, maar de communicatie
    // container tussen Service en Controller is de Dto. We moeten de RemoteControllers dus vertalen naar RemoteControllerDtos. Dit
    // moet een voor een, omdat de translateToDto() methode geen lijst accepteert als argument, dus gebruiken we een for-loop.
    public List<RemoteControllerDto> getAllRemoteControllersByBrand(String brand) {
        List<RemoteController> tvList = remoteControllerRepository.findAllRemoteControllersByBrandEqualsIgnoreCase(brand);
        List<RemoteControllerDto> tvDtoList = new ArrayList<>();

        for(RemoteController tv : tvList) {
            RemoteControllerDto dto = transferToDto(tv);
            tvDtoList.add(dto);
        }
        return tvDtoList;
    }

    // Deze methode is inhoudelijk hetzelfde als het was in de vorige opdracht. Wat verandert is, is dat we nu checken
    // op optional.isPresent in plaats van optional.isEmpty en we returnen een RemoteControllerDto in plaats van een RemoteController.
    public RemoteControllerDto getRemoteControllerById(Long id) {
        Optional<RemoteController> remoteControllerOptional = remoteControllerRepository.findById(id);
        if (remoteControllerOptional.isPresent()){
            RemoteController tv = remoteControllerOptional.get();
            return transferToDto(tv);
        } else {
            throw new RecordNotFoundException("geen remote controller gevonden");
        }
    }

    // In deze methode moeten we twee keer een vertaal methode toepassen.
    // De eerste keer van dto naar remote controller omdat de parameter een dto is.
    // De tweede keer van remoteController naar dto, omdat de return waarde een dto is.
    public RemoteControllerDto addRemoteController(RemoteControllerInputDto dto) {

        RemoteController tv = transferToRemoteController(dto);
        remoteControllerRepository.save(tv);

        return transferToDto(tv);
    }

    // Deze methode is inhoudelijk neit veranderd. Het is alleen verplaatst naar de Service laag.
    public void deleteRemoteController(@RequestBody Long id) {

        remoteControllerRepository.deleteById(id);

    }

    // Deze methode is inhoudelijk niet veranderd, alleen staat het nu in de Service laag en worden er Dto's en
    // vertaal methodes gebruikt.
    public RemoteControllerDto updateRemoteController(Long id, RemoteControllerInputDto newRemoteController) {

        Optional<RemoteController> remoteControllerOptional = remoteControllerRepository.findById(id);
        if (remoteControllerOptional.isPresent()){

            RemoteController remoteController1 = remoteControllerOptional.get();


            remoteController1.setAmbiLight(newRemoteController.getAmbiLight());
            remoteController1.setAvailableSize(newRemoteController.getAvailableSize());
            remoteController1.setAmbiLight(newRemoteController.getAmbiLight());
            remoteController1.setBluetooth(newRemoteController.getBluetooth());
            remoteController1.setBrand(newRemoteController.getBrand());
            remoteController1.setHdr(newRemoteController.getHdr());
            remoteController1.setName(newRemoteController.getName());
            remoteController1.setOriginalStock(newRemoteController.getOriginalStock());
            remoteController1.setPrice(newRemoteController.getPrice());
            remoteController1.setRefreshRate(newRemoteController.getRefreshRate());
            remoteController1.setScreenQuality(newRemoteController.getScreenQuality());
            remoteController1.setScreenType(newRemoteController.getScreenType());
            remoteController1.setSmartTv(newRemoteController.getSmartTv());
            remoteController1.setSold(newRemoteController.getSold());
            remoteController1.setType(newRemoteController.getType());
            remoteController1.setVoiceControl(newRemoteController.getVoiceControl());
            remoteController1.setWifi(newRemoteController.getWifi());
            RemoteController returnRemoteController = remoteControllerRepository.save(remoteController1);

            return transferToDto(returnRemoteController);

        } else {

            throw new  RecordNotFoundException("geen remote controllergevonden");

        }

    }

    // Dit is de vertaal methode van RemoteControllerInputDto naar RemoteController.
    public RemoteController transferToRemoteController(RemoteControllerInputDto dto){
        var remoteController = new RemoteController();

        remoteController.setType(dto.getType());
        remoteController.setBrand(dto.getBrand());
        remoteController.setName(dto.getName());
        remoteController.setPrice(dto.getPrice());
        remoteController.setAvailableSize(dto.getAvailableSize());
        remoteController.setRefreshRate(dto.getRefreshRate());
        remoteController.setScreenType(dto.getScreenType());
        remoteController.setScreenQuality(dto.getScreenQuality());
        remoteController.setSmartTv(dto.getSmartTv());
        remoteController.setWifi(dto.getWifi());
        remoteController.setVoiceControl(dto.getVoiceControl());
        remoteController.setHdr(dto.getHdr());
        remoteController.setBluetooth(dto.getBluetooth());
        remoteController.setAmbiLight(dto.getAmbiLight());
        remoteController.setOriginalStock(dto.getOriginalStock());
        remoteController.setSold(dto.getSold());

        return remoteController;
    }

    // Dit is de vertaal methode van RemoteController naar RemoteControllerDto
    public RemoteControllerDto transferToDto(RemoteController remoteController){
        RemoteControllerDto dto = new RemoteControllerDto();

        dto.setId(remoteController.getId());
        dto.setType(remoteController.getType());
        dto.setBrand(remoteController.getBrand());
        dto.setName(remoteController.getName());
        dto.setPrice(remoteController.getPrice());
        dto.setAvailableSize(remoteController.getAvailableSize());
        dto.setRefreshRate(remoteController.getRefreshRate());
        dto.setScreenType(remoteController.getScreenType());
        dto.setScreenQuality(remoteController.getScreenQuality());
        dto.setSmartTv(remoteController.getWifi());
        dto.setWifi(remoteController.getWifi());
        dto.setVoiceControl(remoteController.getVoiceControl());
        dto.setHdr(remoteController.getHdr());
        dto.setBluetooth(remoteController.getBluetooth());
        dto.setAmbiLight(remoteController.getAmbiLight());
        dto.setOriginalStock(remoteController.getOriginalStock());
        dto.setSold(remoteController.getSold());

        return dto;
    }
}
