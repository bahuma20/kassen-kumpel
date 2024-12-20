# KassenKumpel

Digitales Kassensystem für den Point-of-Sale bei Veranstaltungen.

Android App ohne Cloud-Anbindung, die alle Daten lokal speichert.

## Projektstatus

Dieses Projekt wird aktiv entwickelt und befindet sich im Moment in einer frühen Entwicklungsphase.

## Features (umgesetzt und geplant)

- [x] Verwaltung von Produkten
    - [x] Produkte hinzufügen
    - [x] Produkte bearbeiten
    - [x] Produkte löschen
    - [x] Produkte mit Aufpreis (z.B. Pfand)
    - [x] Produkte mit negativen Preisen (z.B. Pfandrückgabe, Rabatte)
- [x] Verwaltung von Kategorien
    - [x] Kategorien hinzufügen
    - [x] Kategorien bearbeiten
    - [x] Kategorien löschen
    - [x] Icons von Kategorien auswählen
    - [x] Produkte Kategorien zuweisen
- [ ] Point of Sale
    - [x] Auswahl der Produkte
    - [x] Berechnung des Gesamtpreises im Warenkorb
    - [x] Entfernen einzelner Produkte aus dem Warenkorb
    - [x] Entfernen aller Produkte aus dem Warenkorb
    - [x] Bearbeiten der Anzahl der Produkte im Warenkorb
    - [x] Produkte nach Kategorie filtern
    - [ ] Hinzufügen von Individuellen Beträgen (ohne Produkt)
        - [ ] Kommentar für individuelle Beträge
        - [ ] Negative individuelle Beträge
- [x] Barzahlung mit Rückgeldberechnung
- [ ] Kartenzahlung mit SumUp Geräten
    - [x] Starten des Bezahlvorgangs vom Point of Sale aus
    - [x] Speichern der Transaktion mit SumUp Transaktions-ID
    - [x] Zahlungs-Button abhängig vom Login-Status (+ Error-Handling bei abgelaufenen Tokens)
    - [ ] Einstellungs-Seite
        - [ ] Login per OAuth
    - [ ] Rückbuchung von Kartenzahlungen
- [ ] Anschreiben (Gesamtzahlung am Ende einer Veranstaltung)
    - [ ] Anschreibungs-Konten anlegen
    - [ ] Warenkorb in Anschreibungs-Konto transferieren
    - [ ] Anschreibungs-Konten bezahlen
    - [ ] Detailansicht Anschreibungs-Konto nach Transaktion
    - [ ] Detailansicht Anschreibungs-Konto Gesamt
- [x] Speichern aller Transaktionen
    - [x] Export der Transaktionen als CSV
    - [ ] Statistik über gekaufte Produkte
- [ ] Quittung für Transaktionen (als PDF per E-Mail oder auf dem Gerät gespeichert)
- [ ] Konfiguration der aktivierten Features (Barzahlung, Kartenzahlung, Anschreiben)

## Screenshots

![point-of-sale-logged-out.jpg](docs/images/point-of-sale-logged-out.jpg)

![point-of-sale.jpg](docs/images/point-of-sale.jpg)

![cash-payment.jpg](docs/images/cash-payment.jpg)

![navigation.jpg](docs/images/products.jpg)

![products.jpg](docs/images/products.jpg)

![edit-product.jpg](docs/images/edit-product.jpg)

![categories.jpg](docs/images/categories.jpg)

![transactions.jpg](docs/images/transactions.jpg)

## Technologie

Android mit Kotlin, Jetpack Compose, Room, Hilt und Material 3

## Testabdeckung

Im Moment gibt es keinerlei automatisierte Tests der App.

## Contributions

Sind gerne gesehen. Einfach nen PR stellen.