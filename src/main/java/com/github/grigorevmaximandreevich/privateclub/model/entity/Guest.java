package com.github.grigorevmaximandreevich.privateclub.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guests")
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QRCode> qrCodes = new ArrayList<>();

    public Guest() {
    }

    public void addQrCode(QRCode qrCode) {
        qrCodes.add(qrCode);
        qrCode.setGuest(this);
    }

    public void removeQrCode(QRCode qrCode) {
        qrCodes.remove(qrCode);
        qrCode.setGuest(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public List<QRCode> getQrCodes() {
        return qrCodes;
    }

    public void setQrCodes(List<QRCode> qrCodes) {
        this.qrCodes = qrCodes;
    }
}
