package com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.api.RentallOfficeApiController;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.RentalOfficeDto;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.RentalOfficeListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RentalOfficeController extends RentallOfficeApiController {

    final private RentalOfficeManager rentalOfficeManager;
    final private RentalOfficeMapperImpl rentalOfficeMapper;

    public RentalOfficeController(final ObjectMapper objectMapper, final HttpServletRequest request, final RentalOfficeManager rentalOfficeManager, final RentalOfficeMapperImpl rentalOfficeMapper) {
        super(objectMapper, request);
        this.rentalOfficeManager = rentalOfficeManager;
        this.rentalOfficeMapper = rentalOfficeMapper;
    }


    @Override
    public ResponseEntity<RentalOfficeDto> rentalOffice(@PathVariable("id") final String id) {
        return ResponseEntity.of(rentalOfficeManager.findById(Long.valueOf(id)).map(rentalOfficeMapper::mapToRentalOfficeDTO));
    }

    @Override
    public ResponseEntity<RentalOfficeDto> createRentalOffice(@RequestBody final RentalOfficeDto body) {
        return new ResponseEntity<>(
                rentalOfficeMapper.mapToRentalOfficeDTO(rentalOfficeManager.save(rentalOfficeMapper.mapToRentalOffice(body))), HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Boolean> deleteRentalOffice(@PathVariable("id") final String id) {
        if (id == null) return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        rentalOfficeManager.delete(Long.valueOf(id));
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RentalOfficeListDto> listRentalOffice() {
        RentalOfficeListDto rentalOfficeDTOS = new RentalOfficeListDto();
        for (RentalOffice rentalOfficeDAO : rentalOfficeManager.findAll()) {
            rentalOfficeDTOS.add(rentalOfficeMapper.mapToRentalOfficeDTO(rentalOfficeDAO));
        }
        return new ResponseEntity<>(rentalOfficeDTOS, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RentalOfficeDto> updateRentalOffice(@PathVariable("id") final String id, @RequestBody final RentalOfficeDto body) {
        RentalOffice rentalOfficeFromRequest = rentalOfficeMapper.mapToRentalOffice(body);
        return new ResponseEntity<>(
                rentalOfficeMapper.mapToRentalOfficeDTO(
                        rentalOfficeManager.findById(Long.valueOf(id))
                                .map(rentalOfficeManager::save)
                                .orElse(
                                        rentalOfficeManager.update(rentalOfficeFromRequest)
                                                .orElse(null)
                                )
                ), HttpStatus.OK
        );


    }


}
