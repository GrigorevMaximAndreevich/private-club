package com.github.grigorevmaximandreevich.privateclub.controller;

import com.github.grigorevmaximandreevich.privateclub.model.dto.GuestBriefDto;
import com.github.grigorevmaximandreevich.privateclub.model.dto.QRCodeCreateRequest;
import com.github.grigorevmaximandreevich.privateclub.model.dto.QRCodeDto;
import com.github.grigorevmaximandreevich.privateclub.service.QRCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/qrcodes")
public class QRCodeController {

    private final QRCodeService qrCodeService;
    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping
    public List<QRCodeDto> getAllQRCodes() {
        return qrCodeService.findAll();
    }

    @GetMapping("/{id}")
    public QRCodeDto getQRCodeById(@PathVariable Long id) {
        return qrCodeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QRCodeDto createQRCode(@RequestBody QRCodeCreateRequest request) {
        return qrCodeService.create(request.guestId());
    }

    @PostMapping("/access")
    public GuestBriefDto enterByQrCode(@RequestParam UUID code) {
        return qrCodeService.enterByQrCode(code);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQRCode(@PathVariable Long id) {
        qrCodeService.delete(id);
    }
}
