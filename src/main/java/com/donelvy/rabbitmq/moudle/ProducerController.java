package com.donelvy.rabbitmq.moudle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : mawei
 * @description : TODO
 * @since : 2020-03-13 18:28
 */
@RestController
@RequestMapping("/api")
public class ProducerController {

    @Autowired
    private Producer producer;

    @PostMapping("/produce")
    public ResponseEntity<Boolean> produce(@RequestBody String content){
//        producer.produceDIRECT(content);
//        producer.produceFanout(content);
        producer.produceTopic(content);
        return ResponseEntity.ok(true);
    }

}
