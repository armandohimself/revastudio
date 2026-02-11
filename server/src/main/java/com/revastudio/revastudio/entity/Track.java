package com.revastudio.revastudio.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Track")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    @Id
    @Column(name = "TrackId")
    private UUID trackId;

    @Column(name = "Name")
    private String name;

    @Column(name = "AlbumId")
    private UUID albumId;

    @Column(name = "MediaTypeId")
    private UUID mediaTypeId;

    @Column(name = "GenreId")
    private UUID genreId;

    @Column(name = "Composer")
    private String composer;

    @Column(name = "Milliseconds")
    private Number milliseconds;

    @Column(name = "Bytes")
    private Number bytes;

    @Column(name = "UnitPrice")
    private Number unitPrice;
}
