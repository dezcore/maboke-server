package com.zcore.mabokeserver.nomatch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import com.zcore.mabokeserver.season.Season;
import com.zcore.mabokeserver.video.Video;

import reactor.core.publisher.Mono;

@Service
public class NomatchService {
    @Autowired
    private NomatchRepository nomatchRepository;
    private Logger logger = LoggerFactory.getLogger(NomatchService.class);

    public  Nomatch videoToNomatch(Video video) {
        Season season; 
        List<Video> videos;
        List<Season> seasons;
        Nomatch serie = new Nomatch();

        if(video != null) {
            season = new Season();
            videos = new ArrayList<>();
            seasons = new ArrayList<>();

            videos.add(video);
            season.setTitle(video.getTitle());
            season.setImg(video.getImg());
            season.setNumber(1);
            season.setSummary("");
            season.setDate(LocalDateTime.now());
            season.setVideos(videos);

            serie.setTitle(video.getTitle());
            serie.setImg(video.getImg());
            serie.setHide(true);
            serie.setYear(LocalDateTime.now());
            seasons.add(season);
            serie.setSeasons(seasons);
        }

        return serie;
    }
    
    public Mono<ResponseEntity<Nomatch>> add(Video video) {
        Nomatch nomatch = this.videoToNomatch(video);
        return this.nomatchRepository.findByTitle(video.getTitle())
            .flatMap(nomatch1 -> {
                return nomatchRepository.save(nomatch)
            .map(nomatch2 -> new ResponseEntity<>(nomatch2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Page<Nomatch>> getNoMatchSerie(Pageable paging) {
        return this.nomatchRepository.count()
            .flatMap(serieCount -> {
                return this.nomatchRepository.findAll()
                    .skip((paging.getPageNumber()-1) * paging.getPageSize())
                    .take(paging.getPageSize())
                    .collectList().map(series -> new PageImpl<Nomatch>(series, paging, serieCount));
        });
    }

    public Mono<ResponseEntity<Nomatch>> findByTitle(String title) {
        return this.nomatchRepository.findByTitle(title)
        .map(serie -> new ResponseEntity<>(serie, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Nomatch>> updateNomatch(Nomatch serie) {
        String id = serie.getId();

        return this.nomatchRepository.findById(serie.getId())
            .flatMap(serie1 -> {
                serie.setId(id);
                return nomatchRepository.save(serie)
            .map(serie2 -> new ResponseEntity<>(serie2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Void> deleteNomatch(String id) {
        return this.nomatchRepository.deleteById(id);
    }
}
