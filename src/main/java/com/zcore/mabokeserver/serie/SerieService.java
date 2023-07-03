package com.zcore.mabokeserver.serie;

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

import com.zcore.mabokeserver.season.Season;
import com.zcore.mabokeserver.video.Video;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SerieService {
    @Autowired
    private SerieRepository serieRepository;
    private Logger logger = LoggerFactory.getLogger(SerieService.class);

     public Serie videoToSerie(Video video) {
        Season season; 
        List<Video> videos;
        List<Season> seasons;
        Serie serie = new Serie();

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
    
    public Mono<ResponseEntity<Serie>> add(Serie serie) {
        return serieRepository.save(serie).map(serie1 -> new ResponseEntity<>(serie1, HttpStatus.ACCEPTED))
        .defaultIfEmpty(new ResponseEntity<>(serie, HttpStatus.NOT_ACCEPTABLE));
    }

    public Mono<Page<Serie>> getSeries(Pageable paging, String state) {
        return this.serieRepository.countByState(state)
            .flatMap(serieCount -> {
                return this.serieRepository.findAllByState(state)
                    .skip((paging.getPageNumber()-1) * paging.getPageSize())
                    .take(paging.getPageSize())
                    .collectList().map(series -> new PageImpl<Serie>(series, paging, serieCount));
        });
    }

    public Mono<Page<Serie>> getSeriesByCategories(Pageable paging) {
        return this.serieRepository.countByCategoryIsNot("Default")
            .flatMap(serieCount -> {
                return this.serieRepository.findByCategoryIsNot("Default")
                    .skip((paging.getPageNumber()-1) * paging.getPageSize())
                    .take(paging.getPageSize())
                    .collectList().map(series -> new PageImpl<Serie>(series, paging, serieCount));
        });
    }
    
    public Mono<ResponseEntity<Serie>> findById(String id) {
        return serieRepository.findById(id)
        .map(serie -> new ResponseEntity<>(serie, HttpStatus.OK))
        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ResponseEntity<Serie>> updateSerie(Serie serie) {
        String id = serie.getId();

        return this.serieRepository.findById(serie.getId())
            .flatMap(serie1 -> {
                serie.setId(id);
                return serieRepository.save(serie)
            .map(serie2 -> new ResponseEntity<>(serie2, HttpStatus.ACCEPTED));
        }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    public Flux<Serie> findByCategory(String category) {
        return this.serieRepository.findByCategory(category);
    }

    public Mono<Void> deleteSerie(String id) {
        return serieRepository.deleteById(id);
    }
}
