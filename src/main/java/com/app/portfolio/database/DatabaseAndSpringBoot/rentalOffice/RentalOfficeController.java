package com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice;

import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.api.RentallOfficeApiController;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.RentalOfficeDto;
import com.app.portfolio.database.DatabaseAndSpringBoot.rentalOffice.model.RentalOfficeListDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@RestController
public class RentalOfficeController extends RentallOfficeApiController {

    private final RentalOfficeManager rentalOfficeManager;
    private final RentalOfficeMapperImpl rentalOfficeMapper;

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
        return ResponseEntity.ok(
                rentalOfficeMapper.mapToRentalOfficeDTO(rentalOfficeManager.save(rentalOfficeMapper.mapToRentalOffice(body)))
        );
    }

    @Override
    public ResponseEntity<Boolean> deleteRentalOffice(@PathVariable("id") final String id) {
        if (!isNotBlank(id)) return ResponseEntity.notFound().build();
        rentalOfficeManager.delete(Long.valueOf(id));
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<RentalOfficeListDto> listRentalOffice() {
        final RentalOfficeListDto rentalOfficeDTOS = new RentalOfficeListDto();
        rentalOfficeDTOS.addAll(rentalOfficeManager.findAll().stream().map(rentalOfficeMapper::mapToRentalOfficeDTO).toList());
        return ResponseEntity.ok(rentalOfficeDTOS);
    }

    @Override
    public ResponseEntity<RentalOfficeDto> updateRentalOffice(@PathVariable("id") final String id, @RequestBody final RentalOfficeDto body) {
        return ResponseEntity.ok(
                rentalOfficeMapper.mapToRentalOfficeDTO(rentalOfficeManager.save(rentalOfficeMapper.mapToRentalOffice(body)))
        );
    }


}
